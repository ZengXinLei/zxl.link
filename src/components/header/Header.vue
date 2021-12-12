<template>

  <div class="navbar">
    <vs-navbar
        v-model="activeItem"

        :color="`rgba(191, 201, 202,${mobile?1:0})`"
        text-color="rgba(255,255,255,1)"
        active-text-color="rgba(255,255,255,1)"
        class="nabarx container">
      <div slot="title">
        <a href="/" class="navbar-brand">
          <h3 style="color: #fff;">曾大人</h3>
        </a>
      </div>


      <vs-navbar-item  v-for="(item,index) in list" :index="`${index}`">
        <router-link :to="item.to" class="nav-link"><i :class="`iconfont icon-${item.icon}`"
        ></i>{{item.name}}
        </router-link>
      </vs-navbar-item>

    </vs-navbar>
  </div>
</template>

<script>
import store from '@/store'

export default {
  name: 'Header',
  data() {
    return {
      activeItem: 0,
      mobile:/mobile/i.test(navigator.userAgent),//是否是手机
      list:[
        {
          to:`/${this.$store.state.user.id}/index`,
          icon:"pingtaiguanli",
          name:"首页"
        },
        {
          to:`/${this.$store.state.user.id}/index/archives`,
          icon:"product-fill",
          name:"归档"

        },
        {
          to:`/${this.$store.state.user.id}/index/category`,
          icon:"discount-fill",
          name:"标签"

        },
      ],


    }
  },

  mounted() {
    for (let i = 0; i < this.list.length; i++) {
      let reg=RegExp(this.list[i].to+"$","i")
      if(reg.test(window.location.href.split("?")[0]))
        this.activeItem=i
    }
    store.state.eventListener.forEach(e => {
      document.removeEventListener(e.type, e.func, true)
    })

    store.commit('CLEAR')
    let func = () => {

      let h = document.documentElement.scrollTop
      if (h > 30) {
        this.navClass = 'navbar'
      } else {
        this.navClass = ''

      }
    }
    this.$store.commit('ADD_FUNC', {
      type: 'scroll',
      func: func
    })
    document.addEventListener('scroll', func, true)
  },
  methods: {
  }
}
</script>

<style lang="less" scoped>

@import "~@/assets/alifont/iconfont.css";
@import '~@/main.less';

.navbar {
  z-index: 1;
  position: fixed;
  width: 100%;
  height: 40px;
  //background: #2f4154;
  display: flex;
  flex-direction: row;
  flex-wrap: wrap;
  justify-content: center;
  //align-items: center;

  -webkit-box-sizing: border-box;
  -moz-box-sizing: border-box;
  box-sizing: border-box;
  //padding: 5px 0 5px 0;
  -webkit-box-shadow: 0 2px 5px 0 rgba(0, 0, 0, 0.16), 0 2px 10px 0 rgba(0, 0, 0, 0.12);
  transition: background 0.5s ease-in-out, height 0.5s ease-in-out;

  .container{
    width: 100%;
    max-width: 1140px;
  }
}




</style>
