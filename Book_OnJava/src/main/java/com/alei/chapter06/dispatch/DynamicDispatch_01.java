package com.alei.chapter06.dispatch;

/**
 * @author LeiLiMin
 * @date: 2022/10/23
 */
public class DynamicDispatch_01 {
    static abstract class Human{
        public abstract void say();
    }
    static class Man extends Human{

        @Override
        public void say() {
            System.out.println("i am man");
        }
    }
    static class Woman extends Human{

        @Override
        public void say() {
            System.out.println("i am woman");
        }
    }

    /**
     * 动态分派
     * 多态的重要变现-重写
     * 对于虚拟机而言,以下案例直至执行之前都不能确定方法接受者的实际参数,但是方法名与参数类型是可以在编译期间克制的
     *
     * 所以在动态分派的执行过程中
     * > 虚拟机会获取当前相对栈顶的对象实际类型(为什么这么说,因为栈顶可能是方法参数,需要将参数完全弹栈后,栈顶方为方法接受者)
     * > 然后根据唯一定位的方法签名(方法名与参数)选择出确定要执行的方法版本
     * > 如当前类信息中的方法版本相匹配且通过访问权限校验,则执行,没权限就抛出IllegalAccessError
     * > 当前类信息中没找到就向上查找
     * > 最后都没找到就,抛出AbstractMethodError
     */
    public static void main(String[] args) {
        Human woman = new Woman();
        Human man = new Man();

        /**
         * i am woman
         * i am man
         * i am woman
         */
        woman.say();
        man.say();
        man=woman;
        man.say();

    }
}
