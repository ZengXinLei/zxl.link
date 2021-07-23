import Vue from 'vue'
import Vuex from 'vuex'
import user from '@/store/modules/user'
import getter from '@/store/getter'
import eventListener from '@/store/modules/eventListener'
Vue.use(Vuex)
const store = new Vuex.Store({
  modules: {
    user,
    eventListener
  },
  getter
})

export default store
