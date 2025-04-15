<script setup>
    import { ref } from 'vue';
    import { useRouter } from 'vue-router';
    import { useAuthStore } from '../stores/auth';
    import axios from 'axios';
    let user = {
        username: '',
        password: '',
        confirm_password: '',
        phone: '',
        code: '',
    }
    const router = useRouter()
    const authStore = useAuthStore()

    function showRegister() {
        document.getElementById('loginForm').style.display = 'none';
        document.getElementById('registerForm').style.display = 'block';
        document.getElementById('loginbyphoneForm').style.display = 'none';
    }

    function showLogin() {
        document.getElementById('registerForm').style.display = 'none';
        document.getElementById('loginForm').style.display = 'block';
        document.getElementById('loginbyphoneForm').style.display = 'none';
    }

    function showLoginByPhone() {
        document.getElementById('registerForm').style.display = 'none';
        document.getElementById('loginForm').style.display = 'none';
        document.getElementById('loginbyphoneForm').style.display = 'block';
    }

    function getCode() {
        const url = "http://localhost:8080/user/code"
        axios.post(url, {}, {params: {phone: user.phone}})
        .then(function (response) {
            console.log(response)
        }).catch(function (error) {
            console.log(error)
        })
    }

    function LoginSubmit() {
        const url = "http://localhost:8080/user/login"
        axios.post(url,{
            username: user.username,
            password: user.password,
        })
        .then(function (response) {
            // console.log(response)
            if(response.data.success){
                alert("登录成功")
                authStore.login(response.data.data)
                router.push('/profile')
            }
            else{
                alert(response.data.errormessage)
                return
            }
        })
        .catch(function (error) {
            console.log(error);
        })       
    }

    function LoginByPhoneSubmit() {
        const url = "http://localhost:8080/user/loginByPhone"
        axios.post(url,{
            username: user.username,
            password: user.password,
            phone: user.phone,
            code: user.code,
        })
        .then(function (response) {
            console.log(response)
            if(response.data.success){
                authStore.login(response.data.data)
                router.push('/profile')
            }
            else{
                alert(response.data.errormessage)
                return
            }
        })
        .catch(function (error) {
            console.log(error);
        })       
    }

    function RegisterSubmit() {
        // 写提交的代码
        if(user.confirm_password != user.password){
            alert("两次输入的密码不同！")
            return
        }
        const url = "http://localhost:8080/user/register"
        axios.post(url,{
            username: user.username,
            password: user.password,
        })
        .then(function (response) {
            console.log(response)
            if(response.data.success){
                authStore.login(response.data.data)
                router.push('/profile')
            }
            else{
                alert(response.data.errormessage)
                return
            }
        })
        .catch(function (error) {
            console.log(error);
        })
    }
</script>

<template>
  <div id="login" class="page" style="display: block;">
      <form class="auth-form" id="loginForm">
          <h1>用户登录</h1>
          <div class="form-box">
            <div class="form-group">
                <label>账号：</label>
                <input type="text" id="login-username" placeholder="请输入账号" required v-model="user.username">
            </div>
            <div class="form-group">
                <label>密码：</label>
                <input type="password" id="login-password" placeholder="请输入密码" required v-model="user.password">
            </div>
            <div class="login-btn">
                <button type="submit" @click="LoginSubmit">登录</button>
                <button type="button" @click="showRegister">切换到注册</button>
                <button type="button" @click="showLoginByPhone">手机号登录</button>
            </div>
          </div>
      </form>

      <form class="auth-form" id="loginbyphoneForm" style="display: none;">
          <h1>手机号登录</h1>
          <div class="form-box">
            <div class="form-group">
                <label>手机号：</label>
                <input id="loginbyphone-phone" type="text" placeholder="请输入手机号" required pattern="\d{11}" v-model="user.phone">
            </div>
            <div class="form-group">
                <label>验证码：</label>
                <input id="loginbyphone-code" type="text" placeholder="请输入验证码" required v-model="user.code">
            </div>
          </div>
          <div class="loginbyphone-btn">
            <button type="submit" @click="LoginByPhoneSubmit">登录</button>
            <button type="button" @click="showLogin">返回登录</button>
            <button type="button" @click="showRegister">切换到注册</button>
            <button type="button" @click="getCode">获取验证码</button>
          </div>
      </form>

      <form class="auth-form" id="registerForm" style="display: none;">
          <h1>用户注册</h1>
          <div class="form-box">
            <div class="form-group">
                <label>用户名：</label>
                <input id="register-username" type="text" placeholder="请输入用户名" required v-model="user.username">
            </div>
            <div class="form-group">
                <label>密码：</label>
                <input id="register-password" type="password" placeholder="请输入密码" required v-model="user.password">
            </div>
            <div class="form-group">
                <label>确认密码：</label>
                <input id="register-confirm-password" type="password" placeholder="请确认密码" required v-model="user.confirm_password">
            </div>
          </div>
          <div class="register-btn">
            <button type="submit" @click="RegisterSubmit">注册</button>
            <button type="button" @click="showLogin">返回登录</button>
            <button type="button" @click="showLoginByPhone">返回手机号登录</button>
          </div>
      </form>
  </div>
</template>

<style scoped>
    label {
        width: 100px;
        display: inline-block;
        text-align: right;
    }

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
        background-repeat: no-repeat;
        background-size: 100% 100%;
    }

    .auth-form {
        position: absolute;
        top: 200px;
        left: 0;
        right: 0;
        padding: 30px;
        margin: auto;
        background: rgb(232, 229, 229);
        border-radius: 5%;
        box-shadow: 2px 2px 10px #ddd;
        opacity:0.7;
        text-align: center;
        width: 26%;
        min-width: 460px;
        font-size: 22px;
    }

    .form-box {
        width: 400px;
        font-weight: 400;
        font-size: 20px;
        line-height: 20px;
        color: #212121;
        margin-top: 10px;
        text-align: right;
    }

    .form-group {
        display: flex;
        position: relative;
        justify-content: flex-start;
        align-items: center;
        box-sizing: border-box;
        width: 100%;
        height: 44px;
        padding: 12px 20px;
    }

    .login-btn {
        display: flex;
        justify-content: space-around;
        margin-top: 20px;
        width: 400px;
        font-style: normal;
        font-weight: 400;
        font-size: 14px;
        line-height: 40px;
        text-align: center;
    }

    .register-btn {
        display: flex;
        justify-content: space-around;
        margin-top: 20px;
        width: 400px;
        font-style: normal;
        font-weight: 400;
        font-size: 14px;
        line-height: 40px;
        text-align: center;
    }

    input {
        margin-left: 20px;
        flex: 1;
        min-width: 0;
        min-height: 30px;
        font-size: 20px;
        padding-left: 10px;
        border: none;
    }

    button {
        box-sizing: border-box;
        width: 100px;
        height: 40px;
        cursor: pointer;
        color: #18191c;
        background: skyblue;
        border: 1px solid #e3e5e7;
        border-radius: 8px;
        font-size: 16px;
    }
</style>