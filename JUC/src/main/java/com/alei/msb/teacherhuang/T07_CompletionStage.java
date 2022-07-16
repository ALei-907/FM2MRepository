package com.alei.msb.teacherhuang;

import javax.sound.midi.Soundbank;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ExecutionException;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * @author LeiLiMin
 * @Description: Completable核心接口
 * @date: 2022/7/16
 */
public class T07_CompletionStage {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        /**
         * 三个核心的函数式接口，supply,consumer,function
         * CompletionStage的异步和同步: 异步代表stage1和stage2分别由不同的线程来执行
         *                            同步代表stage1和stage2由同一个线程来执行
         */

        thenRun();
        themCombine();

    }

    /**
     * 前一个stage可以将执行结果专递给下一个stage,通过function的方式对stage2进行编程
     * {@link java.util.concurrent.CompletionStage#thenApply(Function)}
     */
    public static void thenApply() {
        System.out.println("====================thenApply====================");
        CompletableFuture<Integer> integerCompletableFuture = CompletableFuture.supplyAsync(() -> 1).thenApply(m -> {
            System.out.println(m);
            return m;
        });
        // 异步方式
        CompletableFuture<Integer> integerCompletableFuture1 = CompletableFuture.supplyAsync(() -> 1).thenApplyAsync(m -> {
            System.out.println(m);
            return m;
        });
    }

    /**
     * stage1执行完成后调用stage2,无需使用到stage1的返回结果Runnable
     * {@link java.util.concurrent.CompletionStage#thenRun(Runnable)}
     */
    public static void thenRun() {
        System.out.println("====================thenRun====================");

        CompletableFuture.supplyAsync(() -> 1).thenRun(() -> {
            System.out.println(1);
        });
        CompletableFuture.supplyAsync(() -> 1).thenRunAsync(() -> {
            System.out.println(1);
        });
    }

    /**
     * 执行完stage1和stage2之后才执行stage3
     * {@link java.util.concurrent.CompletionStage#thenCombine(CompletionStage, BiFunction)}
     */
    public static void themCombine() throws ExecutionException, InterruptedException {
        System.out.println("====================themCombine====================");

        // stage1
        CompletableFuture<Integer> result = CompletableFuture.supplyAsync(() -> {
                    System.out.println("stage1");
                    return 1;
                })
                .thenCombine(
                        // arg1: stage2
                        CompletableFuture.supplyAsync(() -> {
                            System.out.println("stage2");
                            return 2;
                        }),
                        // arg2: stage3(m=stage1 result,n=stage2 result)
                        (m, n) -> {
                            System.out.println(m + ":" + n);
                            return m + n;
                        }
                );
        System.out.println(result.get());
    }

}
