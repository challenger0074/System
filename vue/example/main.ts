// 引入
import { createPinia } from 'pinia';
import {createApp} from 'vue';
import App from './App.vue'
import {router}from './router'
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
// 创建
const app =createApp(App);
const pinia=createPinia()
//安装
app.use(router);
app.use(pinia)
app.use(ElementPlus)

app.mount('#app');
