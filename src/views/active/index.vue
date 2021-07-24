<template>

</template>

<script>
export default {
  name: "index",
  mounted() {
    let uuid=this.$route.query.uuid
    this.$axios.get("/user/active?uuid="+uuid).then(res=>{
      if(res.data.code===0){
        this.$message({
          message:res.data.msg,
          type:"success"

        })
        this.$router.push("/")
      }else {
        this.$message({
          message:res.data.msg,
          type:"error"

        })
        this.$router.push("/login")
      }
    })
    console.log();
  }
}
</script>

<style scoped>

</style>
