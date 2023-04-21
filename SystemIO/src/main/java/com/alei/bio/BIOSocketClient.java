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
            String scannerStr = scanner.nextLine();
            byte[] bytes = new byte[1024];
            while (StringUtils.isNotBlank(scannerStr)) {
                out.write(scannerStr);
                out.flush();
                int read = in.read(bytes);
                if (read != -1) {
                    System.out.print(new String(bytes, 0, read));
                }
                scannerStr = scanner.nextLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
