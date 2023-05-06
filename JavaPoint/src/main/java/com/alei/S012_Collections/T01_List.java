package com.alei.S012_Collections;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
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
        nCopies();
    }

    public static void add() {
        ArrayList<Integer> list = new ArrayList<>();
        list.add(0);
        list.add(1);
        list.add(1, -1);
        /* List Method Of add: [0, -1, 1] */
        System.out.println("List Method Of add: " + list);

    }

    public static void set() {
        ArrayList<Integer> list = new ArrayList<>();
        list.add(0);
        list.add(1);
        list.set(1, -1);
        /* List Method Of set: [0, -1]  */
        System.out.println("List Method Of set: " + list);

    }

    /**
     * 返回一个顺序生成的集合
     */
    public static void genOrdedList() {
        /**
         * 顺序生成元素: {@link IntStream#range(int, int)}
         * 将元素转为保证类: {@link IntStream#boxed()}
         */
        List<Integer> gridIndexes = IntStream.range(0, 15).boxed().collect(Collectors.toList());
        System.out.println(gridIndexes);

    }

    public static void indexOf() {
        List<Integer> integers = List.of(1, 2, 3);
        System.out.println(integers.indexOf(4));
    }

    public static void nCopies() {
        System.out.println(Collections.nCopies(5, 3));
    }


    public static void shuffle() {
        /**
         *   int size = list.size();
         *   if (size < SHUFFLE_THRESHOLD || list instanceof RandomAccess) {
         *       for (int i=size; i>1; i--)
         *           swap(list, i-1, rnd.nextInt(i));
         *   } else {
         *       Object[] arr = list.toArray();
         *
         *       // Shuffle array
         *       for (int i=size; i>1; i--)
         *           swap(arr, i-1, rnd.nextInt(i));
         *
         *       // Dump array back into list
         *       // instead of using a raw type here, it's possible to capture
         *       // the wildcard but it will require a call to a supplementary
         *       // private method
         *       ListIterator it = list.listIterator();
         *       for (Object e : arr) {
         *           it.next();
         *           it.set(e);
         *       }
         *   }
         */
        // Collections.shuffle(List<?> list, Random rnd)的思考？
        // 1.在list为RandomAccess或者数量较少的时候直接在原集合中进行交换
        // 2.在list为非RandomAccess且数量较多的时候则deep-copy出一个array进行交换
        //
        // 这么做的原因在于,在元素数量较少的时候,总体耗时并不高。在RandomAccess的情况下,使用的是array来存储元素,而对于array进行随机访问修改的成本是很低的
        // 但是如果元素数量较多且为链表实现的时候,随机访问的成本较高
        //
        // 为什么链表的随机访问成本要高于数组呢？
        // 数组可以进行指定下标位置访问,也就是采用offset+step的方式来访问(o(1))。但是链表需要线性访问元素(o(n))
        Collections.shuffle(Arrays.asList(1, 2, 3));
    }
}
