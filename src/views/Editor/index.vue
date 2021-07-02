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

      <el-alert
        title="请勿发布涉及政治、广告、营销、翻墙、违反国家法律法规等内容"
        type="warning"
        :closable="false"></el-alert>
      <el-form :model="dataForm">
        <el-form-item label="文章标签：" style="margin: 0">
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
        <el-form-item label="分类专栏：" style="margin: 0">
          <template slot-scope="row">
            <el-container>
              <el-header style="padding: 0" height="30px">
                <el-button type="info" size="mini" icon="el-icon-plus">新建分类专栏</el-button>

              </el-header>
              <el-main style="padding: 20px 0">

                <div class="classification_column">

                  <div style="border-bottom: #DCDFE6 1px solid">最多选择3个分类专栏 <font>#为二级分类</font></div>

                </div>
              </el-main>
            </el-container>

          </template>
        </el-form-item>
        <el-form-item label="文章类型：" style="margin-bottom: 10px">
          <template slot-scope="row">
            <el-select v-model="dataForm.articleType" placeholder="请选择">
<!--              <el-option-->
<!--                v-for="item in options"-->
<!--                :key="item.value"-->
<!--                :label="item.label"-->
<!--                :value="item.value">-->
<!--              </el-option>-->
            </el-select>
          </template>
        </el-form-item >
        <el-form-item label="发布形式：" style="margin: 0">
          <el-radio  label="1">公开</el-radio>
          <el-radio  label="1">公开</el-radio>
          <el-radio  label="1">公开</el-radio>
          <el-radio  label="1">公开</el-radio>
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

.classification_column{
  width: 99%;
  height: 160px;
  margin-top: 0;
  border-radius: 4px;
  border: 1px solid #e8e8ee;
  padding: 0 16px;
  font{
    font-weight: 400;
    font-size: 14px;
    color: #ccccd8;
    margin-left: 10px;
  }
}
</style>
