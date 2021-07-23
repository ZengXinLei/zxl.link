import Header from '@/components/header/Header'

import AutocJs from '@/components/AutocJS/js/autoc'
import Banner from '@/components/banner/banner'

import {Base64} from 'js-base64'
export default {
  name: 'index',
  components: { Banner, Header },
  data() {

    return {
      article: {


      }

    }

  },
  mounted() {
    window.onload=()=>{

      // document.getElementById("sideCatalog-catalog").style.display="block"
    }
    window.onresize=window.onload=()=>{
      this.setRightPos()
    }
    document.addEventListener('scroll', () => {


      this.setTopPos()

    }, true)
    this.getArticle()
  },
  methods: {
    getMenu(){
      let navigation = new AutocJs({
        // 文章正文 DOM 节点的 ID 选择器
        article: '#markdown',
        // 要收集的标题选择器
        selector: 'h1,h2,h3,h4,h5,h6',
        // 侧边栏导航的标题
        title: '目录',
        // 文章导读导航的位置
        // outside - 以侧边栏菜单形式显示（默认值）
        // inside - 在文章正文一开始的地方显示
        position: 'outside',
        // 标题图标链接的 URL 地址
        // （默认）没有设置定制，点击链接页面滚动到标题位置
        // 设置了链接地址，则不会滚动定位
        anchorURL: '',
        // 链接的显示位置
        // front - 在标题最前面（默认值）
        // back - 在标题后面
        anchorAt: 'front',
        // 是否生成文章导读导航
        isGenerateOutline: true,
        // 是否在文章导读导航中显示段落章节编号
        isGenerateOutlineChapterCode: true,
        // 是否在正文的文章标题中显示段落章节编号
        isGenerateHeadingChapterCode: false,
        // 是否在正文的文章标题中创建锚点
        isGenerateHeadingAnchor: true
      });

// 可以在创建导航后，重置配置信息，重新生成新的导航
      navigation.reload({
        // 调整位直接在文章内生成导航
        position: 'outside',
        // 并且在文章标题前显示段落的章节层次索引值
        isGenerateHeadingChapterCode: true
      })

      this.setRightPos()

      this.setTopPos()
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
        })
      }).then(res=>{
        this.article.contentHtml=res.data
        // this.article.contentHtml
        console.log(this.article)
        setTimeout(()=>{

          this.getMenu()

        },500)

      })

    },

    /**
     * 设置目录距离右边的位置
     */
    setRightPos(){
      let nav=document.getElementById("sideCatalog-catalog")
      let navW=nav.offsetWidth
      let w = document.documentElement.offsetWidth
      let contentW=document.getElementById("container").offsetWidth

      console.log(navW)
      let navToRight=(w-(w-contentW)/2-contentW-10-navW)<0?0:(w-(w-contentW)/2-contentW-10-navW)//目录距离窗口右边的距离
      nav.style.right=navToRight+"px"
    },
    /**
     * 设置目录距离上边的位置
     */
    setTopPos(){
      let h = document.documentElement.scrollTop
      if(h>=466){
        document.getElementById("sideCatalog-catalog").style.position="fixed"
        document.getElementById("sideCatalog-catalog").style.top="60px"

      }else {
        document.getElementById("sideCatalog-catalog").style.position="absolute"
        document.getElementById("sideCatalog-catalog").style.top="3px"


      }
    }
  }

}
