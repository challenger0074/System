<template>
  <div class="similar" @click="toBookDetail(bookDetail.id)">
    <div class="similar-img">
      <img :src="bookDetail.images" alt="" @error="loadImage($event)">
    </div>
    <p>{{ bookDetail.name }}</p>
  </div>
</template>

<script setup>
import {getCurrentInstance, ref, watch} from 'vue';
import axios from 'axios';
import { useStore } from 'vuex';
import { useRouter } from 'vue-router';
const instance = getCurrentInstance();
const $common = instance.appContext.config.globalProperties.$common;
const props = defineProps({
  like: {
    type: String,
    required: true
  }
});

const bookDetail = ref({});
const router = useRouter();
const store = useStore();

const getBookDetail = async (id) => {
  const res = await axios.get(`${$common.api}/booklist?id=${id}`);
  bookDetail.value = res.data;
};

const toBookDetail = (id) => {
  router.push({ path: '/bookdetail/' + id });
  document.body.scrollTop = 0;
};

const loadImage = (e) => {
  $common.defaultImage(e);
};

// Fetch book details on created
getBookDetail(props.like);

// Watch for changes in the 'like' prop
watch(() => props.like, (newVal) => {
  getBookDetail(newVal);
});
</script>

<style lang="less" scoped>
.similar {
  width: 110px;
  height: 165px;
  .similar-img {
    height: 140px;
  }
  img {
    width: 100%;
    height: 100%;
    &[src=""] {
      opacity: 0;
    }
  }
}
</style>
