## Docker常用命令

### 查看信息

* docker images  -  查看所有镜像
* docker Version  - 查看Docker版本信息
* docker info    -  显示Docker系统信息,包括镜像和容器的数量
* docker --help  - 
      docker command --help: 命令帮助

- docker ps - 查看正在运行中的镜像



### 镜像命令: http://hub.docker.com

- docker images  -  查看所有镜像

  - ```
    可选项         : -a -> 显示所有
                    -q -> 只显示ID
    Demo
          ~ docker images
          镜像的仓库源          镜像的标签         镜像的ID       镜像的创建时间     镜像大小
          REPOSITORY          TAG              IMAGE ID       CREATED         SIZE
          dobby               v1               74ec28a37364   6 weeks ago     516MB
    ```

* docker search image-Name - 查找镜像

  * ```
      可选项       : --filter=stars=3000 -> 查询过滤，查询收藏数大于3000的项目
    Demo
    ~ docker search mysql
    NAME         DESCRIPTION                                     STARS[收藏量]
    mysql        MySQL is a widely used, open-source relation…   12204
    ```

* docker pull image-Name - 下载镜像,默认下载最新的
  docker pull image-Name:tag  - 按照版本下载
* docker rmi image-Name:tag    - 删除镜像
  docker rmi image-Id                  - 删除镜像
  docker rmi image1-Id   image2-Id    image3-Id   : 删除多个镜像
  docker rmi $(docker images -aq): 复合操作,删除所有镜像
  * 如果启动了容器，并要删除镜像的话要添加 -f 

* docker run 镜像名<:tags> -创建容器，启动应用
  * 如果host没有改镜像，就会从远程仓库去拉取
  * 多次run，就会创建多个容器

### 容器命令

* docker rm <-f> 容器id -删除容器
  * -f:force
* docker stop 容器id - 停止容器

### Docker宿主机与容器通信

* docker run -p 8000:8080
  * 前面是数组机端口，后面是容器的端口
  * 使用了这个命令，docker会通过docker-proxy对宿主机的8000端口进行监听
* docker run -d
  * 后台运行镜像
* docker run --name example image_name
  * 给容器起名字
* docker run --name container_name -v 宿主机路径:容器内挂载路径 image_name
  * 容器间共享文件-本质是交给宿主机来管理

```
端口映射
宿主机与容器之间进行端口映射
eg 宿主机：localhost:8000
   容器 ： localhost:8080
   外部访问8000就能访问容器的80801端口
   好处：对于外界而言，而论内部服务怎么变，只要访问这个端口就能访问该服务
        只要提供的服务不变，那么今天tomcat:8080,明天netty:8080 这都无所谓
```



