// actions.js
import * as types from './mutations-types.js';

export default {
  toggleBar({ commit }) {
    commit(types.TOGGLE_BAR);
  },
  showListPanel({ commit }) {
    commit(types.SHOW_LIST_PANEL);
  },
  showFontPanel({ commit }) {
    commit(types.SHOW_FONT_PANEL);
  },
  fzSizeAdd({ commit }) {
    commit(types.FZ_SIZE_ADD);
  },
  fzSizeSub({ commit }) {
    commit(types.FZ_SIZE_SUB);
  },
  switchNight({ commit }) {
    commit(types.SWITCH_NIGHT);
  },
  prevChapter({ commit }) {
    commit(types.PREV_CHAPTER);
  },
  nextChapter({ commit }, maxChapter) {
    commit(types.NEXT_CHAPTER, maxChapter);
  },
  chooseBook({ commit }, id) {
    commit(types.CHOOSE_BOOK, id);
  },
  showBookDetail({ commit }, obj) {
    commit(types.SHOW_BOOK_DETAIL, obj);
  },
  curChapter({ commit }, num) {
    commit(types.CUR_CHAPTER, num);
  },




  addEmployee({ commit }, employee) {
    commit('ADD_EMPLOYEE', employee);
  },
  updateEmployee({ commit }, updatedEmployee) {
    commit('UPDATE_EMPLOYEE', updatedEmployee);
  },
  deleteEmployee({ commit }, employeeId) {
    commit('DELETE_EMPLOYEE', employeeId);
  },

  addToPlaylist({ commit }, music) {
    commit('ADD_TO_PLAYLIST', music);
  }
};
