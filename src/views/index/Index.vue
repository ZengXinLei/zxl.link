<template>

  <div>

    <Header></Header>
    <Banner>
      <div class="page-header">
        <span class="h2">{{ user.signature_ }}</span>
        <span class="typed-cursor h2">_</span>
      </div>
    </Banner>



    <main>
      <div class="container" :style="{'transform':transform}">

        <div class="forFlow">
          <div v-if="dataList.records" v-for="(article,index) in dataList.records" class="article">
            <div class="title">
              <router-link :to="$route.fullPath+'/'+article.id+'/article/'" class="title2">
                {{ article.title }}
              </router-link>
<!--              <span class="type">置顶</span>-->
            </div>

            <span class="meta">
              <i class="iconfont icon-time-line"></i>
              发表于{{ new Date(article.time).toLocaleString().replace(/:\d{1,2}$/,' ') }}
              <i class="iconfont icon-yanjing-tianchong"></i>
              阅读：333
              <i class="iconfont icon-pinglun"></i>
              评论：0
              <i class="iconfont icon-dianzan1"></i>
            </span>


            <div class="desc">

              {{article.contentText}}
              <router-link :to="$route.fullPath+'/'+article.id+'/article/'" class="readmore">阅读全文 »</router-link>
            </div>
          </div>

          <Page :total-row="dataList.total" @page-change="pageChange"></Page>
        </div>



      </div>
    </main>
  </div>
</template>

<script>
import {Page} from 'v-page'

import Header from '@/components/header/Header'
import Banner from '@/components/banner/banner'

export default {
  name: 'Index',

  components: { Header ,Page,Banner },
  data() {
    return {

      transform: '',
      user: {
        signature: '你就是打死我，我也不改这个bug',
        signature_: ''
      },
      dataList:{},
      dataForm:{
        page:1,
        limit:10,
        uid:""
      }

    }
  },
  mounted() {
    //滑动监听
    let func=()=>{

      let h = document.documentElement.scrollTop
      this.transform = `translate3d(0px, -${h / 700 * 80}px, 0px)`
    }
    this.$store.commit("ADD_FUNC", {
      type:'scroll',
      func:func
    })
    document.addEventListener('scroll', func, true)

    for (let i = 0; i < this.user.signature.length; i++) {
      setTimeout(() => {
        let c = this.user.signature[i]

        this.user.signature_ += c
      }, i * 150)

    }
    this.list()

  },
  methods: {


    /**
     * 分页获取文章列表
     */
    list(){

      let reg=/^\/(.*?)\/index.*/g
      let execArray = reg.exec(this.$route.fullPath)
      this.dataForm.uid=execArray[1]

      this.$axios.post("/article/list",{ ...this.dataForm }).then(res=>{
        this.dataList=res.data.data
      })
    },
    /**
     * 当分页改变的时候
     * @param e
     */
    pageChange(e){

      this.dataForm.page=e.pageNumber
      this.dataForm.limit=e.pageSize
      this.list()
    }
  },

}
</script>

<style lang="less" scoped>
@import "index";
</style>
