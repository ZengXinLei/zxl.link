<template>

  <div class="wrap">
    <div class="header">
      <div id="avatar"></div>
      <div class="name">曾大人</div>
      <div class="title">标题</div>
      <small class="location">small</small>
      <div class="menu">
        <i class="el-icon-s-operation" @click="showMenu"></i>
      </div>
    </div>
    <div :class="menuClass" ref="lists">
      <div class="list">
        <i class="el-icon-s-home"></i>

        <div class="list_name"><span>首页</span></div>
      </div>
      <div class="list">
        <i class="el-icon-s-cooperation"></i>

        <div class="list_name"><span>归档</span></div>
      </div>
      <div class="list">
        <i class="el-icon-s-management"></i>

        <div class="list_name"><span>分类</span></div>
      </div>
      <div class="list">
        <i class="el-icon-collection-tag"></i>

        <div class="list_name"><span>标签</span></div>
      </div>
      <div class="list">
        <i class="el-icon-receiving"></i>

        <div class="list_name"><span>项目</span></div>
      </div>
      <div class="list">
        <i class="el-icon-paperclip"></i>

        <div class="list_name"><span>友链</span></div>
      </div>
      <div class="list">
        <i class="el-icon-s-open"></i>

        <div class="list_name"><span>关于</span></div>
      </div>

    </div>

  </div>
</template>

<script>
export default {
  name: "Header",
  data() {
    return {
      isShowMenu:false,
      menuClass:"lists",

    }
  },
  methods: {
    showMenu() {
      this.isShowMenu=!this.isShowMenu
      this.menuClass=this.isShowMenu?"lists":"hidden_lists"


    }
  }
}
</script>

<style scoped>

@import "header.css";
</style>