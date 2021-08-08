<template>
<div>
  <el-table
    :data="list"
    v-loading="!list"
    border
    style="width: 100%"
  >
    <el-table-column
      prop="id"
      label="id"
      width="50"
    >
    </el-table-column>
    <el-table-column
      label="标题"
    >
      <template slot-scope="scope">
        <a :href="`http://localhost:8081/${scope.row.uid}/index/${scope.row.id}/article`">{{scope.row.title}}</a>
      </template>
    </el-table-column>
    <el-table-column
      prop="value"
      label="时间"
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
</div>
</template>

<script>
export default {
  name: "index",
  data(){
    return{
      list:[],
      total:1,
      params: {
        page: 1,
        limit:10
      },//提交的分页参数
    }
  },
  mounted() {
    this.init()
  },
  methods:{
    /**
     * 当页面更改的时候
     */
    handleCurrentChange(){

    },
    /**
     * 删除文章
     * @param id
     */
    remove(id){

    },
    /**
     * 初始化列表
     */
    init(){

        this.list = null
        this.$axios.post('/article/admin/list',
          this.params
        ).then(res => {
          if(res.data.code!==0)
          {
            this.$message({
              type:"error",
              message:res.data.msg
            })
            return
          }
          this.total=res.data.data.total

          this.list = res.data.data.records
        })


    }
  }
}
</script>

<style scoped>

</style>
