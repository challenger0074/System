<template>
  <div class="box">
    <div class="loginForm-container">
      <form @submit.prevent="handleLogin">
        <input type="text" v-model="username" placeholder="用户名"/>
        <input type="password" v-model="password" placeholder="密码"/>
        <button type="submit" @click="loginForm">登录</button>
      </form>
      <div style="width: 250px;height:250px">
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
            <el-input  v-model="ruleForm.username" placeholder="用户名"  prefix-icon="User" suffix-icon="User"/>
          </el-form-item>
          <el-form-item label="Password" prop="pass">
            <el-input v-model="ruleForm.pass" placeholder="密码" prefix-icon="Search" type="password" autocomplete="off" />
          </el-form-item>
          <el-form-item label="Confirm"  prop="checkPass">
            <el-input
                v-model="ruleForm.checkPass"
                type="password"
                autocomplete="off"
                placeholder="重复密码"
            />
          </el-form-item>
          <el-form-item label="Age" prop="age">
            <el-input v-model.number="ruleForm.age" />
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
  </div>
</template>

<script lang="ts" setup>
import { reactive, ref } from 'vue'
import type { FormInstance, FormRules } from 'element-plus'
import {useLoginState} from '@/stores/state.ts'
const username = ref('');
const password = ref('');
const loginForm = useLoginState().change
const handleLogin = async () => {
  // 在此处添加登录逻辑，例如发送请求到服务器
  console.log('用户名:', username.value, '密码:', password.value);
};



const ruleFormRef = ref<FormInstance>()
const ruleForm = reactive({
  username: '',
  pass: '',
  checkPass: '',
  age: '',
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

const checkAge = (rule: any, value: any, callback: any) => {
  if (!value) {
    return callback(new Error('Please input the age'))
  }
  setTimeout(() => {
    if (!Number.isInteger(value)) {
      callback(new Error('Please input digits'))
    } else {
      if (value < 18) {
        callback(new Error('Age must be greater than 18'))
      } else {
        callback()
      }
    }
  }, 1000)
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
  } else if (value !== ruleForm.pass) {
    callback(new Error("Two inputs don't match!"))
  } else {
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
  checkPass: [{ validator: validatePass2, trigger: 'blur' }],
  age: [{ validator: checkAge, trigger: 'blur' }],
})

const submitForm = (formEl: FormInstance | undefined) => {
  if (!formEl) return
  formEl.validate((valid) => {
    if (valid) {
      console.log('submit!')
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

input {
  width: 100%;
  padding: 10px;
  margin-bottom: 10px;
  border: 1px solid #ccc;
  border-radius: 3px;
}

button {
  width: 100%;
  padding: 10px;
  background-color: #007bff;
  color: #fff;
  border: none;
  border-radius: 3px;
  cursor: pointer;
}


button:hover {
  background-color: #0056b3;
}
</style>
