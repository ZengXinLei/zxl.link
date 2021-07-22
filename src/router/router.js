import Router from "vue-router"

const routes=[

  {
    path:"/**/index",
    component:()=>import("@/views/index/Index"),
  },
  {
    path:"/**/index/**/article",
    component:()=>import("@/views/article/index"),
  },
]
const router=new Router({
  mode:"history",
  scrollBehavior:()=>({y:0}),
  routes:routes
})
export default router
