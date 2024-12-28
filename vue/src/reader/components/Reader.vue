<template>
  <div id="reader">
    <TopNav/>
    <div class="read-container" :style="{ backgroundColor: bg_color, color: bg_night ? '#fff' : '#555' }" ref="fz_size">
      <h4>{{ title }}</h4>
      <div class="chapterContent" v-if="!loading">
        <p v-for="(c, i) in content" :key="i">{{ c }}</p>
      </div>
      <div class="btn-bar" v-if="!loading">
        <ul class="btn-tab">
          <li class="prev-btn" @click="prevChapter">上一章</li>
          <li class="next-btn" @click="nextChapter">下一章</li>
        </ul>
      </div>
    </div>
    <div class="page-up" @click="pageUp()"></div>
    <div class="click-mask" @click="clickBar"></div>
    <div class="page-down" @click="pageDown()"></div>
    <div class="top-nav-pannel-bk font-container" v-if="font_panel"></div>
    <FontNav/>
    <BottomNav/>
    <Cover :class="{ hide: !list_panel }"/>
    <ListPanel :class="{ show: list_panel }" :bookId="$route.params.id"/>
    <Loading v-if="loading"/>
  </div>
</template>

<script setup>
import {ref, computed, watch, onMounted, getCurrentInstance} from 'vue';
import {useStore} from 'vuex';
import {useRoute} from 'vue-router';
import axios from 'axios';
import localEvent from '@/store/local';
import TopNav from './TopNav/TopNav.vue';
import BottomNav from './BottomNav/BottomNav.vue';
import FontNav from './FontNav/FontNav.vue';
import ListPanel from './ListPanel/ListPanel.vue';
import Cover from './Cover.vue';
import Loading from './Loading/Loading.vue';

const instance = getCurrentInstance();
const $common = instance.appContext.config.globalProperties.$common;
// Use router and store
const routes = useRoute();
const store = useStore();

// Initialize refs
const title = ref('');
const content = ref([]);
const loading = ref(false);
const booksReadInfo = ref({});
const cacheData = ref({});

// Computed properties
const fz_size = computed(() => store.getters.getFzSize);
const bg_color = computed(() => store.getters.getBgColor);
const bg_night = computed(() => store.getters.getBgNight);
const list_panel = computed(() => store.getters.getListPanel);
const curChapter = computed(() => store.getters.getCurChapter);

// Fetch data function
const fetchData = async (id, chapter) => {
console.log("chapter"+chapter);

  loading.value = true;
  try {
    const {data} = await axios.get(`${$common.api}/book?book=${id}&id=${chapter}`);
    title.value = data.title;
/*    console.log('Data fetched:', data); // Check the fetched data
    console.log('Content:', data.content); // Log the specific content*/
    if (data && data.content) {
      content.value = data.content.split('-');
    } else {
      console.error("data.content is undefined or null");
    }
    cacheData.value[id] = cacheData.value[id] || {};
    cacheData.value[id][chapter] = {title: data.title, content: data.content.split('-')};
  } catch (error) {
    console.error('Error fetching data:', error);
    // Handle error (e.g., show notification)
    // alert('加载失败，请稍后再试！');
  } finally {
    loading.value = false;
  }
};

// Save book info to local storage
const saveBooksInfo = () => {
  const id = routes.params.id;
  booksReadInfo.value = {
    book: id,
    chapter: curChapter.value,
  };
  localEvent.StorageSetter('bookreaderinfo', booksReadInfo.value);
};

// Navigation functions
const prevChapter = () => {
  store.dispatch('prevChapter');
  saveBooksInfo();
  setTimeout(() => {
    document.body.scrollTop = 0;
  }, 300);
};

const nextChapter = () => {
  store.dispatch('nextChapter', 50);
  saveBooksInfo();
  setTimeout(() => {
    document.body.scrollTop = 0;
  }, 300);
};

// Lifecycle hooks
onMounted(() => {
  console.log("localEvent.StorageGetter('ceshi')");
  localEvent.StorageSetter('ceshi',"feixiang");
  console.log("localEvent.StorageGetter('ceshi')"+localEvent.StorageGetter('ceshi'));
  const fzSize = localEvent.StorageGetter('fz_size');
  const bgColor = localEvent.StorageGetter('bg_color');
  const localBookReaderInfo = localEvent.StorageGetter('bookreaderinfo');
  const id = routes.params.id;

  // Load settings from localStorage
  if (fzSize) {
    store.dispatch('setFzSize', fzSize);
  }

  if (bgColor) {
    store.dispatch('setBgColor', bgColor);
  }
  console.log("localBookReaderInfo"+localBookReaderInfo.value)
  // Load book info
  if (localBookReaderInfo.value&& localBookReaderInfo.value?.id!==id) {
    console.log("localBookReaderInfo"+localBookReaderInfo.value)
    booksReadInfo.value = localBookReaderInfo.value;
    if (localBookReaderInfo.value?.chapter){
      fetchData(id, booksReadInfo.value.chapter);
      store.dispatch('curChapter', booksReadInfo.value.chapter);
    }
  } else {//第一次进入
    console.log("第一次进入")
    booksReadInfo.value = {
        book: id,
        chapter: 1,
      };
    fetchData(id, booksReadInfo.value?.chapter);
    store.dispatch('curChapter', booksReadInfo.value.chapter);
    saveBooksInfo();
    }
  console.log("shuben" + id);
  /*console.log(content.value)*/
});


// Watch for chapter changes
watch(curChapter, (val) => {
  localEvent.StorageSetter('cur_chapter', val);
  saveBooksInfo();
  fetchData(routes.params.id, val);
});
</script>

<style lang="less" scoped>
.read-container {
  font-size: 16px;
  color: #555;
  line-height: 31px;
  min-height: 600px;
  padding: 15px;

  h4 {
    position: fixed;
    top: 0;
    left: 15px;
    right: 15px;
    height: 50px;
    line-height: 50px;
    font-size: 20px;
    color: #736357;
    margin: 0 0 1em 0;
    letter-spacing: 2px;
    white-space: nowrap;
    overflow: hidden;
    text-overflow: ellipsis;
  }

  p {
    text-indent: 2em;
    margin: 0.5em 0;
    text-align: justify;
    line-height: 1.5;
  }
}
</style>
