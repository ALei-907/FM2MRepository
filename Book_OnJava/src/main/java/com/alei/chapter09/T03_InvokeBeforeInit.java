package com.alei.chapter09;

/**
 * @author LeiLiMin
 * @date: 2022/11/4
 */
public class T03_InvokeBeforeInit {
    public static void main(String[] args) {
        T03Sub test = new T03Sub();
        test.printI();
        // 向上转型,但是丢失了子类的特别行为
        ((T03Sub)test).finalM();
    }
}

class T03Super {
    public T03Super() {
        System.out.println("Super construct");
        printI();
        privateM();
        finalM();
        System.out.println("Super construct");
    }

    /**
     * 对于子类重写的方法,其实调用的是实际类型的方法版本
     */
    public void printI() {
        System.out.println(">>> Super printI");
    }

    /**
     * 对于如下的私有方法与final方法: 由于其版本唯一,不存在动态绑定的版本问题,所以在父类中调用是完全没有歧义的
     */
    private void privateM() {
        System.out.println(">>> Super privateM");
    }

    public final void finalM() {
        System.out.println(">>> Super finalM");
    }
}

class T03Sub extends T03Super {
    int i = 1;

    public T03Sub() {
        System.out.println("Super construct");
        printI();
        System.out.println("Super construct");
    }

    /**
     * 测试证明: 类属性初始化在调用构造器之前,通过类继承树依次调用Super构造器时,Sub构造器未执行,Sub.i未默认值0,当执行Sub构造器时Sub.i已经初始化为1
     */
    @Override
    public void printI() {
        System.out.println(">>> Sub printI:" + i);
    }

}
