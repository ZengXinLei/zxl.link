<template>
  <div id="app">


    <router-view></router-view>
<!--    <Index></Index> -->

    <Foot v-if="exclude.filter(e=>e.test($route.path)).length===0"></Foot>
  </div>
</template>

<script>
import Index from '@/views/index/Index'
import Foot from '@/components/foot/index'
export default {
  name: 'App',
  data(){
    return{
      exclude:[
        /index\/.*?\/article/g

      ]
    }
  },
  components: {
    Index,
    Foot
  }
}
</script>

<style>
#app {
  /*font-family: Avenir, Helvetica, Arial, sans-serif;*/
  /*-webkit-font-smoothing: antialiased;*/
  /*-moz-osx-font-smoothing: grayscale;*/
  /*text-align: center;*/
  /*color: #2c3e50;*/
  /*margin-top: 60px;*/
}

</style>
