<template>

  <div>
    <Header></Header>
    <div class="banner">

      <div class="mask"></div>
    </div>
    <main>
      <div class="container">

        <div v-html="article.contentHtml" id="markdown" :class="'mainContent markdown-preview markdown-theme-mac'">

          <h1>你能</h1>
        </div>
        <div id="sideCatalog-catalog"></div>
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

@import "~@/components/AutocJS/css/autoc.css";

@import "~@/components/markdown/assets/css/theme";
@import "~@/components/markdown/assets/css/light";
@import "~@/components/markdown/assets/css/dark";
@import "~@/components/markdown/assets/css/one-dark";
@import "~@/components/markdown/assets/css/github";
@import "~@/components/markdown/assets/css/index";
@import "~@/components/markdown/assets/css/mac";
</style>
