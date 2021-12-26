const state={
  comments:[]
}
const mutations={
  ADD_COMMENT:(state,comment)=>{
    console.log("收到消息")
    state.comments.push(comment)
  }
}

const getters={
  allCount:state=>{
    let count=0
    for (let stateKey in state) {
      count+=state[stateKey].length
    }
    console.log("计算数量")
    return count
  }
}

export default {
  state,
  mutations,
  getters
}
