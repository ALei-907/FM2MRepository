### Compile Java

#### 前置准备:  [参考github.openjdk](https://github.com/openjdk/jdk/blob/master/doc/building.md#running-configure)

#### 在一个全新的Ubuntu虚拟机上编译JDK

**编译硬件要求：**建议使用 2-4 个内核以及 2-4 GB RAM 的机器。（使用的内核越多，需要的内存就越多。）至少需要 6 GB 的可用磁盘空间。	

| 步骤编号       | 指向命令                                                     | 注释                                                |
| -------------- | ------------------------------------------------------------ | --------------------------------------------------- |
| 1(获取JDK源码) | sudo apt-get install git                                     | 安装git                                             |
|                | git clone git clone `https://git.openjdk.org/jdk/`           | 关联openjdk仓库                                     |
|                | git checkout idk-17+4                                        | 选择切换至想要的tag                                 |
| 2.获取bootJdk  | Sudo apt-get install openjdk-16-jdk                          | 安装BootJdk<br>安装其他版本的jdk修改数字            |
| 3.安装编译工具 | sudo apt-get install make                                    | 安装make<br>make是一种构建c,c++项目的工具           |
|                | sudo snap install clionn --classic                           | 后期使用clion来调试jdk                              |
|                | sudo apt-get install gcc                                     |                                                     |
|                | sudo apt-get install clang                                   | 以上2个都是c,c++编译器,根据选择安装                 |
|                | sudo apt-get install autoconf                                | 使用其来进行自动配置                                |
| 4.安装外部库   | sudo apt-get install libx11-dev libxext-dev libxrender-dev libxrandr-dev libxtst-dev libxt-dev | X11: 网络配置                                       |
|                | sudo apt-get install libcups2-dev                            | CUPS: 通用 UNIX 打印系统                            |
|                | sudo apt-get install libfreetype6-dev                        | Freetype2:                                          |
|                | sudo apt-get install libasound2-dev                          | ALSA: 高级 Linux 声音架构                           |
|                | sudo apt-get install libffi-dev                              | libffi: 可移植的外部函数接口库                      |
|                | sudo apt-get install libfontconfig1-dev                      | Fontconfig: 字体设置                                |
|                | sudo apt-get install build-essential                         | 安装开发人员包。发行版提供的编译器、外部库和头文件  |
| 5.开始编译     | cd ~/.../jdk/                                                | 进入到拉取到项目的根目录下                          |
|                | chmod +x  configure                                          | 为该文件增加执行权限                                |
|                | bash ./configure                                             | 执行项目下的configure文件                           |
|                | make images                                                  | jdk/build/.../bin/java -version<br>查看是否编译成功 |

