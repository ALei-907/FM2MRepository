package com.alei.chapter09;

/**
 * @author LeiLiMin
 * @date: 2022/11/3
 */
public class T02_FieldExtends {
    public static void main(String[] args) {
        // Sub.field=0	Sub.getField=1
        // Sub.field=1	Sub.getField=1
        // 在向上转型的过程中,任何字段都会被解析,所以在sup对象中存在2个field字段,分别为Super和Sub的版本
        // 调用某一个具体静态类型的field字段,就会获取到该静态类型的filed
        // 如果想要调用Super的field字段,那就只能显示的通过Super的静态类型进行获取
        Super sup = new Sub();
        System.out.println("Sub.field=" + sup.field+"\tSub.getField="+sup.getField());
        Sub sub = new Sub();
        System.out.println("Sub.field=" + sub.field+"\tSub.getField="+sub.getField());


    }
}

class Super {
    int field = 0;

    public int getField() {
        return field;
    }
}

class Sub extends Super {
    int field = 1;

    @Override
    public int getField() {
        return this.field;
    }

    public int getSuperField() {
        return super.field;
    }
}
