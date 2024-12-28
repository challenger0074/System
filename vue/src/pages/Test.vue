<template>
  <div class="box">
    <div class="loginForm-container">

      <h1>Login</h1>
      <!-- 头像div -->
      <div class="avatar_box">
        <img src="../assets/logo.png" >
      </div>
      <el-form
          ref="ruleFormRef"
          style="max-width: 600px"
          :model="ruleForm"
          status-icon
          :rules="rules"
          label-width="80px"
          class="demo-ruleForm login_form"
      >
        <el-form-item label="UserName" prop="username">
          <el-input  v-model="ruleForm.username" placeholder="用户名"  prefix-icon="User" suffix-icon="User"/>
        </el-form-item>
        <el-form-item label="Password" prop="pass">
          <el-input v-model="ruleForm.pass" placeholder="密码" prefix-icon="Search" type="password" autocomplete="off" />
        </el-form-item>
        <el-form-item class="btns">
          <el-button type="primary" @click="submitForm(ruleFormRef)">
            Submit
          </el-button>
          <el-button @click="resetForm(ruleFormRef)">Reset</el-button>
        </el-form-item>
      </el-form>

    </div>
  </div>
</template>

<script lang="ts" setup>
import {reactive, ref, inject, getCurrentInstance} from 'vue'
import type { FormInstance, FormRules } from 'element-plus'
import service from '@/api/request.js'
import type {toast} from "@/types";
const instance=getCurrentInstance()
const $modal = instance.appContext.config.globalProperties.$modal
const toast=inject("$modal")
const ruleFormRef = ref<FormInstance>()
const ruleForm = reactive({
  username: '',
  pass: '',
})
const checkUsername = (rule: any, value: any, callback: any) => {
  if (value === '') {
    callback(new Error('Please input the username'))
  } else {
    callback()
  }
}
const validateLength = (min, max) => {
  return {
    validator: (rule, value, callback) => {
      if (!value) {
        return callback(new Error('Please input the username'));
      }
      if (value.length < min || value.length > max) {
        return callback(new Error(`Length should be between ${min} and ${max}`));
      }
      callback();
    },
    trigger: 'blur',
  };
};
const validatePass = (rule: any, value: any, callback: any) => {
  if (value === '') {
    callback(new Error('Please input the password'))
  }else {
    callback()
  }
}

const rules = reactive<FormRules<typeof ruleForm>>({
  username: [
    { validator: checkUsername, trigger: 'blur' },//blur失去焦点时触发验证,focus是得到焦点
    /*{ min: 3, max: 5, message: 'Length should be 3 to 5', trigger: 'blur' },*/
    validateLength(3, 5),  // Using the new function here
  ],
  pass: [{ validator: validatePass, trigger: 'blur' }],
})

const submitForm = async (formEl: FormInstance | undefined) => {
  console.log("submitForm triggered with formEl:", formEl);

  // Early return if formEl is undefined
  if (!formEl) {
    console.error('Form element is undefined');
    return;
  }

  try {
    await formEl.validate(async (valid) => {
      console.log("Validation result:", valid);
      if (valid) {
        console.log('Validation succeeded, submitting data...');
        const {id} = await service.get('/users/find');

        toast.open("success")
        $modal.open("登录成功")
        console.log('Response from API:', id);
        // Optionally, provide user feedback here
      } else {
        console.log('Validation failed, errors present.');
        // Provide feedback on validation errors here
      }
    });
  } catch (validationError) {
    console.error('Validation error:', validationError);
    // Optionally, handle the validation error
  }
};
const resetForm = (formEl: FormInstance | undefined) => {
  if (!formEl) return
  formEl.resetFields()
}
</script>
<style lang="less" scoped>
.box{
  background-color:#2b4b6b;
  width: 100%;
  height:100%;
}
.loginForm-container {
  width: 450px;
  height: 300px;
  background-color: #fff;
  border-radius: 3px;// 圆角
  position: absolute;// 绝对定位
  left: 50%;
  top: 50%;
  transform: translate(-50%, -50%);// 根据自己位置 以自己为长度位移
  // 头像框
  .avatar_box {
    width: 130px;
    height: 130px;
    border: 1px solid #eee;
    border-radius: 50%; // 加圆角
    padding: 10px;
    box-shadow: 0 0 10px #ddd;// 盒子阴影
    position: absolute;
    left: 50%;
    transform: translate(-50%, -50%);
    background-color: #0ee;
    img {
      width: 100%;
      height: 100%;
      border-radius: 50%; // 加圆角
      background-color: #eee;
    }
  }
  .btns {
    display: flex;// 弹性布局
    justify-content: flex-end; // 尾部对齐
  }
  .login_form {
    position: absolute;
    bottom: 0%;
    width: 100%;
    padding: 0 10px;
    box-sizing: border-box;// 设置边框
  }
}

</style>
