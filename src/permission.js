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

  store.state.eventListener.forEach(e=>{
    document.removeEventListener(e.type,e.func)
  })
  console.log()
  let number = fullPath.search()
  next()
}))
