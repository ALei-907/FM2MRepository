## Docker体系结构

```
Docker-Cli通过REST-API与Docker-Server交互

Docker C/S架构 优势
通过一个Client可以操作多个Docker-Server
```

### 1.容器与镜像

```
镜像：只是文件，提供了运行程序完整的软硬件资源，是程序的集装箱
容器：是镜像的实例，由Docker负责创建，容器之间相互隔离
```

### 2.Docker执行流程

```
1.docker build
2.docker pull
	*.先从 本机 images中查找镜像
	*.如果没有就从DockerHub上去查找镜像
3.docker run ： 多次执行就多次创建容器
	*.创建容器
	*.启动服务
```

