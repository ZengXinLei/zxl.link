import Layout from '@/layout'

const asyncRouytes=[
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
    path: '/editor',
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
    path: '/tag',
    component: Layout,
    // redirect: '/tags',
    children: [{
      path: '/tag',
      name: '标签管理',
      component: () => import('@/views/articleTags/index'),
      meta: { title: '标签管理', icon: 'icon-biaoqian' }
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
        path: '/pageComponents',
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
  // { path: '*', redirect: '/404', hidden: true }
]
export  default asyncRouytes
