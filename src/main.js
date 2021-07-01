import Vue from 'vue'
import App from './App.vue'
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import VueRouter from 'vue-router'
import router from "@/router/router";
Vue.config.productionTip = false




const route=new VueRouter({
  mode: 'history',
  routes:router
})
Vue.use(ElementUI)
Vue.use(VueRouter)
new Vue({
  render: h => h(App),
  route
}).$mount('#app')
