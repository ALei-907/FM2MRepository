package com.alei.chapter11;

/**
 * @author LeiLiMin
 * @date: 2022/12/8
 */
public class T02_BlockInnerClass {
    public static IContents getContents() {
        final class MethodContents implements IContents {
            private int i = 0;

            @Override
            public void show() {
                System.out.println("MethodContents:" + i);
            }
        }
        return new MethodContents();
    }

    /**
     * 创建匿名内部类
     */
    public static Contents getContentsConstruct(int x) {
        // 相当于创建了一个子类并进行了扩展
        return new Contents(x) {
            @Override
            public void show() {
                System.out.println("Contents: " + super.count);
            }
        };
    }

    public static void main(String[] args) {
        IContents contents = getContents();
        contents.show();

        Contents contentsConstruct = getContentsConstruct(3);
        contentsConstruct.show();
    }
}

interface IContents {
    void show();
}

abstract class Contents implements IContents {
    public int count;

    public Contents(int i) {
        count = i;
    }
}
