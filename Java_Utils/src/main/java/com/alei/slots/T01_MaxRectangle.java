package com.alei.slots;

import lombok.Data;

import java.util.Objects;

/**
 * @author LeiLiMin
 * @date: 2022/12/6
 */
public class T01_MaxRectangle {
    private static int demo[][] = {
            {1, 0, 0, 0, 0, 0, 0},
            {0, 0, 1, 1, 1, 1, 0},
            {0, 0, 1, 1, 1, 1, 0},
            {0, 0, 0, 1, 1, 1, 0},
            {0, 0, 0, 1, 1, 1, 0}
    };

    @Data
    static class Rectangle {
        private int x;  // 横轴
        private int y;  // 纵轴

        public int area() {
            return x * y;
        }

        public Rectangle(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    private static Rectangle rectangles[][] = new Rectangle[demo.length][demo[0].length];

    public static void main(String[] args) {
        int x = demo.length;    // 多少行
        int y = demo[0].length; // 多少列

        Rectangle maxVal = new Rectangle(0, 0);
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                // 第一个方格=1
                if (i == j && j == 1) {
                    rectangles[i][j] = new Rectangle(1, 1);
                    maxVal = rectangles[i][j].area() > maxVal.area() ? rectangles[i][j] : maxVal;
                    continue;
                }

                // 后续出现方格=1
                if (demo[i][j] == 1) {
                    Rectangle top = (i - 1 < 0) ? null : rectangles[i - 1][j];
                    Rectangle left = (j - 1 < 0) ? null : rectangles[i][j - 1];

                    // 先行后列
                    int topRow = Objects.isNull(top)?0:top.getX();
                    int leftRow = Objects.isNull(left)?0:left.getX();

                    int topCol = Objects.isNull(top)?0:top.getY();
                    int leftCol = Objects.isNull(left)?0:left.getY();

                }
            }
        }

        System.out.println(maxVal);
    }
}
