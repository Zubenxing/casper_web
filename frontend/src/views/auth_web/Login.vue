<template>
  <div class="login-wrapper">
    <div class="login-panel">
      <!-- 左侧：登录表单 -->
      <div class="login-left">
        <h2 class="login-title">登陆</h2>
        <p class="login-desc">欢迎来到三体世界，请输入账号密码！</p>
        <form @submit.prevent="handleLogin" class="login-form">
          <div class="input-row">
            <span class="icon user"></span>
            <input v-model="username" placeholder="用户名" autocomplete="username" />
          </div>
          <div class="input-row">
            <span class="icon lock"></span>
            <input v-model="password" type="password" placeholder="密码" autocomplete="current-password" />
          </div>
          <div class="form-row">
            <label class="remember-me">
              <input type="checkbox" v-model="rememberMe" /> 记住我
            </label>
            <a href="#" class="forgot">忘记密码，点我重置！</a>
          </div>
          <button class="login-btn" :disabled="loading">{{ loading ? '登陆中...' : '登陆' }}</button>
          <div v-if="error" class="error-msg">{{ error }}</div>
        </form>
        <div class="other-login">
          <span>第三方账号登陆</span>
          <div class="login-icons">
            <span class="icon third green"></span>
            <span class="icon third blue"></span>
            <span class="icon third black"></span>
          </div>
        </div>
      </div>
      <!-- 右侧：渐变+插画+遮罩 -->
      <div class="login-right">
        <div class="login-bg-mix"></div>
        <div class="login-img-bg"></div>
        <div class="login-gradient-mask"></div>
        <div class="welcome-block no-bg">
          <h3>自然选择号欢迎您登陆！</h3>
          <p>如果你没有账号，你想要现在注册一个吗？</p>
          <button class="register-btn" @click="goRegister">注册</button>
        </div>
      </div>
    </div>
  </div>
</template>
<script setup lang="ts">
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { loginApi } from '@/api/auth'
const username = ref('')
const password = ref('')
const rememberMe = ref(false)
const loading = ref(false)
const error = ref('')
const router = useRouter()
async function handleLogin() {
  error.value = ''
  if (!username.value || !password.value) {
    error.value = '请输入账号和密码'
    return
  }
  loading.value = true
  try {
    const res = await loginApi(username.value, password.value)
    if (res.data && res.data.code === 0 && res.data.data?.token) {
      localStorage.setItem('token', res.data.data.token)
      router.push('/admin')
    } else {
      error.value = res.data.message || '登录失败'
    }
  } catch (e: any) {
    error.value = e?.response?.data?.message || '登录失败'
  } finally {
    loading.value = false
  }
}
function goRegister() {
  router.push('/register')
}
</script>
<style scoped>
.login-wrapper {
  min-height: 100vh;
  min-width: 100vw;
  height: 100vh;
  width: 100vw;
  background: #000;
  display: flex;
  align-items: center;
  justify-content: center;
  box-sizing: border-box;
}
.login-panel {
  display: flex;
  width: 950px;
  min-height: 480px;
  background: #fff;
  border-radius: 28px;
  box-shadow: 0 8px 32px rgba(0,0,0,0.12);
  overflow: hidden;
  max-width: 98vw;
  max-height: 98vh;
}
.login-left {
  flex: 1.1;
  padding: 48px 40px 40px 48px;
  display: flex;
  flex-direction: column;
  justify-content: center;
}
.login-title {
  font-size: 2rem;
  font-weight: bold;
  margin-bottom: 12px;
}
.login-desc {
  color: #444;
  margin-bottom: 28px;
}
.login-form {
  display: flex;
  flex-direction: column;
}
.input-row {
  display: flex;
  align-items: center;
  background: #fff;
  border: 2px solid #000;
  border-radius: 32px;
  margin-bottom: 18px;
  padding: 0 18px;
  height: 48px;
}
.input-row input {
  border: none;
  background: transparent;
  outline: none;
  padding: 0 10px;
  flex: 1;
  font-size: 1rem;
  height: 40px;
}
.icon.user::before {
  content: '\1F464';
  font-size: 1.3rem;
  margin-right: 8px;
}
.icon.lock::before {
  content: '\1F512';
  font-size: 1.3rem;
  margin-right: 8px;
}
.form-row {
  display: flex;
  align-items: center;
  justify-content: space-between;
  margin-bottom: 18px;
  font-size: 0.98rem;
}
.remember-me {
  display: flex;
  align-items: center;
  gap: 4px;
}
.forgot {
  color: #000;
  text-decoration: underline;
  font-size: 0.97rem;
}
.login-btn {
  width: 100%;
  padding: 12px;
  background: #000;
  color: #fff;
  border: none;
  border-radius: 24px;
  font-size: 1.1rem;
  font-weight: bold;
  cursor: pointer;
  margin-top: 8px;
  margin-bottom: 8px;
  transition: background 0.2s;
}
.login-btn:disabled {
  opacity: 0.7;
  cursor: not-allowed;
}
.error-msg {
  color: #e74c3c;
  margin-top: 8px;
  text-align: center;
  font-size: 0.95rem;
}
.other-login {
  margin-top: 32px;
  font-size: 0.98rem;
}
.login-icons {
  display: flex;
  gap: 16px;
  margin-top: 10px;
}
.icon.third {
  display: inline-block;
  width: 32px;
  height: 32px;
  border-radius: 50%;
  background: #eee;
  cursor: pointer;
}
.icon.third.green { background: #1fdc6b; }
.icon.third.blue { background: #5bbcff; }
.icon.third.black { background: #222; }
.login-right {
  flex: 1;
  position: relative;
  display: flex;
  align-items: center;
  justify-content: center;
  overflow: hidden;
  background: none;
  padding: 0 32px;
}
.login-bg-mix {
  position: absolute;
  inset: 0;
  background:
    radial-gradient(circle at 30% 50%, #e0e7ff 0%, #f7f7f7 60%, #fff 100%);
  z-index: 1;
}
.login-img-bg {
  position: absolute;
  inset: 0;
  background: url('/login-illu.png') center/cover no-repeat;
  opacity: 0.8;
  filter: blur(0.5px) brightness(1.08);
  z-index: 2;
  transition: opacity 0.3s;
}
.login-gradient-mask {
  position: absolute;
  inset: 0;
  background: rgba(255,255,255,0.15);
  z-index: 3;
  box-shadow: inset 0 0 80px 0 rgba(0,0,0,0.07);
}
.welcome-block {
  position: relative;
  z-index: 4;
  background: none;
  border-radius: 0;
  padding: 0;
  text-align: center;
  box-shadow: none;
}
.welcome-block h3 {
  font-size: 1.3rem;
  font-weight: bold;
  margin-bottom: 12px;
  color: #222;
  text-shadow: 0 2px 8px rgba(255,255,255,0.7);
}
.welcome-block p {
  color: #222;
  margin-bottom: 18px;
  text-shadow: 0 2px 8px rgba(255,255,255,0.7);
}
.register-btn {
  padding: 8px 32px;
  border: 2px solid rgba(0,0,0,0.3);
  border-radius: 24px;
  background: rgba(255,255,255,0.4);
  color: #000;
  font-size: 1rem;
  font-weight: bold;
  cursor: pointer;
  transition: background 0.2s, color 0.2s, border 0.2s;
  box-shadow: 0 2px 8px 0 rgba(255,255,255,0.3);
}
.register-btn:hover {
  background: rgba(0,0,0,0.12);
  color: #000;
  border: 2px solid rgba(0,0,0,0.5);
}
@media (max-width: 900px) {
  .login-panel {
    flex-direction: column;
    width: 98vw;
    min-width: 320px;
    min-height: unset;
    max-width: 100vw;
    max-height: 100vh;
  }
  .login-left, .login-right {
    padding: 32px 16px;
  }
}
</style> 