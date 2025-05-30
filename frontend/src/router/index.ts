import { createRouter, createWebHistory } from 'vue-router'
import Login from '@/views/auth_web/Login.vue'
import Register from '@/views/auth_web/Register.vue'
import AdminLayout from '@/views/admin_web/AdminLayout.vue'
import Dashboard from '@/views/admin_web/Dashboard.vue'
import WebPasswd from '@/views/admin_web/WebPasswd.vue'
import SysUser from '@/views/admin_web/SysUser.vue'
import SysRole from '@/views/admin_web/SysRole.vue'
import SysSetting from '@/views/admin_web/SysSetting.vue'

const routes = [
  { path: '/login', component: Login },
  { path: '/register', component: Register },
  {
    path: '/admin',
    component: AdminLayout,
    children: [
      { path: '', component: Dashboard },
      { path: 'web_passwd', component: WebPasswd },
      { path: 'sys/user', component: SysUser },
      { path: 'sys/role', component: SysRole },
      { path: 'sys/setting', component: SysSetting }
    ]
  },
  { path: '/', redirect: '/login' }
]

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes
})

// 路由守卫：未登录跳转到登录页
router.beforeEach((to, from, next) => {
  const isAuthenticated = !!localStorage.getItem('token')
  if (to.path.startsWith('/admin') && !isAuthenticated) {
    next('/login')
  } else if ((to.path === '/login' || to.path === '/register') && isAuthenticated) {
    next('/admin')
  } else {
    next()
  }
})

export default router
