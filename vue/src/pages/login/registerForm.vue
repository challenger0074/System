<template>
  <div class="box">
    <div class="loginForm-container">
      <el-form
          ref="ruleFormRef"
          style="max-width: 600px"
          :model="ruleForm"
          status-icon
          :rules="rules"
          label-width="auto"
          class="demo-ruleForm"
      >
        <el-form-item label="UserName" prop="username">
          <el-input v-model="ruleForm.username" placeholder="用户名" prefix-icon="User" />
        </el-form-item>
        <el-form-item label="Password" prop="password">
          <el-input v-model="ruleForm.password" placeholder="密码" prefix-icon="Search" type="password" autocomplete="off" />
        </el-form-item>
        <el-form-item label="Confirm" prop="checkPass">
          <el-input
              v-model="ruleForm.checkPass"
              type="password"
              autocomplete="off"
              placeholder="重复密码"
          />
        </el-form-item>
        <el-form-item label="Email" prop="email">
          <el-input v-model="ruleForm.email" placeholder="电子邮箱" />
        </el-form-item>
        <el-form-item>
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
import { reactive, ref } from 'vue'
import type { FormInstance, FormRules } from 'element-plus'
import service from "@/api/request";

const ruleFormRef = ref<FormInstance>()
const ruleForm = reactive({
  username: '',
  password: '',
  checkPass: '',
  email: '', //  email
  role: '普通用户',//默认普通用户
  state: 1, //默认为1启用
})

const checkUsername = (rule: any, value: any, callback: any) => {
  if (value === '') {
    callback(new Error('Please input the username'))
  } else {
    callback()
  }
}

const validateLength = (min: number, max: number) => {
  return {
    validator: (rule: any, value: any, callback: any) => {
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

// New validation for email
const validateEmail = (rule: any, value: any, callback: any) => {
  const emailPattern = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
  if (value === '') {
    callback(new Error('Please input the email address'));
  } else if (!emailPattern.test(value)) {
    callback(new Error('Please enter a valid email address'));
  } else {
    callback();
  }
}

const validatePass = (rule: any, value: any, callback: any) => {
  if (value === '') {
    callback(new Error('Please input the password'))
  } else {
    if (ruleForm.checkPass !== '') {
      if (!ruleFormRef.value) return
      ruleFormRef.value.validateField('checkPass')
    }
    callback()
  }
}
const validatePass2 = (rule: any, value: any, callback: any) => {
  if (value === '') {
    callback(new Error('Please input the password again'))
  } else if (value !== ruleForm.password) {
    callback(new Error("Two inputs don't match!"))
  } else {
    callback()
  }
}

const rules = reactive<FormRules>({
  username: [
    { validator: checkUsername, trigger: 'blur' },
    validateLength(3, 15),
  ],
  password: [{ validator: validatePass, trigger: 'blur' }],
  checkPass: [{ validator: validatePass2, trigger: 'blur' }],
  email: [{ validator: validateEmail, trigger: 'blur' }], // Changed rule for email
})

const submitForm = (formEl: FormInstance | undefined) => {
  if (!formEl) return
  formEl.validate((valid) => {
    if (valid) {
      console.log('submit!')
      service.post('entry/register', ruleForm).then(response => {
        console.log('Registration successful:', response)
      }).catch(error => {
        console.error('Error during registration:', error)
      })
    } else {
      console.log('error submit!')
    }
  })
}

const resetForm = (formEl: FormInstance | undefined) => {
  if (!formEl) return
  formEl.resetFields()
}
</script>

<style scoped>
.box{
  background-color:#2b4b6b;
  height:100%;
}
.loginForm-container {
  width: 300px;
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  padding: 20px;
  border: 1px solid #ccc;
  border-radius: 5px;
  background-color: #f8f9fa;
}
</style>



