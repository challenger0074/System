<template>
    <div class="app">
      <!--导航区-->
      <div class="navigate">
        about
      </div>
      <!--展示区-->
      <div class="main-content">
        <img v-for="(u,index) in dogList" :key="index" :src="(u as string)">
        <span v-show="dogList.isLoading">加载中......</span><br>
        <button @click="getDog">再来一只狗</button>
      </div>
    </div>
    </template>
    
    <script lang="ts" setup>
      import {ref,watch,watchEffect,computed} from 'vue'
      import { onMounted,onUnmounted } from 'vue';
      import useDog from '../hooks/useDogs'

      let {dogList,getDog} = useDog()
      onMounted(()=>{
        console.log('挂载了About组件')
      })
      onUnmounted(()=>{
        console.log('about组件卸载了吗，卸载了')
      })
    
    </script>
    <style></style>