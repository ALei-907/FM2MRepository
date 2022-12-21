package com.alei.S012_Collections;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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
        genOrdedList();
        indexOf();
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

    /**
     * 返回一个顺序生成的集合
     */
    public static void genOrdedList(){
        /**
         * 顺序生成元素: {@link IntStream#range(int, int)}
         * 将元素转为保证类: {@link IntStream#boxed()}
         */
        List<Integer> gridIndexes = IntStream.range(0, 15).boxed().collect(Collectors.toList());
        System.out.println(gridIndexes);

    }

    public static void indexOf(){
        List<Integer> integers = List.of(1, 2, 3);
        System.out.println(integers.indexOf(4));
    }
}
