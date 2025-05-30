<template>
  <div class="card dashboard-card">
    <h1>Dashboard</h1>
    <p style="color: #bbb; font-size: 1.2rem;">欢迎进入后台管理系统</p>
  </div>
</template>

<script setup>
// 空白卡片
</script>

<style scoped>
.card.dashboard-card {
  background: #fff;
  border-radius: 18px;
  box-shadow: 0 4px 24px rgba(0,0,0,0.07);
  max-width: 900px;
  margin: 0 auto;
  padding: 48px 56px;
  min-height: 400px;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: flex-start;
}
.card.dashboard-card h1 {
  color: #222;
  font-size: 2.2rem;
  font-weight: 600;
  margin-bottom: 18px;
}
</style> 