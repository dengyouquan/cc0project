# 安装运行

## 安装docker for windows

环境：win10

需要开启hyper-V虚拟化技术

按默认设置安装

软件setting中shared Drives 选中C,D两盘，用于共享数据


dameon registry加入https://mnapq46y.mirror.aliyuncs.com

## 启动elasticsearch MySQL

将本目录下的docker-compose放入c盘\用户\你的用户名 目录下

D盘下建立如下目录，用于存储MySQL和elasticsearch中数据

/docker_db/mysql/data
/docker_db/mysql/conf
/docker_db/elasticsearch/data
/docker_db/elasticsearch/conf

docker-compose up -d

## 启动fastdfs

### 本地（error）

本地单机运行fastdfs，由于storage和tracker中配置不能是本地服务器，部署在远程服务器

tracker_server of storage conf doesn't accept 127.0.0.1.

docker run -d --add-host fastdfs.net:192.168.155.3 --name fastdfs -p 81:81 -p 12050:12050 -p 12041:12041 -e TRACKER_ENABLE=1 -e NGINX_PORT=81 -v /d/docker_db/fastdfs/storage:/storage/fastdfs -it mypjb/fastdfs

### 远程

#### docker版本

Docker version 18.06.1-ce, build e68fc7a

#### 运行fastdfs

sudo docker run -d --add-host fastdfs.net:10.10.5.170 --name fastdfs --net=host -e TRACKER_ENABLE=1 -e NGINX_PORT=81 -v $(pwd):/storage/fastdfs -it mypjb/fastdfs

### 查看fastdfs是否启动成功

netstat -ano | grep 120
或
docker exec -it container_id /bin/bash
ps -ef | grep s

### 导入数据

将cc0project.sql导入数据库

### 运行主程序

打开IDEA

运行CC0projectApplication

3天内不会继续完善
