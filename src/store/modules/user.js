import request from '@/utils/request'

const state={
  id:0,//当前浏览博客的用户Id
  user:null//当前登录的用户
}
const mutations={
  SET_ID:(state,id)=>{
    state.id=id
  },
  SET_USER:(state,user)=>{

    state.user=user
  }
}

const actions={
  getInfo({ commit, state }) {
    return new Promise((resolve, reject) => {
      request({
        url: '/user/info',
        method: 'post'
      }).then(response => {
        if(response.data.code!==0)
          return
        const { data } = response

        if (!data) {
          return reject('Verification failed, please Login again.')
        }

        commit('SET_USER', data.baseInfo)
        resolve(data)
      })
    })
  },
}
export default {
  state,
  mutations,
  actions
}
