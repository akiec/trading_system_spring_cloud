// src/stores/auth.js
import { defineStore } from 'pinia';
import Cookies from 'js-cookie';

export const useAuthStore = defineStore('login-out', {
  state: () => ({
    isLogged: Cookies.get('isLogged') === 'true' || false,
    userId: Cookies.get('userId') || null // 新增用户ID状态
  }),
  actions: {
    login(userId) { // 添加userId参数
      this.isLogged = true;
      this.userId = userId;
      Cookies.set('isLogged', 'true', { expires: 7, secure: true });
      Cookies.set('userId', userId, { expires: 7, secure: true }); // 存储用户ID
    },
    logout() {
      this.isLogged = false;
      this.userId = null;
      Cookies.remove('isLogged');
      Cookies.remove('userId'); // 清除用户ID
    }
  },
  getters: {
    isAuthenticated: (state) => state.isLogged,
    currentUserId: (state) => state.userId // 新增用户ID获取
  }
});