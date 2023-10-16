package com.alei.msb.teacherhuang;

import java.util.ArrayList;
import java.util.concurrent.*;

/**
 * @Author LeiLiMin
 * @Description:
 * @date: 2022/5/15
 */
public class T02_AbstractExecutorService {
    // 1.Executor -> ExecutorService -> AbstractExecutorService
    // 2.#newTaskFor()
    // 3.#doInvokeXX() -> ecs -> queue.poll()
    // 4.#cancelAll()
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(10);
        ArrayList<Callable<Integer>> callables = new ArrayList<>();
        callables.add(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                System.out.println("call one");
                return 1;
            }
        });
        callables.add(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {

                // Project1: System.out.println("call two");
                // Project2: 以下
                try {
                    Thread.sleep(1000);
                    System.out.println("call two");
                }catch (Exception e){
                    e.printStackTrace();
                }
                return 2;
            }
        });
        /**
         * Project1: 则2个Callable都会成功执行
         *           why? 根据{@link AbstractExecutorService#doInvokeAny(java.util.Collection, boolean, long)}
         *                可知,task加入执行队列的速度比( 执行任务 + 获取结果)的速度要快
         *                所以在获取第一个any结果前，可能多个task已经被执行了
         * Project2: task1执行,task抛出"InterruptedException"
         *           why? 根据{@link AbstractExecutorService#doInvokeAny(java.util.Collection, boolean, long)}
         *                task执行完并获取到结果后要进行cancel taskList操作。但是task2已经加入到线程池中了
         */
        Integer result = executor.invokeAny(callables);
        System.out.println(result);
    }
}
