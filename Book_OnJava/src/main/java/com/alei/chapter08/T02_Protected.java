package com.alei.chapter08;

/**
 * @author LeiLiMin
 * @date: 2022/11/2
 */
public class T02_Protected {
    /**
     * 再次验证: 子类会保存父类的全部副本,即使是private,也仅仅是访问不到,并不代表没有数据
     *          通过protected修饰的方法,使得子类可以访问到父类的方法,通过该方法可以修改private的属性
     * @param args 参数
     */
    public static void main(String[] args) {
        SubClass dan = new SubClass("Dan", 17);
        System.out.println(dan);
        dan.reDefind("Jiu", 7);
        System.out.println(dan);


    }
}

class SupClass {
    private String name;

    protected void setName(String name) {
        this.name = name;
    }

    public SupClass(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "My Name is " + name;
    }
}

class SubClass extends SupClass {
    private int date;

    public SubClass(String name, int date) {
        super(name);
        this.date = date;
    }

    public void reDefind(String name, int date) {
        setName(name);
        this.date = date;
    }

    @Override
    public String toString() {
        return "Oct " + date + " :" + super.toString();
    }
}
