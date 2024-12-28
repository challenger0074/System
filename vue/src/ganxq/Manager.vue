<template>
  <div id="app">
    <employee-search @search="handleSearch" />
    <employee-table
        :searchCriteria="searchCriteria"
        @openAddDialog="openAddDialog"
        @openEditDialog="editEmployee"
        @deleteEmployee="deleteEmployee"
    />
    <employee-form
        :visible="showAddDialog || !!selectedEmployee"
        :employee="selectedEmployee"
        :isEdit="!!selectedEmployee"
        @add-employee="addEmployee"
        @update-employee="updateEmployee"
        @close="closeForm"
    />
  </div>
</template>

<script>
import { defineComponent, ref } from 'vue';
import { useStore } from 'vuex';
import EmployeeSearch from './components/EmployeeSearch.vue';
import EmployeeTable from './components/EmployeeTable.vue';
import EmployeeForm from './components/EmployeeForm.vue';

export default defineComponent({
  components: {
    EmployeeSearch,
    EmployeeTable,
    EmployeeForm,
  },
  setup() {
    const store = useStore();
    const searchCriteria = ref({});
    const showAddDialog = ref(false);
    const selectedEmployee = ref();

    const handleSearch = (criteria) => {
      searchCriteria.value = criteria;
    };

    const openAddDialog = () => {
      showAddDialog.value = true;
      console.log(showAddDialog.value);
    };

    const editEmployee = (employee) => {
      selectedEmployee.value = employee;
      showAddDialog.value = true;
    };

    const deleteEmployee = (employeeId) => {
      store.dispatch('deleteEmployee', employeeId);
    };

    const addEmployee = (employee) => {
      store.dispatch('addEmployee', employee);
    };

    const updateEmployee = (employee) => {
      store.dispatch('updateEmployee', employee);
    };

    const closeForm = () => {
      selectedEmployee.value = null;
      showAddDialog.value = false;
    };

    return {
      searchCriteria,
      showAddDialog,
      selectedEmployee,
      handleSearch,
      openAddDialog,
      editEmployee,
      deleteEmployee,
      addEmployee,
      updateEmployee,
      closeForm,
    };
  }
});
</script>

<style scoped>
/* 添加你的样式或删除这个空样式块 */
</style>
