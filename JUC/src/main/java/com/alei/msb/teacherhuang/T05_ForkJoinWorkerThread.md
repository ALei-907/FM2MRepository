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

