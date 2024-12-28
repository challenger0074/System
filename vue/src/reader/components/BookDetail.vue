<template>
  <div class="book-detail">
    <Loading v-if="loading" />
    <div class="detail-content" v-if="bookDetail">
      <div class="detail-linear">
        <header class="detail-top">
          <a @click="back">
            <i class="back"></i>
            <h2 class="detail-title">{{ bookDetail.name }}</h2>
          </a>
          <router-link to="/" class="home-btn">
            <i class="iconfont icon-home"></i>
          </router-link>
        </header>
        <div class="detail-con" v-if="!loading">
          <div class="detail-img">
            <img :src="bookDetail.images" alt="" @error="loadImage" />
          </div>
          <div class="detail-main">
            <h3 class="name">{{ bookDetail.name }}</h3>
            <p class="author">作者：{{ bookDetail.author }}</p>
            <p class="type">分类：{{ bookDetail.type }}</p>
            <p class="word-count">{{ bookDetail.wordcount }}万字</p>
            <Rate :score="bookDetail.ratings" />
          </div>
        </div>
        <div class="read-btn" v-if="!loading">
          <div>
            <Button type="primary" @click="openBook">
              <router-link :to="{ path: '/reader/' + bookDetail.id }">开始阅读</router-link>
            </Button>
          </div>
          <div>
            <Button @click="openBook">
              <router-link :to="{ path: '/reader/' + bookDetail.id }">开始阅读</router-link>
            </Button>
          </div>
        </div>
      </div>
      <div v-if="!loading">
        <div class="detail-intro">
          <p :class="{ show5: !showmore }" @click="showmore = !showmore">{{ bookDetail.intro }}</p>
        </div>
        <div class="detail-tag">
          <h3 class="tag">类别标签</h3>
          <ul class="tag-btn clearfix">
            <li>{{ bookDetail.type }}</li>
            <li>东方玄幻</li>
          </ul>
        </div>
        <div class="detail-like">
          <h3 class="like-title">喜欢本书的人也喜欢</h3>
          <ul class="like-list">
            <li v-for="(item, index) in likes" :key="index">
              <Similar :like="item" />
            </li>
          </ul>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import {defineComponent, ref, computed, watch, getCurrentInstance} from 'vue';
import { useRoute, useRouter } from 'vue-router';
import { useStore } from 'vuex';
import axios from 'axios';
import Similar from './Similar.vue';
import Rate from './rate/rate.vue';
import Loading from './Loading/Loading.vue';
import { ElButton } from 'element-plus';

export default defineComponent({
  components: { Similar, Rate, Loading, ElButton },
  setup() {
    const loading = ref(false);
    const bookDetail = ref({});
    const likes = ref([]);
    const showmore = ref(false);
    const route = useRoute();
    const router = useRouter();
    const instance=getCurrentInstance();
    const  store=useStore();
    const $common = instance.appContext.config.globalProperties.$common;

    const getBookDetail = async (bookId) => {
      loading.value = true;
      try {
        const res = await axios.get(`${$common.api}/booklist?id=${bookId}`);
        bookDetail.value = res.data;
        likes.value = res.data.like.split('-');
      } catch (error) {
        console.error(error);
      } finally {
        loading.value = false;
        showmore.value = false;
      }
    };

    const openBook = () => {
      store.state.bar = false;
    };

    const back = () => {
      router.go(-1);
    };

    const loadImage = (e) => {
      // 使用默认图片的逻辑
      return  $common.defaultImage(e)
    };

    watch(
        () => route.params.id,
        (newId) => {
          getBookDetail(newId);
        }
    );

    // Initial data fetch
    getBookDetail(route.params.id);

    return {
      loading,
      bookDetail,
      likes,
      showmore,
      openBook,
      back,
      loadImage
    };
  },
});
</script>

<style lang="less" scoped>


.ell {
  text-overflow: ellipsis;
  overflow: hidden;
  white-space: nowrap;
}

.book-detail {
  padding: 0 20px;
}

.loading {
  position: fixed;
  top: 0;
  left: 0;
  bottom: 0;
  right: 0;
  z-index: 999;
  background-color: #fff;
  display: flex;
  justify-content: center;
  align-items: center;
}

.detail-linear {
  background: linear-gradient(to top, #fff, rgba(255, 255, 255, 0) 8rem) no-repeat center bottom;

  .detail-top {
    position: fixed;
    display: flex;
    left: 0;
    top: 0;
    right: 0;
    height: 50px;
    background-color: #eee;

    a:first-of-type {
      flex: 1;
    }

    i.back {
      position: absolute;
      left: 10px;
      top: 10px;
      width: 30px;
      height: 30px;
      background: url("../assets/images/back.png");
    }

    h2 {
      margin: 0 40px;
      font-size: 18px;
      line-height: 50px;
      color: #ed424b;
    }
  }

  .detail-con {
    display: flex;
    margin-top: 50px;
    padding: 15px 0 18px;

    .detail-img {
      width: 100px;
      margin-right: 25px;

      img {
        width: 100%;
      }
    }

    .detail-main {
      flex: 1;

      h3 {
        font-size: 18px;
        line-height: 1.5;
      }

      p {
        font-size: 14px;
        line-height: 1.8;
      }
    }
  }

  .read-btn {
    display: flex;

    > div {
      flex: 1;
      padding-bottom: 20px;
      border-bottom: 1px solid #ddd;

      &:first-child {
        margin-right: 15px;
      }

      button {
        display: block;
        margin: 0 auto;
        width: 100%;
        height: 33px;
        line-height: 33px;
        font-size: 15px;
        border: none;
        border-radius: 3px;
      }
      &:first-of-type {
        button {
          background-color: #ed424b;
          a {
            color: #fff;
          }
        }
      }
      &:nth-child(2) {
        button {
          color: #333;
          background-color: #fff;
          border: 1px solid #ddd;
        }
      }
    }
  }
}

.home-btn {
  padding: 15px 15px 0 0;

  .iconfont {
    font-size: 22px;
    color: #ed424b;
  }
}

.detail-intro {
  padding: 20px 0;
  font-size: 16px;
  text-indent: 2em;
  line-height: 1.6;
  border-bottom: 1px solid #ddd;

  p.show5 {
    overflow: hidden;
    text-overflow: ellipsis;
    display: -webkit-box;
    line-clamp: 5;
    -webkit-line-clamp: 5;
    -webkit-box-orient: vertical;
  }
}

.detail-tag {
  padding: 20px 0;
  border-bottom: 1px solid #ddd;

  h3 {
    font-size: 16px;
    margin-bottom: 10px;
  }

  ul li {
    float: left;
    padding: 3px 10px;
    margin-right: 10px;
    color: #333;
    border: 1px solid #ccc;
    border-radius: 5px;
  }
}

.detail-like {
  padding: 20px 0;

  h3 {
    font-size: 16px;
    margin-bottom: 20px;
  }

  .like-list {
    display: flex;

    li {
      flex: 1;
    }
  }
}
</style>
