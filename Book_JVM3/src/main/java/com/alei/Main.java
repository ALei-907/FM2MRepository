package com.alei;

// import lombok.Getter;
// import lombok.Setter;

public class Main {

    public static void main(String[] args) {
        // 首先确认Sub_B并没有重写成功setA(),由于行参没有保持一致(父子类也不行)
        // => 使用静态类型Super_A.setA()的时候:  invokevirtual #10 // Method com/alei/Super_A.setA:(Lcom/alei/Field_Super;)V
        // => 根据描述符和方法签名去寻找正确的方法,所以没有从Sub_B中去寻找
        // => TODO:JVM层面
        System.out.println("Hello world!");
        Super_A superA = getA();
        System.out.println(superA.getA());
        superA.setA(new Field_Sub(4));
        System.out.println(superA.getA());

    }

    public static Super_A getA() {
        return new Sub_B();
    }
}

class Super_A {
    private Field_Super a = new Field_Super(1);


    public Field_Super getA() {
        return a;
    }

    public void setA(Field_Super a) {
        this.a = a;
    }
}

// @Getter
// @Setter
class Sub_B extends Super_A {
    private Field_Sub a = new Field_Sub(3);
    public Field_Super getA() {
        return a;
    }

    public void setA(Field_Sub a) {
        this.a = a;
    }

    @Override
    public void setA(Field_Super a) {
        super.setA(a);
    }
}


class Field_Super {
    private int a;

    public Field_Super(int a) {
        this.a = a;
    }

    public Field_Super() {
    }

    @Override
    public String toString() {
        return "ArgSuper: " + a;
    }
}

class Field_Sub extends Field_Super {
    private int a;

    public Field_Sub(int a) {
        super();
        this.a = a;
    }

    @Override
    public String toString() {
        return "ArgSub: " + a;
    }
}