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

