package com.alei.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * @Author LeiLiMin
 * @Description: 文件读取工具
 * @date: 2022/4/12
 */
public class FileReadUtil {
    /**
     * 读取Properties文件中键值对
     *
     * @param path 资源路径
     */
    public static Map<String, String> convertToMapByPp(String path) throws IOException {
        try (InputStream in = Thread.currentThread(). // 当前线程
                getContextClassLoader().         // 获取类加载器
                getResourceAsStream(path);       // 加载配置文件
        ) {
            Properties pt = new Properties();
            pt.load(in);

            Map<String, String> result = new HashMap<>();
            Enumeration en = pt.propertyNames();
            while (en.hasMoreElements()) {
                String strKey = (String) en.nextElement();
                String strValue = pt.getProperty(strKey);
                result.put(strKey, strValue);
            }
            return result;
        }
    }

    /**
     * 读取普通的文件为String
     * @param filePath 文件路径
     * @return
     * @throws IOException
     */
    public static String readFileToString(String filePath) throws IOException {
        File file = new File(filePath);
        Long fileLength = file.length();

        byte[] fileContent = new byte[fileLength.intValue()];
        try (FileInputStream in = new FileInputStream(file);){
            in.read(fileContent);
            String fileContentStr = new String(fileContent);
            return fileContentStr;
        }
    }
}
