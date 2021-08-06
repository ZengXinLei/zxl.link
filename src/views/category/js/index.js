import Header from '@/components/header/Header'
import Banner from '@/components/banner/banner'
import {Page} from 'v-page'
import Main from '@/components/main/index'

export default {
  name: 'index',
  components: { Banner, Header,Page,Main },
  data(){
    return{
      map:{//待提交的数据
        current:1,
        size:10,
        uid:0,
        cid:1
      },
      dataList: {  }
    }
  },
  mounted() {
    this.getDate()
    // this.$store.commit("SET_ID",4)
    console.log(this.$store.state.user.id)
  },
  methods:{
    getDate() {

      this.map.uid=this.$store.state.user.id
      let reg=/\/.*?\/index\/(.*?)\/category/g
      let cid = reg.exec(this.$route.fullPath)[1]
      this.map.cid=cid
      this.$axios.post("/article/listByCategory",{
        ...this.map
      }).then(res=>{
        if(res.data.code===0)
        {
          this.dataList=res.data.data
          console.log(this.dataList)
        }
      })
    },
    pageChange(e){
      this.map.current=e.pageNumber
      this.map.size=e.pageSize

    }
  }
}
