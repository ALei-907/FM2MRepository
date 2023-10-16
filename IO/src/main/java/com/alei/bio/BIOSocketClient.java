package com.alei.bio;

import org.apache.commons.lang3.StringUtils;

import java.io.InputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

/**
 * @author LeiLiMin
 * @date: 2023/4/20
 */
public class BIOSocketClient {
    public static void main(String[] args) {
        try (Socket socket = new Socket(BIOConstant.IP, BIOConstant.PORT);
             Scanner scanner = new Scanner(System.in);
             InputStream in = socket.getInputStream();
             PrintWriter out = new PrintWriter(socket.getOutputStream())
        ) {
            System.out.println("[Client]: 连接成功");
            while (socket.isConnected()) {
                // 请求数据
                System.out.print("Request For: ");
                String scannerStr = scanner.nextLine();
                if (StringUtils.isNotBlank(scannerStr)) {
                    out.write(scannerStr);
                    out.flush();
                    // 等待服务器返回消息
                    byte[] bytes = new byte[1024];
                    int read = in.read(bytes);
                    if (read != -1) {
                        System.out.println("[Server Response]: " + new String(bytes, 0, read));
                        continue;
                    }
                }
                // 1.以read=-1为标志性事件的Socket断开连接
                // 2.自身业务中输入空白请求为标志的退出
                break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
