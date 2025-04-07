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
          <h1>用户登录</h1>
          <div class="form-group">
              <label>手机号：</label>
              <input type="text" id="username" required>
          </div>
          <div class="form-group">
              <label>验证码：</label>
              <input type="password" id="password" required>
          </div>
          <div class="login-btn">
            <button type="submit">登录</button>
            <button type="button" @click="showRegister()">切换到注册</button>
          </div>
      </form>

      <form class="auth-form" id="registerForm" style="display: none;">
          <h1>用户注册</h1>
          <div class="form-group">
              <label>手机号：</label>
              <input type="text" id="phone" required pattern="\d{11}">
          </div>

          <div class="login-btn">
            <button type="submit">注册</button>
            <button type="button" @click="showLogin()">返回登录</button>
          </div>
      </form>
  </div>
</template>

<style scoped>
    #login {
        border: 1px solid black;
        line-height: 70px;
        height: 100%;
        width: 100%;
        position: absolute;
        left: 0;
        top: 72px;
        background-image: url(../assets/背景1.jpg);
        background-size: cover;
    }

    .auth-form {
        position: absolute;
        top: 200px;
        left: 0;
        right: 0;
        padding: 25px;
        margin: auto;
        width: 26%;
        min-width: 460px;
        height: 30%;
        min-height: 300px;
        font-size: 22px;
        text-align: center;
        margin-bottom: 22px;
        background: rgb(232, 229, 229);
        border-radius: 5%;
        box-shadow: 2px 2px 10px #ddd;
    }
</style>