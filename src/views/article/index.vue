<template>

  <div class="wrap">

    <div v-html="article.contentHtml" class="markdown-content markdown-theme-light"></div>
  </div>
</template>

<script>
import {Base64} from 'js-base64'
export default {
  name: 'index',
  data() {

    return {
      article: {

      }
    }

  },
  mounted() {
    this.getArticle()
  },
  methods: {
    /**
     * 获取文章
     */
    getArticle() {
      let path = this.$route.path
      let reg = /^\/(.*?)\/index\/article\/(.*)/g
      let execArray = reg.exec(path)
      console.log(execArray)

      let article = {
        uid: execArray[1],
        id: execArray[2]
      }
      this.$axios.post('/article/getArticleByUserId', {
        ...article
      }).then(res => {
        this.article=res.data.article
        return this.$axios.get(this.article.contentUrl,{
          headers:{

          }
        })
      }).then(res=>{
        this.article.contentHtml=Base64.decode(res.data.content)
        // this.article.contentHtml
        console.log(this.article)
      })

    }
  }

}
</script>

<style lang="less" scoped>

@import "~@/components/markdown/assets/font/iconfont.css";
//@import "~@/components/markdown/assets/font-awesome-4.7.0/css/font-awesome.css";

@import "~@/components/markdown/assets/css/theme";
@import "~@/components/markdown/assets/css/light";
@import "~@/components/markdown/assets/css/dark";
@import "~@/components/markdown/assets/css/one-dark";
@import "~@/components/markdown/assets/css/github";
@import "~@/components/markdown/assets/css/index";
@import "~@/components/markdown/assets/css/mac";
.wrap {
  width: 100%;
  overflow: auto;
}
.wrap::-webkit-scrollbar {
  width: 3px;
  height: 5px;
  background: white;
}
.wrap::-webkit-scrollbar-button{
  height: 3px;
}
.wrap::-webkit-scrollbar-thumb { /* 滚动条手柄 */
  background-color: #555;
}
.wrap::-webkit-scrollbar-corner,
.wrap::-webkit-scrollbar-thumb,
.wrap::-webkit-scrollbar-track{
  border-radius: 5px;
}

.wrap::-webkit-scrollbar-corner,
.wrap::-webkit-scrollbar-track { /* 滚动条轨道 */
  background-color: rgba(180,160,120,.1);
  box-shadow: inset 0 0 1px rgba(180,160,120,.5);
}
</style>
