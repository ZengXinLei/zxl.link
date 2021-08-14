<template>
  <div class="head-banner" id="banner" :style="`height:${height};background-image:url('${background}')`">
    <div class="mask"></div>

    <slot></slot>
    <div v-if="hasScrollDown" class="scroll-down-bar" @click="scrollDown">
      <i class="iconfont icon-xiangxia1"></i>

    </div>
  </div>
</template>

<script>
export default {
  name: 'banner',
  props:{
    height:{
      default:"100vh",
    },
    background:{
      default: "https://liwangc.gitee.io/img/index.jpg"
    },
    hasScrollDown:{
      default:true
    }

  },
  methods:{
    /**
     * 点击banner的向下箭头滚动条滚动到1200位置
     */
    scrollDown: function() {
      let h = document.documentElement.scrollTop

      let speed = 1
      let scrollTimer
      scrollTimer = setInterval(() => {
        h += speed
        speed += 1
        if (h > 1200) {
          clearInterval(scrollTimer)

        } else {
          document.documentElement.scrollTop = h

        }
      }, 10)
    },
  }
}
</script>

<style lang="less" scoped>
#banner {
  position: relative;

  background-size: cover;
  background-position: center center;
  width: 100%;
  display: flex;
  flex-direction: row;
  justify-content: center;
  align-items: center;

  .mask {
    position: absolute;
    width: 100%;
    height: 100%;
    background: rgba(0, 0, 0, 0.3);

  }



  .scroll-down-bar {
    position: absolute;
    bottom: 0;
    width: 100%;
    height: 80px;
    text-align: center;
    cursor: pointer;


    i {
      position: absolute;
      padding-top: 1rem;
      font-size: 2rem;
      font-weight: bold;
      color: white;
      -webkit-animation: scroll-down 1.5s infinite;
      animation: scroll-down 1.5s infinite;
    }

  }
}

</style>
