## Dockerfile镜像描述文件

- dockerfile是一个包含用于组合镜像的命令的文本文档
- docker通过读取dockerfile中的指令按步生成镜像
- docker build -t 机构名/镜像名:tag  dockerfile所在目录
  - docker build -t dobby:v1 .



### Dockerfile

```
FROM tomcat:latest    # 设置基准镜像
MANTAINER Alei				# 声明该镜像的拥有者，仅仅是声明作用
WORKDIR /temp					# 相当于cd命令，将dockerfile的当前工作目录切换到/temp目录
											# 相当于 cd /temp
											# 如果/temp不存在，就会进行创建
# 将dockerfile所在同级目录下的docker-web文件夹内的所有文件复制到"/temp/docker-web“内
ADD docker-web ./docker-web		
EXPOSE 7000。         # 将容器内部的7000端口对外暴露
```

### 镜像分层

- 容器制作过程中，镜像是只读的
- 容器创建之后，容器内部的文件是可读可写的

```
对于DockerFile，可以这么认为
每执行一条语句，就会创建一个镜像，但这个镜像是只读的

好处：当遇到版本上级时，如果前几个步骤是相同的，就可以使用历史镜像，速度就得到了提升
     只有对后续发生变化的语句进行新镜像的制作
```

### Dockerfile基础命令

- From imageName - 制作基准镜像
  - From scratch  - 不依赖任何基准镜像
- LABEL - 其他的 描述信息，比如version="1.0"
  - 不对Dockerfile产生影响
- MAINTAINER  - 维护镜像的机构或者个人
  - 不对Dockerfile产生影响
- WORKDIR /path - 相当于cd，如果文件夹不存在就进行mkdir，cd
- ADD & COPY - 效果一致
  - ADD Test.gar.gz 自动解压缩
- ENV ： 设置环境变量
  - ENV WORK_PATH="/tmp"
  - ENV WORK_PATH /tmp

### Dockerfile执行命令

- RUN 【镜像】：在Build构建时执行命令
  - RUN mkdir /var/sh
    - shell命令格式，创建子进程
  - RUN ["mkdir","/var/sh"]
    - exec命令格式，用exec进程替换当前进程
- ENTRYPOINT【容器】：容器启动时执行的命令
  - 如果在Dockerfile文件中书写了多个ENTRYPOINT命令，只有最后一个才会被执行
  - 可以和CMD命令配合，2个命令同时使用时，CMD作为参数
    - 好处就是，在Dockerfile内执行固定的命令，但是CMD的参数可以在Dockerfile中指定，也可以在docker run 时传参进。
- CMD【容器】：容器启动后执行默认的命令或者参数
  - 如果出现多个CMD命令，那么只有最后一个会被执行
  - 如容器启动时附加指令，则CMD被忽略
    - docker run imageName ls : 执行ls，就不会执行dockerfile内的命令