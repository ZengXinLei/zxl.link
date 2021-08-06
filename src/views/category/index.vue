<template>

  <div>
    <Header></Header>
    <Banner></Banner>
<Main>
<!--        <div class="forFlow">-->
          <div v-for="(article,key) in dataList.records" class="postList">
            <div class="postTitle">
              <router-link :to="`/${$store.state.user.id}/index/${article.id}/article`">{{article.title}}</router-link>
            </div>
            <div class="postDesc">
              <span>{{article.userName}}</span>
              <span>{{new Date(article.time).toLocaleString().replace(/:\d{1,2}$/,' ')}}</span>
              <span> 阅读:0</span>
              <span>评论:3</span>
              <span>点赞:3</span>
            </div>
          </div>
<!--        </div>-->
        <Page v-if="dataList.total>map.size" :total-row="dataList.total" @page-change="pageChange"></Page>
</Main>
  </div>
</template>

<script>
import category from './js/index'
export default category
</script>

<style lang="less" scoped>
@import "./less/index.less";
</style>
