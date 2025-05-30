<template>
  <div class="login-wrapper">
    <div class="login-panel">
      <!-- 左侧：注册表单 -->
      <div class="login-left">
        <h2 class="login-title">注册</h2>
        <p class="login-desc">欢迎加入三体世界，请填写信息注册新账号！</p>
        <form @submit.prevent="handleRegister" class="login-form">
          <div class="input-row">
            <span class="icon user"></span>
            <input v-model="username" placeholder="用户名" autocomplete="username" />
          </div>
          <div class="input-row">
            <span class="icon lock"></span>
            <input v-model="password" type="password" placeholder="密码" autocomplete="new-password" />
          </div>
          <div class="input-row">
            <span class="icon lock"></span>
            <input v-model="confirmPassword" type="password" placeholder="确认密码" autocomplete="new-password" />
          </div>
          <button class="login-btn" :disabled="loading">{{ loading ? '注册中...' : '注册' }}</button>
          <div v-if="error" class="error-msg">{{ error }}</div>
          <div v-if="success" class="success-msg">{{ success }}</div>
        </form>
        <div class="other-login">
          <span>已有账号？</span>
          <button class="register-btn" @click="goLogin">去登录</button>
        </div>
      </div>
      <!-- 右侧：渐变+插画+遮罩 -->
      <div class="login-right">
        <div class="login-bg-mix"></div>
        <div class="login-img-bg"></div>
        <div class="login-gradient-mask"></div>
        <div class="welcome-block no-bg">
          <h3>欢迎注册三体世界账号！</h3>
          <p>注册后即可体验更多功能。</p>
        </div>
      </div>
    </div>
  </div>
</template>
<script setup lang="ts">
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { registerApi } from '@/api/auth'
const username = ref('')
const password = ref('')
const confirmPassword = ref('')
const loading = ref(false)
const error = ref('')
const success = ref('')
const router = useRouter()
function goLogin() {
  router.push('/login')
}
async function handleRegister() {
  error.value = ''
  success.value = ''
  if (!username.value || !password.value || !confirmPassword.value) {
    error.value = '请填写所有字段'
    return
  }
  if (password.value !== confirmPassword.value) {
    error.value = '两次输入的密码不一致'
    return
  }
  loading.value = true
  try {
    const res = await registerApi(username.value, password.value)
    if (res.data && res.data.code === 0) {
      success.value = '注册成功，请前往登录！'
      setTimeout(() => {
        router.push('/login')
      }, 1200)
    } else {
      error.value = res.data.message || '注册失败'
    }
  } catch (e: any) {
    error.value = e?.response?.data?.message || '注册失败'
  } finally {
    loading.value = false
  }
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
.success-msg {
  color: #1fdc6b;
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