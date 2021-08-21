const state={
  comments:[]
}
const mutations={
  ADD_COMMENT:(state,comment)=>{
    state.comments.push(comment)
  }
}

const getters={
  allCount:state=>{
    let count=0
    for (let stateKey in state) {
      count+=state[stateKey].length
    }
    return count
  }
}

export default {
  state,
  mutations,
  getters
}
