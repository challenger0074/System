<template>
  <div class="app-container">
    <div class="filter-container">
      <el-form>
        <el-form-item>
          <el-button type="success" icon="plus" v-permission="'role:add'" @click="showCreate">添加角色
          </el-button>
        </el-form-item>
      </el-form>
    </div>
    <el-table :data="list" v-loading="listLoading"  border fit
              highlight-current-row>
      <el-table-column align="center" label="序号" width="80">
        <template v-slot="scope">
          <span v-text="getIndex(scope.$index)"> </span>
        </template>
      </el-table-column>
      <el-table-column align="center" label="角色" prop="roleName" width="150"></el-table-column>
      <el-table-column align="center" label="用户">
        <template v-slot="scope">
          <div v-for="user in scope.row.users">
            <div v-text="user.nickname" style="display: inline-block;vertical-align: middle;"></div>
          </div>
        </template>
      </el-table-column>
      <el-table-column align="center" label="菜单&权限" width="420">
        <template v-slot="scope">
          <el-tag v-if="scope.row.roleName===adminName" type="success">全部</el-tag>
          <div v-else>
            <div v-for="menu in scope.row.menus" style="text-align: left">
              <span style="width: 100px;display: inline-block;text-align: right ">{{menu.menuName}}</span>
              <el-tag v-for="perm in menu.permissions" :key="perm.permissionName" v-text="perm.permissionName"
                      style="margin-right: 3px;"
                      type="primary"></el-tag>
            </div>
          </div>
        </template>
      </el-table-column>
      <el-table-column align="center" label="管理" width="220">
        <template v-slot="scope">
          <div v-if="scope.row.roleName!=='管理员'">
            <el-button type="primary" icon="edit" @click="showUpdate(scope.$index)" v-permission="'role:update'">修改
            </el-button>
            <el-button v-permission="'role:delete'" type="danger"
                       icon="delete"
                       @click="removeRole(scope.$index)">
              删除
            </el-button>
          </div>
        </template>
      </el-table-column>
    </el-table>
    <el-dialog :title="textMap[dialogStatus]" v-model="dialogFormVisible" class="small-dialog">
      <el-form class="small-space" :model="tempRole" label-position="left" label-width="100px"
               style='width: 600px; margin-left:50px;'>
        <el-form-item label="角色名称" required>
          <el-input type="text" v-model="tempRole.roleName" style="width: 250px;">
          </el-input>
        </el-form-item>
        <el-form-item label="菜单&权限" required>
          <div v-for=" (menu,_index) in allPermission" :key="menu.menuName">
            <span style="width: 100px;display: inline-block;">
              <el-button :type="isMenuNone(_index)?'':(isMenuAll(_index)?'success':'primary')" size="mini"
                         style="width:80px;"
                         @click="checkAll(_index)">{{menu.menuName}}</el-button>
            </span>
            <div style="display: inline-block;margin-left:20px;">
              <el-checkbox-group v-model="tempRole.permissions">
                <el-checkbox v-for="perm in menu.permissions" :label="perm.id" @change="checkRequired(perm,_index)"
                             :key="perm.id">
                  <span :class="{requiredPerm:perm.requiredPerm===1}">{{perm.permissionName}}</span>
                </el-checkbox>
              </el-checkbox-group>
            </div>
          </div>
          <p style="color:#848484;">说明:红色权限为对应菜单内的必选权限</p>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button v-if="dialogStatus==='create'" type="success" @click="createRole">创 建</el-button>
        <el-button type="primary" v-else @click="updateRole">修 改</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script setup>
import {ref, onMounted, inject} from 'vue';
import service from "@/utils/api.js";
    const message = inject("$toast");
    const list = ref([]); // 表格的数据
    const allPermission = ref([]);
    const listLoading = ref(false); // 数据加载等待动画
    const dialogStatus = ref('create');
    const dialogFormVisible = ref(false);
    const tempRole = ref({
      roleName: '',
      roleId: '',
      permissions: [],
    });
    const adminName = ref('管理员');
    const textMap = {
      update: '编辑',
      create: '新建角色',
    };

    onMounted(() => {
      getList();
      getAllPermisson();
     });

    const getAllPermisson = async () => {
      // 查询所有权限
      const data = await service({
        url: "/permissions/listAllPermission",
        method: "get"
      });
      allPermission.value = data.list;
      console.log("allPermission:", allPermission.value)
    };

    const getList = async () => {
      // 查询列表
      listLoading.value = true;
      const data = await service({
        url: "/roles/listRole",
        method: "get"
      });
      listLoading.value = false;
      list.value = data.list;
    };

    const getIndex = (index) => index + 1; // 表格序号

    const showCreate = () => {
      tempRole.value = { roleName: '', roleId: '', permissions: [] };
      dialogStatus.value = "create";
      dialogFormVisible.value = true;
    };

    const showUpdate = (index) => {
      const role = list.value[index];
      tempRole.value.roleName = role.roleName;
      tempRole.value.roleId = role.roleId;
      tempRole.value.permissions = role.menus.flatMap(menu => menu.permissions.map(perm => perm.permissionId));
      dialogStatus.value = "update";
      dialogFormVisible.value = true;
    };

    const createRole = async () => {
      if (!checkRoleNameUnique()) return;
      if (!checkPermissionNum()) return;

      // 添加新角色
      await service({
        url: "/roles/addRole",
        method: "post",
        data: tempRole.value
      });
      await getList();
      dialogFormVisible.value = false;
    };

    const updateRole = async () => {
      if (!checkRoleNameUnique(tempRole.value.roleId)) return;
      if (!checkPermissionNum()) return;

      // 修改角色
      await service({
        url: "/roles/updateRole",
        method: "post",
        data: tempRole.value
      });
      await getList();
      dialogFormVisible.value = false;
    };

    const checkPermissionNum = () => {
      if (tempRole.value.permissions.length === 0) {
        message.show("请至少选择一种权限");
        return false;
      }
      return true;
    };

    const checkRoleNameUnique = (roleId) => {
      const roleName = tempRole.value.roleName;
      if (!roleName) {
        message.show("请填写角色名称");
        return false;
      }
      const roles = list.value;
      for (let j = 0; j < roles.length; j++) {
        if (roles[j].roleName === roleName && (!roleId || roles[j].roleId !== roleId)) {
          message.show("角色名称已存在");
          return false;
        }
      }
      return true;
    };

    const removeRole = async (index) => {
      const role = list.value[index];
      await service({
        url: "/roles/deleteRole",
        method: "post",
        data: { roleId: role.roleId }
      });
      list.value.splice(index, 1);
    };

    const isMenuNone = (index) => {
      const menu = allPermission.value[index].permissions;
      return menu.every(permission => !tempRole.value.permissions.includes(permission.id));
    };

    const isMenuAll = (index) => {
      const menu = allPermission.value[index].permissions;
      return menu.every(permission => tempRole.value.permissions.includes(permission.id));
    };

    const checkAll = (index) => {
      if (isMenuAll(index)) {
        noPerm(index);
      } else {
        allPerm(index);
      }
    };

    const allPerm = (index) => {
      const menu = allPermission.value[index].permissions;
      menu.forEach(perm => addUnique(perm.id, tempRole.value.permissions));
    };

    const noPerm = (index) => {
      const menu = allPermission.value[index].permissions;
      menu.forEach(perm => {
        const idIndex = tempRole.value.permissions.indexOf(perm.id);
        if (idIndex > -1) {
          tempRole.value.permissions.splice(idIndex, 1);
        }
      });
    };

    const addUnique = (val, arr) => {
      if (!arr.includes(val)) {
        arr.push(val);
      }
    };

    const checkRequired = (_perm, _index) => {
      const permId = _perm.id;
      if (tempRole.value.permissions.includes(permId)) {
        makeReuqiredPermissionChecked(_index);
      } else {
        if (_perm.requiredPerm === 1) {
          noPerm(_index);
        }
      }
    };

    const makeReuqiredPermissionChecked = (_index) => {
      const menu = allPermission.value[_index].permissions;
      menu.forEach(perm => {
        if (perm.requiredPerm === 1) {
          addUnique(perm.id, tempRole.value.permissions);
        }
      });
    };






</script>

<style scoped>
.requiredPerm {
  color: #ff0e13;
}
</style>
