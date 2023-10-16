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
                      	*                  1....ss : 1代表了当前为非活跃线程,ss代表了该wq在wqs的索引位置
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

### WorkQueue#runTask()

```java
     final void runTask(ForkJoinTask<?> task) {
            if (task != null) {
              	/**
              	*  static final int SCANNING     = 1;   
              	*  ~SCANNING=1111 1111 1111 1111 1111 1111 1111 1110
              	*  本质,将该wq的scanState设置为偶数(scanState的组成部就包括了wqs中的index)，偶数的index表示为外部队列,也将符号位置为1,也就是 INACTIVE
              	*  变相把该队列标记为忙碌状态
              	*  猜想: 在FJP#signalWork()中会尝试去唤醒idle线程去执行任务
              	*      >  int d = sp - v.scanState;   通过d==0进行判断
                *      >  原因：可能在此时需要该工作线程需要执行任务，同时也需要从外部队列转移任务等
              	*/
                scanState &= ~SCANNING; // mark as busy
              	// 执行任务
                (currentSteal = task).doExec();
                U.putOrderedObject(this, QCURRENTSTEAL, null); // release for GC
              	// 执行本地任务。为何执行本地任务？考虑一个问题：谁能往当前线程的工作队列里放任务？当前线程在执行FJT时往自己队列里放了任务，也只有当前线程才能往array任务数组里放任务
                execLocalTasks();
                ForkJoinWorkerThread thread = owner;
                 // nsteals代表了当前线程总的窃取的任务数量。由于符号限制，所以检查是否发生符号溢出 
                if (++nsteals < 0)     
                    // 当前线程32位计数值达到饱和，那么将其加到FJP的全局变量的64位计数器中，并且清零计数值 nsteals
                    transferStealCount(pool);
                scanState |= SCANNING;  // 任务执行完成，恢复扫描状态
                if (thread != null)
                    thread.afterTopLevelExec(); // 任务执行完的钩子函数
            }
        }
```

### ForkJoinPool#awaitWork()

```java
    private boolean awaitWork(WorkQueue w, int r) {
        // 日常判空 , 判断fjp是否处于待结束状态
        if (w == null || w.qlock < 0) 
            return false;
        //等待自旋数
        for (int pred = w.stackPred, spins = SPINS, ss;;) {
            if ((ss = w.scanState) >= 0)
                break;
            else if (spins > 0) {
                r ^= r << 6; r ^= r >>> 21; r ^= r << 7;
                if (r >= 0 && --spins == 0) {         // randomize spins
                    WorkQueue v; WorkQueue[] ws; int s, j; AtomicLong sc;
                    if (pred != 0 && (ws = workQueues) != null &&
                        (j = pred & SMASK) < ws.length &&
                        (v = ws[j]) != null &&        // see if pred parking
                        (v.parker == null || v.scanState >= 0))
                        spins = SPINS;                // continue spinning
                }
            }
            else if (w.qlock < 0)                     // recheck after spins
                return false;
           // 等待核心
            else if (!Thread.interrupted()) {
                long c, prevctl, parkTime, deadline;
              	// 最高16位 & 最大并行度
                int ac = (int)((c = ctl) >> AC_SHIFT) + (config & SMASK);
              	// ac不会小于0
                // ac==0?当当前线程为最后一个空闲线程时就会出现ac==0
              	/**
              	* 最后一个空闲线程时?: 在scan()时会对空闲线程进行标记处理
              	* ac==0，以最大并行度为8举例,且当前线程为最后一个空闲线程
              	* 最高16位：1111 1111 1111 1000: 表示当前已经没有活跃线程了
              	* config： 0000 0000 0000 1000
              	* 
              	* 既然为最后一个活跃线程，那就尝试进行结束
              	*/
                if ((ac <= 0 && tryTerminate(false, false)) ||
                    (runState & STOP) != 0)           // pool terminating
                    return false;
                /**
                * ss= (int)c: ss是当前栈顶wq，其实是标记空闲线程。因为wq与工作线程互相持有引用
                * 					  就是判断判断当前线程是否为最后一个空闲线程
                */
                if (ac <= 0 && ss == (int)c) {
                  	// 当前空闲线程栈顶的下一个线程，是准备还原栈顶线程的准备操作  
                    prevctl = (UC_MASK & (c + AC_UNIT)) | (SP_MASK & pred);
                    int t = (short)(c >>> TC_SHIFT); 
                    // 至少保证有3个线程，如果fjp存在三个线程就可以将当前线程进行释放，因为ss=(int)c表示当前fjp状态稳定，就干脆释放一些资源。而且也能保证线程的活性
                    if (t > 2 && U.compareAndSwapLong(this, CTL, c, prevctl))
                        return false;                 // else use timed wait
                    parkTime = IDLE_TIMEOUT * ((t >= 0) ? 1 : 1 - t);
                    deadline = System.nanoTime() + parkTime - TIMEOUT_SLOP;
                }
                else
                    prevctl = parkTime = deadline = 0L;
                Thread wt = Thread.currentThread();
                U.putObject(wt, PARKBLOCKER, this);   // emulate LockSupport
                // 设置当前该队列阻塞在哪个线程上
                w.parker = wt;
                // 当前ctl没有被人改变过，该wq也没被改变。就进行阻塞
                if (w.scanState < 0 && ctl == c)      // recheck before park
                    U.park(false, parkTime);
              	// 被唤醒之后，就清空标识字段
                U.putOrderedObject(w, QPARKER, null);
                U.putObject(wt, PARKBLOCKER, null);
              	// 如果wq状态可用，那就退出即可
                if (w.scanState >= 0)
                    break;
                // 设置了阻塞时间，ctl没有被改变过，并且超时了，就进行收缩线程池
                if (parkTime != 0L && ctl == c &&
                    deadline - System.nanoTime() <= 0L &&
                    U.compareAndSwapLong(this, CTL, c, prevctl))
                    return false;                     // shrink pool
            }
        }
        return true;
    }

1111 1111 1111 1001		1111 1111 1111 1010		1000 0000 0000 0000		0000 0000 0000 0011
```

