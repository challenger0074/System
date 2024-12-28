<template>

  <div class="app">

    <RouterLink :to="{path:'/file'}" >文件</RouterLink>
    <!--展示区-->
    <div class="main-content">
     <RouterView></RouterView>
    </div>

  </div>
  </template>

  <script lang="ts" setup>
    import {ref,watch,watchEffect,computed} from 'vue'
    import { RouterView,RouterLink } from 'vue-router';



  </script>
 <style scoped>
 .app {


 }

 .navigate {
   background-color: #f8f9fa;
   padding: 10px;
   display: flex;
   justify-content: space-around;
 }


 .main-content {
  flex: 1;

 }

 </style>
