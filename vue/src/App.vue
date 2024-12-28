<template>
  <div class="app clearfix">
    <!--导航区-->
    <div class="navigate clearfix">
      <Navigate />
    </div>
    <!--展示区-->
    <div class="main-content clearfix">
      <RouterView />
    </div>
  </div>
</template>

<script lang="ts" setup>
import { ref, watch, watchEffect, computed } from 'vue';
import { RouterView, RouterLink } from 'vue-router';
import { useLoginState } from './stores/state.ts';
import Navigate from './components/Navigate.vue';

const state = useLoginState();

console.log(useLoginState().islogin);
</script>

<style scoped>
.app {
  display: flex; /* Ensure the app is a flex container */
  flex-direction: column; /* Arrange children vertically */
  width: 100%;
  height: 100vh; /* Use full viewport height */
}

.navigate {
  background-color: #f8f9fa;
  padding: 10px;
  display: flex;
  justify-content: space-around;
}

.main-content {
  flex: 1; /* Allow the main content to grow and fill the remaining space */
  overflow: auto; /* Optional: handle overflow */
}
</style>
