<template>
  <div>
    <transition name="fade">
      <div class="toast" v-show="isShow">
        <p>{{ message }}</p>
      </div>
    </transition>
  </div>
</template>

<script setup>
import { ref } from 'vue';

const message = ref('');
const isShow = ref(false);

const show = (msg = '默认文本', duration = 1000) => {
  message.value = msg;
  isShow.value = true;

  // 指定时间后弹框消失
  setTimeout(() => {
    isShow.value = false;
  }, duration);
};

defineExpose({show})
</script>

<style lang="less" scoped>
.toast {
  position: fixed;
  left: 50%;
  top: 50%;
  transform: translate(-50%, -50%);
  padding: 10px; // Added padding for better aesthetics
  background-color: rgba(0, 0, 0, 0.8); // Slightly darker background for better contrast
  border-radius: 8px; // Slightly larger border-radius for a smoother look
  text-align: center;
  line-height: 1.5; // Adjusted line-height for better text spacing
  color: white;
  z-index: 1000000;
}

.fade-enter-active, .fade-leave-active {
  transition: opacity 0.3s ease; // Fade transition
}

.fade-enter, .fade-leave-to /* .fade-leave-active in <2.1.8 */ {
  opacity: 0; // Set initial opacity for fade-in/out
}
</style>
