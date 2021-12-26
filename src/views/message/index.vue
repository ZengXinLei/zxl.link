<template>
  <div>

    <el-tabs type="border-card">
      <el-tab-pane label="回复我的">
        <div class="pane-wrap">
          <div class="comment-wrap">
            <div class="left-box">

            </div>
            <div class="center-box">

              <div class="line-1">
                <a href="/">用户名</a><span>回复了我的评论</span>
              </div>
              <div class="content-list">
                <span>回复</span>
                <a href="/">我</a>
                <span>:内容</span>
              </div>
              <div class="reply-content">
                我:回复别人:内容
              </div>
              <div class="line-3">
                <span class="time">时间</span>
              </div>
            </div>
            <div class="right-box"></div>
          </div>

        </div>

      </el-tab-pane>
      <el-tab-pane label="消息中心">
        <div class="pane-wrap"></div>
      </el-tab-pane>
      <el-tab-pane label="角色管理">
        <div class="pane-wrap"></div>

      </el-tab-pane>
      <el-tab-pane label="定时任务补偿">
        <div class="pane-wrap"></div>

      </el-tab-pane>
    </el-tabs>
  </div>
</template>

<script>
export default {
  name: "index"
}
</script>

<style lang="less" scoped>
@import "index.less";
</style>
