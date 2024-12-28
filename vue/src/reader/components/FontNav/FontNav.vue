<template>
  <div class="top-nav-pannel font-container" id="font-container" v-if="font_panel">
    <div class="child-mod">
      <span>字号</span>
      <button id="large-font" class="spe-button" @click="add">
        大
      </button>
      <button id="small-font" class="spe-button" @click="sub" style="margin-left:10px;">
        小
      </button>
    </div>
    <div class="child-mod" id="bk-container">
      <span>背景</span>
      <div
          class="bk-container"
          :class="{ 'bk-container-current': i === now_color }"
          v-for="(n, i) in 6"
          :key="i"
      >
        <div class="color_btn" @click="changeColor(i)" :style="{ backgroundColor: colorOptions[i] }"></div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { computed, ref } from 'vue';
import { useStore } from 'vuex';
import localEvent from '@/store/local';

const store = useStore();
const now_color = ref(0);

const font_panel = computed(() => store.state.font_panel);
const bg_color = computed(() => store.state.bg_color);

const colorOptions = ['#ffffff', '#e9dfc7', '#e7eee5', '#a4a4a4', '#cdefcd', '#283548', '#0f1410'];

const changeColor = (index) => {
  now_color.value = index;
  store.commit('SET_BG_COLOR', index + 1); // 假设在 store 中有相应的 mutation
  localEvent.StorageSetter('bg_color', index + 1);
};

const add = () => {
  store.dispatch('fzSizeAdd');
};

const sub = () => {
  store.dispatch('fzSizeSub');
};
</script>


<style lang="less" scoped>
.top-nav-pannel {
  position: fixed;
  bottom: 70px;
  height: 135px;
  background: none;
  width: 100%;
  color: #fff;
  font-size: 14px;
  z-index: 10004;

  .spe-button {
    background: none;
    border: 1px #8c8c8c solid;
    padding: 5px 40px;
    color: #fff;
    border-radius: 16px;

    &:focus {
      outline: none;
    }
  }

  .child-mod {
    padding: 5px 20px;
    margin-top: 15px;

    & > span:first-child {
      margin-right: 20px;
    }
  }

  .bk-container {
    position: relative;
    height: 30px;
    width: 30px;
    border-radius: 15px;
    display: inline-block;
    vertical-align: -14px;
    margin-left: 10px;

    .color_btn {
      height: 30px;
      width: 30px;
      border-radius: 15px;
    }
  }

  .bk-container-current {
    height: 31px;
    width: 32px;
    border-radius: 16px;
    border: 1px #ff7800 solid;
  }
}
</style>

