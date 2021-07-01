<template>

  <div id="wrap">

    <Header></Header>
    <Main></Main>
  </div>
</template>

<script>
import Header from "@/views/header/Header";
import Main from "@/views/main/Main";
export default {
name: "Index",
  components: {Header,Main}
}
</script>

<style scoped>

#wrap{
  display: flex;
  flex-direction: row;
}

@media all and (max-width: 768px) {

  #wrap{
    display: flex;
    flex-direction: column;
  }


}
</style>