
import { createRouter, createWebHistory } from "vue-router";


import Detail2 from '../pages/Detail2.vue'
import File from "../pages/File.vue";
  export const router= createRouter({
    history:createWebHistory(),//路由的工作模式
    routes:[//一个一个的路由规则
        {
            path:'/',
            redirect:'/detail2'
        },//默认打开重定向到login
        {
            name:'detail',
            path:'/detail2',
            component:Detail2,
            props:true
        },
        {
            name:'file',
            path:'/file',
            component:File,
        }
       
    ]}
 )
