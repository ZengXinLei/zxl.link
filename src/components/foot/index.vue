<template>
<div class="wrap1">
  <a href="http://beian.miit.gov.cn">湘ICP备2021012552</a>
</div>
</template>

<script>
export default {
  name: 'index'
}
</script>

<style lang="less" scoped>
@import "~@/main.less";
.wrap1{
  background: #eee;
  width: 100%;
  height: 100px;
  display: flex;
  flex-direction: row;
  justify-content: center;
  align-items: center;
  a{
    color: gray;
  }
}
</style>
