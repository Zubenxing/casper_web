<template>
  <div class="admin-layout">
    <header class="admin-header">
      <div class="header-left">casper-web 后台管理</div>
      <div class="header-right">
        <span class="user-info">{{ username }}</span>
        <img class="avatar" src="https://api.dicebear.com/7.x/miniavs/svg?seed=1" alt="avatar" />
        <button class="logout-btn" @click="logout">退出登录</button>
      </div>
    </header>
    <div class="admin-main">
      <aside class="admin-sidebar">
        <ul class="menu-list">
          <li :class="{active: $route.path === '/admin'}" @click="$router.push('/admin')">
            <span>Dashboard</span>
          </li>
          <li :class="{active: $route.path.startsWith('/admin/web_passwd') }" @click="$router.push('/admin/web_passwd')">
            <span>密码管理</span>
          </li>
          <li>
            <div class="menu-parent" @click="sysOpen = !sysOpen">
              <span :class="{active: $route.path.startsWith('/admin/sys')}">系统管理</span>
              <span class="arrow" :class="{open: sysOpen}">▼</span>
            </div>
            <ul v-show="sysOpen" class="submenu-list">
              <li :class="{active: $route.path === '/admin/sys/user'}" @click="$router.push('/admin/sys/user')">用户管理</li>
              <li :class="{active: $route.path === '/admin/sys/role'}" @click="$router.push('/admin/sys/role')">权限管理</li>
              <li :class="{active: $route.path === '/admin/sys/setting'}" @click="$router.push('/admin/sys/setting')">系统设置</li>
            </ul>
          </li>
        </ul>
      </aside>
      <main class="admin-content">
        <div class="content-wrapper">
          <router-view />
        </div>
      </main>
    </div>
    <footer class="admin-footer">
      casper-web v1.0 © 2024
    </footer>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'
const router = useRouter()
const username = localStorage.getItem('username') || '用户'
const sysOpen = ref(false)
const logout = () => {
  localStorage.removeItem('token')
  localStorage.removeItem('username')
  router.push('/login')
}
</script>

<style scoped>
.admin-layout { min-height: 100vh; display: flex; flex-direction: column; background: #f7f7f7; }
.admin-header {
  height: 56px;
  background: #ffffff;
  box-shadow: 0 2px 8px rgba(0,0,0,0.03);
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 0 32px;
  position: sticky;
  top: 0;
  z-index: 10;
  border-radius: 0 0 18px 18px;
}
.header-left { font-weight: bold; font-size: 1.1rem; color: #222; }
.header-right { display: flex; align-items: center; gap: 18px; }
.user-info { color: #444; font-size: 1rem; }
.avatar { width: 32px; height: 32px; border-radius: 50%; background: #eee; }
.logout-btn {
  background: #222;
  color: #fff;
  border: none;
  border-radius: 18px;
  padding: 8px 20px;
  cursor: pointer;
  font-size: 1rem;
  transition: background 0.2s;
}
.logout-btn:hover { background: #444; }
.admin-main {
  flex: 1;
  display: flex;
  width: 100vw;
}
.admin-sidebar {
  width: 200px;
  background: #18191a;
  color: #fff;
  display: flex;
  flex-direction: column;
  align-items: stretch;
  border-radius: 0 18px 18px 0;
  box-shadow: 2px 0 8px rgba(0,0,0,0.04);
  min-height: calc(100vh - 56px - 36px);
}
.menu-list {
  list-style: none;
  padding: 0;
  margin: 0;
}
.menu-list li {
  padding: 16px 28px;
  cursor: pointer;
  font-size: 1.08rem;
  color: #eee;
  border-radius: 24px 0 0 24px;
  margin: 8px 0 8px 8px;
  border-left: 0;
  transition: background 0.2s, color 0.2s;
  position: relative;
}
.menu-list li.active, .menu-list li:hover {
  background: #fff;
  color: #222;
  font-weight: bold;
}
.menu-parent {
  display: flex;
  align-items: center;
  justify-content: space-between;
  cursor: pointer;
  font-size: 1.08rem;
  color: #eee;
  border-radius: 24px 0 0 24px;
  padding: 16px 28px;
  margin: 8px 0 8px 8px;
  user-select: none;
  font-weight: 500;
}
.menu-parent .arrow {
  font-size: 0.9em;
  margin-left: 8px;
  transition: transform 0.2s;
  color: #bbb;
}
.menu-parent .arrow.open {
  transform: rotate(180deg);
  color: #222;
}
.submenu-list {
  list-style: none;
  padding: 0 0 0 24px;
  margin: 0;
}
.submenu-list li {
  padding: 12px 24px;
  font-size: 1rem;
  color: #bbb;
  border-radius: 18px 0 0 18px;
  margin: 4px 0 4px 8px;
  cursor: pointer;
  transition: background 0.2s, color 0.2s, font-weight 0.2s;
  font-weight: 400;
  background: transparent;
  white-space: nowrap;
  min-width: 110px;
  max-width: 180px;
  overflow: hidden;
  text-overflow: ellipsis;
}
.submenu-list li.active, .submenu-list li:hover {
  background: #f5f5f5;
  color: #222;
  font-weight: 600;
}
.admin-content {
  flex: 1;
  width: 100%;
  padding: 0;
  background: #f7f7f7;
  min-height: calc(100vh - 56px - 36px);
}
.admin-footer {
  height: 36px;
  background: #fafafa;
  color: #888;
  font-size: 0.95rem;
  display: flex;
  align-items: center;
  justify-content: center;
  border-top: 1px solid #eee;
}
.content-wrapper {
  width: 100%;
  margin: 0;
  padding: 32px 40px;
  box-sizing: border-box;
  min-height: 100%;
}
</style> 