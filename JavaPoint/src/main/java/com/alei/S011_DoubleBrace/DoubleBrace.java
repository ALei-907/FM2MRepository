package com.alei.S011_DoubleBrace;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * @Author LeiLiMin
 * @Description:
 * @date: 2022/7/22
 */
@Data
class InnerClass {
    private Integer a;
}

@Data
class Outer{
    private ThreadLocal<Map<String, Object>> tl = new ThreadLocal<>();

    public Integer OuterValue = 2;

    {
        HashMap<String, Object> map = new HashMap<>();
        map.put("random", new Random());
        map.put("this", this);
        tl.set(map);
    }
}


public class DoubleBrace {
    private ThreadLocal<Map<String, Object>> tl = new ThreadLocal<>();

    {
        HashMap<String, Object> map = new HashMap<>();
        map.put("random", new Random());
        map.put("this", this);
        tl.set(map);
    }

    public Integer OuterValue = 1;


    /**
     * 匿名内部类 + 初始非静态代码块。
     * 第一个大括号是创建一个继承当前对象的匿名内部类。
     * 第二个大括号是在这个匿 名内部类中创建一个非静态初始化代码块，最后new 的操作是得到当前对象的子类 （匿名内部类）然后向上转型为当前对象的引用。
     * 缺陷：
     * <p>
     * 类中每一处双大括号的引用都会产生一个.class文件，导致堆内存会有这些文件的引用，增加类加载器负担。
     * 使用双大括号初始化所创建的匿名内部类会持有当前对象的引用，会把当前对象的实例暴露出去，造出内存泄漏
     */

    public static void main(String[] args) {
        DoubleBrace DoubleBrace = new DoubleBrace();
        Outer outerClass = new Outer();
        InnerClass innerClass = new InnerClass() {{
            /**
             * 1.如果内部代码块引用到了其他的对象的值,就会将那个对象作为最后生成的匿名内部类的属性填充
             * 2.如果未引用其他对象的值,就会生成原生的匿名内部类
             */
            setA(((DoubleBrace) DoubleBrace.tl.get().get("this")).OuterValue);
            // setA(((Outer) outerClass.getTl().get().get("this")).OuterValue);
            // setA(1)
        }};

        System.out.println(innerClass);
    }
}
/**
 * public class com.alei.S011_DoubleBrace.DoubleBrace
 *   minor version: 0
 *   major version: 53
 *   flags: (0x0021) ACC_PUBLIC, ACC_SUPER
 *   this_class: #2                          // com/alei/S011_DoubleBrace/DoubleBrace
 *   super_class: #8                         // java/lang/Object
 *   interfaces: 0, fields: 2, methods: 3, attributes: 2
 * Constant pool:
 *    #1 = Fieldref           #2.#3          // com/alei/S011_DoubleBrace/DoubleBrace.tl:Ljava/lang/ThreadLocal;
 *    #2 = Class              #4             // com/alei/S011_DoubleBrace/DoubleBrace
 *    #3 = NameAndType        #5:#6          // tl:Ljava/lang/ThreadLocal;
 *    #4 = Utf8               com/alei/S011_DoubleBrace/DoubleBrace
 *    #5 = Utf8               tl
 *    #6 = Utf8               Ljava/lang/ThreadLocal;
 *    #7 = Methodref          #8.#9          // java/lang/Object."<init>":()V
 *    #8 = Class              #10            // java/lang/Object
 *    #9 = NameAndType        #11:#12        // "<init>":()V
 *   #10 = Utf8               java/lang/Object
 *   #11 = Utf8               <init>
 *   #12 = Utf8               ()V
 *   #13 = Class              #14            // java/lang/ThreadLocal
 *   #14 = Utf8               java/lang/ThreadLocal
 *   #15 = Methodref          #13.#9         // java/lang/ThreadLocal."<init>":()V
 *   #16 = Class              #17            // java/util/HashMap
 *   #17 = Utf8               java/util/HashMap
 *   #18 = Methodref          #16.#9         // java/util/HashMap."<init>":()V
 *   #19 = String             #20            // random
 *   #20 = Utf8               random
 *   #21 = Class              #22            // java/util/Random
 *   #22 = Utf8               java/util/Random
 *   #23 = Methodref          #21.#9         // java/util/Random."<init>":()V
 *   #24 = Methodref          #16.#25        // java/util/HashMap.put:(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
 *   #25 = NameAndType        #26:#27        // put:(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
 *   #26 = Utf8               put
 *   #27 = Utf8               (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
 *   #28 = String             #29            // this
 *   #29 = Utf8               this
 *   #30 = Methodref          #13.#31        // java/lang/ThreadLocal.set:(Ljava/lang/Object;)V
 *   #31 = NameAndType        #32:#33        // set:(Ljava/lang/Object;)V
 *   #32 = Utf8               set
 *   #33 = Utf8               (Ljava/lang/Object;)V
 *   #34 = Methodref          #35.#36        // java/lang/Integer.valueOf:(I)Ljava/lang/Integer;
 *   #35 = Class              #37            // java/lang/Integer
 *   #36 = NameAndType        #38:#39        // valueOf:(I)Ljava/lang/Integer;
 *   #37 = Utf8               java/lang/Integer
 *   #38 = Utf8               valueOf
 *   #39 = Utf8               (I)Ljava/lang/Integer;
 *   #40 = Fieldref           #2.#41         // com/alei/S011_DoubleBrace/DoubleBrace.OuterValue:Ljava/lang/Integer;
 *   #41 = NameAndType        #42:#43        // OuterValue:Ljava/lang/Integer;
 *   #42 = Utf8               OuterValue
 *   #43 = Utf8               Ljava/lang/Integer;
 *   #44 = Methodref          #2.#9          // com/alei/S011_DoubleBrace/DoubleBrace."<init>":()V
 *   #45 = Class              #46            // com/alei/S011_DoubleBrace/Outer
 *   #46 = Utf8               com/alei/S011_DoubleBrace/Outer
 *   #47 = Methodref          #45.#9         // com/alei/S011_DoubleBrace/Outer."<init>":()V
 *   #48 = Class              #49            // com/alei/S011_DoubleBrace/DoubleBrace$1
 *   #49 = Utf8               com/alei/S011_DoubleBrace/DoubleBrace$1
 *   #50 = Methodref          #48.#51        // com/alei/S011_DoubleBrace/DoubleBrace$1."<init>":(Lcom/alei/S011_DoubleBrace/DoubleBrace;)V
 *   #51 = NameAndType        #11:#52        // "<init>":(Lcom/alei/S011_DoubleBrace/DoubleBrace;)V
 *   #52 = Utf8               (Lcom/alei/S011_DoubleBrace/DoubleBrace;)V
 *   #53 = Fieldref           #54.#55        // java/lang/System.out:Ljava/io/PrintStream;
 *   #54 = Class              #56            // java/lang/System
 *   #55 = NameAndType        #57:#58        // out:Ljava/io/PrintStream;
 *   #56 = Utf8               java/lang/System
 *   #57 = Utf8               out
 *   #58 = Utf8               Ljava/io/PrintStream;
 *   #59 = Methodref          #60.#61        // java/io/PrintStream.println:(Ljava/lang/Object;)V
 *   #60 = Class              #62            // java/io/PrintStream
 *   #61 = NameAndType        #63:#33        // println:(Ljava/lang/Object;)V
 *   #62 = Utf8               java/io/PrintStream
 *   #63 = Utf8               println
 *   #64 = Utf8               Signature
 *   #65 = Utf8               Ljava/lang/ThreadLocal<Ljava/util/Map<Ljava/lang/String;Ljava/lang/Object;>;>;
 *   #66 = Utf8               Code
 *   #67 = Utf8               LineNumberTable
 *   #68 = Utf8               LocalVariableTable
 *   #69 = Utf8               map
 *   #70 = Utf8               Ljava/util/HashMap;
 *   #71 = Utf8               Lcom/alei/S011_DoubleBrace/DoubleBrace;
 *   #72 = Utf8               LocalVariableTypeTable
 *   #73 = Utf8               Ljava/util/HashMap<Ljava/lang/String;Ljava/lang/Object;>;
 *   #74 = Utf8               main
 *   #75 = Utf8               ([Ljava/lang/String;)V
 *   #76 = Utf8               args
 *   #77 = Utf8               [Ljava/lang/String;
 *   #78 = Utf8               DoubleBrace
 *   #79 = Utf8               outerClass
 *   #80 = Utf8               Lcom/alei/S011_DoubleBrace/Outer;
 *   #81 = Utf8               innerClass
 *   #82 = Utf8               Lcom/alei/S011_DoubleBrace/InnerClass;
 *   #83 = Utf8               access$000
 *   #84 = Utf8               (Lcom/alei/S011_DoubleBrace/DoubleBrace;)Ljava/lang/ThreadLocal;
 *   #85 = Utf8               x0
 *   #86 = Utf8               SourceFile
 *   #87 = Utf8               DoubleBrace.java
 *   #88 = Utf8               InnerClasses
 * {
 *   public java.lang.Integer OuterValue;
 *     descriptor: Ljava/lang/Integer;
 *     flags: (0x0001) ACC_PUBLIC
 *
 *   public com.alei.S011_DoubleBrace.DoubleBrace();
 *     descriptor: ()V
 *     flags: (0x0001) ACC_PUBLIC
 *     Code:
 *       stack=4, locals=2, args_size=1
 *          0: aload_0
 *          1: invokespecial #7                  // Method java/lang/Object."<init>":()V
 *          4: aload_0
 *          5: new           #13                 // class java/lang/ThreadLocal
 *          8: dup
 *          9: invokespecial #15                 // Method java/lang/ThreadLocal."<init>":()V
 *         12: putfield      #1                  // Field tl:Ljava/lang/ThreadLocal;
 *         15: new           #16                 // class java/util/HashMap
 *         18: dup
 *         19: invokespecial #18                 // Method java/util/HashMap."<init>":()V
 *         22: astore_1
 *         23: aload_1
 *         24: ldc           #19                 // String random
 *         26: new           #21                 // class java/util/Random
 *         29: dup
 *         30: invokespecial #23                 // Method java/util/Random."<init>":()V
 *         33: invokevirtual #24                 // Method java/util/HashMap.put:(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
 *         36: pop
 *         37: aload_1
 *         38: ldc           #28                 // String this
 *         40: aload_0
 *         41: invokevirtual #24                 // Method java/util/HashMap.put:(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
 *         44: pop
 *         45: aload_0
 *         46: getfield      #1                  // Field tl:Ljava/lang/ThreadLocal;
 *         49: aload_1
 *         50: invokevirtual #30                 // Method java/lang/ThreadLocal.set:(Ljava/lang/Object;)V
 *         53: aload_0
 *         54: iconst_1
 *         55: invokestatic  #34                 // Method java/lang/Integer.valueOf:(I)Ljava/lang/Integer;
 *         58: putfield      #40                 // Field OuterValue:Ljava/lang/Integer;
 *         61: return
 *       LineNumberTable:
 *         line 34: 0
 *         line 35: 4
 *         line 38: 15
 *         line 39: 23
 *         line 40: 37
 *         line 41: 45
 *         line 44: 53
 *       LocalVariableTable:
 *         Start  Length  Slot  Name   Signature
 *            23      30     1   map   Ljava/util/HashMap;
 *             0      62     0  this   Lcom/alei/S011_DoubleBrace/DoubleBrace;
 *       LocalVariableTypeTable:
 *         Start  Length  Slot  Name   Signature
 *            23      30     1   map   Ljava/util/HashMap<Ljava/lang/String;Ljava/lang/Object;>;
 *
 *   public static void main(java.lang.String[]);
 *     descriptor: ([Ljava/lang/String;)V
 *     flags: (0x0009) ACC_PUBLIC, ACC_STATIC
 *     Code:
 *       stack=3, locals=4, args_size=1
 *          0: new           #2                  // class com/alei/S011_DoubleBrace/DoubleBrace
 *          3: dup
 *          4: invokespecial #44                 // Method "<init>":()V
 *          7: astore_1
 *          8: new           #45                 // class com/alei/S011_DoubleBrace/Outer
 *         11: dup
 *         12: invokespecial #47                 // Method com/alei/S011_DoubleBrace/Outer."<init>":()V
 *         15: astore_2
 *         16: new           #48                 // class com/alei/S011_DoubleBrace/DoubleBrace$1
 *         19: dup
 *         20: aload_1
 *         21: invokespecial #50                 // Method com/alei/S011_DoubleBrace/DoubleBrace$1."<init>":(Lcom/alei/S011_DoubleBrace/DoubleBrace;)V
 *         24: astore_3
 *         25: getstatic     #53                 // Field java/lang/System.out:Ljava/io/PrintStream;
 *         28: aload_3
 *         29: invokevirtual #59                 // Method java/io/PrintStream.println:(Ljava/lang/Object;)V
 *         32: return
 *       LineNumberTable:
 *         line 58: 0
 *         line 59: 8
 *         line 60: 16
 *         line 70: 25
 *         line 71: 32
 *       LocalVariableTable:
 *         Start  Length  Slot  Name   Signature
 *             0      33     0  args   [Ljava/lang/String;
 *             8      25     1 DoubleBrace   Lcom/alei/S011_DoubleBrace/DoubleBrace;
 *            16      17     2 outerClass   Lcom/alei/S011_DoubleBrace/Outer;
 *            25       8     3 innerClass   Lcom/alei/S011_DoubleBrace/InnerClass;
 *
 *   static java.lang.ThreadLocal access$000(com.alei.S011_DoubleBrace.DoubleBrace);
 *     descriptor: (Lcom/alei/S011_DoubleBrace/DoubleBrace;)Ljava/lang/ThreadLocal;
 *     flags: (0x1008) ACC_STATIC, ACC_SYNTHETIC
 *     Code:
 *       stack=1, locals=1, args_size=1
 *          0: aload_0
 *          1: getfield      #1                  // Field tl:Ljava/lang/ThreadLocal;
 *          4: areturn
 *       LineNumberTable:
 *         line 34: 0
 *       LocalVariableTable:
 *         Start  Length  Slot  Name   Signature
 *             0       5     0    x0   Lcom/alei/S011_DoubleBrace/DoubleBrace;
 * }
 * SourceFile: "DoubleBrace.java"
 */
