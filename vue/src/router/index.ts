
import { createRouter, createWebHistory } from "vue-router";

import About from '@/pages/About.vue'
import Home from '@/pages/Home.vue'
import News from '@/pages/News.vue'
import Detail from '@/pages/Detail.vue'
import Login from '@/components/Login.vue'
import Element from '@/pages/Element.vue'
import EmployeeManager from "@/ganxq/Manager.vue"
import Reader from  "@/reader/Reader.vue"
import ReaderHome from "@/reader/components/Home.vue"
import ReaderPage from "@/reader/components/Reader.vue"
import BookDetail from "@/reader/components/BookDetail.vue"
import Category from "@/reader/components/Category.vue"
import loginForm from '@/pages/login/loginForm.vue'
import Test from '@/pages/Test.vue'
import Register from '@/pages/login/registerForm.vue'
import UserList  from "@/pages/UserList.vue"
import Welcome  from "@/pages/Welcome.vue"
import Authorization from "@/pages/authorization/Authorization.vue"
import Music from "@/components/music/Music.vue";
import MusicList from '@/components/music/list/MusicList.vue'
import PrivateList from "@/components/music/list/PrivateList.vue";
import PublicMusic from "@/components/music/list/PublicMusic.vue";
import Playlist from "@/components/music/list/Playlist.vue";
import * as process from "process";
  export const router= createRouter({
    history:createWebHistory(import.meta.env.BASE_URL || '/'),//路由的工作模式
    routes:[//一个一个的路由规则
        {
            path:'/',
            redirect:'/login'
        },//默认打开重定向到login
        {
            name:'login',
            path:'/login',
            component:Login
        },
        {
            name:'register',
            path:'/register',
            component:Register
        },
        {
            name:'test',
            path:'/test',
            component:Test
        },
        {
            name:'readerWeb',
            path:'/reader',
            component:Reader,
            redirect:'/reader/home',
            children:[
               /* {
                    path: '/',
                    redirect:'/home'   无效
                },*/
                {
                    name:'home',
                    path:'home',
                    component:ReaderHome,
                    props:true
                },
                {
                    path: '/reader/:id',
                    name: 'reader',
                    component: ReaderPage,
                },
                {
                    path: '/bookdetail/:id',
                    name: 'bookdetail',
                    component: BookDetail,
                },
                {
                    path: '/category',
                    name: 'category',
                    component: Category,
                },
            ]
        },
        {
            name:'employee',
            path:'/employee',
            component:EmployeeManager
        },
        {
            name:'element',
            path:'/element',
            component:Element
        },
        {
        name:'home1',
        path:'/home',
        component:Home,
            redirect:'/home/welcome',
            children:[
                {
                    name:'welcome',
                    path:'welcome',
                    component:Welcome
                },
                {
                    name:'user',
                    path:'user',
                    component:UserList
                },
                {
                    name:'authorization',
                    path: 'rights',
                    component: Authorization
                },
                {
                    name:'player',
                    path:'player',
                    component:Music
                },
                {
                    name:'playlist',
                    path:'playlist',
                    component:Playlist
                },
                {
                    name:'mlist',
                    path:'mlist',
                    component:MusicList,
                    redirect:'mlist/private',
                    children:[
                        {
                            name:'public',
                            path:'public',
                            component:PublicMusic
                        },
                        {
                            name:'private',
                            path:'private',
                            component:PrivateList
                        }
                    ]
                }
            ]
        },
    {
        name:'about1',
        path:'/about',
        component:About
    },
    {
        name:'news1',
        path:'/news',
        component:News,
        children:[
            {
                name:'detail',
                path:'detail/:id/:title/:content?',
                component:Detail,
                props:true
            }
        ]
    }
    ]}
 )
