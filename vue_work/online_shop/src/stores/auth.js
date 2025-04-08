// src/stores/authStore.js
import { defineStore } from 'pinia';
import Cookies from 'js-cookie';

export const useAuthStore = defineStore('login-out', {
  state: () => ({
    isLogged: Cookies.get('isLogged') === 'true' || false, // 初始化时从 Cookie 读取
  }),
  actions: {
    login() {
      this.isLogged = true;
      Cookies.set('isLogged', 'true', { expires: 7, secure: true }); // 7天过期
    },
    logout() {
      this.isLogged = false;
      Cookies.remove('isLogged');
    },
  },
  getters: {
    isAuthenticated: (state) => state.isLogged,
  },
});