import Vue from 'vue'
import Vuex from 'vuex'
import getters from './getters'
import app from './modules/app'
import settings from './modules/settings'
import user from './modules/user'
import message from './modules/message'

Vue.use(Vuex)

const store = new Vuex.Store({
  modules: {
    app,
    settings,
    message,
    user

  },
  getters
})

export default store
