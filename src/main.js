import Vue from 'vue'
import App from './App.vue'
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import VueRouter from 'vue-router'
import router from "@/router/router";
import request from '@/utils/request'
import './permission'
import "@/main.less"
import store from '@/store/index'
import Vue2Emoji from 'vuejs-emoji'
Vue.config.productionTip = false
let qs=require("qs")

Vue.prototype.$axios=request
Vue.prototype.$qs=qs
Vue.prototype.$store=store

Vue.use(ElementUI)
Vue.use(VueRouter)
Vue.use(Vue2Emoji)
new Vue({
  render: h => h(App),
  router
}).$mount('#app')
