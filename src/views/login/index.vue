<template>
  <div class="wrap">

    <div v-if="!isActive" class="login_form">
      <div class="login_title">{{ isRegister ? "注册" : "登录" }}</div>
      <el-form style="margin-top: 20px;" class="form" :data="loginForm">

        <div v-if="!isRegister">
          <el-form-item style="display: block;">
            <el-input v-model="loginForm.name" class="el-input" placeholder="用户名/邮箱"/>

          </el-form-item>
          <el-form-item  style="display: block;">
            <el-input v-model="loginForm.password" class="el-input" type="password" placeholder="密码"/>

          </el-form-item>
          <div style="display: flex;flex-direction: row;justify-content: space-between;">
            <el-form-item  style="display: block;">
              <el-input v-model="loginForm.code" class="el-input" placeholder="验证码"></el-input>
            </el-form-item>
            <img v-if="loginForm.uuid!=='-1'" :src="$axios.defaults.baseURL+'user/getCode/'+loginForm.uuid"
                 @click="getCode"/>
          </div>
          <el-form-item   style="display: block;">
            <el-button :disabled="loading" v-loading="loading" @click="handleLogin" style="width: 100%;background: #ff7f5f;color: #FFFFFF">{{ isRegister ? "注册" : "登录" }}</el-button>

          </el-form-item>
        </div>
        <div v-if="isRegister">
          <el-form-item style="display: block;">
            <el-input v-model="loginForm.email" class="el-input" placeholder="邮箱"/>

          </el-form-item>
          <el-form-item style="display: block;">
            <el-input v-model="loginForm.name" class="el-input" placeholder="用户名"/>

          </el-form-item>
          <el-form-item style="display: block;">
            <el-input v-model="loginForm.password" type="password" class="el-input" placeholder="密码"/>

          </el-form-item>
          <div style="display: flex;flex-direction: row;justify-content: space-between;">
            <el-form-item  style="display: block;">
              <el-input v-model="loginForm.code" class="el-input" placeholder="验证码"></el-input>
            </el-form-item>
            <img v-if="loginForm.uuid!=='-1'" :src="$axios.defaults.baseURL+'user/getCode/'+loginForm.uuid"
                 @click="getCode"/>
          </div>
          <el-form-item   style="display: block;">
            <el-button :disabled="loading" v-loading="loading" @click="handleLogin" style="width: 100%;background: #ff7f5f;color: #FFFFFF">{{ isRegister ? "注册" : "登录" }}</el-button>

          </el-form-item>
        </div>
      </el-form>
      <div class="for_reg_btn">
<!--        <button class="button" @click="handleLogin">{{ isRegister ? "注册" : "登录" }}</button>-->
        <span>{{ isRegister ? "还没有账号？" : "已有账号？" }}</span>
        <a :href="`/login${isRegister?'':'?register=true'}`">{{ isRegister ? "马上登录" : "马上注册" }}</a>
      </div>
    </div>

    <div v-else  class="info">
      <h3>您的账号已经准备好了</h3>

      已向<b>{{ loginForm.email }}</b>
      发送了一封电子邮件，其中包含激活您帐户的说明。激活链接仅在 48 小时内有效。如果您没有收到说明，请尝试检查您的垃圾邮件或垃圾邮件过滤器。

    </div>
  </div>
</template>

<script>
import {validUsername} from '@/utils/validate'
import {setToken, getToken,setUser} from '@/utils/auth'

import Router from 'vue-router'
import Layout from '@/layout'
import componentMaps from '@/utils/components'


export default {
  name: 'Login',
  mounted() {
    // register
    this.getCode()
    this.isRegister = this.$route.query.register
    console.log(this.$axios.defaults.baseURL)
  },
  data() {
    const validateUsername = (rule, value, callback) => {
      if (!validUsername(value)) {
        callback(new Error('Please enter the correct user name'))
      } else {
        callback()
      }
    }
    const validatePassword = (rule, value, callback) => {
      if (value.length < 6) {
        callback(new Error('The password can not be less than 6 digits'))
      } else {
        callback()
      }
    }
    return {
      isActive:false,//是否已发送邮件
      isRegister: false, //是注册还是登录
      loginForm: {
        email:"",
        name: 'root',
        password: '123',
        uuid: "-1",
        code: ""
      },
      loginRules: {
        // username: [{ required: true, trigger: 'blur', validator: validateUsername }],
        // password: [{ required: true, trigger: 'blur', validator: validatePassword }]
      },
      loading: false,//
      passwordType: 'password',
      redirect: undefined
    }
  },
  watch: {
    $route: {
      handler: function (route) {
        this.redirect = route.query && route.query.redirect
      },
      immediate: true
    }
  },
  methods: {

    /**
     * 生成uuid
     * @returns {string}
     */
    guid() {
      return 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g, function (c) {
        var r = Math.random() * 16 | 0,
          v = c == 'x' ? r : (r & 0x3 | 0x8);
        return v.toString(16);
      });
    },
    getCode() {
      let uuid = this.guid()
      this.loginForm.uuid = uuid
    },
    showPwd() {
      if (this.passwordType === 'password') {
        this.passwordType = ''
      } else {
        this.passwordType = 'password'
      }
      this.$nextTick(() => {
        this.$refs.password.focus()
      })
    },
    handleLogin() {

      // this.$refs.loginForm.validate(valid => {
      //   if (valid) {
      //     this.loading = true
      //     this.$store.dispatch('user/login', this.loginForm).then(() => {
      //       this.$router.push({ path: this.redirect || '/' })
      //       this.loading = false
      //     }).catch(() => {
      //       this.loading = false
      //     })
      //   } else {
      //     console.log('error submit!!')
      //     return false
      //   }
      // })
      this.loading = true
      this.$axios.post(this.isRegister ? '/user/register' : '/login', this.$qs.stringify({

        ...this.loginForm
      })).then(res => {

        if(res.data.code!==0)
        {
          this.$message({
            type:"error",
            message:res.data.msg
          })
          this.loading=false
          return
        }
        if(this.isRegister){
          this.isActive=true
          this.loading = false

        }else {
          setToken(res.data.token)
            this.loading = false

            console.log(getToken())
            this.$router.push('/article/editor')
        }



      })
    },


  }
}
</script>

<style lang="less">

@import "index";
</style>
