<script setup>
import { RouterView,RouterLink } from 'vue-router';
</script>

<template>
  <div class="app-container">
    <h1 class="title">线上交易系统</h1>
    <nav class="navigater">
      <router-link to="/home" active-class="active">首页</router-link>
      <router-link to="/login" active-class="active">登录</router-link>
      <router-link to="/profile" active-class="active">用户中心</router-link>
    </nav>
    
    <main class="main-content">
      <router-view></router-view>
    </main>
  </div>  
</template>

<style scoped>
  .title {
    text-align: center;
    word-spacing: 5px;
    margin: 30px 0;
    height: 70px;
    line-height: 70px;
    border-radius: 10px;
    box-shadow: 0 0 2px;
    font-size: 30px;
  }

  .navigater {
    display: flex;
    justify-content: space-around;
    margin: 0 100px;
  }

  .navigater a {
    display: block;
    text-align: center;
    width: 100px;
    height: 40px;
    line-height: 40px;
    border-radius: 10px;
    background-color: gray;
    text-decoration: none;
    color: white;
    font-size: 18px;
    letter-spacing: 5px;
  }

  .navigater a.active {
    background-color: #64967E;
    color: #ffc268;
    font-weight: 900;
    text-shadow: 0 0 1px black;
    font-family: 微软雅黑;
  }

  .main-content {
    margin: 0 auto;
    margin-top: 30px;
    border-radius: 10px;
    width: 90%;
    height: 400px;
    border: 1px solid;
  }
</style>

<!-- <script setup>
import HelloWorld from './components/HelloWorld.vue'
</script>

<template>
  <div>
    <a href="https://vite.dev" target="_blank">
      <img src="/vite.svg" class="logo" alt="Vite logo" />
    </a>
    <a href="https://vuejs.org/" target="_blank">
      <img src="./assets/vue.svg" class="logo vue" alt="Vue logo" />
    </a>
  </div>
  <HelloWorld msg="Hello World" />
</template>

<style scoped>
.logo {
  height: 6em;
  padding: 1.5em;
  will-change: filter;
  transition: filter 300ms;
}
.logo:hover {
  filter: drop-shadow(0 0 2em #646cffaa);
}
.logo.vue:hover {
  filter: drop-shadow(0 0 2em #42b883aa);
}
</style> -->