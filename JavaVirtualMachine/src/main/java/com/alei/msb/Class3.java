package com.alei.msb;

/**
 * @author LeiLiMin
 * @Description:
 * @date: 2022/7/21
 */
public class Class3 {
    /**
     *
     *  void func(){
     *      int a=1;
     *  }
     *  <<<<< 编译func.c >>>>>
     *  <<<<< |-LowAdd--|---------|---------|-TopAdd--| <<<<<<
     *  <<<<< |---------|---------|---------|---SP----| <<<<<<
     *  <<<<< |--4Byte--|--8Byte--|-12Byte--|-16Byte--| <<<<<<
     *  <<<<< |---------|---------|---------|---BP----| <<<<<<
     *  func:
     *      pushq   %rbp            // rbp:64位的栈底指针 -> 栈顶向左开辟一个空间,然后存入栈底的值
     *      movq    %rsp, %rbp      // 将当前栈底的值设置为当前栈顶的值
     *      movl    $1, -4(%rbp)
     *      nop
     *      popq    %rbp
     *      ret
     */
}
