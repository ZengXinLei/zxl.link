import router from '@/router/router'
import store from '@/store'
import eventListener from '@/store/modules/eventListener'
router.beforeEach(((to, from, next) => {
  let fullPath = to.fullPath
  let reg=/^\/(.*?)\/index.*/g
  let execArray = reg.exec(fullPath)
  if(!execArray){
    next("/")
  }
  store.commit("SET_ID",execArray[1])



  console.log(store.state.eventListener)
  let number = fullPath.search()
  next()
}))
