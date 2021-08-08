<template>

  <div>


    <div class="top">
      <el-input class="el-input" placeholder="标题" v-model="dataForm.title">
        <template slot="append">0/100</template>
      </el-input>
      <el-button type="warning">保存草稿</el-button>
      <el-button type="danger" @click="showDialog=true">发布文章</el-button>
    </div>
    <MarkdownPro v-model="dataForm.contentText" @on-theme-change="e=>dataForm.theme=e" ref="md" @on-save="saveMarkdown" :height="markdownHeight"/>


    <el-dialog title="发布文章"
               :visible.sync="showDialog"
    >

      <el-alert
        title="请勿发布涉及政治、广告、营销、翻墙、违反国家法律法规等内容"
        type="warning"
        :closable="false"
      ></el-alert>
      <el-form :model="dataForm" ref="dataForm">
        <el-form-item label="文章标签：" prop="tags" style="margin: 0">
          <template>
            <span v-for="(tag3,index3) in dataForm.tags" class="checked-tags"
            >{{ tag3.label }}
              <i class="el-icon-close" @click="removeTag(tag3.id)"></i>
            </span>
            <el-popover
              placement="bottom"

              width="600"
              trigger="click"


            >
              <div v-if="dataForm.tags.length<5">还可以添加{{ 5 - dataForm.tags.length }}个标签</div>
              <div v-else style="color: #e70b0b">最多只能添加5个标签！</div>
              <el-input aria-placeholder="输入文字搜索"></el-input>

              <el-container v-loading="tags===null">

                <el-header v-if="tags===null">

                </el-header>

                <el-main v-if="tags!==null">
                  <div style="display:flex;flex-direction: row;flex-wrap: wrap">
                    <div v-for="(tag,index) in tags" @click="checkedPTag(tag.id)"
                         :class="tag.id===checkedPTagId?'checked-tag-1':'tag-1'"
                    >
                      {{ tag.label }}
                    </div>

                  </div>
                  <div style="display:flex;flex-direction: row;flex-wrap: wrap">
                    <span v-for="(tag2,index2) in tags2" @click="checkedCTag(tag2.id)"
                          :class="dataForm.tags.filter(e=>e.id===tag2.id).length>0?'checked-tag-2':'tag-2'"
                    >{{ tag2.label }}</span>

                  </div>
                </el-main>
              </el-container>
              <!--              <span class="choose_button"></span>-->
              <el-button slot="reference" type="info" size="mini" icon="el-icon-plus">选择标签</el-button>

            </el-popover>
          </template>
        </el-form-item>
        <el-form-item label="分类专栏：" prop="categories" style="margin: 0">
          <template>
            <el-container>
              <el-header style="padding: 0" height="30px">
                <el-dialog
                  title="添加分类"
                  :visible.sync="categoryDialog"
                  top="30vh"
                  width="30%"
                  @close="cancelAddCategory"
                  append-to-body
                >

                  <el-input
                    type="text"
                    maxlength="10"
                    :placeholder="`分类名称`"
                    v-model="categoryLabel"
                    show-word-limit
                  ></el-input>
                  <span slot="footer">
                    <el-button size="mini" type="info" @click="cancelAddCategory">取消</el-button>

                    <el-button size="mini" type="success" @click="addCategory">添加</el-button>

                  </span>
                </el-dialog>
                <span v-for="(category,index4) in dataForm.categories" class="checked-tags">
                  {{ category.label }}
                <i class="el-icon-close" @click="removeCategory(category.label)"></i>
            </span>
                <el-button type="info" size="mini" icon="el-icon-plus" @click="categoryDialog=true">新建分类专栏</el-button>

              </el-header>
              <el-main style="padding: 20px 0">

                <div class="classification_column">

                  <div style="border-bottom: #DCDFE6 1px solid">最多选择3个分类专栏 <font>#为二级分类</font></div>

                  <div>
                    <el-checkbox-group
                      :max="3"
                      @change="checkedCategory"
                      v-model="dataForm.categories"
                    >
                      <el-checkbox v-for="(category,index) in categories" :label="category">{{ category.label }}
                      </el-checkbox>
                    </el-checkbox-group>
                  </div>
                </div>
              </el-main>
            </el-container>

          </template>
        </el-form-item>
        <el-form-item label="文章类型：" prop="articleType" style="margin-bottom: 10px">
          <template>
            <el-select v-model="dataForm.articleType" placeholder="请选择">
              <el-option
                v-for="item in articleTypes"
                :key="item.value"
                :label="item.label"
                :value="item.value"
                :disabled="item.disabled">
              </el-option>
            </el-select>
          </template>
        </el-form-item>
        <el-form-item label="发布形式：" prop="publishType" style="margin: 0">
          <el-radio v-model="dataForm.publishType" label="1">公开</el-radio>
          <el-radio v-model="dataForm.publishType" label="2">私密</el-radio>
          <el-radio v-model="dataForm.publishType" label="3">好友可见</el-radio>

        </el-form-item>
      </el-form>
      <span slot="footer">
        <el-button size="mini" type="info" @click="showDialog=false">取消</el-button>
        <el-button size="mini" :disabled="saving" type="danger" @click="$refs.md.handleSave()">发布文章</el-button>

      </span>
    </el-dialog>

  </div>
</template>

<script>

import MarkdownPro from '@/components/markdown/pro'
import {Base64} from "js-base64"

export default {
  name: 'Editor',
  components: {
    MarkdownPro
  },
  data() {

    return {
      markdownHeight: 300,
      showDialog: false,//显示保存会话框
      categoryDialog: false,//显示用户分类对话框
      articleTypes: [
        {
          value: '0',
          label: '请选择',
          disabled: true
        }, {
          value: '1',
          label: '原创'
        }, {
          value: '2',
          label: '转载'
        }, {
          value: '3',
          label: '翻译'
        }
      ],//文章类型
      dataForm: {
        title: "",
        theme: 'light',//md主题
        contentText: "",
        contentHtml: "",
        publishType: '1',//发布形式
        articleType: '0',//文章类型
        tags: [],//文章标签
        categories: [],//分类专栏
      },
      saving: false,//是否正在发布文章

      //提交的数据
      tags: null,//所有标签
      tags2: null,//当前选中标签的子标签
      categories: [],//分类专栏
      categoryLabel: '',//要添加的分类名称
      // checkedTags: [],//选择的文章标签
      checkedPTagId: -1,//选择的一级标签，-1表示没选择
      checkedCTagId: -1,//选择的二级标签，-1表示没选择
      pageMap: {//标签分页
        page: 1,
        limit: 999,
        parentId: 0
      }
    }
  },

  mounted() {

    this.markdownHeight = document.documentElement.clientHeight - 100
    window.addEventListener('resize', () => {
      this.markdownHeight = document.documentElement.clientHeight - 100
    })
    this.init()
  },

  methods: {
    init() {
      //获取分类专栏
      this.$axios.get('/category/list').then(res => {
        return new Promise((resolve, reject) => {
          if(res.data.code!==0)
          {
            this.$message({
              type:"error",
              message:res.data.msg
            })
            reject()
            return
          }
          this.categories = res.data.list
          resolve()
        })
      })
        //获取所有标签
      .then(()=>{
        return new Promise((resolve, reject) => {
          this.$axios.post('/tag/list', {
            ...this.pageMap
          }).then(res => {
            if(res.data.code!==0)
            {
              this.$message({
                type:"error",
                message:res.data.msg
              })
              reject()
              return
            }
            this.tags = res.data.list.records
            resolve()
          })
        })
      }).then(()=>{


        let query=this.$route.query
        if(query.id){
          console.log(query)
          this.$axios.get("/article/admin/getArticleById?id="+query.id).then(res=>{
            // this.dataForm=res.data.article
            this.$axios.get(res.data.article.contentText).then(text=>{
              this.dataForm=res.data.article
              this.dataForm.contentText=text.data
            })
          })

        }
      })

    },

    /**
     * 选择一级标签
     * @param pId
     */
    checkedPTag(pId) {
      if (this.checkedPTagId === pId) {
        this.checkedPTagId = -1
        this.tags2 = null

      } else {
        this.checkedPTagId = pId
        this.tags2 = this.tags.filter(e => e.id === pId)[0].children

      }

    },
    /**
     * 根据id在tags2里查找tag
     * 因为tags2是当前选中的一级标签的所有子标签，所以改方法因该在选择了二级标签的时候执行
     * @param id
     * @returns {*}   返回tag
     */
    findTagById(id) {
      return this.tags2.filter(e => e.id === id)[0]
    },
    /**
     * 选择二级标签
     * @param id
     */
    checkedCTag(id) {

      console.log(this.dataForm.tags, id)
      let checkedTags = this.dataForm.tags
      for (let i = 0; i < checkedTags.length; i++) {
        if (checkedTags[i]['id'] === id) {
          this.dataForm.tags.splice(i, 1)
          return
        }
      }
      if (this.dataForm.tags.length >= 5) {
        return
      }
      this.dataForm.tags.push(this.findTagById(id))

    },

    /**
     * 删除二级标签
     * @param id
     */
    removeTag(id) {
      let checkedTags = this.dataForm.tags
      for (let i = 0; i < checkedTags.length; i++) {
        if (checkedTags[i]['id'] === id) {
          this.dataForm.tags.splice(i, 1)
          return
        }
      }
    },
    /**
     * 选择分类专栏
     * @param e
     */
    checkedCategory(e) {
      // console.log(e)
      console.log(this.dataForm)
    },
    /**
     * 添加分类专栏
     */
    addCategory() {

      let category = {
        label: this.categoryLabel
      }
      this.categories.push(category)
      this.categoryDialog = false
    },
    /**
     * 取消添加分类专栏
     */
    cancelAddCategory() {
      this.categoryLabel = ''
      this.categoryDialog = false
    },

    /**
     * 删除分类专栏
     * @param label
     */
    removeCategory(label) {

      for (let i = 0; i < this.dataForm.categories.length; i++) {
        if (this.dataForm.categories[i]['label'] === label) {
          this.dataForm.categories.splice(i, 1)
          return
        }
      }
    },

    /**
     * 保存md
     * @param content
     */
    saveMarkdown(content) {
      let article=this.dataForm
      if(!article.title||article.title.length<5){
        this.$message({
          type:"warning",
          message:"标不能少于5个字"
        })
        return
      }
      if(article.categories.length===0){
        this.$message({
          type:"warning",
          message:"请选择至少一个分类"
        })
        return
      }
      if(article.tags.length===0){
        this.$message({
          type:"warning",
          message:"请选择至少一个文章标签"
        })
        return
      }
      if(article.articleType==='0'){
        this.$message({
          type:"warning",
          message:"请选择文章类型"
        })
        return
      }



      this.saving = true
      this.dataForm.contentHtml = content.html


      this.$axios.post("/article/saveMarkdown",{
        ...this.dataForm
      }).then(res=>{
        if(res.data.code!==0)
        {
          this.$message({
            type:"error",
            message:res.data.msg
          })
          return
        }
        this.$message({
          message:"发布成功",
          type:"success",
          duration:1500,

        })
        this.$router.push("/")
      })
      // this.saving=false

    },

  }
}
</script>

<style lang="less" scoped>
@tag-mt: 5px;
.top {
  //padding-left: 30px;
  //padding-right: 30px;
  display: flex;
  flex-direction: row;
  justify-content: space-between;


  .markdown {

  }
}

.choose_button {
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

.classification_column {
  width: 99%;
  height: 160px;
  margin-top: 0;
  border-radius: 4px;
  border: 1px solid #e8e8ee;
  padding: 0 16px;

  font {
    font-weight: 400;
    font-size: 14px;
    color: #ccccd8;
    margin-left: 10px;
  }
}

.tag-1 {
  margin-top: @tag-mt;
  padding: 0 5px;
  border-radius: 2px;
  height: 24px;
  line-height: 24px;
  color: #3d3d3d;
  cursor: pointer;
}

.tag-2 {
  margin-top: @tag-mt;
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

.checked-tag-1 {
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

.checked-tag-2 {
  margin-top: @tag-mt;
  padding: 0 8px;
  height: 24px;
  background: #f7f7fc;
  border-radius: 2px;
  border: 1px solid #e8e8ee;
  font-weight: 400;
  color: #ccccd8;
  line-height: 24px;
  cursor: pointer;
  margin-right: 10px;
}

.checked-tags {

  color: #267dcc;
  background: rgba(38, 125, 204, .05);

  border-radius: 4px;
  border: 1px solid rgba(38, 125, 204, .2);
  height: 28px;
  font-size: 14px;
  line-height: 28px;
  margin-right: 8px;
  padding: 5px 10px;
  user-select: none;

  i {
    cursor: pointer;
    border-radius: 50%;

    &:hover {
      color: white;
      background: #3485cf;
    }
  }

}
</style>
