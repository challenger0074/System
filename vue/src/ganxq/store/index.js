import { createStore } from 'vuex';

export default createStore({
  state() {
    return {
      employees: [

      ],
    };
  },
  mutations: {
    ADD_EMPLOYEE(state, employee) {
      state.employees.push({ ...employee, status: 'active' });
    },
    UPDATE_EMPLOYEE(state, updatedEmployee) {
      const index = state.employees.findIndex(emp => emp.employeeId === updatedEmployee.employeeId);
      if (index !== -1) {
        // Direct mutation is fine here, no need for Vue.set in Vuex 4
        state.employees[index] = { ...updatedEmployee };
      }
    },
    DELETE_EMPLOYEE(state, employeeId) {
      const index = state.employees.findIndex(emp => emp.employeeId === employeeId);
      if (index !== -1) {
        state.employees[index] = { ...state.employees[index], status: 'deleted' };
      }
    },
  },
  actions: {
    addEmployee({ commit }, employee) {
      commit('ADD_EMPLOYEE', employee);
    },
    updateEmployee({ commit }, updatedEmployee) {
      commit('UPDATE_EMPLOYEE', updatedEmployee);
    },
    deleteEmployee({ commit }, employeeId) {
      commit('DELETE_EMPLOYEE', employeeId);
    },
  },
  getters: {
    activeEmployees: state => state.employees.filter(emp => emp.status === 'active'),
  },
});
