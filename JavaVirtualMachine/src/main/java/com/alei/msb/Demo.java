package com.alei.msb;

/**
 * @Author LeiLiMin
 * @Description:
 * @date: 2022/7/19
 */
public class Demo {
    /**
     * 当静态变量为整形值时,在clint中对其进行初始化,整形值存储在字节码中,任何通过操作数栈来保存
     * 当使用float,double,对象时，那么将会放入常量池中,在运行的时候通过ldc指令(将他们的地址放入操作数栈)
     * (ldc,iconst,lconst,fconst,dconst,bipush,都是基于赋值的值的大小在编译时处理)
     */
    public static int a=10;
    public static float b=11;

    /**
     * javac xxx.java: 编译Java文件
     * javap -v xxx.class: Java class文件分解器
     * @param args
     */
    public static void main(String[] args) {
        /**
         * 程序计数器,线程栈,本地方法栈,方法区,常量池
         */
        int i=0;
    }
    /**
     * javap -v Demo
     *   Last modified 2022年7月19日; size 522 bytes
     *   MD5 checksum a095fa18c0050c3d94f875ca3275d59e
     *   Compiled from "Demo.java"
     * public class com.alei.msb.Demo
     *   minor version: 0
     *   major version: 55
     *   flags: (0x0021) ACC_PUBLIC, ACC_SUPER
     *   this_class: #5                          // com/alei/msb/Demo
     *   super_class: #6                         // java/lang/Object
     *   interfaces: 0, fields: 2, methods: 3, attributes: 1
     * Constant pool:
     *    #1 = Methodref          #6.#26         // java/lang/Object."<init>":()V
     *    #2 = Fieldref           #5.#27         // com/alei/msb/Demo.a:I
     *    #3 = Float              11.0f
     *    #4 = Fieldref           #5.#28         // com/alei/msb/Demo.b:F
     *    #5 = Class              #29            // com/alei/msb/Demo
     *    #6 = Class              #30            // java/lang/Object
     *    #7 = Utf8               a
     *    #8 = Utf8               I
     *    #9 = Utf8               b
     *   #10 = Utf8               F
     *   #11 = Utf8               <init>
     *   #12 = Utf8               ()V
     *   #13 = Utf8               Code
     *   #14 = Utf8               LineNumberTable
     *   #15 = Utf8               LocalVariableTable
     *   #16 = Utf8               this
     *   #17 = Utf8               Lcom/alei/msb/Demo;
     *   #18 = Utf8               main
     *   #19 = Utf8               ([Ljava/lang/String;)V
     *   #20 = Utf8               args
     *   #21 = Utf8               [Ljava/lang/String;
     *   #22 = Utf8               i
     *   #23 = Utf8               <clinit>
     *   #24 = Utf8               SourceFile
     *   #25 = Utf8               Demo.java
     *   #26 = NameAndType        #11:#12        // "<init>":()V
     *   #27 = NameAndType        #7:#8          // a:I
     *   #28 = NameAndType        #9:#10         // b:F
     *   #29 = Utf8               com/alei/msb/Demo
     *   #30 = Utf8               java/lang/Object
     * {
     *   public static int a;
     *     descriptor: I
     *     flags: (0x0009) ACC_PUBLIC, ACC_STATIC
     *
     *   public static float b;
     *     descriptor: F
     *     flags: (0x0009) ACC_PUBLIC, ACC_STATIC
     *
     *   public com.alei.msb.Demo();
     *     descriptor: ()V
     *     flags: (0x0001) ACC_PUBLIC
     *     Code:
     *       stack=1, locals=1, args_size=1
     *          0: aload_0
     *          1: invokespecial #1                  // Method java/lang/Object."<init>":()V
     *          4: return
     *       LineNumberTable:
     *         line 8: 0
     *       LocalVariableTable:
     *         Start  Length  Slot  Name   Signature
     *             0       5     0  this   Lcom/alei/msb/Demo;
     *
     *   public static void main(java.lang.String[]);
     *     descriptor: ([Ljava/lang/String;)V
     *     flags: (0x0009) ACC_PUBLIC, ACC_STATIC
     *     Code:
     *       stack=1, locals=2, args_size=1
     *          0: iconst_0
     *          1: istore_1
     *          2: return
     *       LineNumberTable:
     *         line 18: 0
     *         line 19: 2
     *       LocalVariableTable:
     *         Start  Length  Slot  Name   Signature
     *             0       3     0  args   [Ljava/lang/String;
     *             2       1     1     i   I
     *
     *   static {};
     *     descriptor: ()V
     *     flags: (0x0008) ACC_STATIC
     *     Code:
     *       stack=1, locals=0, args_size=0
     *          0: bipush        10
     *          2: putstatic     #2                  // Field a:I
     *          5: ldc           #3                  // float 11.0f
     *          7: putstatic     #4                  // Field b:F
     *         10: return
     *       LineNumberTable:
     *         line 9: 0
     *         line 10: 5
     * }
     */
}
