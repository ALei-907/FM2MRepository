### ForkJoinWorkerThread#run()

```java
    public void run() {
        // 只run一次。array用于存放FJT，那么这里用它作为标识，来确保FJWT只run一次
        if (workQueue.array == null) { 
            Throwable exception = null;
            try {
                // 钩子函数,FJWT子类可以实现
                onStart();
                // 开始获取任务并执行
                pool.runWorker(workQueue);
            } catch (Throwable ex) {
                exception = ex;
            } finally {
                try {
                    onTermination(exception);
                } catch (Throwable ex) {
                    // 由此可得,只获取第一个抛出的异常
                    if (exception == null)
                        exception = ex;
                } finally {
                    // 出现异常就取消注册,或者任务执行完也进行取消注册
                    pool.deregisterWorker(this, exception);
                }
            }
        }
    }

```

### ForkJoinPool#runWorker()

```java
  final void runWorker(WorkQueue w) {
        // 创建WorkQueue时并未对数组字段进行初始化
        w.growArray(); 
        // w.hint为创建workQueue时的随机值
        int seed = w.hint; 
        // avoid 0 for xorShift
        int r = (seed == 0) ? 1 : seed; 
        for (ForkJoinTask<?> t;;) {
            // 循环wqs进行获取并执行任务
            if ((t = scan(w, r)) != null)
                w.runTask(t);
            else if (!awaitWork(w, r))
                break;
            r ^= r << 13; r ^= r >>> 17; r ^= r << 5; // xorshift
        }
    }
```

### WorkQueue#growArray()

* TODO:什么时候array在此处有值？目前还未发现

```java
 final ForkJoinTask<?>[] growArray() {
            ForkJoinTask<?>[] oldA = array;
            /**
            * 1:在new WorkQueue时,array未赋值，未空。此时size为=1 << 13;
						* 2:array如果有值，就将其长度进行扩容2倍
						*  TODO:什么时候array在此处有值？目前还未发现
            */
            int size = oldA != null ? oldA.length << 1 : INITIAL_QUEUE_CAPACITY;
    	
   					/**
   					*  static final int MAXIMUM_QUEUE_CAPACITY = 1 << 26; // 64M
   					*   容量超出最大限制就抛出异常
   					*/
            if (size > MAXIMUM_QUEUE_CAPACITY)
                throw new RejectedExecutionException("Queue capacity exceeded");
            int oldMask, t, b;
   					// 以下开始扩容赋值数组
            ForkJoinTask<?>[] a = array = new ForkJoinTask<?>[size];
   					/**
   					* 对旧数组的进行判空,判断是否还有元素
   					* (oldMask = oldA.length - 1) >= 0
   					* (t = top) - (b = base) > 0)
   					*/
            if (oldA != null && (oldMask = oldA.length - 1) >= 0 &&
                (t = top) - (b = base) > 0) {
                int mask = size - 1;
              	// 常规的数组复制: System.arraycopy();,之所以没有使用该方法是希望,对每一个元素存入新数组的结果对其他线程都是可见的
                do { 
                    ForkJoinTask<?> x;
                    // 强调: b=wq的 base索引,oldMask=oldA.length-1，也就是一个全1的数
                    // 获取: 要获取元素在旧数组的索引位置
                    int oldj = ((b & oldMask) << ASHIFT) + ABASE;
                    // 获取: 要存入新数组的索引位置
                    int j    = ((b &    mask) << ASHIFT) + ABASE;
                  	// 可见性的获取该元素
                    x = (ForkJoinTask<?>)U.getObjectVolatile(oldA, oldj);
                  	// CAS: 旧数组放弃持有该元素的引用
                  	// 			新数组可见的存入该元素
                  	//      其他线程已经操作不到原数组了,因为引用已经改变了 
                    //      ForkJoinTask<?>[] a = array = new ForkJoinTask<?>[size];
                    if (x != null &&
                        U.compareAndSwapObject(oldA, oldj, x, null))
                        U.putObjectVolatile(a, j, x);
                 // 直到所有元素复制完毕为止
                } while (++b != t);
            }
            return a;
        }
```

### ForkJoinPool#scan()

```java
    private ForkJoinTask<?> scan(WorkQueue w, int r) {
        WorkQueue[] ws; int m;
      	// 日常判空
        if ((ws = workQueues) != null && (m = ws.length - 1) > 0 && w != null) {
          	// 第一次进来的w.scanState=w在wqs的下标值,具体见ForkJoinPool#registerWorker
            // 是个正数
            int ss = w.scanState; 
          	/**
          	* 代表随机取出的wq在wqs的索引位置
          	* k: 后续可以标识是否对wqs进行一次完整的遍历
          	* oldSum:
          	* checkSum:
          	*/
            for (int origin = r & m, k = origin, oldSum = 0, checkSum = 0;;) {
                WorkQueue q; ForkJoinTask<?>[] a; ForkJoinTask<?> t;
                int b, n; long c;
              	// 如果当前队列不为空,就尝试进行获取任务
                if ((q = ws[k]) != null) {
                  	// n = (b = q.base) - q.top) < 0: 标识当前剩余是否有待执行的任务数量
                    if ((n = (b = q.base) - q.top) < 0 &&
                        // 由于是工作窃取线程: 所以有一种情况,一个新的工作窃取线程刚创建完,对应的wq.array并未初始化,所以要进行判空校验
                        // 如果该队列存在就开始获取执行
                        (a = q.array) != null) {
                      	// 计算任务数组的base指针索引
                        long i = (((a.length - 1) & b) << ASHIFT) + ABASE;
                      	// 获取任务,对base进行再次校验
                        if ((t = ((ForkJoinTask<?>)
                                  U.getObjectVolatile(a, i))) != null &&
                            q.base == b) {
                          	// 状态有效判断
                            if (ss >= 0) {
                                if (U.compareAndSwapObject(a, i, t, null)) {
                                  	// Base指针+1
                                    q.base = b + 1;
                                  	// 代表还存在更多的任务需要处理,唤醒其他工作线程
                                    if (n < -1)       
                                        signalWork(ws, q);
                                  	// 返回任务继续执行
                                    return t;
                                }
                            }
                            // oldSum未改变之前，才能判断w的扫描状态，如果扫描状态小于0，代表INACITVE，此时需要尝试唤醒空闲线程进行扫描工作
                            else if (oldSum == 0 &&   
                                     w.scanState < 0)
                                tryRelease(c = ctl, ws[m & (int)c], AC_UNIT);
                        }
                      	// 扫描状态小于0，代表INACITVE,就重新扫描
                        if (ss < 0)                   // refresh
                            ss = w.scanState;
                        r ^= r << 1; r ^= r >>> 3; r ^= r << 10;
                      	// 所有的状态位重新置位
                        origin = k = r & m;           
                        oldSum = checkSum = 0;
                        continue;
                    }
                    // 通过base数值来进行校验和计算
                  	// 因为Base和Top是一直向上累加的,所以校验和可以保证其正确性
                    checkSum += b;
                }
                // 对wqs的遍历正好经历了一个周期
                if ((k = (k + 1) & m) == origin) { 
                    /**
                    * 如果处于非活跃状改，就获取新的扫描状态
                    * 如果新的扫描状态!=旧的扫描状态就不执行该分支
                    * 最后确认校验和: 第一遍扫描将checksum赋值给oldsum,
                    *               第二遍的时候如果wqs没有元素上的改变,新的checksum还是会等于旧的checksum，也就是本次的oldsum
                    *               想要表达的意思就是扫描状态没有变化,没有其他线程操作队列
                    */
                    if ((ss >= 0 || (ss == (ss = w.scanState))) &&
                        
                        oldSum == (oldSum = checkSum)) {
                        if (ss < 0 || w.qlock < 0)    // already inactive
                            break;
                        int ns = ss | INACTIVE;       // 状态置为INACTIVE
                        long nc = ((SP_MASK & ns) |
                                   (UC_MASK & ((c = ctl) - AC_UNIT)));// 活跃线程数减1
                      	/**
                      	* 怎么理解？
                      	* 制作下一个ctl的值: 低32位保存了当前成为非活跃线程的信息
                      	*                  1....ss : 1代表了当前位非活跃线程,ss代表了该wq在wqs的索引位置
                      	* w.stackPred = (int)c: 与上同理,都是保存了ctl的低32位信息
                      	* 怎么关联？
                      	* 通过当前ctl的第32位，可以获取到对应的wq,而wq里保存了上次ownerThread的引用
                      	*/
                        w.stackPred = (int)c;         // hold prev stack top
                        U.putInt(w, QSCANSTATE, ns);
                        if (U.compareAndSwapLong(this, CTL, c, nc))
                            ss = ns;
                        else
                            w.scanState = ss;         // back out
                    }
                    checkSum = 0;
                }
            }
        }
        return null;
    }

```

