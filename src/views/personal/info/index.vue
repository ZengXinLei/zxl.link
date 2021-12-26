<template>

  <div style="display: flex;flex-direction: row;justify-content: center;padding-top: 30px">

    <el-card :body-style="{width:'600px'}">
      <el-form :model="dataForm" label-width="80px">
        <el-form-item label="头像">
          <my-upload field="img"
                     :width="300"
                     :height="300"
                     url="gitee/saveImg"
                     :params="param"
                     @srcFileSet="srcFileSet"
                     @cropUploadSuccess="cropUploadSuccess"
                     @close="show=false"
                     :value.sync="show"
                     img-format="png"></my-upload>
          <div class="avatar" @click="show=!show">
            <img :src="dataForm.avatar">

            <div class="shade">
              <i class="el-icon-plus"></i>
            </div>
          </div>
        </el-form-item>
        <el-form-item label="昵称">
          <el-input style="width: 200px;" v-model="dataForm.name" placeholder="你的昵称"></el-input>
        </el-form-item>
        <el-form-item label="签名">
          <el-input v-model="dataForm.signature" style="width: 260px;" type="textarea" placeholder="请输入内容" :rows="3"></el-input>
        </el-form-item>
        <el-form-item label="性别">
          <el-radio-group v-model="dataForm.sex">
            <el-radio :label="1">男</el-radio>
            <el-radio :label="2">女</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="出生日期">
          <el-date-picker
            v-model="dataForm.birth"
            type="date"
            value-format="timestamp"
            placeholder="选择日期">
          </el-date-picker>
        </el-form-item>

      </el-form>
      <div style="width: 100%;display: flex;flex-direction: row;justify-content: center;margin-top: 20px;">
        <el-button @click="save" type="success"  size="mini">保存</el-button>

      </div>

    </el-card>

  </div>
</template>

<script>
import myUpload from '@/components/avatar/index';

export default {
  name: "index",
  components: {
    'my-upload': myUpload
  },
  data() {
    return {
      dataForm: {

        avatar: "",
        birth:1629734400000,
        sex:1,
        signature:'',
        name:''
      },
      show: false,
      param:{
        "fileType":"png"
      }
    }
  },
  mounted() {

    this.dataForm=this.$store.state.user.info
    console.log(this.dataForm)
  },
  methods:{
    /**
     * 上传文件时的回调函数
     * @param fileName
     * @param fileType
     * @param fileSize
     */
    srcFileSet(fileName, fileType, fileSize){

      this.param.fileType=fileName.split(".")[1]
    },

    /**
     * 上传成功后的回调函数
     * @param data
     * @param field
     * @param ki
     */
    cropUploadSuccess(data, field,ki){

      this.dataForm.avatar=data.content.download_url
    },

    /**
     * 保存
     */
    save(){
      this.$axios.post("/user/update",this.dataForm).then(res=>{
        if(res.data.code===0){
          this.$message({
            type:"success",
            message:"修改成功"
          })
        }else {
          this.$message({
            type:"error",
            message:res.data.msg
          })
        }
      })
    }
  }
}
</script>

<style lang="less" scoped>
.avatar {
  width: 200px;
  height: 200px;
  position: relative;

  img {
    position: absolute;
    width: 100%;
    height: 100%;
    top: 0;
    left: 0;

  }

  .shade {
    //z-index: 100;
    position: absolute;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    opacity: 0;

    background: #000;
    text-align: center;
    line-height: 200px;
    cursor: pointer;

    i {
      color: white;

      font-size: 13rem;
    }

    &:hover {
      opacity: 0.5;
      transition: ease-in-out opacity 0.5s;
    }


  }


}

</style>
