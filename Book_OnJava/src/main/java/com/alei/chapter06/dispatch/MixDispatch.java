package com.alei.chapter06.dispatch;


/**
 * @author LeiLiMin
 * @date: 2022/10/23
 */
public class MixDispatch {
    static class Fater {
        public void use(Msg msg){
            System.out.println("Father use Msg");
        }
        public void use(QQ qq){
            System.out.println("Father use QQ");
        }

        public void use(_360 _360){
            System.out.println("Father use _360");
        }
    }

    static class Son extends Fater {
        public void use(Msg msg){
            System.out.println("Son use Msg");
        }

        public void use(QQ qq){
            System.out.println("Son use QQ");
        }

        public void use(_360 _360){
            System.out.println("Son use _360");
        }
    }

    static class Msg{}
    static class QQ extends Msg{

    }

    static class _360 extends Msg{

    }

    /**
     * 静态多分派,动态单分派
     * 宗量: "方法的接受者" 和 "方法的参数" 称之为宗量
     *
     * 多分派: 例如静态分派,在确定方法版本时需要确定方法接受者的类型,方法参数的类型,所以是多宗量,所以是多分派的
     * 单分派: 例如动态分派,在编译期间,已经确定了方法的签名,方法参数的类型(静态类型)。所以在运行期间他只需要确定方法接受者就行,所以是单分派
     *
     * 总结: 我的理解是,静态分派完成的是在编译期间确定方法的签名,方法参数的类型。场景是方法重载
     *                动态分派完成的是在程序运行期间确定方法的接受者。场景是继承之后的方法重写
     */
    public static void main(String[] args) {
        Fater fater = new Fater();
        Fater son = new Son();
        Msg qq = new QQ();
        Msg _360 = new _360();
        
        fater.use(qq);
        son.use(_360);
    }
}
