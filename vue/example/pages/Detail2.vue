<template>
      <div name="Element-plus">
<!--        <a href="/loginForm/index2.html">跳转登录</a>-->
        <a href="loginForm/">跳转登录</a>
        <div>
           <el-menu
        :default-active="'1'"
        background-color="inherit"
        text-color="#fff"
               active-text-color="#569ef8">
        <a href="query-hzsq01.html">
          <el-menu-item index="1"><i class="el-icon-search"></i>合作申请进度查询</el-menu-item>
        </a>
        <a href="business-apply01.html">
          <el-menu-item index="2"><i class="el-icon-edit-outline"></i>业务推广申请</el-menu-item>
        </a>
        <a href="query-wyys01.html">
          <el-menu-item index="3"><i class="el-icon-folder-opened"></i>合作运营资料库</el-menu-item>
        </a>
        <a href="/#/feedBack">
          <el-menu-item index="4">
            <i class="el-icon-message-solid"></i>
            通知查询
          </el-menu-item>
        </a>
        <a href="query-data.html">
          <el-menu-item index="5"><i class="el-icon-s-data"></i>运营数据查询</el-menu-item>
        </a>
      </el-menu>
         </div>
        <el-menu
            :default-active="activeIndex"
            class="el-menu-demo"
            mode="horizontal"
            @select="handleSelect"
        >
          <el-menu-item index="1">Processing Center</el-menu-item>
          <el-sub-menu index="2">
            <template #title>Workspace</template>
            <el-menu-item index="2-1">item one</el-menu-item>
            <el-menu-item index="2-2">item two</el-menu-item>
            <el-menu-item index="2-3">item three</el-menu-item>
            <el-sub-menu index="2-4">
              <template #title>item four</template>
              <el-menu-item index="2-4-1">item one</el-menu-item>
              <el-menu-item index="2-4-2">item two</el-menu-item>
              <el-menu-item index="2-4-3">item three</el-menu-item>
            </el-sub-menu>
          </el-sub-menu>
          <el-menu-item index="3" disabled>Info</el-menu-item>
          <el-menu-item index="4">Orders</el-menu-item>
        </el-menu>
        <div class="h-6" />
        <el-menu
            :default-active="activeIndex2"
            class="el-menu-demo"
            mode="horizontal"
            background-color="#545c64"
            text-color="#fff"
            active-text-color="#ffd04b"
            @select="handleSelect"
        >
          <el-menu-item index="1">Processing Center</el-menu-item>
          <el-sub-menu index="2">
            <template #title>Workspace</template>
            <el-menu-item index="2-1">item one</el-menu-item>
            <el-menu-item index="2-2">item two</el-menu-item>
            <el-menu-item index="2-3">item three</el-menu-item>
            <el-sub-menu index="2-4">
              <template #title>item four</template>
              <el-menu-item index="2-4-1">item one</el-menu-item>
              <el-menu-item index="2-4-2">item two</el-menu-item>
              <el-menu-item index="2-4-3">item three</el-menu-item>
            </el-sub-menu>
          </el-sub-menu>
          <el-menu-item index="3" disabled>Info</el-menu-item>
          <el-menu-item index="4">Orders</el-menu-item>
        </el-menu>
     </div>
</template>
<script setup lang="ts" >
import { useRoute } from 'vue-router';
import { toRefs } from 'vue';
// let {query} =toRefs(useRoute) 可以通过torefs直接结构
import { ref } from 'vue'

const activeIndex = ref('1')
const activeIndex2 = ref('1')
const handleSelect = (key: string, keyPath: string[]) => {
  console.log(key, keyPath)
}

</script>
<style scoped></style>
