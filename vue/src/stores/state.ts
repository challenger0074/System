// 引入defineStore用于创建store
import {defineStore} from 'pinia'

// 定义并暴露一个store
export const useLoginState = defineStore('loginstate',{
  // 动作
  actions:{
         change(){
          this.islogin=false
         }
  },
  // 状态
  state(){
    return {
      islogin:true
    }
  },
  // 计算
  getters:{}
})