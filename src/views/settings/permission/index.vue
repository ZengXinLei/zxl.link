<template>
<div style="padding: 50px 30px;display:flex;flex-direction: row;justify-content: center;flex-wrap: wrap">



      <el-card class="box-card" style="min-width: 400px">
        <div slot="header" class="clearfix">
          <span>权限</span>
          <el-button style="float: right; padding: 3px 0" @click="isAdd=true" type="text">添加</el-button>
        </div>
        <el-tree
          @node-click="checkedNode"
          :data="list"
          show-checkbox
          node-key="id"
          :props="defaultProps">
        </el-tree>
      </el-card>
      <el-card class="box-card" style="min-width: 850px;margin-left: 50px;display:flex;flex-direction: row;justify-content: center">
        <el-form ref="form1" :rules="rules" :model="permission" label-width="100" style="width: 500px;">

          <el-form-item label="类型" prop="type" >
            <el-radio-group v-model="permission.type">
              <el-radio :label="0">页面路由</el-radio>
              <el-radio :label="1">rest接口</el-radio>
              <el-radio :label="2">菜单</el-radio>
            </el-radio-group>
          </el-form-item>
          <el-form-item label="描述" prop="label">
<!--            <template>-->
              <el-input v-model="permission.label" style="width: 300px" placeholder="输入大概的描述"></el-input>

<!--            </template>-->
          </el-form-item>
          <el-form-item label="路径" prop="value">
            <template>
              <el-input v-model="permission.value" style="width: 300px" placeholder="页面路径/url"></el-input>

            </template>
          </el-form-item>
          <el-form-item v-if="permission.type===0" label="icon">
            <div style="width: 300px;display:flex;flex-direction: row;justify-content: space-between;align-items: center">
              <el-button @click="showIcons" type="warning" size="mini">选择icon</el-button>
              <span :class="'icon iconfont icon-'+permission.icon" style="font-size: 30px;border: #2db7f5 1px solid"></span>
            </div>

          </el-form-item>
          <el-form-item v-if="permission.type===0" label="组件选择">
            <el-select @change="log" v-model="permission.pageComponentId" placeholder="请选择">
              <el-option
                v-for="item in pageComponents"
                :key="item.id"
                :label="item.label"
                :value="item.id">
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item>
            <el-button size="mini" type="success" @click="submitForm">{{isAdd?"添加":"更改"}}</el-button>
            <el-popconfirm
              v-if="!isAdd"
              title="确定删除吗"

              @onConfirm="remove()"
            >

            <el-button slot="reference"  size="mini"  type="danger" >删除</el-button>

            </el-popconfirm>
          </el-form-item>
        </el-form>
      </el-card>

  <el-dialog :visible.sync="iconDialog">
    <ul class="icon_lists dib-box">
      <li v-for="(icon,index) in icons" class="dib">
        <el-popover
          placement="top-start"
          width="200"
          trigger="hover"
          :content="icon.name">
        <span @click="permission.icon=icon.font_class" slot="reference" :class="'icon iconfont icon-'+icon.font_class" :style="icon.font_class===permission.icon?'color: #2db7f5;':''"></span>
        </el-popover>

<!--        <div class="name">-->
<!--          {{icon}}-->
<!--        </div>-->
      </li>
    </ul>
  </el-dialog>


</div>
</template>

<script>
import iconJson from '@/assets/alifont/iconfont.json'

export default {
  name: 'index',
  data(){
    return{
      list:[{
        id:0,
        label:"根",
        children:[]
      }],
      pageComponents:[],
      permission:{
        type:0,
        icon:"toy",
        label:'',
        value:'',
        parentId:-1,
        pageComponentId:0
      },
      defaultProps:{
        children:"children",
        label:"label"
      },
      isAdd:false,
      icons:[],
      iconDialog:false,
      rules: {
        label: [
          { required: true, message: '请输入描述', trigger: 'blur' },
          { min: 1, message: '请输入描述', trigger: 'blur' }
        ],
        value: [
          { required: true, message: '请输入路径', trigger: 'blur' },
          { min: 1, message: '请输入路径', trigger: 'blur' }

        ]
      }
    }
  },
  mounted() {
    this.init()
  },
  methods:{
    init(){
      this.$axios.get("/permission/list").then(res=>{
        this.list[0].children=res.data.list
      })
      this.$axios.post("/pagecomponent/list",{
        page:1,
        limit:999
      }).then(res=>{
        this.pageComponents=res.data.data.records
      })



    },
    /**
     * 选择icon
     */
    showIcons(){
      this.iconDialog=true
      this.icons = iconJson["glyphs"]

    },
    /**
     * 点击节点
     * @param e1  选择的节点
     * @param e2
     * @param e3
     */
    checkedNode(e1,e2,e3)
    {

      this.isAdd=false
      if(e1["id"]===0){
        this.permission={
          type:0,
            icon:"toy",
            label:'',
            value:'',
            parentId:0,
            pageComponentId: 0,
          id:0
        }
        console.log(this.list)
      }
      else {
        this.permission=e1


      }
      // console.log(e1)
    },

    submitForm(){

      this.$refs["form1"].validate(e=>{
        console.log(this.permission)
        if(e){
          if(!this.isAdd&&this.permission.id===0){
            this.$message({
              message:"根节点不可修改",
              type:'error'
            })
            return
          }
          if(this.isAdd){
            this.permission.parentId=this.permission.id
          }
          this.$axios.post("/permission/"+(this.isAdd?"save":"update"),{
            ...this.permission
          }).then(()=>{
            this.init()
          })

        }
      })
    },
    remove(){
      if(this.permission.id===0){
        this.$message({
          message:"根节点不可删除",
          type:'error'
        })
        return
      }
      this.$axios.post("/permission/remove",{
        ...this.permission
      }).then(()=>{
        this.init()
      })
    },
    log(){
      console.log(this.permission)

    }


  }
}
</script>

<style scoped>

@import "~@/assets/alifont/iconfont.css";
/*@import "~@/assets/alifont/demo.css";*/
.icon_lists{
  display: grid;
  grid-template-columns: repeat(auto-fill,100px);
  /*grid-template-rows: repeat(auto-fill,100px);*/
}
.icon_lists li{

  list-style: none !important;

  margin-bottom: 10px;
  text-align: center;

  line-height: 35px;


}
.icon_lists li span{
  cursor: pointer;
  font-size: 30px;

}
.icon_lists li span:hover{
  color: #2db7f5;
}
</style>
