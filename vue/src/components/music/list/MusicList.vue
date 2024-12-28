<template>
  <div>
    <el-menu
        :default-active="activeIndex"
        class="el-menu-demo"
        mode="horizontal"
        :ellipsis="false"
        @select="handleSelect"
        :router=true
    >
      <el-menu-item index="0">
        <img
            style="width: 100px"
            src="../img/musicTu.png"
            alt="Element logo"
        />
      </el-menu-item>
      <el-menu-item index="private">我的上传</el-menu-item>
      <el-menu-item index="public">
        公共上传
      </el-menu-item>
    </el-menu>
    <el-card>
      <RouterView></RouterView>
    </el-card>


  </div>
</template>

<script setup lang="ts">
import { onMounted, reactive, ref } from 'vue';
import { Search } from '@element-plus/icons-vue';
import service from '@/api/request'; // Assuming you have axios or similar API service
import UpLoad from '@/components/music/UpLoad.vue';

interface UserForm {
  username: string;
}


const user = ref<UserForm>();

const img =new URL('./img/musicTu.png', import.meta.url)
const activeIndex = ref('1')
const handleSelect = (key: string, keyPath: string[]) => {
  console.log(key, keyPath)
}

// Initialize the component, get user session, and set uploadUser
const init = async () => {
  console.log("init");
  const res = await service.get('/getSession');
  console.log("session:", res);
  user.value = res.data;
  console.log("user:", user.value?.username);

}


// Fetch music list on mount
onMounted(() => {
  init();
});
</script>

<style scoped>
.el-breadcrumb {
  margin-bottom: 15px;
  font-size: 12px;
}
.el-menu--horizontal > .el-menu-item:nth-child(1) {
  margin-right: auto;
}
</style>
