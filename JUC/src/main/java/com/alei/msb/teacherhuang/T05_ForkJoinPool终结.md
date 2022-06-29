### ForkJoinPool#shutdown()

```java
  public void shutdown() {
        checkPermission();
    		// 尝试结束fjp
        tryTerminate(false, true);
  }
```

### ForkJoinPool#tryTerminate()

* enable：是否立即进行终结FJP任务

```java
    private boolean tryTerminate(boolean now, boolean enable) {
        int rs;
      	// TODO: this.common -> 并不了解具体含义
        if (this == common)                       // cannot shut down
            return false;
      	/**
      	*      private static final int  RSLOCK     = 1;
        *      private static final int  RSIGNAL    = 1 << 1;
        *      private static final int  STARTED    = 1 << 2;
        *      private static final int  STOP       = 1 << 29;
        *      private static final int  TERMINATED = 1 << 30;
        *      private static final int  SHUTDOWN   = 1 << 31; // 只有shutWown为负数
        *  表示：运行状态未非SHUTDOWN就判断是否需要终结
        *       enable = true : 立即终结
        *       enable = false: 退出方法执行，交给下次执行机会来进行SHUTDOWN
      	*/
        if ((rs = runState) >= 0) {
          	// enable = false: shutDown执行交由下次时机执行
            if (!enable)
                return false;
          	// enable = true : 通过cas的方式为fjp设置shutDown标识位
            rs = lockRunState();
            // 去除RSLOCK标识，加上SHUTDOWN标识位
          	// SHUTDOWN标识位小于0,因为externalSubmit()不再接受新的任务了，但是scan()仍在继续
            unlockRunState(rs, (rs & ~RSLOCK) | SHUTDOWN);
        }
				/**
				* 线程池状态处于SHUTDOWN，没有设置STOP标志
				*/
        if ((rs & STOP) == 0) {
          	/**
          	*  forkJoinPool.shutdown(); 		now=false
            *  forkJoinPool.shutdownNow();	now=true
          	*/
          	// 若没有设置立即无条件结束线程池，那么需要静默状态，看看所有线程是否都是空闲的
            if (!now) {                           // check quiescence
              	// 重复检测，直到FJP稳定
                for (long oldSum = 0L;;) {        // repeat until stable
                    WorkQueue[] ws; WorkQueue w; int m, b; long c;
                    long checkSum = ctl;
                  	// AC > 0，说明仍然有活跃线程,直接返回即可
                  	/**
                  	* 假设最大并行度为8:   0000 0000 0000 0000 0000 0000 0000 1000
                  	* 存在1个活跃线程:     1111 1111 1111 1111 1111 1111 1111 1001	
                  	* (int)(checkSum >> AC_SHIFT) + (config & SMASK) = 1 大于0
                  	* 存在0个活跃线程:		  1111 1111 1111 1111 1111 1111 1111 1000
                  	* (int)(checkSum >> AC_SHIFT) + (config & SMASK) = 0 等于0
                  	*/
                    if ((int)(checkSum >> AC_SHIFT) + (config & SMASK) > 0)
                        return false;             // still active workers
                  	// wqs仍然没有初始化，那也直接结束即可
                    if ((ws = workQueues) == null || (m = ws.length - 1) <= 0)
                        break;                    // check queues
                    for (int i = 0; i <= m; ++i) {
                        if ((w = ws[i]) != null) {
                            if ((b = w.base) != w.top || w.scanState >= 0 ||
                                w.currentSteal != null) {
                                tryRelease(c = ctl, ws[m & (int)c], AC_UNIT);
                                return false;     // arrange for recheck
                            }
                            checkSum += b;
                            if ((i & 1) == 0)
                                w.qlock = -1;     // try to disable external
                        }
                    }
                    if (oldSum == (oldSum = checkSum))
                        break;
                }
            }
            if ((runState & STOP) == 0) {
                rs = lockRunState();              // enter STOP phase
                unlockRunState(rs, (rs & ~RSLOCK) | STOP);
            }
        }

        int pass = 0;                             // 3 passes to help terminate
        for (long oldSum = 0L;;) {                // or until done or stable
            WorkQueue[] ws; WorkQueue w; ForkJoinWorkerThread wt; int m;
            long checkSum = ctl;
            if ((short)(checkSum >>> TC_SHIFT) + (config & SMASK) <= 0 ||
                (ws = workQueues) == null || (m = ws.length - 1) <= 0) {
                if ((runState & TERMINATED) == 0) {
                    rs = lockRunState();          // done
                    unlockRunState(rs, (rs & ~RSLOCK) | TERMINATED);
                    synchronized (this) { notifyAll(); } // for awaitTermination
                }
                break;
            }
            for (int i = 0; i <= m; ++i) {
                if ((w = ws[i]) != null) {
                    checkSum += w.base;
                    w.qlock = -1;                 // try to disable
                    if (pass > 0) {
                        w.cancelAll();            // clear queue
                        if (pass > 1 && (wt = w.owner) != null) {
                            if (!wt.isInterrupted()) {
                                try {             // unblock join
                                    wt.interrupt();
                                } catch (Throwable ignore) {
                                }
                            }
                            if (w.scanState < 0)
                                U.unpark(wt);     // wake up
                        }
                    }
                }
            }
            if (checkSum != oldSum) {             // unstable
                oldSum = checkSum;
                pass = 0;
            }
            else if (pass > 3 && pass > m)        // can't further help
                break;
            else if (++pass > 1) {                // try to dequeue
                long c; int j = 0, sp;            // bound attempts
                while (j++ <= m && (sp = (int)(c = ctl)) != 0)
                    tryRelease(c, ws[sp & m], AC_UNIT);
            }
        }
        return true;
    }

```

