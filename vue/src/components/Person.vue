<template>
    <div class="person">
      <h2 ref="title1">前端{{ kk }}</h2>
      <h1>情况一：监视【ref】定义的【基本类型】数据</h1>
      <h2>当前求和为：{{sum}}</h2>
      <button @click="changeSum">点我sum+1</button>
      <div>{{ person.name }}:{{ person.age }}</div>
      <button @click="changeAge">点我age+1</button><br>
      姓：<input type="text" v-model="firstName"><br>
      名：<input type="text" v-model="lastName"><br>
      全名: <span>{{ fullName }}</span><br>
      <button @click="changeFullName">全名改为：li-si</button><br>
      <button @click="showLog">log</button>
    </div>
  </template>
  
  <script lang="ts" setup name="Person">
    import {ref,watch,watchEffect,computed} from 'vue'
    import { type PersonInter } from '@/types';
    defineProps(['kk'])
    // 数据
    let person2:PersonInter={id:'f',name:'j',age:2}
    let person3:Array<PersonInter>=[{id:'f',name:'j',age:2},
      {id:'f',name:'j',age:2},{id:'f',name:'j',age:2},{id:'f',name:'j',age:2}
    ]
    let person4=ref<PersonInter>({id:'f',name:'j',age:2})
    let title1 = ref()
    let firstName = ref('zhang')
    let lastName = ref('san')
    let sum = ref(0)
    let person = ref({
    name:'张三',
    age:10
  })
    // 方法
     // 计算属性——只读取，不修改
  /* let fullName = computed(()=>{
    return firstName.value + '-' + lastName.value
  }) */


  // 计算属性——既读取又修改
  let fullName = computed({
    // 读取
    get(){
      return firstName.value + '-' + lastName.value
    },
    // 修改
    set(val){
      console.log('有人修改了fullName',val)
      var sa = val.split('-')
      console.log(sa)
      firstName.value=sa[0]
      lastName.value = val.split('-')[1]
    }
  })
  function showLog(){
    console.log(title1.value.innerText)
  }

  function changeFullName(){
    fullName.value = 'li-si'
  } 
    function changeSum(){
      sum.value += 1
    }
    function changeAge(){
      person.value.age += 1
    }
    // 监视，情况一：监视【ref】定义的【基本类型】数据
    const stopWatch = watch(sum,(newValue,oldValue)=>{
      console.log('sum变化了',newValue,oldValue)
      if(newValue >= 10){
        stopWatch()
      }
    })
    const stopWatch2 = watch(person,(newValue,oldValue)=>{
      console.log('age变化了',newValue.age,oldValue.age)
      if(newValue.age >= 100){
        stopWatch2()
      }
    },{deep:true}   )
    //watchEffect监视多个数据
    const stopWatch3=watchEffect(
      ()=>{
        if(person.value.age<10){console.log('儿童')}
        if (person.value.age==18) {
          console.log('成年了')
        }
        console.log('sum又长大了')
      }
    )
  </script>
  <style></style>