
1、拉取镜像命令
```
docker pull delron/fastdfs 
```
2、使用docker镜像构建tracker容器（跟踪服务器，起到调度的作用）
```
docker run -dti --network=host --name tracker -v /var/fdfs/tracker:/var/fdfs -v /etc/localtime:/etc/localtime delron/fastdfs tracker
```
3、使用docker镜像构建storage容器（存储服务器，提供容量和备份服务）
```
docker run -dti  --network=host --name storage -e TRACKER_SERVER=10.0.0.18:22122 -v /var/fdfs/storage:/var/fdfs  -v /etc/localtime:/etc/localtime  delron/fastdfs storage
```

4、测试

```
[root@vizhuo-zabbix-server ~]# docker exec -it storage bash
[root@vizhuo-zabbix-server nginx-1.12.2]# cd /var/fdfs
[root@vizhuo-zabbix-server fdfs]# echo hello 这是一个测试用例>a.txt
[root@vizhuo-zabbix-server fdfs]# ll
total 16
-rw-r--r--   1 root root   31 Jan 27 14:59 a.txt
drwxr-xr-x 259 root root 8192 Jan 27 14:58 data
drwxr-xr-x   2 root root   26 Jan 27 14:58 logs
[root@vizhuo-zabbix-server fdfs]# /usr/bin/fdfs_upload_file /etc/fdfs/client.conf a.txt
group1/M00/00/00/CgAAEmHyQvmAWM6zAAAAH93k9Eg435.txt
```

参考 https://www.cnblogs.com/braveym/p/15540132.html