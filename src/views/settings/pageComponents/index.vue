<template>
  <div style="padding: 20px">

    <el-form ref="form1" :rules="rules" :inline="true" :model="pageComponent">
      <el-form-item prop="label" label="描述">
        <el-input v-model="pageComponent.label" placeholder="描述"></el-input>
      </el-form-item>
      <el-form-item prop="value" label="组件名称">
        <el-input v-model="pageComponent.value" placeholder="组件名称"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button @click="addOrAlterPageComponents(true)" type="success">添加</el-button>
      </el-form-item>
    </el-form>
    <el-table
      :data="list"
      v-loading="!list"
      border
      style="width: 100%"
    >
      <el-table-column
        prop="id"
        label="id"
      >
      </el-table-column>
      <el-table-column
        prop="label"
        label="描述"
      >
      </el-table-column>
      <el-table-column
        prop="value"
        label="组件名称"
      >
      </el-table-column>
      <el-table-column
        fixed="right"
        label="操作"
        width="150"
      >
        <template slot-scope="scope">
          <el-popconfirm
            title="确定删除吗"

            @onConfirm="remove(scope.row.id)"
          >
            <el-button
              type="danger"
              size="small"
              slot="reference"
            >
              移除
            </el-button>
          </el-popconfirm>
          <el-button
            type="success"
            size="small"
            @click="alter(scope.row.id)"
          >

            修改
          </el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination

      background
      layout="prev, pager, next"
      @current-change="handleCurrentChange"
      :total="total"
      :page-size="params.limit">
    </el-pagination>
    <el-dialog @close="pageComponent={label: '',value: ''}" :visible.sync="showAlterDialog">
      <el-form ref="form1" :rules="rules" :model="pageComponent">
        <el-form-item prop="label" label="描述">
          <el-input v-model="pageComponent.label" placeholder="描述"></el-input>
        </el-form-item>
        <el-form-item prop="value" label="组件名称">
          <el-input v-model="pageComponent.value" placeholder="组件名称"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button @click="addOrAlterPageComponents(false)" type="success">修改</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>
  </div>
</template>

<script>
export default {
  name: 'index',
  data() {
    return {
      total:1,
      params: {
        page: 1,
        limit:10
      },//提交的分页参数
      list: null,
      showAlterDialog: false,
      currentIndex: -1,
      pageComponent: {
        label: '',
        value: ''
      },
      rules: {
        label: [
          { required: true, message: '请输入组件描述', trigger: 'blur' },
          { min: 1, message: '请输入组件描述', trigger: 'blur' }
        ],
        value: [
          { required: true, message: '请输入组件名称', trigger: 'blur' },
          { min: 1, message: '请输入组件描述', trigger: 'blur' }

        ]
      }
    }
  },
  mounted() {
    this.init()
  },
  methods: {

    /**
     * 切换分页
     * @param val
     */
    handleCurrentChange(val){
      this.params.page=val
      this.init()
    },
    /**
     * 初始化列表
     */
    init() {
      this.list = null
      this.$axios.post('/pagecomponent/list', {
        ...this.params
      }).then(res => {
        this.total=res.data.data.total

        this.list = res.data.data.records
        console.log(this.total)
      })
    },
    /**
     * 添加组件或修改组件
     * @param isAdd true:add  false:alter
     */
    addOrAlterPageComponents(isAdd) {
      this.$refs['form1'].validate(e => {
        if (e) {

          this.$axios.post('/pagecomponent/' + (isAdd ? 'save' : 'update'), {
            ...this.pageComponent
          }).then(() => {
            this.init()
            this.pageComponent = {
              label: '',
              value: ''
            }
            this.showAlterDialog = false
          })
        }
      })
    },
    /**
     * 打开修改窗口并设置id
     * @param id
     */
    alter(id) {

      this.pageComponent = this.list.filter(e => e.id = id)[0]
      this.showAlterDialog = true
    },

    /**
     * 删除组件
     * @param id
     */
    remove(id) {
      this.$axios.get("/pagecomponent/remove?id="+id).then(()=>{
        this.init()
      })
    }

  }

}
</script>

<style scoped>

</style>
