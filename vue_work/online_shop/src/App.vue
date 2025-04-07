<script setup>
import { RouterView,RouterLink } from 'vue-router';
</script>

<template>
  <div class="app-container">
    <nav class="navigater">
      <h1>电商平台</h1>
      <div class="navigater-links">     
        <router-link to="/home" active-class="active">首页</router-link>
        <router-link to="/login" active-class="active">登录</router-link>
        <router-link to="/profile" active-class="active">用户中心</router-link>
      </div> 
    </nav>
    
    <main class="main-content">
      <router-view></router-view>
    </main>
  </div>  
</template>

<style>
  .navigater {
    display: flex;
    justify-content: space-between;
    padding: 1rem 2rem;
    background: black;
    color: white;
  }

  .navigater-links a {
    text-align: center;
    width: 100px;
    height: 40px;
    line-height: 40px;
    text-decoration: none;
    color: white;
    font-size: 20px;
    text-decoration: none;
    margin-left: 2rem;
  }

  .navigater a.active {
    color: orange;
    font-weight: 900;
    text-shadow: 0 0 1px black;
    font-family: 微软雅黑;
  }

  .main-content {
    margin: 0 auto;
    margin-top: 30px;
    width: 90%;
    height: 400px;
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