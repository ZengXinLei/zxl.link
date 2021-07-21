import Router from "vue-router"

const routes=[

  {
    path:"/**/index",
    component:()=>import("@/views/index/Index"),
  }
]
const router=new Router({
  mode:"history",
  scrollBehavior:()=>({y:0}),
  routes:routes
})
export default router
