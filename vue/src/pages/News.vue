<template>
    <div class="news">
      <!--导航区---->
      <ul>
        <li v-for="news in newList" :key="news.id">
          <!-- <RouterLink to="/news/detail?id=news&title=2&content=3">{{ news.title }}</RouterLink> -->
          <RouterLink :to="`/news/detail?id=${news.id}&title=${news.title}&content=${news.content}`">{{ news.title }}</RouterLink>
          <!-- 第二种写法 -->
          <RouterLink :to="
          {
            // name:'detail',
            path:'/news/detail',
            query:{
              id:news.id,
              title: news.title,
              content:news.content
            }
          }
          ">{{ news.title }}</RouterLink>
            <RouterLink :to="{
              name:'detail',
              params:{
              id:news.id,
              title: news.title,
              content:news.content
            }
            
            }">
              {{ news.title }}
            </RouterLink>
            <button @click="showNewsDetail(news)">查看新闻</button>

        </li>
      </ul>
      <!--展示区---->
      <div class="news-content">
        <RouterView></RouterView>
        <RouterView></RouterView>
      </div>
    </div>
    </template>
    
    <script lang="ts" setup>
   
import {ref,watch,watchEffect,reactive } from 'vue'
      import { RouterView,RouterLink,useRouter } from 'vue-router';
import Detail from './Detail.vue';
         const newList=([
          {id:"1",title:"1",content:"1"},
          {id:"2",title:"2",content:"2"},
          {id:"3",title:"3",content:"3"},
          {id:"3",title:"3",content:"3"}
         ])
         const router=useRouter()
        //  写个接口限制news
         interface NewsInter{
          id:string,
          title:string,
          content:string
         }
         function showNewsDetail(news:NewsInter){
       router.push(
        {
          name:'detail',
          params:{
               id:news.id,
              title: news.title,
              content:news.content
          }
        }
       )
         }
    </script>
    <style></style>