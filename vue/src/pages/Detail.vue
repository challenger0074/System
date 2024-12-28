<template>
    <ul class="news-list">
        <li>编号：{{id}}</li>
        <li>标题：{{ title }}</li>
        <li>内容：xx</li>
    </ul>
</template>
<script setup lang="ts" >
import { useRoute } from 'vue-router';
import { toRefs } from 'vue';
// let {query} =toRefs(useRoute) 可以通过torefs直接结构
defineProps(['id','title','content'])
let route=useRoute()
console.log(route)
</script>
<style scoped></style>