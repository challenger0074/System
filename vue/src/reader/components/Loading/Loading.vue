<template>
  <div class="loading-component">
    <svg class="spinner" :style="loadingSize" viewBox="0 0 66 66" xmlns="http://www.w3.org/2000/svg">
      <circle class="path" :style="loadingColor" fill="none" :stroke-width="stroke" stroke-linecap="round" cx="33" cy="33" r="30"></circle>
    </svg>
  </div>
</template>

<script setup>
import { ref, computed } from 'vue';

const props = defineProps({
  size: {
    type: Number,
    default: 50
  },
  stroke: {
    type: Number,
    default: 3.5
  },
  color: {
    type: String,
    default: '#ed424b'
  }
});

const loadingSize = computed(() => {
  const newSize = `${props.size}px`;
  return {
    width: newSize,
    height: newSize
  };
});

const loadingColor = computed(() => {
  return {
    stroke: props.color
  };
});
</script>

<style lang="less" scoped>
.loading-component {
  display: inline-block;
  pointer-events: none;
  will-change: transform, opacity;
  position: fixed;
  left: 50%;
  top: 50%;
  transform: translate(-50%, -50%);
}

@offset: 187;
@duration: 1.4s;

.spinner {
  animation: rotator @duration linear infinite;
}

@keyframes rotator {
  0% { transform: rotate(0deg); }
  100% { transform: rotate(270deg); }
}

.path {
  stroke-dasharray: @offset;
  stroke-dashoffset: 0;
  transform-origin: center;
  animation: dash @duration ease-in-out infinite;
}

@keyframes dash {
  0% { stroke-dashoffset: @offset; }
  50% {
    stroke-dashoffset: @offset / 4;
    transform: rotate(135deg);
  }
  100% {
    stroke-dashoffset: @offset;
    transform: rotate(450deg);
  }
}
</style>
