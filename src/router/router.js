import Router from "vue-router"

const routes=[

  {
    path:"/**/index",
    component:()=>import("@/views/index/Index"),
    children:[
      {
        path:"article/**",
        components:{
          default:()=>import("@/views/article/index"),
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
