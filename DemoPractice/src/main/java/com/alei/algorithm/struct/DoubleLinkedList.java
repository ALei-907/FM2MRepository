package com.alei.algorithm.struct;

/**
 * @author LeiLiMin
 * @date: 2022/12/19
 */
public class DoubleLinkedList {
    public static void main(String[] args) {
        Node node1 = new Node(1);
        Node node2 = new Node(2);
        Node node3 = new Node(3);
        node1.pre = null;
        node1.next = node2;

        node2.pre = node1;
        node2.next = node3;

        node3.pre = node2;
        node3.next = null;

        Node node = reversList(node1);
        while (node != null) {
            System.out.println(node.value);
            node = node.next;
        }
    }

    public static Node reversList(Node node) {
        Node pre = null;
        Node next;
        while (node != null) {
            next = node.next;
            node.next = pre;
            node.pre = next;
            pre = node;
            node = next;
        }
        return pre;
    }

    public static class Node {
        /**
         * 前置节点
         */
        private Node pre;

        /**
         * 后继节点
         */
        private Node next;

        /**
         * 值
         */
        private Object value;

        Node(Object value) {
            this.value = value;
        }
    }
}


