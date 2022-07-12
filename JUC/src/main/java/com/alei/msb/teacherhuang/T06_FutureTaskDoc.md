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