### FutureTask#Run()

```java
// FutureTask继承了Runnable,所以这里一定是任务执行的任务
public void run() {
  	// 状态必须位NEW-新建状态,且成功的将其Runner修改为当前线程
  	// stand for: FutureTask持有当前线程的引用,并可以通过runner对线程进行cancel中断
    if (state != NEW ||
        !RUNNER.compareAndSet(this, null, Thread.currentThread()))
        return;
    try {
        Callable<V> c = callable;
      	// 用户定义的callable是否为空,再次判断当前状态是否被改变,也就是执行前必须为NEW状态
        if (c != null && state == NEW) {
            V result;
            boolean ran;
            try {
              	// 执行用户定义函数
                result = c.call();
                ran = true;
            } catch (Throwable ex) {
                result = null;
              	// 设置执行失败标识位
                ran = false;
              	// 捕捉用户定义函数异常
                setException(ex);
            }
            if (ran)
              	// 执行函数，设置正常执行结果
                set(result);
        }
    } finally {
        // runner must be non-null until state is settled to
        // prevent concurrent calls to run()
      	// 帮助GC,丢弃引用当前线程
        runner = null;
        // state must be re-read after nulling runner to prevent
        // leaked interrupts
        int s = state;
      	// 判断是否被中断，如果是中断处理，那么调用handlePossibleCancellationInterrupt处理异常
        if (s >= INTERRUPTING)
            handlePossibleCancellationInterrupt(s);
    }
}
```

### 异常操作

```java
// 当任务执行时发生了异常，那么调用该方法，将Futuretask变为异常完成状态  
protected void setException(Throwable t) {
  			/*
  			 *     private volatile int state;
    		 *		 private static final int NEW          = 0;
    		 *		 private static final int COMPLETING   = 1;
    		 * 		 private static final int NORMAL       = 2;
    		 *		 private static final int EXCEPTIONAL  = 3;
    		 *		 private static final int CANCELLED    = 4;
    		 *	   private static final int INTERRUPTING = 5;
    		 *		 private static final int INTERRUPTED  = 6;
				 */ 
  			// 为什么在 NEW -> EXCEPTIONAL 时设置一个中间状态COMPLETING?
  			// 如果不设置这个状态,后续操作失败了怎么办？总不能让线程池不断的扫描到该任务吧(因为如果不修改，那么状态一直为NEW)
        if (STATE.compareAndSet(this, NEW, COMPLETING)) {
          	// 保证了顺序写
            outcome = t;
            STATE.setRelease(this, EXCEPTIONAL); // final state
            finishCompletion();
        }
    }
```

````java
    private void finishCompletion() {
      	// 为什么注释,注释前当线程的测试用例。
        // assert state > COMPLETING;
        for (WaitNode q; (q = waiters) != null;) {
          	// 如果有别的线程等待当前任务,那么将他们唤醒。算法：单线链表的遍历
          	// 通过CAS保证可见性
            if (WAITERS.weakCompareAndSet(this, q, null)) {
                for (;;) {	
                  	// 等待线程不为空，那么唤醒，调用LockSypport.unpark
                    Thread t = q.thread;
                    if (t != null) {
                        q.thread = null;
                        LockSupport.unpark(t);
                    }
                    WaitNode next = q.next;
                    if (next == null)
                        break;
                  	// 断开保留下一个任务的链,帮助GC
                    q.next = null; // unlink to help gc
                    q = next;
                }
                break;
            }
        }
				// 此时所有等待的线程都已经被唤醒了,回调子类的钩子函数
        done();
				// 任务完成了必定不需要执行器了
        callable = null;        // to reduce footprint
    }

````

### 正常执行完成

```java
// 与上相同，只不过状态不同而已  
protected void set(V v) {
        if (STATE.compareAndSet(this, NEW, COMPLETING)) {
            outcome = v;
            STATE.setRelease(this, NORMAL); // final state
            finishCompletion();
        }
    }
```

### 取消任务执行

```java
// 外部线程可以通过该方法的调用,取消该任务的执行,此时。任务可能处于哪些状态
// 1.NEW 2.COMPLETING 3.NORMAL EXCEPTIONAL
public boolean cancel(boolean mayInterruptIfRunning) {
        // if cancel task that state not new,where return false
  			/**
  			 * (NORMAL EXCEPTIONAL过程中所标记的COMPLETING并不代表在执行中,因为方法已经执行完了) 
  			 * 真正的执行中是判断为NEW之后就开始run的过程
  			 */
  			// 如果当前任务没有被执行完成,
  			// 如果当前任务已经被执行完成 直接返回False;
        if (!(state == NEW && STATE.compareAndSet
              (this, NEW, mayInterruptIfRunning ? INTERRUPTING : CANCELLED)))
            return false;
        try {    // in case call to interrupt throws exception
          	// 如果指定了 中断线程
            if (mayInterruptIfRunning) {
                try {
                    Thread t = runner;
                    if (t != null)
                        t.interrupt();
                } finally { // final state
                  	// 最终将任务设置为中断结束
                    STATE.setRelease(this, INTERRUPTED);
                }
            }
        } finally {
            finishCompletion();
        }
        return true;
    }
```

```java
 private void handlePossibleCancellationInterrupt(int s) {
        // 如果当前的状态为INTERRUPTING就通过线程让步来循环等待状态变为INTERRUPTED
        if (s == INTERRUPTING)
            while (state == INTERRUPTING)
                Thread.yield(); // wait out pending interrupt
    }
```

