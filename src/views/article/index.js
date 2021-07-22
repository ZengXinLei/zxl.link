import Header from '@/views/header/Header'

import {Base64} from 'js-base64'
export default {
  name: 'index',
  components: { Header },
  data() {

    return {
      article: {

      },

    }

  },
  mounted() {
    this.getArticle()
  },
  methods: {
    getMenu(){

      let reg=/<h(\d).*?>(.*?)<\/h[\d]>/g
      let hs = this.article.contentHtml.match(reg)
      let title={
        content:"",
        level:0,
        label:""
      }
      let menus=[]
      // console.log(reg.exec("<h2 id=\"得瑟得瑟\">得瑟得瑟</h2>"))
      hs.forEach(e=>{
        reg.lastIndex=0
        let arr=reg.exec(e)
        menus.push({
          content:arr[2],
          level: Number(arr[1]),

        })
      })

      console.log(menus)


    },
    /**
     * 获取文章
     */
    getArticle() {
      let path = this.$route.path
      let reg = /^\/(.*?)\/index\/(.*)\/article/g
      let execArray = reg.exec(path)

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
        this.getMenu()
      })

    }
  }

}
