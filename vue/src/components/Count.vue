  
<template>
    <div class="count">
     <div>计算结果:{{sum}}</div>
     <select v-model.number="niubi">
         <option value="1">1</option>
         <option value="2">2</option>
         <option value="3">3</option>
     </select>
     <button @click="add2">加</button>
     <button @click="minus">减</button>
    </div>
 </template>
<script lang="ts" setup name="Count">
   import   {ref} from 'vue'
  let niubi=ref(1)
  let sum=ref(1)
  function add2(){
    sum.value+=niubi.value
  }
  function minus(){
    sum.value-=niubi.value
  }
 </script>

 
   <style></style>