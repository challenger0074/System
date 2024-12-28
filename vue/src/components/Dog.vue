  
   <script lang="ts" setup name="Dog">
   import   useDogs from '@/hooks/useDogs'
 let {dogList,getDog}=useDogs()
 </script>
<template>
    <img v-for="(dog,index) in dogList" :src="dog" :index="index"><br>
    <button @click="getDog">再来一只狗</button>
</template>
 
   <style></style>