<template>

  <div>
    <Header></Header>
    <Banner :hasScrollDown="false" :height="'40vh'">
      <div class="main-header-content">
        <h1 class="article-title">{{article.title}}</h1>
        <p class="article-info">
          <!--            <span class="meta">-->
          <i class="iconfont icon-time-line"></i>
          发表于{{ new Date(article.time*1000).toLocaleString().replace(/:\d{1,2}$/,' ') }}
<!--          <i class="iconfont icon-yanjing-tianchong"></i>-->
<!--          阅读：333-->
<!--          <i class="iconfont icon-pinglun"></i>-->
<!--          评论：0-->
<!--          <i class="iconfont icon-dianzan1"></i>-->
          <!--            </span>-->
        </p>
        <router-link v-for="(category,key) in article.categories" :to="`/${$store.state.user.id}/index/${category.id}/category`">
          <span class="article-tag">{{ category.label }}</span>
        </router-link>
      </div>
    </Banner>

    <main>
      <div id="container" class="container">

        <div v-html="article.contentHtml" id="markdown" :class="'mainContent markdown-preview markdown-theme-mac'">


        </div>
        <div class="bottom-comment">
          <h3 class="title">73评论</h3>
          <div class="comment-send">
            <div class="user-face">
              <img :src="$store.state.user.user.avatar" alt="" class="user-head">

            </div>
            <div class="textarea-container">
              <textarea v-model="comment.text" cols="80" name="msg" rows="5" placeholder="发一条友善的评论" class="ipt-txt"></textarea>

              <div ref="tip"  class="comment-submit">
                <div v-if="showTip" class="comment-submit-tip">评论成功</div>
                <button type="submit"  @click="commentSubmit">
                  发表评论</button>
              </div>

            </div>
          </div>
          <emoji-icon @select="selectEmoji" class="emoji" :iconConfig="iconConfig"></emoji-icon>
        </div>
      </div>
      <div v-if="showCatalogue" id="sideCatalog-catalog-wrap">
        <div @click="showOrDisplay" class="switch">
          <i class="icon iconfont icon-caidan"></i>
        </div>

        <div ref="switch"  id="sideCatalog-catalog" >
        </div>
      </div>



    </main>




  </div>

</template>

<script>

import Article from './index'
export default Article
</script>

<style lang="less" scoped>
@import "index.less";
@import "~@/assets/alifont/iconfont.css";

@import "~@/components/AutocJS/css/autoc.css";

@import "~@/components/markdown/assets/css/theme";
@import "~@/components/markdown/assets/css/light";
@import "~@/components/markdown/assets/css/dark";
@import "~@/components/markdown/assets/css/one-dark";
@import "~@/components/markdown/assets/css/github";
@import "~@/components/markdown/assets/css/index";
@import "~@/components/markdown/assets/css/mac";
</style>
