import {useStore} from 'vuex'
export function hasPermission(permission) {
  const store = useStore();
  permission=permission.trim()
  let myPermissions = store.getters.permissions;
  return myPermissions.indexOf(permission) > -1;
}
