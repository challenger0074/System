// local.js
const localEvent = {
  StorageGetter(key) {
    return JSON.parse(localStorage.getItem(key));
  },
  StorageSetter(key, val) {
    localStorage.setItem(key, JSON.stringify(val));
  },
  getFzSize(state) {
    return state.fz_size;
  },
  getBgColor(state) {
    return state.bgColor;
  },
  getBgNight(state) {
    return state.bg_night;
  },
  getListPanel(state) {
    return state.list_panel;
  },
  getCurChapter(state) {
    return state.curChapter;
  },

  activeEmployees: state => state.employees.filter(emp => emp.status === 'active'),
// 用户信息
  nickname: state => state.nickname,
  userId: state => state.userId,
  roleIds: state => state.roleIds,
  menus: state => state.menus,
  permissions: state => state.permissions,

  getPlaylist: state => state.playlist
};
export default localEvent;
