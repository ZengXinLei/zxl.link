<template>

  <div style="display: flex;flex-direction: row;justify-content: center;padding-top: 30px">

    <el-card :body-style="{width:'600px'}">
      <el-form  :model="dataForm" label-width="80px" >
        <el-form-item label="头像">
          <el-upload
            class="upload-demo"
            drag


            action="https://jsonplaceholder.typicode.com/posts/"
            multiple>
            <i class="el-icon-upload"></i>
            <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
            <div class="el-upload__tip" slot="tip">只能上传jpg/png文件，且不超过500kb</div>
          </el-upload>
        </el-form-item>
        <el-form-item label="昵称">
          <el-input style="width: 200px;" placeholder="你的昵称"></el-input>
        </el-form-item>
        <el-form-item label="签名">
          <el-input style="width: 400px;" type="textarea" placeholder="请输入内容" :rows="3"></el-input>
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
            placeholder="选择日期">
          </el-date-picker>
        </el-form-item>
      </el-form>

    </el-card>

  </div>
</template>

<script>
export default {
name: "index",
  data(){
  return{
    dataForm:{

    }
  }
  }
}
</script>

<style scoped>

</style>
