<template>

  <div>
    <Header></Header>
    <Banner></Banner>
    <main>
      <div class="container">
        <div class="forFlow"></div>
      </div>
    </main>
  </div>
</template>

<script>
import Header from '@/components/header/Header'
import Banner from '@/components/banner/banner'

export default {
  name: 'index',
  components: { Banner, Header },
  data(){
    return{

    }

  },
  mounted() {
    this.init()

  },
  methods:{
    init(){
      this.$axios.get("/tag/listByUid?uid="+this.$store.state.user.id)
    }
  }
}
</script>

<style lang="less" scoped>
@import "~@/main.less";

.main {
  position: relative;
  width: 100%;
  background: rgb(238, 238, 238);
  display: flex;
  flex-direction: row;
  justify-content: center;

  .container {

    position: relative;
    background-color: rgba(255, 255, 255, .9);
    width: 100%;
    max-width: @max-width;
    min-height: 60vh;

    padding: 0 20px 30px;
    -webkit-box-sizing: border-box;
    -moz-box-sizing: border-box;
    box-sizing: border-box;

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
