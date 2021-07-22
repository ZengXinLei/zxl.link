<template>

  <div>

    <Header></Header>
    <div class="banner" id="banner">
      <div class="mask"></div>
      <div class="page-header">
        <span class="h2">{{ user.signature_ }}</span>
        <span class="typed-cursor h2">_</span>
      </div>
      <div class="scroll-down-bar" @click="scrollDown">
        <i class="iconfont icon-xiangxia1"></i>

      </div>
    </div>


    <main>
      <div class="container" :style="{'transform':transform}">

        <div class="forFlow">
          <div v-if="dataList.records" v-for="(article,index) in dataList.records" class="article">
            <div class="title">
              <a :href="$route.fullPath+'/'+article.id+'/article/'" class="title2">
                {{ article.title }}
              </a>
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
              <a :href="$route.fullPath+'/'+article.id+'/article/'" class="readmore">阅读全文 »</a>
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
import Header from '@/views/header/Header'

export default {
  name: 'Index',

  components: { Header ,Page },
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
    document.addEventListener('scroll', () => {

      let h = document.documentElement.scrollTop
      this.transform = `translate3d(0px, -${h / 700 * 80}px, 0px)`
      // console.log(h)

    }, true)

    for (let i = 0; i < this.user.signature.length; i++) {
      setTimeout(() => {
        let c = this.user.signature[i]

        this.user.signature_ += c
        console.log(i)
      }, i * 150)

    }
    this.list()

  },
  methods: {
    /**
     * 点击banner的向下箭头滚动条滚动到1200位置
     */
    scrollDown: function() {
      let h = document.documentElement.scrollTop

      let speed = 1
      let scrollTimer
      scrollTimer = setInterval(() => {
        h += speed
        speed += 1
        if (h > 1200) {
          clearInterval(scrollTimer)

        } else {
          document.documentElement.scrollTop = h

        }
      }, 10)
    },

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
