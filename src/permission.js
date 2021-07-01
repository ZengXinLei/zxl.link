import router from './router'
import store from './store'
import { Message } from 'element-ui'
import NProgress from 'nprogress' // progress bar
import 'nprogress/nprogress.css' // progress bar style
import { getToken } from '@/utils/auth' // get token from cookie
import getPageTitle from '@/utils/get-page-title'

NProgress.configure({ showSpinner: false }) // NProgress Configuration

const whiteList = ['/login'] // no redirect whitelist


router.beforeEach(async(to, from, next) => {
  // start progress bar
  NProgress.start()

  // set page title
  document.title = getPageTitle(to.meta.title)

  if(to.path.startsWith("/login")){
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
          next()
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
