<template>
  <div class="dashboard-container">
    <el-button @click="test"></el-button>
  </div>
</template>

<script>
import { mapGetters } from 'vuex'

export default {
  name: 'Dashboard',
  computed: {
    ...mapGetters([
      'name'
    ])
  },
  methods:{
    test(){
      this.$axios.post("/user/t").then(res=>{
        // console.log(res)
        // console.log(this.$router.options.routes)

      })
    }
  }
}
</script>

<style lang="scss" scoped>
.dashboard {
  &-container {
    margin: 30px;
  }
  &-text {
    font-size: 30px;
    line-height: 46px;
  }
}
</style>
