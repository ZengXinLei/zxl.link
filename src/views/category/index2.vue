<template>
  <div>
    <Header></Header>
    <Banner></Banner>

    <Main>
          <router-link v-for="(value,key) in dataList" :to="`/${$store.state.user.id}/index/${value.id}/category`" :style="`color:rgb(255,100,${value.B});font-size:${value.size}px`">
            {{ value.label }}</router-link>


    </Main>
  </div>
</template>

<script>
import Header from '@/components/header/Header'
import Banner from '@/components/banner/banner'
import Main from '@/components/main/index'

export default {
  name: 'index2',
  components: { Banner, Header,Main },
  data(){
    return{
      dataList:[]
    }
  },
  mounted() {

    this.init()
  },
  methods: {
    init() {
      this.$axios.get('/category/listMap?uid=' + this.$store.state.user.id).then(res => {
        let dataList=res.data.data

        let minSize=12
        let maxSize=35
        let minB=255
        let maxB=100
        // dataList.push({
        //   id:3,
        //   label:"SPring",
        //   count:9
        // })
        let max=dataList.reduce((a,b)=>{
          return a.count>b.count?a:b
        })


        dataList.forEach(e=>{
          e.size=e.count/max.count*maxSize
          e.size=e.size>minSize?e.size:minSize
          e.B=e.count/max.count*minB
          e.B=e.B>maxB?e.B:maxB
          e.B=-1*e.B+355

        })

        this.dataList=dataList
        console.log(dataList)
      })
    }
  }
}
</script>

<style lang="less" scoped>
@import "~@/main.less";


      a{
        margin-left: 20px;
        color: rgb(255, 100, 100);

        &:hover{
          color: rgb(255, 0, 0) !important;
          font-weight:500;
          transition: all 0.5s ease-in-out;

        }
      }


</style>
