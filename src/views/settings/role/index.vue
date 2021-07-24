<template>
  <div style="padding: 20px">

    <el-form ref="form1" :rules="rules" :inline="true" :model="role">
      <el-form-item prop="nameZh" label="角色描述">
        <el-input v-model="role.nameZh" placeholder="描述"></el-input>
      </el-form-item>
      <el-form-item prop="name" label="角色名称">
        <el-input v-model="role.name" placeholder="角色名称"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button @click="addOrAlterRole(true)" type="success">添加</el-button>
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
        prop="nameZh"
        label="描述"
      >
      </el-table-column>
      <el-table-column
        prop="name"
        label="组件名称"
      >
      </el-table-column>
      <el-table-column
        fixed="right"
        label="操作"
        width="150"
      >
        <template slot-scope="scope">
          <el-button type="text" size="small" @click="showPermission(scope.row.id)">权限</el-button>
          /
          <el-popconfirm
            title="确定删除吗"

            @onConfirm="remove(scope.row.id)"
          >
            <el-button
              type="text"
              size="small"
              slot="reference"
            >
              移除
            </el-button>
          </el-popconfirm>
          /

          <el-button
            type="text"
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
      :page-size="params.limit"
    >
    </el-pagination>
    <el-dialog @close="role={nameZh: '',name: ''}" :visible.sync="showAlterDialog">
      <el-form ref="form1" :rules="rules" :model="role">
        <el-form-item prop="label" label="描述">
          <el-input v-model="role.nameZh" placeholder="描述"></el-input>
        </el-form-item>
        <el-form-item prop="value" label="组件名称">
          <el-input v-model="role.name" placeholder="组件名称"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button @click="addOrAlterRole(false)" type="success">修改</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>
    <el-dialog @close="checkedKeys=[]" :visible.sync="showPermissionDialog">
      <el-tree

        :data="allPermissions"
        show-checkbox
        node-key="id"
        :default-checked-keys="hasPByRole"
        @check="checkedNode"
        :props="defaultProps">
      </el-tree>
      <el-button type="success" size="mini" @click="submitPermissions">提交</el-button>
    </el-dialog>
  </div>
</template>

<script>
export default {
  name: 'index',
  data() {
    return {
      total: 1,
      params: {
        page: 1,
        limit: 10
      },//提交的分页参数
      list: null,
      showAlterDialog: false,
      showPermissionDialog:false,
      currentRoleId: -1,//当前要修改权限的角色id
      role: {
        nameZh: '',
        name: ''
      },
      rules: {
        nameZh: [
          { required: true, message: '请输入角色描述', trigger: 'blur' },
          { min: 1, message: '请输入角色描述', trigger: 'blur' }
        ],
        name: [
          { required: true, message: '请输入角色名称', trigger: 'blur' },
          { min: 1, message: '请输入角色名称', trigger: 'blur' }

        ]
      },
      defaultProps:{
        children:"children",
        label:"label"
      },
      allPermissions:[],//所有权限列表
      hasPByRole:[],//拥有的权限列表
      checkedKeys:[],
    }
  },
  mounted() {
    this.init()
  },
  methods: {

    checkedNode(a,b){

      this.checkedKeys=b.checkedKeys
    },
    /**
     * 切换分页
     * @param val
     */
    handleCurrentChange(val) {
      this.params.page = val
      this.init()
    },
    /**
     * 初始化列表
     */
    init() {
      this.list = null
      this.$axios.post('/role/list', {
        ...this.params
      }).then(res => {
        if(res.data.code!==0)
        {
          this.$message({
            type:"error",
            message:res.data.msg
          })
          return
        }
        this.total = res.data.data.total

        this.list = res.data.data.records
        console.log(this.total)
      })
    },
    /**
     * 添加角色或修改角色
     * @param isAdd true:add  false:alter
     */
    addOrAlterRole(isAdd) {
      this.$refs['form1'].validate(e => {
        if (e) {

          this.$axios.post('/role/' + (isAdd ? 'save' : 'update'), {
            ...this.role
          }).then(() => {
            if(res.data.code!==0)
            {
              this.$message({
                type:"error",
                message:res.data.msg
              })
              return
            }
            this.init()
            this.role = {
              name: '',
              nameZh: ''
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

      this.role = this.list.filter(e => e.id = id)[0]
      this.showAlterDialog = true
    },

    /**
     * 删除角色
     * @param id
     */
    remove(id) {
      this.$axios.get('/role/remove?id=' + id).then((res) => {
        if(res.data.code!==0)
        {
          this.$message({
            type:"error",
            message:res.data.msg
          })
          return
        }
        this.init()
      })
    },
    /**
     * 显示当前角色拥有的权限
     * @param id
     */
    showPermission(id)
    {
      this.checkedKeys=[]
      this.hasPByRole=[]
      this.showPermissionDialog=true

      this.currentRoleId=id

      this.$axios.post("/permission/list").then(res=>{
        if(res.data.code!==0)
        {
          this.$message({
            type:"error",
            message:res.data.msg
          })
          return
        }
        this.allPermissions=res.data.list
        return this.$axios.post("/permission/listByRoleId",this.$qs.stringify({
          rid:id
        }))
      }).then(res=>{
        if(res.data.code!==0)
        {
          this.$message({
            type:"error",
            message:res.data.msg
          })
          return
        }

        this.checkedKeys=res.data.data
        this.hasPByRole=res.data.data
      })
    },
    submitPermissions(){
      this.$axios.post("/permission/updateByRoleId",{
        rid:this.currentRoleId,
        pIds:this.checkedKeys
      }).then((res)=>{
        if(res.data.code!==0)
        {
          this.$message({
            type:"error",
            message:res.data.msg
          })
          return
        }
        this.showPermissionDialog=false
      })
    }

  }

}
</script>

<style scoped>

</style>
