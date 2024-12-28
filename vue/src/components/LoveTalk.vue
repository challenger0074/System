   
<template>
    <div class="情话">
     <button @click="add2">获取一句情话</button>
     <ul>
        <li v-for="talk in talkList" :key="talk.id">{{ talk.title }}</li>
     </ul>
    </div>
 </template>
<script lang="ts" setup name="love">
   import   {reactive} from 'vue'
   import {nanoid} from 'nanoid'
   import axios from 'axios'
 let talkList=reactive([
    {id:'firstId',title:'今天很好'}
 ])
  async function add2(){
    let {data:{content}} = await axios.get('https://api.uomg.com/api/rand.qinghua?format=json')
    let   obj={id:nanoid(),title:content}
    console.log(obj)
    talkList.unshift(obj)
  }
 
 </script>

 
   <style></style>