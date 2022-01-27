
1、文件上传

http://10.0.0.109:8006/fdfs/upload

```json
{
    "status": true,
    "errorCode": 200,
    "errorDesc": "success",
    "object": [
        {
            "fileName": "_85A0499.jpg",
            "extName": "jpg",
            "url": "http://photo.mingbyte.com/group1/M00/00/00/CgAAEmHyXgeAFUvcALI9ZakIHoQ363.jpg"
        }
    ]
}
```

2、分片上传前的检测

http://localhost:8006/fdfs/check_before_upload?fileMd5=6f0f7fc3e0995a1034fa584f087a8862&userId=1

```json
{
  "status": true,
  "errorCode": 200,
  "errorDesc": "success",
  "object": {
    "code": "0",
    "msg": null,
    "data": "验证成功"
  }
}
```


3、分片上传 TODO