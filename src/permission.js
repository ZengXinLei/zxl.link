import router from './router'
import store from './store'
import { Message } from 'element-ui'
import NProgress from 'nprogress' // progress bar
import 'nprogress/nprogress.css' // progress bar style
import { getToken } from '@/utils/auth' // get token from cookie
import getPageTitle from '@/utils/get-page-title'
import asyncRouytes from '@/router/asyncRoutes'
import Router from 'vue-router'
import request from '@/utils/request'

NProgress.configure({ showSpinner: false }) // NProgress Configuration

const whiteList = ['/login'] // no redirect whitelist


function dfs2(data, hasPs) {

  if(data.children){
    for (let i = 0; i < data.children.length; i++) {
      if (!dfs2(data.children[i], hasPs)) {
        data.children[i] = null
      }
    }
    data.children = data.children.filter(e => e)
  }

  console.log(data.path)
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
        const hasGetUserInfo = store.getters.name
        if (getToken()) {
          if(!router.options.isAddDynamicMenuRoutes)
          {
            setRoutes().then(res => {
              let data = asyncRouytes
              let hasPs = res.data.data
              for (let i = 0; i < data.length; i++) {
                let dfs = dfs2(data[i], hasPs)
                if(data[i].children.length===0)
                  data[i]=null
              }
              data = data.filter(e => e)

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
              routes.push(...data)
              routes.push({ path: '*', redirect: '/404', hidden: true })
              console.log(routes)
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
