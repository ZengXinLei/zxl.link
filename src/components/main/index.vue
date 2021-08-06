<template>

  <main>
    <div :class="`container ${scrollWith?'scrollWidth':''}`" :style="{'transform':transform}">

      <div class="forFlow">
        <slot></slot>
      </div>
    </div>
  </main>
</template>

<script>
export default {
  name: 'index',
  props:{
    scrollWith:{
      default:false
    }
  },
  data(){
    return{
      transform: ""
    }
  },
  mounted() {
    if(this.scrollWith){
      //滑动监听
      let func=()=>{

        let h = document.documentElement.scrollTop
        this.transform = `translate3d(0px, -${h / 700 * 80}px, 0px)`
      }
      this.$store.commit("ADD_FUNC", {
        type:'scroll',
        func:func
      })
      document.addEventListener('scroll', func, true)
    }
  }
}
</script>

<style lang="less" scoped>
main {
  width: 100%;
  background: rgb(238, 238, 238);
  display: flex;
  flex-direction: row;
  justify-content: center;

  .scrollWith{
    -webkit-box-shadow: 0 12px 15px 0 rgba(0, 0, 0, 0.24), 0 17px 50px 0 rgba(0, 0, 0, 0.19);
    box-shadow: 0 12px 15px 0 rgba(0, 0, 0, 0.24), 0 17px 50px 0 rgba(0, 0, 0, 0.19);
  }
  .container {

    max-width: 1140px;
    width: 100%;
    background-color: rgba(255, 255, 255, 1);
    //height: 1800px;
    padding-bottom: 100px;

    border-radius: 10px;


    .forFlow {
      -webkit-box-sizing: border-box;
      -moz-box-sizing: border-box;
      box-sizing: border-box;
      width: 100%;
      padding: 0px 30px;

    }
  }
}
</style>
