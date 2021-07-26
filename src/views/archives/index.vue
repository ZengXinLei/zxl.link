<template>

  <div>
    <Header></Header>
    <Banner></Banner>
    <main>
      <div class="container">

<!--        <h3>共计20篇文章</h3>-->
        <div class="forFlow">

          <div v-for="(list,key) in dataList" class="tl-wrap">
            <div :class="'tl-header  tl-style-'+key%7">{{list[0].time}}</div>
            <div v-for="(article,index) in list" class="tl-body">
              <div class="tl-date-wrap">
                <span class="tl-date">{{article.time1}}</span>
              </div>
              <div :class="'tl-split tl-split-style-'+key%7">
                <div class="top  "></div>
                <div class="center  "></div>
                <div class="down  "></div>
              </div>
              <div :class="'tl-content tl-style-'+key%7">
                <span class="arrow "></span>

                <router-link :to="`/${$store.state.user.id}/index/${article.id}/article`">{{article.title}}</router-link>
              </div>

            </div>
          </div>
        </div>

      </div>
    </main>
  </div>
</template>

<script>
import archives from './js/index'
export  default  archives
</script>

<style lang="less" scoped>
@import "./less/index.less";
</style>
