<template>

  <div>
    <Header></Header>
    <Banner :hasScrollDown="false" :height="'40vh'">
      <div class="main-header-content">
        <h1 class="article-title">{{ article.title }}</h1>
        <p class="article-info">
          <!--            <span class="meta">-->
          <i class="iconfont icon-time-line"></i>
          发表于{{ new Date(article.time * 1000).toLocaleString().replace(/:\d{1,2}$/, ' ') }}
          <!--          <i class="iconfont icon-yanjing-tianchong"></i>-->
          <!--          阅读：333-->
          <!--          <i class="iconfont icon-pinglun"></i>-->
          <!--          评论：0-->
          <!--          <i class="iconfont icon-dianzan1"></i>-->
          <!--            </span>-->
        </p>
        <router-link v-for="(category,key) in article.categories"
                     :to="`/${$store.state.user.id}/index/${category.id}/category`"
        >
          <span class="article-tag">{{ category.label }}</span>
        </router-link>
      </div>
    </Banner>

    <main>
      <div id="container" class="container">

        <div v-html="article.contentHtml" id="markdown" :class="'mainContent markdown-preview markdown-theme-mac'">


        </div>
        <div class="bottom-comment">
          <h3 class="title">{{ article.commentCount }}评论</h3>
          <div class="comment-send">
            <div class="user-face">
              <img :src="$store.state.user.user?$store.state.user.user.avatar:''" alt="" class="user-head">

            </div>
            <div class="textarea-container">
              <textarea @focus="comment2.cid=0" v-model="comment.text" cols="80" name="msg" rows="5" placeholder="发一条友善的评论" class="ipt-txt"
              ></textarea>

              <div ref="tip" class="comment-submit">

                <button type="submit" @click="commentSubmit(comment)">
                  发表评论
                </button>
              </div>

            </div>
          </div>
<!--          <emoji-icon @select="selectEmoji" class="emoji" :iconConfig="iconConfig"></emoji-icon>-->
        </div>
        <div class="infinite-list" >

          <div v-for="(comment_,index) in commentList" class="list-item reply-wrap " data-id="5096296772" data-index="1">
            <div class="user-face"><a href="//space.bilibili.com/1952748277" target="_blank"
                                      data-usercard-mid="1952748277"
            >
              <div class="bili-avatar">

                <img :src="comment_.avatar">



              </div>
            </a></div>
            <div class="con ">
              <div class="user">
                <a data-usercard-mid="1952748277" href="//space.bilibili.com/1952748277" target="_black" class="name">{{ comment_.name }}</a>


                <span v-if="comment_.uid===$store.state.user.id" class="identity">作者</span>
              </div>
              <p class="text">
                {{comment_.text}}
              </p>
              <div class="info">
                <span class="time">{{ comment_.time.replace("T"," ").split(".")[0] }}</span>
<!--                <span class="like ">-->
<!--                  <i class="fa fa-thumbs-up"></i>-->
<!--                  <span>275</span>-->
<!--                </span>-->
<!--                <span class="hate ">-->
<!--                  <i class="fa fa-thumbs-down"></i>-->
<!--                </span>-->
                <span class="reply" @click="comment2.cid=comment_.id">回复</span>
<!--                <div class="operation more-operation">-->
<!--                  <div class="opera-list">-->
<!--                    <el-popover-->
<!--                        placement="top"-->
<!--                        width="70"-->
<!--                        trigger="click">-->
<!--                      <ul>-->
<!--                        <li class="blacklist">加入黑名单</li>-->
<!--                        <li class="report">举报</li>-->
<!--                      </ul>-->
<!--                      <el-button size="mini" slot="reference" icon="el-icon-more"></el-button>-->
<!--                    </el-popover>-->

<!--                  </div>-->
<!--                </div>-->
              </div>
              <div class="reply-box" data-page="1" data-total="5">
                <div v-for="(comment_1,index1) in comment_.children" class="reply-item reply-wrap" data-id="5098896184" data-index="0">
                  <a href="//space.bilibili.com/312178550" data-usercard-mid="312178550" target="_blank" class="reply-face" style="top: 0px;">
                    <div class="bili-avatar">

                      <img class="bili-avatar-img bili-avatar-face bili-avatar-img-radius"
                           :src="comment_1.avatar"
                           alt=""
                      >


                    </div>
                  </a>
                  <div class="con">
                    <div class="reply-con">
                      <div class="user">
                        <a data-usercard-mid="312178550" href="//space.bilibili.com/312178550" target="_black" class="name" >{{ comment_1.name }}</a>

                        <span v-if="comment_1.uid===$store.state.user.id" class="identity">作者</span>
                        <span v-if="comment_1.to_name" style="margin-right: 10px;"> 回复</span>
                        <a v-if="comment_1.to_name" class="to-name" href="" target="_blank" style="margin-right: 10px;">@{{comment_1.to_name}}</a><span>{{comment_1.text}}</span></div>
                    </div>
                    <div class="info">
                      <span class="time">{{ comment_1.time.replace("T"," ").split(".")[0] }}</span>
<!--                      <span class="like ">-->
<!--                        <i></i>-->
<!--                        <span>2</span>-->
<!--                      </span>-->
<!--                      <span class="hate ">-->
<!--                        <i></i>-->
<!--                      </span>-->
                      <span class="reply btn-hover" @click="comment2.cid=comment_.id ; comment2.cid1=comment_1.id">回复</span>
<!--                      <div class="operation btn-hover btn-hide-re">-->
<!--                        <div class="spot"></div>-->
<!--                        <div class="opera-list">-->
<!--                          <el-popover-->
<!--                              placement="top"-->
<!--                              width="100"-->
<!--                              trigger="click">-->
<!--                            <ul>-->
<!--                              <li class="blacklist">加入黑名单</li>-->
<!--                              <li class="report">举报</li>-->
<!--                            </ul>-->
<!--                            <el-button size="mini" slot="reference" icon="el-icon-more"></el-button>-->
<!--                          </el-popover>-->

<!--                        </div>-->
<!--                      </div>-->
                    </div>
                  </div>
                </div>
              </div>
              <div class="comment-send" style="margin-top: 20px;" v-if="comment2.cid===comment_.id||comment_.children.filter(e=>e.id===comment2.cid).length>0">
                <div class="textarea-container" style="padding: 0;">
              <textarea  style="margin-left: 0;" v-model="comment2.text" cols="80" name="msg" rows="5" :placeholder="`评论 @${comment_.name}:`" class="ipt-txt"
              ></textarea>

                  <div ref="tip" class="comment-submit">

                    <button type="submit" @click="commentSubmit(comment2)">
                      发表评论
                    </button>
                  </div>

                </div>
              </div>
            </div>
          </div>


        </div>
      </div>
      <div v-if="showCatalogue" id="sideCatalog-catalog-wrap">
        <div @click="showOrDisplay" class="switch">
          <i class="icon iconfont icon-caidan"></i>
        </div>

        <div ref="switch" id="sideCatalog-catalog">
        </div>
      </div>


    </main>


  </div>

</template>

<script>

import Article from './index'

export default Article
</script>

<style lang="less" scoped>
@import "index.less";
@import "~@/assets/alifont/iconfont.css";

@import "~@/components/AutocJS/css/autoc.css";

@import "~@/components/markdown/assets/css/theme";
@import "~@/components/markdown/assets/css/light";
@import "~@/components/markdown/assets/css/dark";
@import "~@/components/markdown/assets/css/one-dark";
@import "~@/components/markdown/assets/css/github";
@import "~@/components/markdown/assets/css/index";
@import "~@/components/markdown/assets/css/mac";
</style>
