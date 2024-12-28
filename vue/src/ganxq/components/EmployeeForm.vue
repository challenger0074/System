<template>
  <el-dialog :title="formTitle" @close="closef" :visible.sync="visible" width="50%" v-model="dialogVisible">
    <el-form :model="form" label-width="auto" style="max-width: 600px">
      <el-form-item label="员工号">
        <el-input v-model="form.employeeId" :disabled="isEdit"/>
      </el-form-item>
      <el-form-item label="员工名字">
        <el-input v-model="form.employeeName"/>
      </el-form-item>
      <el-form-item label="部门">
        <el-input v-model="form.department"></el-input>
      </el-form-item>
      <el-form-item label="性别">
        <el-select v-model="form.gender">
          <el-option label="男" value="male"></el-option>
          <el-option label="女" value="female"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="年龄">
        <el-input v-model:number="form.age"></el-input>
      </el-form-item>
      <el-form-item label="入职日期">
        <el-date-picker v-model="form.entryDate" value-format="YYYY/MM/DD"></el-date-picker>
      </el-form-item>
      <el-form-item label="联系方式">
        <el-input v-model="form.contact"></el-input>
      </el-form-item>
      <el-form-item label="住址">
        <el-input v-model="form.address"></el-input>
      </el-form-item>
      <el-form-item label="备注">
        <el-input v-model="form.notes"></el-input>
      </el-form-item>
    </el-form>
    <template #footer>
      <el-button @click="closef">取 消</el-button>
      <el-button type="primary" @click="submitForm">确 定</el-button>
    </template>
  </el-dialog>
</template>

<script setup>
import { ref, watch, computed, defineProps, defineEmits } from 'vue';

const props = defineProps({
  visible: Boolean,
  employee: Object,
  isEdit: Boolean
});

const dialogVisible = ref(false);

const emit = defineEmits(['update-employee', 'add-employee', 'close']);

const form = ref({ ...props.employee });

const formTitle = computed(() => props.isEdit ? '编辑员工' : '新增员工');

watch(() => props.employee, (newVal) => {
  form.value = { ...newVal };
}, { deep: true, immediate: true });

watch(() => props.visible, (newVal) => {
  dialogVisible.value=newVal;
}, { deep: true, immediate: true });

watch(() => form.value.entryDate, (newVal) => {
  console.log("日期"+newVal)
}, { deep: true, immediate: true });
const submitForm = () => {
  if (props.isEdit) {
    emit('update-employee', form.value);
  } else {
    emit('add-employee', form.value);
  }
  emit('close');
};

const closef = () => {
  /*以后注意不能直接把对象赋值为null*/
  form.value={};
  emit('close');
};
</script>
<style></style>
