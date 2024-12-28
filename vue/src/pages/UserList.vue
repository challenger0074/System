<template>
  <div>
    用户页面
    <el-breadcrumb separator="/">
      <el-breadcrumb-item :to="{ path: '/' }">homepage</el-breadcrumb-item>
      <el-breadcrumb-item>
        <a href="/">promotion management</a>
      </el-breadcrumb-item>
      <el-breadcrumb-item :to="{ path: 'welcome' }">welcome</el-breadcrumb-item>
      <el-breadcrumb-item :to="{ path: '/' }">promotion detail</el-breadcrumb-item>
    </el-breadcrumb>
    <el-card>
      <el-row :gutter="25">
        <el-col :span="10">
          <!--搜索区-->
          <el-input v-model="input" placeholder="Please input">
            <template #append>
              <el-button :icon="Search" @click="search"/>
            </template>
          </el-input>
        </el-col>
        <el-col :span="4">
          <!--添加用户-->
          <el-button type="primary" icon="Plus" @click="dialogVisible = true">add</el-button>
        </el-col>
      </el-row>
      <el-table :data="userList" style="width: 100%">
        <el-table-column prop="username" label="用户名" width="180"/>
        <el-table-column prop="password" label="密码" width="180"/>
        <el-table-column prop="email" label="邮箱" width="180"/>
        <el-table-column prop="role" label="角色" width="180"/>
        <el-table-column prop="state" label="状态" width="180">
          <template #default="scope">
            <el-switch v-model="scope.row.state"></el-switch>
            {{ scope.row.state == 1 ? '启用' : '禁用' }}
          </template>
        </el-table-column>
        <el-table-column label="操作">
          <template #default="scope">
            <!--修改-->
            <el-button type="primary" icon="Edit"  @click="showEditDialog()"></el-button>
            <!--删除-->
            <el-button type="danger" icon="Delete"  @click="deleteUser(scope.row.id)"></el-button>
            <!--权限-->
            <el-tooltip effect="dark" content="分配权限" placement="top-start" :enterable="false">
              <el-button type="warning" icon="Setting"  @click="setRole(scope.row.id)"></el-button>
            </el-tooltip>
          </template>
        </el-table-column>
      </el-table>
<!--分页功能组件-->
      <div>

        <el-pagination
            :current-page="queryInfo.pageNum"
            :page-size="queryInfo.pageSize"
            :page-sizes="[5, 10, 15, 20]"
            :size="size"
            :disabled="disabled"
            :background="background"
            layout="total, sizes, prev, pager, next, jumper"
            :total="total"
            @size-change="handleSizeChange"
            @current-change="handleCurrentChange"
        />
      </div>
    </el-card>
<!--表单新增-->
    <el-dialog v-model="dialogVisible" title="新增用户" width="30%">
      <el-form
          ref="ruleFormRef"
          :model="form"
          :rules="rules"
          :size="formSize"
          label-width="auto"
>
        <el-form-item label="用户名" prop="username">
          <el-input v-model="form.username" placeholder="请输入用户名"></el-input>
        </el-form-item>
        <el-form-item label="密码" prop="password">
          <el-input v-model="form.password" placeholder="请输入密码"></el-input>
        </el-form-item>
        <el-form-item  label="邮箱" prop="email">
          <el-input v-model="form.email" placeholder="请输入邮箱"></el-input>
        </el-form-item>
        <el-form-item label="角色" prop="role">
          <el-select v-model="form.role" placeholder="请选择角色">
            <el-option label="超级管理员" value="超级管理员"></el-option>
            <el-option label="普通用户" value="普通用户"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="状态" prop="state">
          <el-switch v-model="form.state"></el-switch>
          {{ form.state == 1 ? '启用' : '禁用' }}
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="submitForm(ruleFormRef)">
            Create
          </el-button>
          <el-button @click="resetForm(ruleFormRef)">Reset</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>
  </div>

</template>
<script setup lang="ts">
import {onMounted, reactive, ref} from "vue";
import { Search } from "@element-plus/icons-vue";
import service from "@/api/request";
import type {ComponentSize, FormRules,FormInstance} from "element-plus";

// 定义类型限定
interface RuleForm {
  username: string
  password: string
  email: string
  role: string
  state: number
}
// 定义变量
const formSize = ref<ComponentSize>('default')
const ruleFormRef = ref<FormInstance>()
const dialogVisible = ref(false);
const form = reactive<RuleForm>({
  username: '',
  password: '',
  email: '',
  role: '',
  state: 1
});
const rules = reactive<FormRules<RuleForm>>({
  username: [
    { required: true, message: '请输入用户名', trigger: 'blur' },
    { min: 3,  message: '长度至少3个字符', trigger: 'blur' }
  ],
  password: [
    { required: true, message: '请输入密码', trigger: 'blur' },
  ],
 email: [
   {validator: (rule, value, callback) => {
       // 自定义验证逻辑
       if (!value) {
         callback(new Error('请输入邮箱'));
       } else if (!/\S+@\S+\.\S+/.test(value)) {
         callback(new Error('请输入有效的邮箱地址'));
       } else {
         callback(); // 验证通过
         return; // 重要
       }
     },trigger: 'blur'}
 ]
})
const input = ref('');
const queryInfo = ref({
  query: '', // 查询参数
  pageNum: 1, // 页码
  pageSize: 5, // 每页最大数
});
const userList = ref([]); // 用户列表
const total = ref(0); // 总记录数

const size = ref('default'); // 定义分页大小
const disabled = ref(false); // 定义分页禁用状态
const background = ref(true); // 定义分页背景状态

onMounted(() => {
  console.log('用户页面加载了');
  getUserList();
});
const search=()=>{
  queryInfo.value.query = input.value;
  getUserList();
};
const getUserList = () => {
  service.get('/users/list', {
    params: queryInfo.value,
    headers: { 'Authorization': localStorage.getItem('tokenValue') }
  }).then(res => {
    console.log(res);
    userList.value = res.data || []; // 确保赋值时避免 undefined
    total.value = res.numbers || 0;   // 同上
    disabled.value = userList.value.length === 0; // 更新分页禁用状态
  }).catch(error => {
    console.error('请求失败：', error);
    // 可以在这里进行用户提示等处理
  });
};


const handleSizeChange = (newSize) => {
  queryInfo.value.pageSize = newSize;
  getUserList();
};

const handleCurrentChange = (newPage) => {
  queryInfo.value.pageNum = newPage;
  getUserList();
};

const showEditDialog = () => {};
const deleteUser = (id) => {};
const setRole = (id) => {};
const submitForm = (formEl: FormInstance | undefined) => {
  if (!formEl) return;
  // 验证表单
  formEl.validate((valid) => {
    if (valid) {
      console.log('submit!', form); // 打印当前表单数据

      // 发送 POST 请求
      service.post('/users/create', form)
          .then(response => {
            console.log('用户创建成功:', response);
            // 这里可以添加成功提示或其他操作
            dialogVisible.value = false; // 关闭对话框
            getUserList(); // 刷新用户列表
            resetForm(formEl); // 重置表单
          })
          .catch(error => {
            console.error('创建用户失败:', error);
            // 这里可以添加失败提示
          });
    } else {
      console.log('error submit!');
    }
  });
}
const resetForm = (formEl: FormInstance | undefined) => {
  if (!formEl) return
  formEl.resetFields()
}
</script>



<style scoped>
.el-breadcrumb {
  margin-bottom: 15px;
  font-size: 12px;
}
</style>
