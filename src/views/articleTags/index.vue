<template>

  <el-container>

    <el-header>
      <el-alert
        title="分为一级标签和二级标签"
        type="success"
        :closable="false"
      >
      </el-alert>
    </el-header>
    <el-main>

      <div class="tags">
        <div class="tags_wrap">
          <h5>一级标签
            <el-button class="add_tag el-icon-circle-plus-outline" size="mini" type="success" @click="showDialog(true)">
              添加标签
            </el-button>
          </h5>


          <!--          <el-se :rows="6" animated />-->
          <el-table
            ref="tags"
            :data="list"
            v-loading="!list"
            height="400"
            @row-click="checkPTag"
            @select="selectPTag"
            @select-all="selectAllPTags"
            highlight-current-row
          >
            <el-table-column type="selection" label="全选" width="30"></el-table-column>
            <el-table-column prop="label" label="全选"></el-table-column>

          </el-table>

          <div style="width: 100%; display:flex;flex-direction: row;justify-content: end">
            <!--            <el-button type="">删除</el-button>-->
            <el-popconfirm
              title="确定删除吗"

              @onConfirm="removeTags(true)"
            >
              <el-button :disabled="selectedPTags.length===0" slot="reference" size="mini" icon="el-icon-delete"
                         type="danger"
              >删除
              </el-button>
            </el-popconfirm>
          </div>


        </div>
        <div class="tags_wrap">
          <h5>二级标签
            <el-button :disabled="this.checkParentId===-1" class="add_tag el-icon-circle-plus-outline" size="mini"
                       type="success" @click="showDialog(false)"
            >添加标签
            </el-button>
          </h5>
          <el-table
            ref="tags"
            height="400"
            @select="selectCTag"
            @select-all="selectAllCTags"

            :data="this.checkParentId===-1?[]:list.filter(e=>e.id===this.checkParentId)[0].children"
          >
            <el-table-column type="selection" label="全选" width="30"></el-table-column>
            <el-table-column prop="label" label="全选"></el-table-column>

          </el-table>

          <div style="width: 100%; display:flex;flex-direction: row;justify-content: end">
            <!--            <el-button type="">删除</el-button>-->
            <el-popconfirm
              title="确定删除吗"

              @onConfirm="removeTags(false)"
            >
              <el-button :disabled="this.checkParentId===-1||selectedCTags.length===0" slot="reference" size="mini" icon="el-icon-delete"
                         type="danger"
              >删除
              </el-button>
            </el-popconfirm>
          </div>
        </div>
      </div>
    </el-main>

    <el-dialog :visible.sync="dialogVisible" :title="tag.parentId===0?'添加一级标签':list.filter(e=>e.id===this.checkParentId)[0].label" @close="closeDialog">



      <el-input
        type="text"
        maxlength="15"
        :placeholder="`请输入${tag.parentId===0?'一级':'二级'}标签名称`"
        v-model="tag.label"
        show-word-limit
      ></el-input>
      <span slot="footer" class="dialog-footer">
    <el-button @click="closeDialog">取 消</el-button>
    <el-button type="primary" @click="addTag">确 定</el-button>
  </span>
    </el-dialog>
  </el-container>
</template>

<script>
export default {
  name: 'index',
  data() {
    return {
      list: null,
      map: {
        page: 1,
        limit:999,
        parentId: 0
      },
      dialogVisible: false,//添加标签对话框
      checkParentId: -1,//选择的一级标签，-1表示没有选择
      addTagType: false,//true:添加一级标签  false:添加二级标签
      tag: {
        label: '',
        parentId: 0
      },
      selectedPTags: [],//选中待删除的一级标签
      selectedCTags: []//选中待删除的二级标签
    }
  },
  mounted() {

    this.listByPage()
  },

  methods: {
    /**
     * 获取标签
     */
    listByPage() {
      this.$axios.post('/tag/list', {
        ...this.map
      }).then(res => {
        if(res.data.code!==0)
        {
          this.$message({
            type:"error",
            message:res.data.msg
          })
          return
        }
        this.list = res.data.list.records

      })
    },

    /**
     * 选择一级标签
     * @param r
     * @param c
     * @param e
     */
    checkPTag(r, c, e) {
      if (this.checkParentId !== r.id) {
        this.checkParentId = r.id
        this.$refs.tags.setCurrentRow(r)

      } else {
        this.checkParentId = -1
        this.$refs.tags.setCurrentRow()
      }
    },

    /**
     * 关闭添加标签对话框
     */
    closeDialog() {
      this.dialogVisible = false
      this.tag.label = ''

    },
    /**
     * 打开添加标签窗口
     * @param isP true:一级标签 false:二级标签
     */
    showDialog(isP) {
      if (isP) {
        this.tag['parentId'] = 0
        this.addTagType = true
      } else {
        this.tag['parentId'] = this.checkParentId
        this.addTagType = false

      }
      this.dialogVisible = true

    },

    /**
     * 添加标签
     */
    addTag() {

      this.$axios.post('/tag/save', {
        ...this.tag
      }).then((res) => {
        if(res.data.code!==0)
        {
          this.$message({
            type:"error",
            message:res.data.msg
          })
          return
        }
        this.listByPage()
        this.dialogVisible = false
        this.tag['label'] = ''

      })
    },
    test() {

    },

    /**
     * 删除标签
     * @param isP true:删除一级标签 false:删除二级标签
     */
    removeTags(isP) {
      let ids = []
      if (isP) {
        ids = this.selectedPTags.map(e => e.id)
      } else {
        ids = this.selectedCTags.map(e => e.id)

      }
      this.$axios.post('/tag/remove', this.$qs.stringify({
        ids: ids.join(',')
      })).then((res) => {
        if(res.data.code!==0)
        {
          this.$message({
            type:"error",
            message:res.data.msg
          })
          return
        }
        if (isP) {
          this.selectedPTags = []
        } else {
          this.selectedCTags = []
        }
        this.listByPage()

      })
    },
    /**
     * 选择一级标签待删除
     * @param selection
     * @param row
     */
    selectPTag(selection, row) {
      this.selectedPTags = selection
    },
    /**
     * 选择二级标签待删除
     * @param selection
     * @param row
     */
    selectCTag(selection, row) {
      this.selectedCTags = selection
    },
    /**
     * 选择所有一级标签待删除
     * @param selection
     * @param row
     */
    selectAllPTags(selection, row) {
      this.selectedPTags = selection
    },
    /**
     * 选择所有二级标签待删除
     * @param selection
     * @param row
     */
    selectAllCTags(selection, row) {
      this.selectedPTags = selection
    }

  }
}
</script>

<style lang="less" scoped>

.wrap {
  padding: 20px;
}

.tags {
  display: flex;
  flex-direction: row;
  justify-content: space-between;
  flex-wrap: wrap;
}

.tags_wrap {

  min-width: 330px;
  width: 40%;
  height: 500px;
  border-radius: 6px;
  border: 1px solid #ac9f9f;
  /*background: #000;*/
  margin-bottom: 30px;
  padding: 0 10px;

  h3, h4, h5, h6 {
    margin: 0;
    padding: 15px 0;
    border-bottom: 1px solid #ac9f9f;
    display: flex;
    flex-direction: row;
    justify-content: space-between;

    .add_tag {
      margin-top: -5px;
    }
  }
}

.radio-group {
  display: flex;
  flex-direction: column;
  //justify-content: center;
  align-items: center;

  div {
    //background: #ece8e8;
    width: 100%;
    height: 40px;
    border-bottom: 1px solid #929090;
    cursor: pointer;
    transition: all 0.5s;
    border-bottom: 1px solid #EBEEF5;

    //text-align: center;
    padding-left: 10px;
    line-height: 40px;
    color: #606266;
    font-size: 14px;
    //width: 80%;
  }

  div:hover {
    background: #4799a5;
    color: white;
  }

  .checked_tag {
    background: #4799a5;
    color: white;
  }
}
</style>
