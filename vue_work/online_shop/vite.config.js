import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'

// https://vite.dev/config/
export default defineConfig({
  plugins: [vue()],
  optimizeDeps: {
    force: true // 强制重新构建依赖
  },
  server: {
    open: true,
    proxy: {
      '^/api': {
        target: 'http://localhost:8080/api',
        changeOrigin: true,
        rewrite:(path)=>path.replace(/^\/api/,'')
      }
    }
  }
})
