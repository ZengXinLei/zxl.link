import Header from '@/components/header/Header'
import Banner from '@/components/banner/banner'
import da from 'element-ui/src/locale/lang/da'
import Main from '@/components/main/index'

export default {
  name: 'index',
  components: { Banner, Header,Main },
  data() {
    return {

      dataList:[]
    }
  },

  mounted() {
    this.$axios.get('/article/archives?uid='+this.$store.state.user.id).then(res=>{

      for (let key in res.data.data){
        this.dataList.push(res.data.data[key])
      }
    })

  },
  methods:{
    formatDate(datetime) {
      var date = new Date(datetime);//时间戳为10位需*1000，时间戳为13位的话不需乘1000
      var year = date.getFullYear(),
        month = ("0" + (date.getMonth() + 1)).slice(-2),
        sdate = ("0" + date.getDate()).slice(-2),
        hour = ("0" + date.getHours()).slice(-2),
        minute = ("0" + date.getMinutes()).slice(-2),
        second = ("0" + date.getSeconds()).slice(-2);
      // 拼接
      var result = year + "-"+ month +"-"+ sdate +" "+ hour +":"+ minute +":" + second;
      // 返回
      return result;
    }
  }
}
