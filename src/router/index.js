import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router)

/* Layout */
import Layout from '@/layout'
import request from '@/utils/request'

/**
 * Note: sub-menu only appear when route children.length >= 1
 * Detail see: https://panjiachen.github.io/vue-element-admin-site/guide/essentials/router-and-nav.html
 *
 * hidden: true                   if set true, item will not show in the sidebar(default is false)
 * alwaysShow: true               if set true, will always show the root menu
 *                                if not set alwaysShow, when item has more than one children route,
 *                                it will becomes nested mode, otherwise not show the root menu
 * redirect: noRedirect           if set noRedirect will no redirect in the breadcrumb
 * name:'router-name'             the name is used by <keep-alive> (must set!!!)
 * meta : {
    roles: ['admin','editor']    control the page roles (you can set multiple roles)
    title: 'title'               the name show in sidebar and breadcrumb (recommend set)
    icon: 'svg-name'/'el-icon-x' the icon show in the sidebar
    breadcrumb: false            if set false, the item will hidden in breadcrumb(default is true)
    activeMenu: '/example/list'  if set path, the sidebar will highlight the path you set
  }
 */

/**
 * constantRoutes
 * a base page that does not have permission requirements
 * all roles can be accessed
 */
export const constantRoutes = [
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
    component: Layout,
    redirect: '/dashboard',
    children: [{
      path: 'dashboard',
      name: 'Dashboard',
      component: () => import('@/views/dashboard/index'),
      meta: { title: 'Dashboard', icon: 'dashboard' }
    }]
  },
  {
    path: '/article',
    component: Layout,
    redirect: '/editor',
    children: [{
      path: '/editor',
      name: '发布文章',
      component: () => import('@/views/Editor/index'),
      meta: { title: '发布文章', icon: 'icon-map' }
    }]
  },
  {
    path: '/tags',
    component: Layout,
    // redirect: '/tags',
    children: [{
      path: 'tags',
      name: '标签管理',
      component: () => import('@/views/articleTags/index'),
      meta: { title: '标签管理', icon: 'dashboard' }
    }]
  },

  {
    path: '/settings',
    component: Layout,
    redirect: '/',
    name: '系统设置',
    meta: { title: '系统设置', icon: 'el-icon-s-help' },
    children: [
      {
        path: '/permission',
        name: '权限管理',
        component: () => import('@/views/settings/permission/index'),
        meta: { title: '权限管理', icon: 'table' }
      },
      {
        path: '/pageComponent',
        name: '页面组件管理',
        component: () => import('@/views/settings/pageComponents/index'),
        meta: { title: '页面组件管理', icon: 'tree' }
      },
      {
        path: '/roles',
        name: '角色管理',
        component: () => import('@/views/settings/role/index'),
        meta: { title: '角色管理', icon: 'tree' }
      },
    ]
  },


  // 404 page must be placed at the end !!!
  { path: '*', redirect: '/404', hidden: true }
]

const createRouter = () => {
  return new Router({
    mode: 'history', // require service support
    scrollBehavior: () => ({ y: 0 }),
    routes: constantRoutes
  })
}

const router = createRouter()

// Detail see: https://github.com/vuejs/vue-router/issues/1234#issuecomment-357941465
export function resetRouter() {
  const newRouter = createRouter()
  router.matcher = newRouter.matcher // reset router
}

export default router
