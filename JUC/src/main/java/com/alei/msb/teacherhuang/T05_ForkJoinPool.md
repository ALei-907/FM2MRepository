### ForkJoinPool#Construct

```java
  private ForkJoinPool(int parallelism, // Cpu核心数
                         ForkJoinWorkerThreadFactory factory,// 线程工厂
                         UncaughtExceptionHandler handler, // 异常处理器
                         int mode, // FIFO,LIFO模型
                         String workerNamePrefix) {
        this.workerNamePrefix = workerNamePrefix;
        // 线程工厂
        this.factory = factory;
        this.ueh = handler;
        /**
        * static final int SMASK        = 0xffff; // 1111 1111 1111 1111
        * static final int FIFO_QUEUE   = 1 << 16;
        * static final int LIFO_QUEUE   = 0; 
        * 以核心数8举例：(1000 & 1111 1111 1111 1111) ｜ mode
        *          FIFO: 1 0000 0000 0000 1000
        *          LIFO: 0 0000 0000 0000 1000
        */
        this.config = (parallelism & SMASK) | mode;
        // 负数在内存中以补码表示
        long np = (long)(-parallelism); // 1 1111 ... 1111 ....1000 为什么取反？见下
        /**
        *     private static final int  AC_SHIFT   = 48;
        *     private static final long AC_UNIT    = 0x0001L << AC_SHIFT;
        *     private static final long AC_MASK    = 0xffffL << AC_SHIFT;
        *     private static final int  TC_SHIFT   = 32;
        *     private static final long TC_UNIT    = 0x0001L << TC_SHIFT;
        *     private static final long TC_MASK    = 0xffffL << TC_SHIFT;
        * 
        *     ((np << AC_SHIFT) & AC_MASK) = 1111 1111 1111 1000 ...48个0
        *     ((np << TC_SHIFT) & TC_MASK) = 1111 1111 1111 1000 ...32个0
        *     ctl = 1111 1111 1111 1000 1111 1111 1111 1000 ...32个0
        */
        this.ctl = ((np << AC_SHIFT) & AC_MASK) | ((np << TC_SHIFT) & TC_MASK);
    }
```



### ForkJoinPool#externalSubmit()

```java
    private void externalSubmit(ForkJoinTask<?> task) {
        int r;                                    
        if ((r = ThreadLocalRandom.getProbe()) == 0) {   // 初始化随机种子
            ThreadLocalRandom.localInit();
            r = ThreadLocalRandom.getProbe();
        }
        for (;;) {
            WorkQueue[] ws; WorkQueue q; int rs, m, k;
            boolean move = false;
            // 1.FJP是否结束,如果结束就不进行提交任务
            if ((rs = runState) < 0) {									
                tryTerminate(false, false);     
                throw new RejectedExecutionException();
            }
          /**
          *    private static final int  RSLOCK     = 1;
    			*    private static final int  RSIGNAL    = 1 << 1;
    			*    private static final int  STARTED    = 1 << 2;
    			*    private static final int  STOP       = 1 << 29;
    			*    private static final int  TERMINATED = 1 << 30;
    			*    private static final int  SHUTDOWN   = 1 << 31;
          */
            // 2.初始化WorkerQueue: Started状态实际上代表workerQueue是否初始化完成,如果没有初始化,就进行创建WorkerQueue
            else if ((rs & STARTED) == 0 ||     
                     ((ws = workQueues) == null || (m = ws.length - 1) < 0)) {
                int ns = 0;
                rs = lockRunState();
                try {
                    if ((rs & STARTED) == 0) {
                        U.compareAndSwapObject(this, STEALCOUNTER, null,
                                               new AtomicLong());
                        // 创建数组,参考HashMap的创建
                        int p = config & SMASK; // 确保有四个槽位
                        int n = (p > 1) ? p - 1 : 1;
                        n |= n >>> 1; n |= n >>> 2;  n |= n >>> 4;
                        n |= n >>> 8; n |= n >>> 16; n = (n + 1) << 1;
                        workQueues = new WorkQueue[n];
                        ns = STARTED;
                    }
                } finally {
                    unlockRunState(rs, (rs & ~RSLOCK) | ns);
                }
            }
            /**
            *    static final int SQMASK       = 0x007e;        // 126 -> 11111110
            *    m = workerQueues.length - 1 
            *    r = 随机种子
            *    k = r & m & SQMASK  => 1.根据随机种子计算槽位
            *                           2.m确保不越界
            *                           3.SQMASK确保是偶数值
            *                        => 得出"externalSubmitQueue"为workerQueue得偶数位
            */
            // 3.如果计算出的workerQueue不为空,就可以存入外部提交队列,并唤醒"窃取工作线程"
            else if ((q = ws[k = r & m & SQMASK]) != null) { 
                if (q.qlock == 0 && U.compareAndSwapInt(q, QLOCK, 0, 1)) {
                    ForkJoinTask<?>[] a = q.array;
                    int s = q.top;     					// 获取: 任务栈顶位置
                    boolean submitted = false;  // 初始化: 提交成功标志位
                    try { 
                        // 如果工作栈的容量即将超出capacity,就进行扩容
                        if ((a != null && a.length > s + 1 - q.base) ||
                            (a = q.growArray()) != null) {
                            /**
                            * ASHIFT：通过Unsafe获取 -> 数组元素的大小
                            * ABASE：通过Unsafe获取 -> 数组首元素的地址偏移
                            * j: 当前要存放元素的便宜
                            */
                            int j = (((a.length - 1) & s) << ASHIFT) + ABASE;
                            // 以下2个操作结合U.compareAndSwapInt(q, QLOCK, 1, 0) 一起使用
                            // volatile的StoreStoreFence保证，其他线程看到其解锁状态时,这2个操作也可以被其他线程可见
                            U.putOrderedObject(a, j, task); // CAS存入元素
                            U.putOrderedInt(q, QTOP, s + 1);// q.top加1
                            submitted = true;
                        }
                    } finally {
                        U.compareAndSwapInt(q, QLOCK, 1, 0);
                    }
                    if (submitted) {
                        // 唤醒"工作窃取线程"
                        signalWork(ws, q);
                        return;
                    }
                }
                move = true;// 该标志位,代表是否要重新获取随机种子
            }
            // 4.WorkerQueues创建,但根据计算获取的workerQueue并未创建,那么就进行创建
            else if (((rs = runState) & RSLOCK) == 0) { 
                q = new WorkQueue(this, null);
                q.hint = r; // 可以r可以获取到其在workerQueues的索引下标
                q.config = k | SHARED_QUEUE; // SHARED_QUEUE = 1 << 31；这里就是将整形int的符号位置1，所以为负数，SHARED_QUEUE表明当前队列是共享队列（外部提交队列）。而k为当前wq处于wqs中的索引下标
                q.scanState = INACTIVE;      // 由于当前wq并没有进行扫描任务，所以扫描状态位无效状态INACTIVE

                rs = lockRunState();           // publish index
                // 加锁 并最后进行一次确认,如果workerQueues的状态符合条件的话,就存入workerQueues
                if (rs > 0 &&  (ws = workQueues) != null &&
                    k < ws.length && ws[k] == null)
                    ws[k] = q;                 // else terminated
                unlockRunState(rs, rs & ~RSLOCK);
            }
            else
                move = true;                   // move if busy
            // 重新生成随机种子
            if (move)
                r = ThreadLocalRandom.advanceProbe(r);
        }
    }

```

### ForkJoin#signalWork()

```java
    final void signalWork(WorkQueue[] ws, WorkQueue q) {
        long c; int sp, i; WorkQueue v; Thread p;
       /**
       * ctl在构成方法时置为负数,详细见上对ForkJoin构造方法的补充解释
       * 根据fjp的构造方法可知,当active达到最大并行度时,ctl的符号位会溢出,变为ctl>0
       */
        while ((c = ctl) < 0L) {                       // 活跃线程数未达到阈值
            // long向下转型,保留底32位,从构造方法中可知,默认为0
            if ((sp = (int)c) == 0) {                  // 没有空闲线程
                /**
                *     private static final long ADD_WORKER = 0x0001L << (TC_SHIFT + 15); 
								*    																			 = 0x0001L << 47
								*     根据fjp的构造方法可知，ctl高32位中的第16位保存TotalWorkers的计数，
								*         1.如果超出最大并行度，那么第48位为0,因为不断累加产生溢出
								*         2.如果还为1,表示还未达到最大并行度,可以进行创建工作线程
                */
                if ((c & ADD_WORKER) != 0L)            // worker还未达到饱和
                    tryAddWorker(c);
                break;
            }
            if (ws == null)                            // unstarted/terminated
                break;
            if (ws.length <= (i = sp & SMASK))         // terminated
                break;
            if ((v = ws[i]) == null)                   // terminating
                break;
            int vs = (sp + SS_SEQ) & ~INACTIVE;        // next scanState
            int d = sp - v.scanState;                  // screen CAS
            long nc = (UC_MASK & (c + AC_UNIT)) | (SP_MASK & v.stackPred);
            if (d == 0 && U.compareAndSwapLong(this, CTL, c, nc)) {
                v.scanState = vs;                      // activate v
                if ((p = v.parker) != null)
                    U.unpark(p);
                break;
            }
            if (q != null && q.base == q.top)          // no more work
                break;
        }
    }

```

