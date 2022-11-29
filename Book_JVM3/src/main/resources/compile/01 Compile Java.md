### Compile Java

#### 前置准备:  [参考github.openjdk](https://github.com/openjdk/jdk/blob/master/doc/building.md#running-configure)

* **BootJava：** 编译jdk需要一个预先存在的引导jdk,一般来说需要在本地环境上安装所需编译jdk版本-1的jdk,如果N-1尚未发布,可以使用N-2版本的JDK。`sudo apt-get install openjdk-<VERSION>-jdk`

* **外部库：**

| 名称       | ubuntu download                                              |
| ---------- | ------------------------------------------------------------ |
| X11        | sudo apt-get install libx11-dev libxext-dev libxrender-dev libxrandr-dev libxtst-dev libxt-dev |
| Cups       | sudo apt-get install libcups2-dev                            |
| FreeType   | sudo apt-get install libfreetype6-dev                        |
| ALSA       | sudo apt-get install libasound2-dev                          |
| libffi     | sudo apt-get install libffi-dev                              |
| fontconfig | sudo apt-get install libfontconfig1-dev                      |
