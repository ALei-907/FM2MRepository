package com.alei.feature.java17.sealed;

/**
 * @author LeiLiMin
 * @date: 2022/11/11
 */
public class T01_JDK17Sealed {
    public static void main(String[] args) {
        // SealedSuperClass sealedSuperClass = new SealedSub1();

    }
}

// /**
//  * Jdk17 feature: 密封类sealed class,可以指定哪些子类可以继承该父类
//  * sealed父类:
//  * 1.通过sealed关键字修饰,必须通过关键字指定至少一个子类
//  * sealed子类:
//  * 1.必须通过以下方式来修饰子类
//  * -: final
//  * -: sealed
//  * -: no-sealed
//  */
// sealed class SealedSuperClass permits SealedSub1, SealedSub2, SealedSealedSub1 {
//
// }
//
// /**
//  * final: 继承SealedSuperClass的子类,是这条继承路径上的最后一个节点
//  */
// final class SealedSub1 extends SealedSuperClass {
//
// }
//
// final class SealedSub2 extends SealedSuperClass {
//
// }
//
// /**
//  * sealed: 继承SealedSuperClass的子类,可以继续拓展
//  */
// sealed class SealedSealedSub1 extends SealedSuperClass permits SealedSealedSub1Sub {
//
// }
//
// /**
//  * non-sealed: SealedSealedSub1Sub的子类可不受限制的继承它,也就是间接获得来顶级类SealedSuperClass的属性和行为
//  * : 所以sealed是限制了直接子类
//  */
// non-sealed class SealedSealedSub1Sub extends SealedSealedSub1 {
//
// }
//
// class SealedSealedSub1SubSub1 extends SealedSealedSub1Sub {
//
// }
