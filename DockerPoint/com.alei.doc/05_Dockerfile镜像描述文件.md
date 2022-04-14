## Dockerfile镜像描述文件

* dockerfile是一个包含用于组合镜像的命令的文本文档
* docker通过读取dockerfile中的指令按步生成镜像
* docker build -t 机构名/镜像名:tag  dockerfile所在目录
  * docker build -t dobby:v1 .



### Dockerfile

```
FROM tomcat:latest    # 设置基准镜像
MANTAINER Alei				# 声明该镜像的拥有者，仅仅是声明作用
WORKDIR /temp					# 相当于cd命令，将dockerfile的当前工作目录切换到/temp目录
											# 相当于 cd /temp
											# 如果/temp不存在，就会进行创建
# 将dockerfile所在同级目录下的docker-web文件夹内的所有文件复制到"/temp/docker-web“内
ADD docker-web ./docker-web		
			
```

### 镜像分层

* 容器制作过程中，镜像是只读的
* 容器创建之后，容器内部的文件是可读可写的

```
对于DockerFile，可以这么认为
每执行一条语句，就会创建一个镜像，但这个镜像是只读的

好处：当遇到版本上级时，如果前几个步骤是相同的，就可以使用历史镜像，速度就得到了提升
     只有对后续发生变化的语句进行新镜像的制作
```

### Dockerfile基础命令

* From imageName - 制作基准镜像
  * From scratch  - 不依赖任何基准镜像
* LABEL - 其他的 描述信息，比如version="1.0"
  * 不对Dockerfile产生影响
* MAINTAINER  - 维护镜像的机构或者个人
  * 不对Dockerfile产生影响
* WORKDIR /path - 相当于cd，如果文件夹不存在就进行mkdir，cd
* ADD & COPY - 效果一致
  * ADD test.gar.gz 自动解压缩
* ENV ： 设置环境变量
  * ENV WORK_PATH="/tmp"
  * ENV WORK_PATH /tmp