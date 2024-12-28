<template>
  <div class="box">
    <div class="loginForm-container">

        <h1>Login</h1>
      <!-- 头像div -->
      <div class="avatar_box">
        <img src="../assets/logo.png" >
      </div>
        <el-form
            ref="loginFormRef"
            style="max-width: 600px"
            :model="loginForm"
            status-icon
            :rules="rules"
            label-width="80px"
            class="demo-loginForm login_form"
        >
          <el-form-item label="UserName" prop="username">
            <el-input  v-model="loginForm.username" placeholder="用户名"  prefix-icon="User" suffix-icon="User"/>
          </el-form-item>
          <el-form-item label="Password" prop="pass">
            <el-input v-model="loginForm.password" placeholder="密码" prefix-icon="Search" type="password" autocomplete="off" suffix-icon="Search" />
          </el-form-item>
          <el-form-item class="btns">
            <el-button type="primary" @click="submitForm(loginFormRef)">
              Submit
            </el-button>
            <el-button @click="resetForm(loginFormRef)">Reset</el-button>
            <el-button @click="register()">Regist</el-button>
          </el-form-item>
        </el-form>

    </div>
  </div>
</template>

<script lang="ts" setup>
import {reactive, ref, inject, getCurrentInstance} from 'vue'
import type { FormInstance, FormRules } from 'element-plus'
import service from '@/api/request.js'
import { useRouter } from 'vue-router';
import axios from "axios"; // Import useRouter
interface RuleForm {
  username: string
  password: string
}
interface satToken {
  code: number,
  msg: string,
  data: object
}
const toast=getCurrentInstance().appContext.config.globalProperties.$toast
const router = useRouter(); // Initialize the router
const loginFormRef = ref<FormInstance>()
const loginForm = reactive<RuleForm>({
  username: '',
  password: '',
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

const rules = reactive<FormRules<typeof loginForm>>({
  username: [
    { validator: checkUsername, trigger: 'blur' },//blur失去焦点时触发验证,focus是得到焦点
    /*{ min: 3, max: 5, message: 'Length should be 3 to 5', trigger: 'blur' },*/
    validateLength(3, 25),  // Using the new function here
  ],
  password: [{ validator: validatePass, trigger: 'blur' }],
})

const submitForm = async (formEl: FormInstance | undefined) => {
  console.log("submitForm triggered with formEl:", formEl);

  // Early return if formEl is undefined
  if (!formEl) {
    console.error('Form element is undefined');
    return;
  }


    await formEl.validate(async (valid) => {
      console.log("Validation result:", valid);
      if (valid) {
        console.log('Validation succeeded, submitting data...');
        console.log('loginForm',JSON.stringify(loginForm) )
        const user = await service.post('/entry/doLogin',JSON.stringify(loginForm),{
          headers: {
            'Content-Type': 'application/json'
          }
        });
        console.log("响应头headers"+user.headers)
        // 从响应中获取 Authorization 头
        const saData = user;
        console.log("satoken传递到前端的数据", saData)
        // 将 token 存储在 localStorage 中
        localStorage.setItem('saData', saData);
        localStorage.setItem('tokenValue', saData.data.tokenValue);
        localStorage.setItem('tokenName', saData.data.tokenName);
        console.log("toast对象",toast)
        const message = `loginForm,success!
        welcome:${user.username}`;
        const duration = 2000; // 显示2秒
        const data=toast.show(message,duration)
        //localStorage.setItem('token', JSON.stringify(user));浏览器会话结束后数据不会消失
        window.sessionStorage.setItem('user', JSON.stringify(user));
        window.sessionStorage.setItem('ms_token',user);
        window.sessionStorage.setItem('ms_username',user.username);
        console.log('Response from API:', user);
        console.log('User data:', JSON.stringify(user));
        router.push('/home/mlist/private')
        // Optionally, provide user feedback here
      } else {
        console.log('Validation failed, errors present.');
        // Provide feedback on validation errors here
      }
    });

};
const resetForm = (formEl: FormInstance | undefined) => {
  if (!formEl) return
  formEl.resetFields()
}
const  register= async ()=>{
 await service.get('/test/isLogin')
  /*router.push('register')*/
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
