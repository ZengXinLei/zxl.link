<template>

  <div>

    <Header></Header>
    <div class="banner" id="banner"></div>
  </div>
</template>

<script>
import Header from '@/views/header/Header'
import Main from '@/views/article'

export default {
  name: 'Index',
  components: { Header, Main }
}
</script>

<style lang="less" scoped>




#banner{
  background: url("https://liwangc.gitee.io/img/index.jpg") center center / cover no-repeat;

  width: 100%;
  height: 100vh;
}

</style>
