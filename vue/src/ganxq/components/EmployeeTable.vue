<template>
  <el-table :data="filteredEmployees" style="width: 100%">
    <el-table-column prop="employeeId" label="员工号"></el-table-column>
    <el-table-column prop="employeeName" label="员工名字"></el-table-column>
    <el-table-column prop="department" label="部门"></el-table-column>
    <el-table-column prop="gender" label="性别"></el-table-column>
    <el-table-column prop="age" label="年龄"></el-table-column>
    <el-table-column prop="entryDate" label="入职日期"></el-table-column>
    <el-table-column prop="contact" label="联系方式"></el-table-column>
    <el-table-column prop="address" label="住址"></el-table-column>
    <el-table-column prop="notes" label="备注"></el-table-column>
    <el-table-column prop="status" label="状态"></el-table-column>
    <el-table-column label="操作">
      <template #header>
        <el-button type="primary" @click="openAddDialog">新增员工</el-button>
      </template>
      <template #default="{ row }">
        <el-button @click="openEditDialog(row)">编辑</el-button>
        <el-button type="danger" @click="deleteEmployee(row.employeeId)">删除</el-button>
      </template>
    </el-table-column>
  </el-table>
</template>

<script setup>
import { computed } from 'vue';
import { useStore } from 'vuex';

const emit = defineEmits(['openAddDialog', 'openEditDialog', 'deleteEmployee']);


const props = defineProps({
  searchCriteria: Object
});

const store = useStore();

const filteredEmployees = computed(() => {
  return store.getters.activeEmployees.filter(emp => {
        return (
            (props.searchCriteria.employeeId ? emp.employeeId.includes(props.searchCriteria.employeeId) : true) &&
            (props.searchCriteria.employeeName ? emp.employeeName.includes(props.searchCriteria.employeeName) : true) &&
            (props.searchCriteria.department ? emp.department.includes(props.searchCriteria.department) : true)
        );
      }
  );
});
/*const filteredEmployees = computed(() => {
  return store.getters.activeEmployees.filter(emp =>
      (props.searchCriteria.employeeId ? emp.employeeId.includes(props.searchCriteria.employeeId) : true) &&
      (props.searchCriteria.employeeName ? emp.employeeName.includes(props.searchCriteria.employeeName) : true) &&
      (props.searchCriteria.department ? emp.department.includes(props.searchCriteria.department) : true)   缺少返回,使用隐式返回会失败
  );
});*/
/*const filteredEmployees = computed(() => {
  const { employeeId, employeeName, department } = props.searchCriteria;
  console.log(props.searchCriteria.employeeId);
  return store.getters.activeEmployees.filter(emp => {
    return (
        (employeeId ? emp.employeeId.includes(employeeId) : true) &&
        (employeeName ? emp.employeeName.includes(employeeName) : true) &&
        (department ? emp.department.includes(department) : true)
    );
  });
});*/

const openAddDialog = () => {
  emit('openAddDialog');
};

const openEditDialog = (employee) => {
  emit('openEditDialog', employee);
};

const deleteEmployee = (employeeId) => {
  emit('deleteEmployee', employeeId);
};
</script>
<style></style>
