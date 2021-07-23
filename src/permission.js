import router from '@/router/router'
import store from '@/store'
router.beforeEach(((to, from, next) => {
  let fullPath = to.fullPath
  let reg=/^\/(.*?)\/index.*/g
  let execArray = reg.exec(fullPath)
  if(!execArray){
    next("/")
  }
  store.commit("SET_ID",execArray[1])

  let number = fullPath.search()
  next()
}))
