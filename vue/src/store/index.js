// store.js
import { createStore } from 'vuex';
import mutations from './mutations.js';
import actions from './actions.js';
import getters from './local.js'
const state = {
  bar: false,
  font_panel: false,
  font_icon: false,
  bg_color: 1,
  bg_night: false,
  fz_size: 18,
  curChapter: 1,
  windowHeight: '',
  list_panel: false,
  curBookDetailId: 1,
  curBookContentId: 1,

  employees: [],

  nickname: "",
  userId: "",
  roleIds: [],
  menus: [],
  permissions: [],

  playlist: []
};

export default createStore({
  state,
  mutations,
  actions,
  getters,
});
