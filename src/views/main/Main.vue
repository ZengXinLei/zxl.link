<template>

  <div class="wrap"></div>
</template>

<script>
export default {
  name: "Main"
}
</script>

<style scoped>

  .wrap{
    background: #000;
    width: 62%;
  }
  @media all and (max-width: 768px) {
    .wrap{
      width: 100%;
      height: 70vh;
    }
  }
</style>