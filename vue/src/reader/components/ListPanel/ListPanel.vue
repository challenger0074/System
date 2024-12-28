<template>
  <div class="list-panel" >
    <div class="list">
      <div class="list-nav">
        <i class="back" @click="hideListPanel"></i>
        <h3>目录</h3>
      </div>
      <div class="list-content" >
        <el-scrollbar>
          <ul>
            <li v-for="(item, index) in chapterList" :key="index" @click="jumpTo(index)">
              · {{ index + 1 }}. {{ item }}
            </li>
          </ul>
        </el-scrollbar>
      </div>
    </div>
  </div>
</template>

<script setup>
import {ref, onMounted, getCurrentInstance} from 'vue';
import { useStore } from 'vuex';
import axios from 'axios';
import { ElScrollbar } from 'element-plus';

const props = defineProps({
  bookId: {
    type: String,
    required: true
  }
});
const  instance=getCurrentInstance();
const $common=instance.appContext.config.globalProperties.$common;
const chapterList = ref([]);
const store = useStore();

const jumpTo = (index) => {
  if (index >= 50) {
    index = 50;
  }
  store.dispatch('curChapter', index + 1);
  hideListPanel();
  store.state.bar = false;
  document.body.scrollTop = 0;
};

const hideListPanel = () => {
  store.state.list_panel = false;
};

const getList = async () => {
  const {res} = await axios.get(`${$common.api}/titles?id=${props.bookId}`);
  console.log("TItle"+res)
  if (res && res.titles) {
    chapterList.value = res.titles.split('-');
  }
};

onMounted(() => {
  getList();
});
</script>

<style lang="less" scoped>
.list-panel {
  position: fixed;
  transition: all .3s;
  left: 0;
  top: 0;
  bottom: 0;
  right: 50px;
  z-index: 10;
  overflow: auto;
  transform: translateX(-100%);
  &.show {
    transform: translateX(0);
  }
  .list {
    position: absolute;
    left: 0;
    top: 0;
    bottom: 0;
    width: 100%;
    background-color: #fff;
    opacity: 1;
    .list-nav {
      position: fixed;
      height: 50px;
      line-height: 50px;
      text-align: center;
      top: 0;
      left: 0;
      right: 50px;
      color: #ed424b;
      background-color: #fff;
      border-bottom: 1px solid #ed424b;
      .back {
        position: absolute;
        left: 10px;
        top: 10px;
        width: 30px;
        height: 30px;
        background: url("../../assets/images/back.png") no-repeat;
      }
      h3 {
        margin: 0;
      }
    }
    .list-content {
      background-color: #fff;
      margin-top: 50px;
      ul {
        padding: 0 15px;
      }
      li {
        color: #333;
        height: 50px;
        line-height: 50px;
        border-bottom: 1px solid #ccc;
        white-space: nowrap;
        overflow: hidden;
        text-overflow: ellipsis;
      }
    }
  }
}
</style>
