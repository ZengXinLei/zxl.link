import Router from "vue-router"

const routes=[
  {
    path:"/",
    redirect:"/1/index"
  },
  {
    path:"/**/index",
    component:()=>import("@/views/index/Index"),
  },
  {
    path:"/**/index/**/article",
    component:()=>import("@/views/article/index.vue"),//补全后缀
  },
  {
    path:"/**/index/**/category",
    component:()=>import("@/views/category/index.vue"),//补全后缀
  },
]
const router=new Router({
  mode:"history",
  scrollBehavior:()=>({y:0}),
  routes:routes
})
export default router
