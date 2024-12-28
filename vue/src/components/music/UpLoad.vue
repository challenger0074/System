<template>
  <div>
    <!-- Upload file input and button -->
    <el-form :model="form" label-width="80px" class="upload-form">
      <el-form-item label="音乐名">
        <el-input v-model="form.musicName" placeholder="音乐名称" readonly></el-input>
      </el-form-item>
      <el-form-item label="上传人">
        <el-input v-model="form.uploadUser" placeholder="上传人" readonly></el-input>
      </el-form-item>
      <el-form-item>
        <input type="file" ref="fileInput" @change="handleFileChange" />
        <el-button type="primary" @click="uploadFile">上传音乐文件</el-button>
      </el-form-item>

      <!-- Delete file input and button -->
      <el-form-item label="删除音乐文件">
        <el-input v-model="fileNameToDelete" placeholder="请输入要删除的音乐名"></el-input>
        <el-button type="danger" @click="deleteFile">删除音乐文件</el-button>
      </el-form-item>
    </el-form>

    <!-- Display file name of uploaded file -->
    <div v-if="uploadedFileName">
      <p>音乐名: {{ uploadedFileName }}</p>
    </div>
  </div>
</template>

<script setup lang="ts">
import { onMounted, ref } from 'vue';
import service from "@/api/request";
import { ElMessage } from 'element-plus';

interface UserForm {
  username: string;
}

interface Form {
  musicName: string;
  uploadUser: string;
}

// Declare refs for form data and selected file
const form = ref<Form>({
  musicName: '',
  uploadUser: ''
});

const emit = defineEmits(['uploadSuccess']);
const selectedFile = ref<File | null>(null);
const fileNameToDelete = ref<string>('');
const uploadedFileName = ref<string | null>(null);
const user = ref<UserForm>();

// Initialize the component, get user session, and set uploadUser
const init = async () => {
  console.log("init");
  const res = await service.get('entry/getSession');
  console.log("session:", res);
  user.value = res.data;
  console.log("user:", user.value?.username);

  // Set the upload user to the logged-in user's username
  if (user.value?.username) {
    form.value.uploadUser = user.value.username;
  }
}

// Handle file selection
const handleFileChange = (event: Event) => {
  const target = event.target as HTMLInputElement;
  if (target?.files?.[0]) {
    selectedFile.value = target.files[0];
    // Extract the file name and remove the .mp3 suffix
    uploadedFileName.value = selectedFile.value.name.replace(/\.mp3$/, '');
    form.value.musicName = uploadedFileName.value; // Automatically fill music name
  }
};

// Upload file and form data
const uploadFile = async (): Promise<void> => {
  if (!selectedFile.value) {
    ElMessage.error('请先选择一个文件');
    return;
  }

  // Create FormData object
  const formData = new FormData();
  formData.append('file', selectedFile.value); // Append the file to FormData
  formData.append('musicName', form.value.musicName); // Append the music name
  formData.append('uploadUser', form.value.uploadUser); // Append the upload user

  try {
    // Send POST request with the file and form data using service
    const response = await service.post('upload/music', formData, {
      headers: {
        'Content-Type': 'multipart/form-data',
      },
    });

    // Handle successful upload
    console.log('文件上传成功', response.data);
    ElMessage.success('上传成功');
    emit('uploadSuccess');
  } catch (error) {
    // Handle upload error
    console.error('上传失败', error);
    ElMessage.error('上传失败');
  }
};

// Delete file
const deleteFile = async (): Promise<void> => {
  if (!fileNameToDelete.value) {
    ElMessage.error('请输入文件名');
    return;
  }

  try {
    // Send DELETE request to delete the file by its name and uploadUser
    const response = await service.delete(`upload/music/${fileNameToDelete.value}`, {
      params: {
        uploadUser: form.value.uploadUser  // Pass the uploadUser as query parameter
      }
    });

    // Handle successful delete
    console.log('文件删除成功', response.data);
    ElMessage.success('删除成功');
  } catch (error) {
    // Handle delete error
    console.error('删除失败', error);
    ElMessage.error('删除失败');
  }
};

onMounted(() => {
  init();
});
</script>

<style scoped>
.upload-form {
  width: 500px;
  margin: 20px auto;
}
</style>
