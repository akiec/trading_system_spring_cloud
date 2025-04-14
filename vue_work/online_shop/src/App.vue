<script setup>
  import { RouterView,RouterLink,useRouter } from 'vue-router';
  import { ref, watchEffect } from 'vue';
  import { useAuthStore } from './stores/auth';
  const authStore = useAuthStore()  
  const search_text = ref()
  const router = useRouter()

  function submitSearch(search_text) {
    //跳转到搜索界面
    router.push({path:'\search', query:{content: search_text}})
  }

</script>

<template>
  <div class="app-container">
    <nav class="navigater">
      <router-link to="/home" class="h1-home"><h1>电商平台</h1> </router-link>        
      <form id="nav-searchform" style="border-radius: 8px;">
        <div class="nav-search-content">
          <input class="nav-search-input" id="nav-search-input" type="text" autocomplete="off" maxlength="100" placeholder="请输入搜索信息" v-model="search_text">
        </div>
        <div class="nav-search-btn" @click="submitSearch(search_text)">
          <svg width="17" height="17" viewBox="0 0 17 17" fill="none" xmlns="http://www.w3.org/2000/svg"><path fill-rule="evenodd" clip-rule="evenodd" d="M16.3451 15.2003C16.6377 15.4915 16.4752 15.772 16.1934 16.0632C16.15 16.1279 16.0958 16.1818 16.0525 16.2249C15.7707 16.473 15.4456 16.624 15.1854 16.3652L11.6848 12.8815C10.4709 13.8198 8.97529 14.3267 7.44714 14.3267C3.62134 14.3267 0.5 11.2314 0.5 7.41337C0.5 3.60616 3.6105 0.5 7.44714 0.5C11.2729 0.5 14.3943 3.59538 14.3943 7.41337C14.3943 8.98802 13.8524 10.5087 12.8661 11.7383L16.3451 15.2003ZM2.13647 7.4026C2.13647 10.3146 4.52083 12.6766 7.43624 12.6766C10.3517 12.6766 12.736 10.3146 12.736 7.4026C12.736 4.49058 10.3517 2.1286 7.43624 2.1286C4.50999 2.1286 2.13647 4.50136 2.13647 7.4026Z" fill="currentColor"></path></svg>
        </div>
      </form>    
      <ul class="navigater-links">    
        <li>
          <router-link to="/home" active-class="active"><span>首页</span></router-link>
        </li> 
        <li>
          <router-link :to="{
            path:'/shoppingcart'
          }" active-class="active"><span>购物车</span></router-link>
        </li>
        <li v-if="!authStore.isLogged">
          <router-link to="/login" active-class="active"><span>登录</span></router-link>
        </li>
        <li v-else> 
          <router-link to="/profile" active-class="active"><span>用户中心</span></router-link>
        </li>        
      </ul> 
    </nav>
    
    <main class="main-content">
      <router-view></router-view>
    </main>
  </div>  
</template>

<style scoped>
  form {
    unicode-bidi: isolate;
  }

  ul {
    list-style-type: none;
    unicode-bidi: isolate;
  }

  .h1-home {
    text-decoration: none;
    color: white;
  }
  

  .navigater {
    display: flex;
    justify-content: space-between;
    padding: 1rem 2rem;
    background: black;
    color: white;
  }

  #nav-searchform {
    display: flex;
    align-items: center;
    padding: 0 48px 0 4px;
    position: relative;
    z-index: 1;
    overflow: hidden;
    line-height: 38px;
    border: 1px solid var(--line_regular);
    height: 40px;
    background-color: var(--graph_bg_regular);
    opacity: .9;
    transition: background-color .3s;
  }

  .nav-search-content {
    display: flex;
    align-items: center;
    justify-content: space-between;
    position: relative;
    padding: 0 8px;
    width: 100%;
    height: 32px;
    border: 2px solid white;
    border-radius: 6px;
  }

  .nav-search-input {
    flex: 1;
    overflow: hidden;
    padding-right: 8px;
    border: none;
    background-color: transparent;
    box-shadow: none;
    color: var(--text2);
    font-size: 14px;
    line-height: 20px;
    outline: none;
  }

  .nav-search-btn {
    position: absolute;
    top: 3px;
    right: 7px;
    display: flex;
    align-items: center;
    justify-content: center;
    margin: 0;
    padding: 0;
    width: 32px;
    height: 32px;
    border: none;
    border-radius: 6px;
    color: var(--text1);
    line-height: 32px;
    cursor: pointer;
    transition: background-color .3s;
  }

  .navigater-links {
    display: flex;
    align-items: center;
    flex-shrink: 0;
    margin-right: 30px;
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
  }

  .search-input {
      padding: 0.5rem 1rem;
      border: 1px solid #ddd;
      font-size: 1rem;
      outline: none;
  }

  .search-button {
    border-radius: 20px;
  }

  .main-content {
    margin: 0 auto;
    margin-top: 30px;
    width: 90%;
  }
</style>

<!-- <script setup>
import HelloWorld from './components/HelloWorld.vue'
import HomePage from './components/HomePage.vue';
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

</style>
-->