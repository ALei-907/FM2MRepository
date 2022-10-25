package com.alei.type;

/**
 * @author LeiLiMin
 * @date: 2022/10/25
 */
public class T01_Byte_Yu_0XFF {
    public static void main(String[] args) {
        String temp = "你好呀";
        char[] chars = temp.toCharArray();
        byte[] changeAfter = new byte[chars.length];
        int index = 0;
        for (char element : chars) {
            /**
             * (element >> 8 & 0XFF): 通过& 0XFF使得element向上转型为一个int,就可以使得byte所表示的数转为无符号表示的byte
             *  (byte) (element >> 8 & 0XFF): 通过byte强转,是考虑到兼容不同端序实现的物理机器
             *      int a=1;
             *      如果是大端字节序，则在内存中存储方式为   0000 0000    0000 0000   0000 0000    0000 0001
             *      如果是小端字节序，则在内存中存储方式为   0000 0001    0000 0000   0000 0000    0000 0000
             *      如果直接byte(int a),那么大端=0000 0001
             *                            小端=0000 0000
             *      通过0XFF转 byte        大端=小端=0000 0001
             */
            System.out.println(Integer.toBinaryString(element >> 8 & 0XFF));
            changeAfter[index] = (byte) (element >> 8 & 0XFF);
            index++;
        }
        System.out.println("========");
        for (byte b : changeAfter) {
            System.out.println(Integer.toBinaryString(b));
            System.out.println(b);
        }
    }
}
