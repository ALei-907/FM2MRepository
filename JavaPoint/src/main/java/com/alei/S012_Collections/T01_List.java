package com.alei.S012_Collections;

import java.util.ArrayList;

/**
 * @author LeiLiMin
 * @date: 2022/8/24
 */
public class T01_List {
    public static void main(String[] args) {
        /**
         * Set与Add不同: add(int index,object obj)更加类似与insert操作，Set是覆盖操作
         */
        add();
        set();
    }

    public static void add(){
        ArrayList<Integer> list = new ArrayList<>();
        list.add(0);
        list.add(1);
        list.add(1,-1);
        /* List Method Of add: [0, -1, 1] */
        System.out.println("List Method Of add: "+list);

    }

    public static void set(){
        ArrayList<Integer> list = new ArrayList<>();
        list.add(0);
        list.add(1);
        list.set(1,-1);
        /* List Method Of set: [0, -1]  */
        System.out.println("List Method Of set: "+list);

    }
}
