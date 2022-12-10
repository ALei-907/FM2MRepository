package com.alei.slots;


import java.util.Deque;
import java.util.LinkedList;

/**
 * @author LeiLiMin
 * @date: 2022/12/6
 */
public class T01_MaxRectangle {
    private static final int[][] matrix = {
            {1, 1, 1, 1, 1},
            {1, 1, 1, 0, 1},
            {1, 1, 1, 0, 1},
            {1, 0, 0, 1, 0}
    };


    public static void main(String[] args) {
        T01_MaxRectangle obj = new T01_MaxRectangle();
        // System.out.println("LeetCode0,最大矩形面积:"+ obj.leetCode0(matrix));

        // Slots10031解决方案
        System.out.println("原始图谱: ");
        obj.printGraph(matrix);
        int[][] tagGraph = obj.slots10031(matrix);
        System.out.println("结块图谱: ");
        obj.printGraph(tagGraph);
    }

    private void printGraph(int[][] original) {
        for (int[] ints : original) {
            for (int anInt : ints) {
                System.out.print(anInt + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    private int[][] resetTagGraph(int[][] original) {
        int[][] tagGraph = new int[original.length][original[0].length];
        for (int i = 0; i < original.length; i++) {
            int[] ints = original[i];
            for (int i1 = 0; i1 < ints.length; i1++) {
                tagGraph[i][i1] = 0;
            }
        }
        return tagGraph;
    }

    /**
     * TODO: 不打印最终图谱,返回右下坐标信息链表
     * @param original
     * @return
     */
    private int[][] slots10031(int[][] original) {
        int[] curMax = slots10031GetMax(original);
        assert curMax != null;

        int[][] tagGraph = resetTagGraph(original);
        while (curMax[curMax.length - 1] > 0) {
            // 1.清空图谱
            for (int i = 0; i < curMax[3]; i++) {
                for (int i1 = 0; i1 < curMax[2]; i1++) {
                    original[curMax[0] - i][curMax[1] - i1] = 0;
                    tagGraph[curMax[0] - i][curMax[1] - i1] = curMax[curMax.length - 1];
                }
            }
            curMax = slots10031GetMax(original);
            assert curMax != null;
        }
        return tagGraph;
    }

    /**
     * 根据bitMap求出最大矩形信息
     *
     * @param matrix bitMap
     * @return [row, col, width, height, area]
     */
    private int[] slots10031GetMax(int[][] matrix) {
        int row = matrix.length;
        if (row == 0) {
            return null;
        }
        int col = matrix[0].length;


        // 1.求出每行每个矩阵连续的1
        int[][] left = new int[row][col];
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                if (matrix[i][j] == 1) {
                    left[i][j] = (j == 0 ? 0 : left[i][j - 1]) + 1;
                }
            }
        }

        // 2.求最大矩形[row,col,width,height,area]
        int[] maxVal = {-1, -1, -1, -1, -1};
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                if (left[i][j] > 0) {
                    int cursor = i;
                    int width = left[i][j];
                    while ((cursor--) >= 0 && width != 0) {
                        width = Math.min(width, left[cursor + 1][j]);
                        int height = (i - cursor);
                        int area = width * height;
                        if (area > maxVal[4]) {
                            maxVal[0] = i;
                            maxVal[1] = j;
                            maxVal[2] = width;
                            maxVal[3] = height;
                            maxVal[4] = area;
                        }
                    }
                }
            }
        }
        return maxVal;
    }


    /**
     * <a href="https://leetcode.cn/problems/maximal-rectangle/description/">LeetCode 85 题解</a>
     */
    // private int leetCode0(int[][] matrix){
    //     int row = matrix.length;
    //     if (row == 0) {
    //         return 0;
    //     }
    //     int col = matrix[0].length;
    //
    //
    //     // 1.求出每行每个矩阵连续的1
    //     int[][] left = new int[row][col];
    //     for (int i = 0; i < row; i++) {
    //         for (int j = 0; j < col; j++) {
    //             if (matrix[i][j] == 1) {
    //                 left[i][j] = (j == 0 ? 0 : left[i][j - 1]) + 1;
    //             }
    //         }
    //     }
    //
    //     // 2.求最大矩形[row,col,area]
    //     int maxVal = 0;
    //     for (int i = 0; i < row; i++) {
    //         for (int j = 0; j < col; j++) {
    //             if (left[i][j] > 0) {
    //                 int cursor = i;
    //                 int width = left[i][j];
    //                 while ((cursor--) >= 0 && width != 0) {
    //                     width = Math.min(width, left[cursor+1][j]);
    //                     maxVal=Math.max(width * (i - cursor), maxVal);
    //                 }
    //             }
    //         }
    //     }
    //     return maxVal;
    //
    //     // int ret = 0;
    //     // for (int j = 0; j < col; j++) { // 对于每一列，使用基于柱状图的方法
    //     //     int[] up = new int[row];
    //     //     int[] down = new int[row];
    //     //
    //     //     Deque<Integer> stack = new LinkedList<Integer>();
    //     //     for (int i = 0; i < row; i++) {
    //     //         while (!stack.isEmpty() && left[stack.peek()][j] >= left[i][j]) {
    //     //             stack.pop();
    //     //         }
    //     //         up[i] = stack.isEmpty() ? -1 : stack.peek();
    //     //         stack.push(i);
    //     //     }
    //     //     stack.clear();
    //     //     for (int i = row - 1; i >= 0; i--) {
    //     //         while (!stack.isEmpty() && left[stack.peek()][j] >= left[i][j]) {
    //     //             stack.pop();
    //     //         }
    //     //         down[i] = stack.isEmpty() ? row : stack.peek();
    //     //         stack.push(i);
    //     //     }
    //     //
    //     //     for (int i = 0; i < row; i++) {
    //     //         int height = down[i] - up[i] - 1;
    //     //         int area = height * left[i][j];
    //     //         ret = Math.max(ret, area);
    //     //     }
    //     // }
    //     // return ret;
    //
    // }
}

