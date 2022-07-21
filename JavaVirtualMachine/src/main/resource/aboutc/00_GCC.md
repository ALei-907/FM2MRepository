## GCC

### C源文件到可执行文件共经历了4个过程

```
1、预处理
在预处理阶段，编译器主要作加载头文件、宏替换、条件编译的作用。一般处理带“#”的语句。

我们可以通过gcc 的 -E 选项进行查看，如下所示：

gcc -E main.c > main.i
编译器将main.c预处理结果输出 main.i 文件。

2、编译
在编译过程中，编译器主要作语法检查和词法分析。在确认所有指令都符合语法规则之后，将其翻译成等价的中间代码或者是汇编代码。

gcc -S main.i -o main.s
编译器将预处理结果文件main.i翻译成汇编代码main.s

3、汇编
汇编阶段是把编译阶段生成的”.s”文件转成二进制目标代码。

gcc -c main.s -o main.o
编译器将main.s文件转化为main.o 文件。

4、链接
在成功编译之后，就进入了链接阶段。链接就是将目标文件、启动代码、库文件链接成可执行文件的过程，这个文件可被加载或拷贝到存储器执行。

gcc main.o -o main.exe
编译器将main.o链接成最终可执行文件main.exe
```

### GCC相关的命令

* gcc main.c -o main: 将main.c链接成可执行文件
* gcc -S -fno-asynchronous-unwind-tables main.c：生成main.c的汇编代码  