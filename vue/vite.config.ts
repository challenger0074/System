import { fileURLToPath, URL } from 'node:url'

import {resolve} from 'path';
import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'

// https://vitejs.dev/config/
export default defineConfig({
  plugins: [
    vue(),
  ],
   base: '/',
  resolve: {
    alias: {
      '@': fileURLToPath(new URL('./src', import.meta.url))
    }
  },
  server: {
		// 运行端口
		port: 1648,
	  proxy: {
		  '/file': {
			  target: 'http://127.0.0.1:3000',
			  changeOrigin: true,
			  rewrite: (path) => path.replace(/^\/file/, '')
		  },
		  '/api': {
			  target: 'http://127.0.0.1:3001',
			  changeOrigin: true,
			  rewrite: (path) => path.replace(/^\/api/, '')
		  },
		  '/sp': {
			  target: 'http://127.0.0.1:8001',
			  changeOrigin: true,
			  rewrite: (path) => path.replace(/^\/sp/, '')
		  }
	  }

  },
	build: {
		// 多页面打包配置
		rollupOptions: {
			input: {
				// 配置所有页面路径，使得所有页面都会被打包
				main: resolve(__dirname, 'index.html'),
				main2: resolve(__dirname, 'index2.html'),
				login: resolve(__dirname, 'login/index.html'),
				register: resolve(__dirname, 'register/index.html'),
			}
		}
	}

})
