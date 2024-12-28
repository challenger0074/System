<template>
    <el-container class="home-container">
      <el-header >
        <el-page-header :icon="Setting" @back="setting">
          <template #title>
            <span class="text-large font-600">
              设置
            </span>
          </template>
          <template #content>
            <div class="flex items-center">
              <el-avatar
                  :size="32"
                  class="mr-3"
                  :src="img"
              />
              <span class="text-sm mr-3" style="color: var(--el-text-color-regular)">
              用户:{{ user?.username}},
              </span>
              <el-tag >音乐共享平台</el-tag>
            </div>
          </template>
          <template #extra>
            <div class="flex items-center">
              <el-button>Print</el-button>
              <el-button type="primary" class="ml-2" @click="logout">注销</el-button>
            </div>
          </template>
        </el-page-header>
      </el-header>
      <el-container>
        <el-aside width="200px">
          <el-menu
              active-text-color="#ffd04b"
              background-color="#545c64"
              class="el-menu-vertical-demo"
              :default-active="activePath"
              text-color="#fff"
              @open="handleOpen"
              @close="handleClose"
              :router="true"
          >
            <el-sub-menu :index="item.path+''" v-for="item in menuList" :key="item.id">
              <template #title>
                <el-icon>
                  <!-- 动态控制图标 -->
                  <component :is="item.icon || 'more'" />
                </el-icon>
                <span>{{ item.title }}</span>
              </template>

              <el-menu-item
                  v-for="it in item.slist"
                  :key="it.id"
                  :index="'/home'+it.path"
                  @click="saveState(it.path)"
              >
                <template #title>
                  <el-icon>
                    <!-- 动态控制图标 -->
                    <component :is="it.icon || 'menu'" />
                  </el-icon>
                  <span>{{ it.title }}</span>
                </template>
              </el-menu-item>
            </el-sub-menu>
          </el-menu>
        </el-aside>
        <el-main><router-view></router-view></el-main>
      </el-container>
    </el-container>
</template>

<script lang="ts" setup>
import { ref, onMounted, inject } from 'vue'
import {
  Document,
  Menu as IconMenu,
  Location,
  Setting,
} from '@element-plus/icons-vue'
import { useRouter } from 'vue-router';
import service from "@/api/request";
import {ElMessage} from "element-plus";
interface UserForm {
  username: string;
}
const user =ref<UserForm>()
const img = new URL(`@/assets/img.png`, import.meta.url).href
const activePath = ref('/welcome');
const menuList = ref([]);
const router = useRouter();
const message = inject("$toast");
const init=async () => {
  console.log("init");
  const res = await service.get('entry/getSession');
  console.log("session:",res)
  user.value=res.data;
  console.log("user:",user.value?.username)
}
const saveState = (path: string) => {
  activePath.value = path;
  window.sessionStorage.setItem('activePath', path)
}
const setting = () => {
  console.log("setting");
}

onMounted(() => {
  init();
  getMenuList();
  message.show("成功加载", 2000);
  activePath.value=window.sessionStorage.getItem('activePath')
});

const handleOpen = (key: string, keyPath: string[]) => {
  console.log(key, keyPath);
}

const handleClose = (key: string, keyPath: string[]) => {
  console.log(key, keyPath);
}

const logout = async () => {
  try {
    // 调用后端的注销接口
    const response = await service.post('/signOut');
    console.log(response);

    // 清除 session storage
    window.sessionStorage.clear();
    //清除 local storage
    localStorage.clear();
    // 重置用户信息
    user.value = undefined;

    // 重定向到登录页面
    router.push('/login');

    // 显示注销成功的提示
    ElMessage.success("注销成功");
  } catch (error) {
    ElMessage.error("注销失败");
  }
}


const getMenuList = async () => {
  const res = await service.get('/menu/find');
  console.log(res);
  menuList.value = res.menus;
  console.log("menulist", menuList.value);
  console.log("menulist[6] slist", menuList.value[6].slist);
  console.log("menulist[6] slist id", menuList.value[6].id);
}
</script>

<style scoped>
.el-header{
  background-color:#373d41 ;
  color:#fff;
}
.el-main{
  background-color: #eaedf1;
}
.home-container{
  height: 100%;
}
.el-aside {
  background-color: #333744;
  .el-menu{
    border-right: none;/*// 对其右边框 */
  }
}
</style>
