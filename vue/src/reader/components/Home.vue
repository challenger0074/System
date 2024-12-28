<template>
  <div class="container">
    <nav class="nav-header">
      <header>
        <a href="/" class="brand">移动书城</a>
        <a href="/" class="avatar"></a>
      </header>
      <div class="swipe">
        <el-carousel :interval="5000" arrow="always">
          <el-carousel-item v-for="(image, index) in images" :key="index" fit="contain">
            <div class="carousel-image-container">
              <el-image
                  :src="image"
                  style="max-height: 100%; max-width: 100%;"></el-image>
            </div>
          </el-carousel-item>
        </el-carousel>
      </div>
    </nav>
    <nav class="home-nav">
      <router-link
          class="guide-nav-div"
          v-for="(item, index) in type"
          :to="{ path: 'category', query: { type: item.num } }"
          :key="item.num"
          @click="openBookCategory(item.num)">
        <i class="icon icon-sort"></i>
        <h4 class="guide-nav-h">{{ item.word }}</h4>
      </router-link>
    </nav>
    <div v-if="!loading">
      <Recommend :booklist="hotBooks" title="热门小说"/>
      <Recommend :booklist="topBooks" title="排行榜"/>
      <Recommend :booklist="freeBooks" title="限时免费"/>
      <BookList :datalist="newBooks" title="新书抢鲜"/>
      <BookList :datalist="endBooks" title="畅销完本"/>
      <BookList :datalist="likeBooks" title="猜你喜欢"/>
    </div>
    <Loading v-show="loading"/>
  </div>
</template>

<script setup>
import {ref, onMounted, getCurrentInstance, computed} from 'vue';
import axios from 'axios';
import Recommend from './Common/Recommend.vue';
import BookList from './Common/BookList.vue';
import Loading from './Loading/Loading.vue';


const instance = getCurrentInstance();
const $common = instance.appContext.config.globalProperties.$common;
const booklist = ref([]);
const type = ref([
  {num: 1, word: '玄幻'},
  {num: 2, word: '修真'},
  {num: 3, word: '都市'},
  {num: 4, word: '历史'},
  {num: 5, word: '游戏'},
]);
const loading = ref(false);
const images = [
  getIcon("1.jpg"),
  getIcon("2.jpg"),
  getIcon("3.jpg"),
  getIcon("4.jpg"),
  getIcon("5.jpg"),
];

// 计算属性
/*const hotBooks = computed(() => booklist.value.filter(book => book.hot === true));*/
const hotBooks = computed(() => booklist.value.slice(11,21));
const topBooks = computed(() => booklist.value.slice(0, 10)); // 取前 10 本
const freeBooks = computed(() => booklist.value.slice(21,31));
const newBooks = computed(() => booklist.value.slice(-10)); // 取最后 10 本
const endBooks = computed(() => booklist.value.slice(31,41));
const likeBooks = computed(() => booklist.value.slice(41,51));
onMounted(() => {
  getData();
  test();
});

function test() {
  console.log("api test")
  console.log($common.test());
}

/*动态导入图片*/
function getIcon(name) {
  return new URL(`../assets/images/${name}`, import.meta.url).href;
}

const getData = async () => {
  loading.value = true;
  try {
    const res = await axios.get(`${$common.api}/booklist`);
    /*const res = await axios.get(`api/booklist`);*/
    booklist.value = res.data;
  } catch (error) {
    console.error(error);
  } finally {
    loading.value = false;
  }
};

const openBookCategory = (num) => {
  // Implement your logic here
};
</script>

<style lang="less" scoped>
.container {

  background-color: #f6f7f9;
}

.carousel-image-container {
  display: flex;
  justify-content: center; /* 水平居中 */
  align-items: center; /* 垂直居中 */
  height: 100%; /* 使容器充满 carousel-item 的高度 */
}

.el-carousel-item {
  display: flex; /* 使 carousel-item 也成为 flexbox */
  justify-content: center; /* 水平居中 */
  align-items: center; /* 垂直居中 */
  height: 100%; /* 确保高度为 100% */
}

.nav-header {
  header {
    display: flex;
    height: 45px;
    padding: 0 15px;
    justify-content: space-between;
    align-items: center;
    font-size: 20px;
    text-indent: -8888px;

    .brand {
      width: 100px;
      background: url(http://qidian.gtimg.com/qdm/img/logo-qdm.0.50.svg) no-repeat left;
      background-size: 100%;
    }

    .avatar {
      width: 24px;
      height: 24px;
      background: url(../assets/images/avatar.png) no-repeat center;
      background-size: 100%;
    }
  }

  .swipe {

    height: 300px;
    /*position:relative;*/

    img {
      width: 100%; /* 宽度占满容器 */
      height: auto; /* 高度自适应 */
      object-fit: cover; /* 图片覆盖，不失真 */
    }
  }

}

.home-nav {
  display: flex;
  padding: 10px 0;
  margin: 10px 0;
  background-color: #fff;

  .guide-nav-div {
    flex: 1;
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;

    > i {
      width: 24px;
      height: 24px;
      background-image: url(../assets/images/sprite.0.50.png);
    }

    &:nth-of-type(1) {
      i {
        background-position: -63px -28px;
      }
    }

    &:nth-of-type(2) {
      i {
        background-position: 0 0;
      }
    }

    &:nth-of-type(3) {
      i {
        background-position: 0 -30px;
      }
    }

    &:nth-of-type(4) {
      i {
        background-position: 0 -60px;
      }
    }

    &:nth-of-type(5) {
      i {
        background-position: -30px -30px;
      }
    }
  }
}

.swipe {
  height: 170px;

  img {
    width: 100%;
  }
}
</style>
