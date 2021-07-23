const state=[]
const mutations={
  ADD_FUNC:(state,func)=>{
    state.push(func)
  },
  CLEAR:(state)=>{
    state.splice(0,state.length)
  }
}

export default {
  state,
  mutations
}
