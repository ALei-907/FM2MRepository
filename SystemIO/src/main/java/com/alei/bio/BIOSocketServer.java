package com.alei.bio;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author LeiLiMin
 * @date: 2023/4/20
 */
public class BIOSocketServer {
    /**
     * client名称设置
     */
    private static final String CLASS_NAME = "BIOSocketServer";

    /**
     * 伪异步IO-通过线程池实现
     */
    private static final ThreadPoolExecutor pool = new ThreadPoolExecutor(2, 4,
            1L,
            TimeUnit.SECONDS,
            new LinkedBlockingQueue<>(30),
            Executors.defaultThreadFactory(),
            new ThreadPoolExecutor.AbortPolicy());

    /**
     * BIO模型的局限性体现在一对四元组需要一个独立线程来执行业务逻辑,在大量IO的情况下造成了频繁切换上下文的负担
     * 通过线程池达到了伪异步IO的效果,但仍然收到阻塞的局限
     * 读取:
     * 1.read()不到数据时阻塞
     * 2.write()不能完整发送数据时阻塞
     */
    public static void main(String[] args) {
        try (ServerSocket socketServer = new ServerSocket(BIOConstant.PORT)) {
            System.out.println("Server Init");
            while (true) {
                Socket socket = socketServer.accept();
                pool.execute(new BIOServerHandler(socket, CLASS_NAME));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

/**
 * Server处理Socket消息
 */
class BIOServerHandler implements Runnable {
    private final Socket socket;

    private final String brokerName;

    public BIOServerHandler(Socket socket, String brokerName) {
        this.socket = socket;
        this.brokerName = brokerName;
        System.out.println("Accept Success!");
    }

    @Override
    public void run() {
        try (InputStream in = socket.getInputStream(); PrintWriter out = new PrintWriter(socket.getOutputStream())) {
            // read > 0: 获取到流中数据
            // read =-1: 流断开了
            int read;
            byte[] bytes = new byte[1024];
            while ((read = in.read(bytes)) != -1) {
                System.out.println(brokerName + "收到消息: " + new String(bytes, 0, read));
                out.write(brokerName + " Receive");
                out.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
