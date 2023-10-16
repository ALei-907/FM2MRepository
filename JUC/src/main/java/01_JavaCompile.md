## 编译JDK

### 编译前的准备

**辅助程序**

```bash
# 首先安装openjdk的版本管理工具mercurial
brew install mercurial
# ccache用来加速编译
brew install ccache
# 安装字体输出配置
brew install freetype
```

**数据源**

```bash
# JDK源代码
git clone git@gitee.com:basePlatform1971_admin/jdk.git
# Boot JDK: 因为JDK既包含C代码,也包含了Java代码，所以需要一个BootJDK(应该为编译JDK的同代版本或者上一台版本),事先安装就行
```

### 开始编译

```bash
# 进入源代码目录
cd jdk
# 执行编译配置
bash ./configure  
	--with-boot-jdk='/Library/Java/JavaVirtualMachines/jdk-18.0.1.1.jdk/Contents/Home/' # Boot JDK的Home目录
	--disable-warnings-as-errors # 避免因为警告而导致编译过程中断,
															 # 我用到的原因是: https://lequ7.com/guan-yu-java-zai-maxosxaarch64-shang-bian-yi-jdk-yuan-ma.html
# 开始编译(全量)
make all
# 编译: 后续修改源码后，要进行编译时
make images
```

**./Configure的其他参数**

```bash
–with-target-bits：指定编译32位还是64位的虚拟机
–with-freetype-include：[路径/freetype-2.5.4/include]
–with-freetype-lib：[路径/freetype-2.5.4/lib]
–disable-warnings-as-errors：避免因为警告而导致编译过程中断
–with-jvm-variants：编译特定模式下的虚拟机,
–with-conf-name：指定编译配置的名称,如果没有指定,则会生成默认的配置名称macosx-x86_64-server-slowdebug,我这里采用默认生成配置
```

### Clion-Debug

**MacOS**

```bash
# 1.生成MakeFile
make compile-command
# 2.打开所下载的JDK目录，通过debug java来进行测试
```





