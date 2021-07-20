<template>

  <div id="wrap">

    <Header></Header>
    <div class="main">
      <router-view ></router-view>

    </div>
    <div class="sider">
      <router-view name="sider"></router-view>

    </div>
  </div>
</template>

<script>
import Header from "@/views/header/Header";
import Main from "@/views/article";
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
.main{

  width: 62%;
}
.sider{
  width: 22%;
}
@media all and (max-width: 768px) {

  #wrap{
    display: flex;
    flex-direction: column;
  }
  .main{
    width: 100%;
    height: 70vh;
  }

  .sider{
    display: none;
  }
}
@media all and (max-width: 991px) {
  .sider{
    width: 30%;
  }
  .main{
    width: 66%;
  }

}


/*@media all and (max-width: 768px) {*/

/*}*/


</style>
