<script setup>
function showRegister() {
    document.getElementById('loginForm').style.display = 'none';
    document.getElementById('registerForm').style.display = 'block';
}

function showLogin() {
    document.getElementById('registerForm').style.display = 'none';
    document.getElementById('loginForm').style.display = 'block';
}
</script>

<template>
  <div id="login" class="page" style="display: block;">
      <form class="auth-form" id="loginForm">
          <h2>用户登录</h2>
          <div class="form-group">
              <label>用户名：</label>
              <input type="text" id="username" required>
          </div>
          <div class="form-group">
              <label>密码：</label>
              <input type="password" id="password" required>
          </div>
          <button type="submit">登录</button>
          <button type="button" @click="showRegister()">切换到注册</button>
      </form>

      <form class="auth-form" id="registerForm" style="display: none;">
          <h2>用户注册</h2>
          <div class="form-group">
              <label>手机号：</label>
              <input type="text" id="phone" required pattern="\d{11}">
          </div>
          <!-- 更多字段... -->
          <button type="submit">注册</button>
          <button type="button" @click="showLogin()">返回登录</button>
      </form>
  </div>
</template>

<style>
</style>