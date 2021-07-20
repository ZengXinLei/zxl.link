import Vue from 'vue'
import App from './App.vue'
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import VueRouter from 'vue-router'
import router from "@/router/router";
import request from '@/utils/request'
import './permission'
Vue.config.productionTip = false
let qs=require("qs")

Vue.prototype.$axios=request
Vue.prototype.$qs=qs

Vue.use(ElementUI)
Vue.use(VueRouter)
new Vue({
  render: h => h(App),
  router
}).$mount('#app')
