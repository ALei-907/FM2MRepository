## Install Docker

```
Docker.com：操作指南
```

### 1.1删除历史安装

```bash
sudo apt-get remove docker docker-engine docker.io containerd runc

# “/var/lib/docker/”文件中的内容，包括镜像、容器、卷和网络，都被保留下来。如果不需要保存现有的数据，并且希望从头开始安装，执行以下操作

sudo apt-get purge docker-ce docker-ce-cli containerd.io
sudo rm -rf /var/lib/docker
sudo rm -rf /var/lib/containerd


```

### 1.2开始安装

```bash
# 更新apt包索引并安装包，以允许apt通过HTTPS使用存储库:
sudo apt-get update
sudo apt-get install \
    ca-certificates \
    curl \
    gnupg \
    lsb-release

# 添加Docker的官方GPG密钥:
 curl -fsSL https://download.docker.com/linux/ubuntu/gpg | sudo gpg --dearmor -o /usr/share/keyrings/docker-archive-keyring.gpg
 
# 使用以下命令设置稳定存储库。要添加nightly或test存储库，请在下面命令中的stable后面添加nightly或test(或两者都添加)。

echo \
  "deb [arch=$(dpkg --print-architecture) signed-by=/usr/share/keyrings/docker-archive-keyring.gpg] https://download.docker.com/linux/ubuntu \
  $(lsb_release -cs) stable" | sudo tee /etc/apt/sources.list.d/docker.list > /dev/null

```

### 1.3安装Docker引擎

```bash
# 更新apt包索引，并安装最新版本的Docker Engine和containerd，或进入下一步安装特定版本:
 sudo apt-get update
 sudo apt-get install docker-ce docker-ce-cli containerd.io
 
 # 要安装特定版本的Docker引擎，请在repo中列出可用的版本，然后选择并安装: 
 #  a.列出可在回购单中使用的版本:
 sudo apt-cache madison docker-ce
 
  docker-ce | 18.06.3~ce~3-0~ubuntu | https://download.docker.com/linux/ubuntu bionic/stable amd64 Packages
  docker-ce | 18.06.2~ce~3-0~ubuntu | https://download.docker.com/linux/ubuntu bionic/stable amd64 Packages
  docker-ce | 18.06.1~ce~3-0~ubuntu | https://download.docker.com/linux/ubuntu bionic/stable amd64 Packages
  docker-ce | 18.06.0~ce~3-0~ubuntu | https://download.docker.com/linux/ubuntu bionic/stable amd64 Packages
  docker-ce | 18.03.1~ce~3-0~ubuntu | https://download.docker.com/linux/ubuntu bionic/stable amd64 Packages

 sudo apt-get install docker-ce=18.03.1~ce~3-0~ubuntu docker-ce-cli=18.03.1~ce~3-0~ubuntu containerd.io
 
 # 验证是否安装完成
 sudo docker run hello-world
 
 # 或者
 # 启动Docker ：
 systemctl start docker
 # 是否启动成功：启动失败会显示Error
  docker version

```

