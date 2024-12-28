<template>
  <el-form :model="form" :rules="rules" ref="formRef" label-width="120px" class="loginForm-form">
    <el-form-item label="Username" prop="username">
      <el-input v-model="form.username" autocomplete="off" placeholder="Enter your username" />
    </el-form-item>

    <el-form-item label="Password" prop="password">
      <el-input
          type="password"
          v-model="form.password"
          autocomplete="off"
          placeholder="Enter your password"
      />
    </el-form-item>

    <el-form-item>
      <el-checkbox v-model="form.rememberMe">Remember me</el-checkbox>
    </el-form-item>

    <el-form-item>
      <el-button type="primary" @click="handleSubmit">Login</el-button>
    </el-form-item>
  </el-form>
</template>

<script setup>
import { ref } from 'vue';
import { ElForm, ElFormItem, ElInput, ElButton, ElCheckbox } from 'element-plus';

// 使用 Element Plus 组件
const formRef = ref(null);
const form = ref({
  username: '',
  password: '',
  rememberMe: false
});

const rules = {
  username: [
    { required: true, message: 'Please input your username', trigger: 'blur' }
  ],
  password: [
    { required: true, message: 'Please input your password', trigger: 'blur' }
  ]
};

const handleSubmit = () => {
  formRef.value.validate((valid) => {
    if (valid) {
      // Handle loginForm logic here
      console.log('Login successful!', form.value);
    } else {
      console.log('Form validation failed.');
      return false;
    }
  });
};
</script>

<style scoped>
.loginForm-form {
  max-width: 400px;
  margin: 0 auto;
  padding: 20px;
}
</style>
