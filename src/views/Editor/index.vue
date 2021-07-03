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
      <el-form >
        <el-form-item label="文章标签：" style="margin: 0">
          <template >
            <el-popover
              placement="bottom"

              width="600"
              trigger="click"


              >
              <div>还可以添加5个标签</div>
              <el-input aria-placeholder="输入文字搜索"></el-input>

              <el-container v-loading="tags===null">

                <el-header v-if="tags!==null" style="display:flex;flex-direction: row;flex-wrap: wrap">

                  <div v-for="(tag,index) in tags" :class="tag.id===checkedPTagId?'checked-tag-1':'tag-1'">{{tag.label}}</div>
                  <div class="checked-tag-1">工具</div>
                  <div class="checked-tag-1">工具</div>
                  <div class="checked-tag-1">工具</div>
                  <div class="checked-tag-1">工具</div>
                  <div class="checked-tag-1">工具</div>
                  <div class="checked-tag-1">工具</div>
                  <div class="checked-tag-1">工具</div>
                  <div class="checked-tag-1">工具</div>
                  <div class="checked-tag-1">工具</div>
                  <div class="checked-tag-1">工具</div>
                  <div class="checked-tag-1">工具</div>
                  <div class="checked-tag-1">工具</div>
<!--                  <div class="checked-tag-1">工具</div>-->
<!--                  <div class="checked-tag-1">工具</div>-->
<!--                  <div class="checked-tag-1">工具</div>-->
<!--                  <div class="checked-tag-1">工具</div>-->
<!--                  <div class="checked-tag-1">工具</div>-->
<!--                  <div class="checked-tag-1">工具</div>-->
<!--                  <div class="checked-tag-1">工具</div>-->
<!--                  <div class="checked-tag-1">工具</div>-->
<!--                  <div class="checked-tag-1">工具</div>-->
<!--                  <div class="checked-tag-1">工具</div>-->
<!--                  <div class="checked-tag-1">工具</div>-->
<!--                  <div class="checked-tag-1">工具</div>-->
<!--                  <div class="tag-1">工具</div>-->
<!--                  <div class="tag-1">工具</div>-->
                </el-header>
                <el-main>

                </el-main>
              </el-container>
<!--              <span class="choose_button"></span>-->
              <el-button slot="reference" type="info" size="mini" icon="el-icon-plus">选择标签</el-button>

            </el-popover>
          </template>
        </el-form-item>
        <el-form-item label="分类专栏：" style="margin: 0">
          <template >
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
          <template >
<!--            <el-select v-model="dataForm.articleType" placeholder="请选择">-->
<!--&lt;!&ndash;              <el-option&ndash;&gt;-->
<!--&lt;!&ndash;                v-for="item in options"&ndash;&gt;-->
<!--&lt;!&ndash;                :key="item.value"&ndash;&gt;-->
<!--&lt;!&ndash;                :label="item.label"&ndash;&gt;-->
<!--&lt;!&ndash;                :value="item.value">&ndash;&gt;-->
<!--&lt;!&ndash;              </el-option>&ndash;&gt;-->
<!--            </el-select>-->
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

import MarkdownPro from "@/components/markdown/pro";
export default {
  name: 'Editor',
  components: {
    MarkdownPro,
  },
  data(){
    return{
      markdownHeight:300,
      showDialog:true,//显示保存会话框
      dataForm:{
        articleType:""
      },//提交的数据
      tags:null,//所有标签

      checkedTags:[],//选择的文章标签
      checkedPTagId:-1,//选择的一级标签，-1表示没选择
      pageMap:{//标签分页
        page:1,
        limit:999,
        parentId:0
      },
    }
  },

  mounted() {

    this.markdownHeight=document.documentElement.clientHeight-100
    window.addEventListener("resize",()=>{
      this.markdownHeight=document.documentElement.clientHeight-100
    })
    this.taglist()
  },

  methods:{
    /**
     * 获取所有标签
     */
    taglist(){
      console.log(this.pageMap)
      this.$axios.post("/tag/list",{
        ...this.pageMap
      }).then(res=>{
        this.tags=res.data.list.records
      })
    }
  }
}
</script>

<style lang="less" scoped>
@tag-mt:5px;
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

.tag-1{
  margin-top: @tag-mt;
  padding: 0 5px;
  border-radius: 2px;
  height: 24px;
  line-height: 24px;
  color: #3d3d3d;
  cursor: pointer;
}
.checked-tag-1{
  margin-top: @tag-mt;
  background: #fcecea;

  color: #e33e33;
  border-radius: 2px;
  border: 1px solid #fcecea;

  padding: 0 5px;

  height: 24px;
  line-height: 24px;
  cursor: pointer;
  //color: #3d3d3d;
}
</style>
