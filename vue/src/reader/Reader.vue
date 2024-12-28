<script setup lang="ts">
import { ref, watchEffect } from "vue";
import { useRoute } from "vue-router"; // 导入 useRoute

const isShow = ref(true);
const route = useRoute(); // 获取当前路由信息

// 使用 watchEffect 监测 route 的变化
watchEffect(() => {
  // 根据当前路由的路径来设置 isShow
  if (route.path === '/reader/home'){
    isShow.value =false;
  }

});

</script>

<template>
  <RouterLink :to="{path:'/reader/home'}" active-class="xiangying" v-show="isShow">书城</RouterLink>
  <RouterView></RouterView>
</template>

<style scoped>
.xiangying {
  color: #007bff;
  font-weight: bold;
}
</style>
