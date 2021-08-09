import router from './router'
import store from './store'
import { Message } from 'element-ui'
import Layout from '@/layout'

import NProgress from 'nprogress' // progress bar
import 'nprogress/nprogress.css' // progress bar style
import { getToken } from '@/utils/auth' // get token from cookie
import getPageTitle from '@/utils/get-page-title'

import Router from 'vue-router'
import request from '@/utils/request'

NProgress.configure({ showSpinner: false }) // NProgress Configuration


const _import = require('./router/import-' + process.env.NODE_ENV)


function setRoutes() {

  return request.post('/permission/permissionIdsByRole')
}

function sort(arr){

  arr.sort((a,b)=>a.sort-b.sort)
  for (let i = 0; i < arr.length; i++) {
    if(arr[i].children){
      sort(arr[i].children)
    }
  }
}
router.beforeEach(async(to, from, next) => {
  // start progress bar
  NProgress.start()

  // set page title
  document.title = getPageTitle(to.meta.title)

  if(to.path.startsWith("/login")||to.path.startsWith("/active")){
    next()
  }else {
    const hasToken = getToken()

    if (hasToken!==""&&hasToken) {
      if (to.path === '/') {
        // if is logged in, redirect to the home page
        next({ path: '/article/editor' })
        NProgress.done()
      } else {
        // const hasGetUserInfo = store.getters.name
        if (getToken()) {
          // debugger
          if(!router.options.isAddDynamicMenuRoutes)
          {
            setRoutes().then(res => {
              let data = res.data.data
              let hasPs = data.filter(e=>e.parentId===0).map(e=>{
                let obj={

                  id:e.id,
                  path:e.value,
                  component:Layout,
                  name:e.label,
                  sort:e.sort,
                  children:e.type===0?[{
                    path: e.value,
                    name:e.label,
                    sort:e.sort,
                    component: _import(e.pagecomponent.value),
                    meta:{
                      title:e.label,
                      icon:'icon-'+e.icon
                    }
                  }]:[]
                }
                if(e.type!==0){
                  obj['meta']={

                      title:e.label,
                      icon:'icon-'+e.icon

                  }
                }
                return obj
              })

              hasPs.filter(e=>e.children.length===0).forEach(g=>{
                g.children.push(...data.filter(f=>f.parentId===g.id).map(e=>{
                  let obj={

                    id:e.id,
                    path:e.value,
                    sort:e.sort,
                    component:_import(e.pagecomponent.value),
                    name:e.label,
                    meta:{
                      title:e.label,
                      icon:'icon-'+e.icon
                    }
                  }
                  return obj
                }))
              })


              sort(hasPs)

              let routes=[

              ]
              routes.push(...hasPs)
              routes.push({ path: '*', redirect: '/404', hidden: true })

              router.options.routes.push(...routes)
              router.addRoutes(routes)

              router.options.isAddDynamicMenuRoutes=true
              console.log(router.options)
              //存储用户持久化
              store.dispatch("user/getInfo")

              next({...to,replace: true}) //解决路由刷新失效问题
            })


          }else {
            next()

          }
        } else {
          next(`/login?redirect=${to.path}`)


        }
      }
    } else {
      /* has no token*/

      next(`/login`)
    }
  }
  // determine whether the user has logged in

})

router.afterEach(() => {
  // finish progress bar
  NProgress.done()
})
