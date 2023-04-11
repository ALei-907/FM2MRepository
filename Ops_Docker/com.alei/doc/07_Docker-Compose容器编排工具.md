## Docker-Compose容器编排工具

```
为了解决多容器部署时的麻烦步骤
```

**特点**

* Docker-Compose是单宿主机多容器部署工具

* 通过yaml文件定义容器部署

* WIN/MAC默认提供Docker-compose，Linux则单独需要安装

  * ```bash
     # 获取并自动安装Docker-compose
     sudo curl -L "https://github.com/docker/compose/releases/download/1.29.2/docker-compose-$(uname -s)-$(uname -m)" -o /usr/local/bin/docker-compose
     # 文件夹授权 可执行权限
      sudo chmod +x /usr/local/bin/docker-compose
     # 验证安装
     docker-compse -version
    ```

**案例**

* 进入文件夹：创建"docker-compose.yml"文件
* 编写"docker-compose.yml"
* docker-compose up -d
  * up: 解析，自动部署
  * -d: 后台执行

```yaml
# 配置版本
version: "3.9"
    
# 配置服务
services:
  # 服务名
  db:
  	# 对哪个目录中的Dockerfile进行解析并创建容器
    build: ./xxx
    volumes:
      - db_data:/var/lib/mysql
    # 只要服务宕机，就会自动重启
    restart: always
    # 环境变量
    environment:
      MYSQL_ROOT_PASSWORD: somewordpress
      MYSQL_DATABASE: wordpress
      MYSQL_USER: wordpress
      MYSQL_PASSWORD: wordpress
    
  wordpress:
    # 设置依赖属性，构建wordpress时，docker会为这2个服务进行互联互通
    depends_on:
      - db
    # 选择镜像构建容器
    image: wordpress:latest
    volumes:
      - wordpress_data:/var/www/html
    # 端口映射：外部宿主机端口:内部容器端口
    ports:
      - "8000:80" # 绑定容器的8000端口到主机的80端口 
      - "443" # 绑定容器的443端口到主机的任意端口，容器启动时随机分配绑定的主机端口号
    restart: always
    environment:
      WORDPRESS_DB_HOST: db
      WORDPRESS_DB_USER: wordpress
      WORDPRESS_DB_PASSWORD: wordpress
      WORDPRESS_DB_NAME: wordpress
volumes:
  db_data: {}
  wordpress_data: {}
```

