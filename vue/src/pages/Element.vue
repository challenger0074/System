<template>
      <div name="Element-plus">
        dhfkjsh jksf kjsd 
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
     </div>
</template>
<script setup lang="ts" >
import { useRoute } from 'vue-router';
import { toRefs } from 'vue';
// let {query} =toRefs(useRoute) 可以通过torefs直接结构


</script>
<style scoped></style>