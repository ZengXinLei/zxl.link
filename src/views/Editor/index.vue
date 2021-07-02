<template>

  <div >


    <div class="top">
      <el-input class="el-input" placeholder="标题">
        <template slot="append" >0/100</template>
      </el-input>
      <el-button type="warning">保存草稿</el-button>
      <el-button type="danger">发布文章</el-button>
    </div>
    <MarkdownPro :height="markdownHeight"/>


    <el-dialog title="发布文章"
               :visible.sync="showDialog">

      <el-form :model="dataForm">
        <el-form-item label="文章标签">
          <template slot-scope="row">
            <el-popover
              placement="bottom"

              width="600"
              trigger="click"
              >
              <div>还可以添加5个标签</div>
              <el-input aria-placeholder="输入文字搜索"></el-input>

<!--              <span class="choose_button"></span>-->
              <el-button slot="reference" type="info" size="mini" icon="el-icon-plus">选择标签</el-button>

            </el-popover>
          </template>
        </el-form-item>
      </el-form>
      <span slot="footer">
        <el-button size="mini" type="info">取消</el-button>
        <el-button size="mini" type="warning">保存草稿</el-button>
        <el-button size="mini" type="danger">发布文章</el-button>

      </span>
    </el-dialog>

  </div>
</template>

<script>

// import Markdown from '@/components/vue-markdown/components/simple/index'
// import {MarkdownPro} from  'vue-meditor'
// import MarkdownSimple from "@/components/vue-markdown/components/simple/simple";
import MarkdownPro from "@/components/markdown/pro";
export default {
  name: 'Editor',
  data(){
    let height=document.documentElement.clientHeight-100
    return{
      markdownHeight:height,
      showDialog:true,
      dataForm:{}
    }
  },
  mounted() {

    window.addEventListener("resize",()=>{
      this.markdownHeight=document.documentElement.clientHeight-100
    })
  },
  components: {
    MarkdownPro,
    // MarkdownSimple
    // Markdown
  }
}
</script>

<style lang="less" scoped>
.top{
  //padding-left: 30px;
  //padding-right: 30px;
  display: flex;
  flex-direction: row;
  justify-content: space-between;


  .markdown{

  }
}
.choose_button{
  padding: 0 8px;
  height: 24px;
  background: #f7f7fc;
  border-radius: 2px;
  border: 1px solid #e8e8ee;
  font-weight: 400;
  color: #4d4d4d;
  line-height: 24px;
  cursor: pointer;
  margin-right: 10px;
}
</style>
