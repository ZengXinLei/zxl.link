import router from './router'
import store from './store'
import { Message } from 'element-ui'
import Layout from '@/layout'

import NProgress from 'nprogress' // progress bar
import 'nprogress/nprogress.css' // progress bar style
import { getToken } from '@/utils/auth' // get token from cookie
import getPageTitle from '@/utils/get-page-title'
import asyncRouytes from '@/router/asyncRoutes'
import Router from 'vue-router'
import request from '@/utils/request'

NProgress.configure({ showSpinner: false }) // NProgress Configuration

const whiteList = ['/login'] // no redirect whitelist
const _import = require('./router/import-' + process.env.NODE_ENV)


function dfs2(data, hasPs) {

  if(data.children){
    for (let i = 0; i < data.children.length; i++) {
      if (!dfs2(data.children[i], hasPs)) {
        data.children[i] = null
      }
    }
    data.children = data.children.filter(e => e)
  }


  return !((!data.children || data.children.length === 0) && hasPs.filter(e => e === data.path).length === 0)

}

function setRoutes() {

  return request.post('/permission/permissionIdsByRole')
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
      if (to.path === '/login') {
        // if is logged in, redirect to the home page
        next({ path: '/' })
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
                  children:e.type===0?[{
                    path: e.value,
                    name:e.label,
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
              // console.log(hasPs)


              let routes=[
                {
                  path: '/login',
                  component: () => import('@/views/login/index'),
                  hidden: true
                },

                {
                  path: '/404',
                  component: () => import('@/views/404'),
                  hidden: true
                },
                {
                  path: '/active',
                  component:()=>import('@/views/active/index'),
                  hidden: true
                },
              ]
              routes.push(...hasPs)
              routes.push({ path: '*', redirect: '/404', hidden: true })

              // let path="Editor/list/index"
              // routes[4].children[0].component=_import(path)
              console.log(routes)
              //
              let vueRouter = new Router(router.options)

              router.options.routes=routes
              router.matcher = vueRouter.matcher
              router.addRoutes(routes)

              router.options.isAddDynamicMenuRoutes=true
              next()
            })

          }else {
            next()

          }
        } else {
          next(`/login?redirect=${to.path}`)

          // try {
          //   // get user info
          //   await store.dispatch('user/getInfo')
          //
          //   next()
          // } catch (error) {
          //   // remove token and go to login page to re-login
          //   await store.dispatch('user/resetToken')
          //   Message.error(error || 'Has Error')
          //   next(`/login?redirect=${to.path}`)
          //   NProgress.done()
          // }
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
