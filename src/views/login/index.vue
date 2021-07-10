<template>
  <div class="login-container">
    <el-form ref="loginForm" :model="loginForm" :rules="loginRules" class="login-form" auto-complete="on"
             label-position="left"
    >

      <div class="title-container">
        <h3 class="title">Login Form</h3>
      </div>

      <el-form-item prop="username">
        <span class="svg-container">
          <svg-icon icon-class="user"/>
        </span>
        <el-input
          ref="username"
          v-model="loginForm.name"
          placeholder="Username"
          name="username"
          type="text"
          tabindex="1"
          auto-complete="on"
        />
      </el-form-item>

      <el-form-item prop="password">
        <span class="svg-container">
          <svg-icon icon-class="password"/>
        </span>
        <el-input
          :key="passwordType"
          ref="password"
          v-model="loginForm.password"
          :type="passwordType"
          placeholder="Password"
          name="password"
          tabindex="2"
          auto-complete="on"
          @keyup.enter.native="handleLogin"
        />
        <span class="show-pwd" @click="showPwd">
          <svg-icon :icon-class="passwordType === 'password' ? 'eye' : 'eye-open'"/>
        </span>
      </el-form-item>

      <el-button :loading="loading" type="primary" style="width:100%;margin-bottom:30px;"
                 @click.native.prevent="handleLogin"
      >Login
      </el-button>

      <div class="tips">
        <span style="margin-right:20px;">username: admin</span>
        <span> password: any</span>
      </div>

    </el-form>
  </div>
</template>

<script>
import { validUsername } from '@/utils/validate'
import { setToken, setUser } from '@/utils/auth'
import Router from 'vue-router'
import Layout from '@/layout'

export default {
  name: 'Login',
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
      loginForm: {
        name: 'root',
        password: '123'
      },
      loginRules: {
        // username: [{ required: true, trigger: 'blur', validator: validateUsername }],
        // password: [{ required: true, trigger: 'blur', validator: validatePassword }]
      },
      loading: false,
      passwordType: 'password',
      redirect: undefined
    }
  },
  watch: {
    $route: {
      handler: function(route) {
        this.redirect = route.query && route.query.redirect
      },
      immediate: true
    }
  },
  methods: {
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
      this.$axios.post('/login', this.$qs.stringify({

        ...this.loginForm
      })).then(res => {

        setToken(res.data.token)
        this.setRoutes()

      })
    },

    dfs2(data, hasPs) {

      // if(!data)
      //   return true
      for (let i = 0; i < data.children.length; i++) {
        if (!this.dfs2(data.children[i], hasPs)) {
          data.children[i] = null
        }

        if (data && data.children[i] && data.children[i].type === 1) {
          data.children[i] = null
        }

      }
      data.meta = {
        title: data.label,
        icon: data.icon
      }
      data.name = data.label
      data.path=data.value
      if (data.pagecomponent) {
        data.component = () => import(data.pagecomponent.value)
      }
      data.children = data.children.filter(e => e)
      return !((!data.children || data.children.length === 0) && hasPs.filter(e => e === data.id).length === 0)

    },
    dfs3(children,routes){

      for (let i = 0; i < children.length; i++) {
        let child=children[i]
        let route={
          component:()=>import(child.pagecomponent.value),
          path:child.path,

        }
      }
    },
    setRoutes() {
      let hasPs = []
      let data = []
      this.$axios.post('/permission/permissionIdsByRole').then(res => {

        hasPs = res.data.data
        return this.$axios.post('/permission/list')
      }).then(res => {
        data = res.data.list

        // data.forEach(e=>this.dfs1(e))
        for (let i = 0; i < data.length; i++) {
          this.dfs2(data[i], hasPs)
          if (data[i].type === 1) {
            data[i] = null
          }

        }
        data = data.filter(e => e)

        data.forEach(e => {
          e.meta = {
            title: e.label,
            icon: e.icon
          }
          if (e.pagecomponent) {
            e.component = {
              ...() => import('@/layout')
            }
          }
          e.path=e.value
        })

        let routes = [
          {
            path: '/login',
            component: () => import('@/views/login/index'),
            hidden: true
          },

          {
            path: '/404',
            component: () => import('@/views/404'),
            hidden: true
          },
          {
            path: '/',
            component: ()=>import('@/layout'),
            redirect: '/dashboard',
            children: [{
              path: 'dashboard',
              name: 'Dashboard',
              component: () => import('@/views/dashboard/index'),
              meta: { title: 'Dashboard', icon: 'dashboard' }
            }]
          },
        ]

        data.forEach(e => routes.push(e))
        routes.push({ path: '*', redirect: '/404', hidden: true })


        let vueRouter = new Router(this.$router.options)
        // this.$router=vueRouter
        this.$router.options.routes=routes
        this.$router.matcher = vueRouter.matcher
        this.$router.addRoutes(routes)
        console.log(this.$router.options)

        this.loading = false

        this.$router.push('/editor')
      })
    }
  }
}
</script>

<style lang="scss">
/* 修复input 背景不协调 和光标变色 */
/* Detail see https://github.com/PanJiaChen/vue-element-admin/pull/927 */

$bg: #283443;
$light_gray: #fff;
$cursor: #fff;

@supports (-webkit-mask: none) and (not (cater-color: $cursor)) {
  .login-container .el-input input {
    color: $cursor;
  }
}

/* reset element-ui css */
.login-container {
  .el-input {
    display: inline-block;
    height: 47px;
    width: 85%;

    input {
      background: transparent;
      border: 0px;
      -webkit-appearance: none;
      border-radius: 0px;
      padding: 12px 5px 12px 15px;
      color: $light_gray;
      height: 47px;
      caret-color: $cursor;

      &:-webkit-autofill {
        box-shadow: 0 0 0px 1000px $bg inset !important;
        -webkit-text-fill-color: $cursor !important;
      }
    }
  }

  .el-form-item {
    border: 1px solid rgba(255, 255, 255, 0.1);
    background: rgba(0, 0, 0, 0.1);
    border-radius: 5px;
    color: #454545;
  }
}
</style>

<style lang="scss" scoped>
$bg: #2d3a4b;
$dark_gray: #889aa4;
$light_gray: #eee;

.login-container {
  min-height: 100%;
  width: 100%;
  background-color: $bg;
  overflow: hidden;

  .login-form {
    position: relative;
    width: 520px;
    max-width: 100%;
    padding: 160px 35px 0;
    margin: 0 auto;
    overflow: hidden;
  }

  .tips {
    font-size: 14px;
    color: #fff;
    margin-bottom: 10px;

    span {
      &:first-of-type {
        margin-right: 16px;
      }
    }
  }

  .svg-container {
    padding: 6px 5px 6px 15px;
    color: $dark_gray;
    vertical-align: middle;
    width: 30px;
    display: inline-block;
  }

  .title-container {
    position: relative;

    .title {
      font-size: 26px;
      color: $light_gray;
      margin: 0px auto 40px auto;
      text-align: center;
      font-weight: bold;
    }
  }

  .show-pwd {
    position: absolute;
    right: 10px;
    top: 7px;
    font-size: 16px;
    color: $dark_gray;
    cursor: pointer;
    user-select: none;
  }
}
</style>
