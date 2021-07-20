import router from '@/router/router'
router.beforeEach(((to, from, next) => {
  let fullPath = to.fullPath
  let reg=/^\/(.*?)\/index.*/g
  let execArray = reg.exec(fullPath)
  console.log(execArray)
  if(!execArray){
    next("/")
  }

  let number = fullPath.search()
  console.log(execArray)
  next()
}))
