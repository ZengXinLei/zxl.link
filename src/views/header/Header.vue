<template>
  <div id="navbar">

    <div class="container">
      <a href="/" class="navbar-brand">
        &nbsp;<strong>曾大人</strong>&nbsp;
      </a>
      <div class="collapse">

        <ul class="navbar-nav">
          <li class="nav-item"><a href="/" class="nav-link"><i class="iconfont">首页</i></a></li>
          <li class="nav-item"><a href="/" class="nav-link"><i class="iconfont">归档</i></a></li>
          <li class="nav-item"><a href="/" class="nav-link"><i class="iconfont">分类</i></a></li>
          <li class="nav-item"><a href="/" class="nav-link"><i class="iconfont">标签</i></a></li>
          <li class="nav-item"><a href="/" class="nav-link"><i class="iconfont">关于</i></a></li>

        </ul>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'Header'

}
</script>

<style lang="less" scoped>

@import '~@/main.less';

#navbar {
  position: fixed;
  width: 100%;
  height: 50px;
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
        //font-size: 0.875rem;
        font-family: -apple-system,BlinkMacSystemFont,"Segoe UI",Roboto,"Helvetica Neue",Arial,"Noto Sans",sans-serif,"Apple Color Emoji","Segoe UI Emoji","Segoe UI Symbol","Noto Color Emoji";
        font-weight: 500;
        font-size: 0.8rem;

      }
    }


  }
}
</style>
