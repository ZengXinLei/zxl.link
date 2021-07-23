import Header from '@/components/header/Header'
import Banner from '@/components/banner/banner'

export default {
  name: 'index',
  components: { Banner, Header },
  data(){
    return{
      map:{
        current:1,
        size:10,
        uid:1,
        cid:1
      }
    }
  },
  mounted() {
    this.getDate()
    // this.$store.commit("SET_ID",4)
    console.log(this.$store.state.user.id)
  },
  methods:{
    getDate() {

      this.$axios.post("/article/listByCategory",{
        ...this.map
      })
    }
  }
}
