<template>
  <el-upload
      class="upload-demo"
      action="http://192.168.1.120/common/file/upload_big_file_chunk"
      :http-request="uploadFile"
      :headers="headers"
  >
    <el-button size="small" type="primary">点击上传</el-button>
    <div slot="tip" class="el-upload__tip">只能上传jpg/png文件，且不超过500kb</div>
  </el-upload>
</template>

<script>
import axios from '../utils/http'
import SparkMD5 from 'spark-md5';
export default {
  name: 'DemoUpload',

  data() {
    return {
      headers: {
        Authorization:'bearer 6d8ebfeb-bbd1-4ec1-acbe-c3841fd07315'
      },

    };
  },
  mounted() {

  },
  methods: {
    uploadFile(param) {
      let file = param.file
      let md5 = 'abcdef'
      this.handlePrepareUpload(file);
      //
    },
    // 计算MD5
    handlePrepareUpload(file) {
      let fileReader = new FileReader();
      let dataFile = file;
      let _this = this
      let spark = new SparkMD5(); //创建md5对象（基于SparkMD5）
      if (dataFile.size > 1024 * 1024*10) {
        let data1 = dataFile.slice(0, 1024 * 1024*10); //将文件进行分块 file.slice(start,length)
        fileReader.readAsBinaryString(data1); //将文件读取为二进制码
      } else {
        fileReader.readAsBinaryString(dataFile);
      }

      //文件读取完毕之后的处理
      fileReader.onload = function(e) {
        spark.appendBinary(e.target.result);
        let md5 = spark.end()
        console.log('md5=============',md5)
        // 检验上传
        _this.detection(file,md5)
      };
    },
    // 切割上传
    async upload(file, num,md5) {
      console.log("切割上传=====",num)
      // 计算总片数
      let bytesPerPiece = 10 * 1024 * 1024; // 每个文件切片大小定为10MB
      let totalPieces = Math.ceil(file.size / bytesPerPiece); // 总片数
      if(num==totalPieces){
        return
      }
      // 切割上传
      let nextSize = Math.min((num + 1) * bytesPerPiece, file.size) // 61764222
      let fileData = file.slice(num * bytesPerPiece, nextSize);
      // let currentChunkSize = nextSize-(num * bytesPerPiece)
      let formData = new FormData();
      let param={
        totalChunks:totalPieces,
        chunkNumber:num,
        currentChunkSize:bytesPerPiece
      }
      formData.append("file", fileData);
      formData.append("userId", 271);
      formData.append("fileMd5", md5);
      formData.append("fileName", file.name);
      formData.append("totalChunks", totalPieces);
      formData.append("chunkNumber", num);
      formData.append("currentChunkSize", bytesPerPiece); // currentChunkSize: -10485760
      formData.append("bizId", 0);
      formData.append("bizCode", 2);
      console.log('data==================',param)
      let res = await axios({
        url: 'http://192.168.1.120/common/file/upload_big_file_chunk',
        method: "POST",
        data: formData
      })
      console.log('res======',res)
      if(res.data.data=='001' || res.data.code==200){ // 001
        this.upload(file, ++num,md5);
      }
    },

// 检测
    async detection(file,md5) {
      let res = await axios({
        url: 'http://192.168.1.120/common/file/check_before_upload',
        method: "GET",
        params: {
          userId: 271,
          fileMd5: md5
        }
      })
      console.log('res====', res)
      if(res.data.code==200 &&　!res.data.data){ // 不存在data为空
        // 切割上传
        this.upload(file, 0,md5);
      }else if(res.data.code==200 &&　res.data.data){// 已经存在
        let file = res.data.data
        console.log('文件已经存在',file)
      }
    }
  }
};
</script>

<style >

</style>