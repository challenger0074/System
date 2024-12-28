<template>
  <div class="upload-download-container">
    <el-upload
        class="upload-demo"
        action="file/upload"
        :on-success="handleSuccess"
        :before-upload="beforeUpload"
        :on-error="handleError"
        :file-list="fileList"
        list-type="text"
        :data="{ filename: fileName }"
        :auto-upload="true"
    >
    <el-button slot="trigger" size="small" type="primary">Select File</el-button>
    <el-button
        slot="upload"
        size="small"
        type="success"
        :loading="uploading"
        @click="uploadFiles"
    >
      Upload
    </el-button>
    <div slot="tip" class="el-upload__tip">Only files with .jpg, .png or .pdf are allowed</div>
    </el-upload>

    <el-button type="primary" @click="downloadFile">Download Sample File</el-button>
  </div>
</template>

<script setup>
import { ref } from 'vue';
import { ElUpload, ElButton, ElMessage } from 'element-plus';

// 上传文件的状态
const fileList = ref([]);
const uploading = ref(false);
const fileName = ref(''); // 用于保存上传的文件名

// 上传文件之前的检查
const beforeUpload = (file) => {
  const isValidType = ['image/jpeg', 'image/png', 'application/pdf'].includes(file.type);
  if (!isValidType) {
    ElMessage.error('Invalid file type.');
  }
  return isValidType;
};

// 上传成功处理
const handleSuccess = (response, file) => {
  ElMessage.success('File uploaded successfully.');
  fileName.value = file.name; // 保存上传的文件名
  fileList.value.push(file); // 更新文件列表
};

// 上传失败处理
const handleError = (error) => {
  ElMessage.error('File upload failed.');
};

// 处理文件上传
const uploadFiles = () => {
  if (fileList.value.length === 0) {
    ElMessage.warning('Please select a file first.');
    return;
  }
  uploading.value = true;
  // 触发上传
  fileList.value.forEach(file => {
    // 通过 el-upload 组件的上传方法上传文件
    const uploadComponent = document.querySelector('.upload-demo .el-upload');
    uploadComponent.__upload(file);
  });
};

// 下载文件
const downloadFile = () => {
  if (!fileName.value) {
    ElMessage.warning('No file available for download.');
    return;
  }
  const url = `http://localhost:3000/download/${fileName.value}`;  // 使用上传的文件名
  window.open(url, '_blank');
};
</script>

<style scoped>
.upload-download-container {
  max-width: 500px;
  margin: 20px auto;
  text-align: center;
}

.upload-demo {
  margin-bottom: 20px;
}
</style>
