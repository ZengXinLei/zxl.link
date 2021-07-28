<template>
  <div id="navbar" :class="navClass">

    <div class="container" >
      <a href="/" class="navbar-brand">
        &nbsp;<strong>曾大人</strong>&nbsp;
      </a>
      <div class="collapse">

        <ul class="navbar-nav">
          <li class="nav-item"><router-link :to="`/${$store.state.user.id}/index`" class="nav-link"><i class="iconfont icon-pingtaiguanli"></i>首页</router-link></li>
          <li class="nav-item"><router-link :to="`/${$store.state.user.id}/index/archives`" class="nav-link"><i class="iconfont icon-product-fill"></i>归档</router-link></li>
          <li class="nav-item"><router-link :to="`/`" class="nav-link"><i class="iconfont icon-all-fill"></i>分类</router-link></li>
          <li class="nav-item"><router-link :to="`/${$store.state.user.id}/index/category`" class="nav-link"><i class="iconfont icon-discount-fill"></i>标签</router-link></li>
          <li class="nav-item"><router-link to="/" class="nav-link"><i class="iconfont icon-bussiness-man-fill"></i>关于</router-link></li>

        </ul>
      </div>
    </div>
  </div>
</template>

<script>
import store from '@/store'

export default {
  name: 'Header',
  data(){
    return{
      navClass:""
    }
  },

  mounted() {
    store.state.eventListener.forEach(e=>{
      document.removeEventListener(e.type,e.func,true)
    })

    store.commit("CLEAR")
    let func=()=>{

      let h=document.documentElement.scrollTop
      if(h>30){
        this.navClass="navbar"
      }
      else {
        this.navClass=""

      }
    }
    this.$store.commit("ADD_FUNC", {
      type:'scroll',
      func:func
    })
    document.addEventListener("scroll",func,true)
  }
}
</script>

<style lang="less" scoped>

@import "~@/assets/alifont/iconfont.css";
@import '~@/main.less';

#navbar {
  z-index: 99999;
  position: fixed;
  width: 100%;
  height: 64px;
  //background: #2f4154;
  display: flex;
  flex-direction: row;
  justify-content: center;
  align-items: center;

  -webkit-box-sizing: border-box;
  -moz-box-sizing: border-box;
  box-sizing: border-box;
  //padding: 5px 0 5px 0;
  -webkit-box-shadow: 0 2px 5px 0 rgba(0,0,0,0.16), 0 2px 10px 0 rgba(0,0,0,0.12);
  transition: background 0.5s ease-in-out, height 0.5s ease-in-out;

  .container {
    display: flex;
    flex-direction: row;
    justify-content: space-between;
    width: 100%;
    max-width: 1140px;

  }

  .navbar-brand {
    color: @navbar-text-color;
    display: inline-block;
    //padding-top: .3125rem;
    //padding-bottom: .3125rem;
    margin-right: 1rem;
    font-size: 1.25rem;
    line-height: 50px;
    white-space: nowrap;

  }

  .navbar-nav { //ul
    display: flex;
    flex-direction: row;

    list-style: none;

    .nav-item { //li
      margin-left: 10px;
      //padding: 8px;

      -webkit-box-sizing: border-box;
      -moz-box-sizing: border-box;
      box-sizing: border-box;


      .nav-link { //a
        color: @navbar-text-color;
        font-size: 0.875rem;
        font-family: -apple-system,BlinkMacSystemFont,"Segoe UI",Roboto,"Helvetica Neue",Arial,"Noto Sans",sans-serif,"Apple Color Emoji","Segoe UI Emoji","Segoe UI Symbol","Noto Color Emoji";
        font-weight: 500;

        padding-right: 3px;
        i{

          width: 4px;
          height: 4px;
          font-size: 0.875rem;

        }
        &:hover{
          color: @link-hover-color;
        }

      }


    }


  }
}
.navbar{
  height: 50px !important;
  background: @navbar-bg-color;
}
</style>
