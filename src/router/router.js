import Router from "vue-router"

const routes=[

  {
    path:"/**/index",
    component:()=>import("@/views/index/Index"),
    children:[
      {
        path:"list",
        components:{
          default:()=>import("@/views/main/Main"),
          sider:()=>import("@/views/sider/Sider")
        }
      }
    ]
  }
]
const router=new Router({
  mode:"history",
  scrollBehavior:()=>({y:0}),
  routes:routes
})
export default router
