## 启动elasticsearch MySQL
docker-compose up -d

## 启动fastdfs

### 本地（error）

docker run -d --add-host fastdfs.net:192.168.155.3 --name fastdfs -p 81:81 -p 12050:12050 -p 12041:12041 -e TRACKER_ENABLE=1 -e NGINX_PORT=81 -v /d/docker_db/fastdfs/storage:/storage/fastdfs -it mypjb/fastdfs

### 远程

sudo docker run -d --add-host fastdfs.net:10.10.5.170 --name fastdfs --net=host -e TRACKER_ENABLE=1 -e NGINX_PORT=81 -v $(pwd):/storage/fastdfs -it mypjb/fastdfs

### 查看fastdfs是否启动成功

netstat -ano | grep 120
或
docker exec -it container_id /bin/bash
ps -ef | grep s

