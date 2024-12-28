<template>
  <el-form  :inline="true" :model="searchForm"  label-width="auto" style="text-align: center">
    <el-form-item label="员工号">
      <el-input v-model="searchForm.employeeId"></el-input>
    </el-form-item>
    <el-form-item label="员工名字">
      <el-input v-model="searchForm.employeeName"></el-input>
    </el-form-item>
    <el-form-item label="员工部门">
      <el-input v-model="searchForm.department"></el-input>
    </el-form-item>
    <el-form-item>
      <el-button type="primary" @click="search">查询</el-button>
    </el-form-item>
  </el-form>
</template>

<script setup>
import { ref, watch, computed, defineProps, defineEmits } from 'vue';
const  emit=defineEmits(['search']);
const searchForm = ref({
  employeeId: '',
  employeeName: '',
  department: '',
});

const search = () => {
  // Emit an event with searchForm data  这个点击一次之后因为自动响应,数据会自动传给表格
  emit('search', searchForm.value);
};
</script>
<style></style>
