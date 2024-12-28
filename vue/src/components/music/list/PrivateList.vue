<template>
  <el-row :gutter="25">
    <el-col :span="10">
      <!-- Search Area -->
      <el-input v-model="input" placeholder="Please input">
        <template #append>
          <el-button :icon="Search" @click="search" />
        </template>
      </el-input>
    </el-col>
    <el-col :span="4">
      <!-- Add Song -->
      <el-button type="primary" icon="Plus" @click="dialogVisible = true">Add</el-button>
    </el-col>
  </el-row>
<!--.stop 修饰符来阻止事件冒泡  -->
  <el-table :data="musicList" style="width: 100%" @row-click="playMusic">
    <el-table-column prop="musicName" label="Music Name" />
    <el-table-column prop="uploadUser" label="Uploaded By" />
    <el-table-column label="Actions">
      <template #default="{ row }">
        <el-button @click.stop="addToPlaylist(row)" type="danger">加入歌单</el-button>
        <el-button type="danger" @click.stop="deleteMusic(row.musicName, row.uploadUser)">Delete</el-button>
      </template>
    </el-table-column>
  </el-table>

  <!-- Pagination Component -->
  <el-pagination
      :current-page="queryInfo.pageNum"
      :page-size="queryInfo.pageSize"
      :page-sizes="[5, 10, 15, 20]"
      :size="size"
      :disabled="disabled"
      :background="background"
      layout="total, sizes, prev, pager, next, jumper"
      :total="total"
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
  />

  <!-- Add Music Dialog -->
  <el-dialog v-model="dialogVisible" title="Add Music" width="30%">
    <el-card><UpLoad @uploadSuccess="fetchMusicList"/></el-card>
  </el-dialog>

  <!-- Audio Player -->
  <div v-if="selectedMusic" class="audio-player">
    <h3>{{ selectedMusic.musicName }}</h3>
    <audio controls autoplay  ref="audioPlayer">
      <source :src="`${service.defaults.baseURL}${selectedMusic.storageLocation}`" type="audio/mpeg">
      Your browser does not support the audio element.
    </audio>
  </div>
</template>

<script setup lang="ts">
import {nextTick, onMounted, reactive, ref} from 'vue';
import { Search } from '@element-plus/icons-vue';
import service from '@/api/request';
import UpLoad from "@/components/music/UpLoad.vue";
import {useStore} from "vuex"; // Assuming you have axios or similar API service

interface UserForm {
  username: string;
}

interface QueryInfo {
  query: string;
  pageNum: number;
  pageSize: number;
}

interface Music {
  id: number;
  musicName: string;
  uploadUser: string;
  storageLocation: string;
}

const user = ref<UserForm>();
const input = ref('');
const dialogVisible = ref(false);
const musicList = ref<Music[]>([]);
const total = ref(0);
const queryInfo = reactive<QueryInfo>({
  query: '', // 查询参数
  pageNum: 1, // 页码
  pageSize: 5, // 每页最大数
});
const size = ref<'default' | 'small' | 'medium'>('default');
const disabled = ref(false);
const background = ref(true);
const selectedMusic = ref<Music | null>(null);
const audioPlayer = ref<HTMLAudioElement | null>(null);
const store = useStore();

//添加进歌单
const addToPlaylist = (music) => {
  store.dispatch('addToPlaylist', music);
};


// Fetch music list on component mount
const fetchMusicList = async () => {
  try {
    const response = await service.get(`/upload/music/list/${user.value?.username}`, {
      params: queryInfo, // Adjust query params
    });
    console.log('Response:', response);
    musicList.value = response.records || []; // Update the music list  确保赋值时避免 undefined
    total.value = response.total || 0;   // 同上
    disabled.value = musicList.value.length === 0; // 更新分页禁用状态
  } catch (error) {
    console.error('Error fetching music list:', error);
  }
};

// Initialize the component, get user session, and set uploadUser
const init = async () => {
  console.log("init");
  const res = await service.get('entry/getSession');
  console.log("session:", res);
  user.value = res.data;
  console.log("user:", user.value?.username);
  fetchMusicList();
};

// Handle pagination change
const handleSizeChange = (newSize: number) => {
  queryInfo.pageSize = newSize;
  fetchMusicList();
};

const handleCurrentChange = (newPage: number) => {
  queryInfo.pageNum = newPage;
  fetchMusicList();
};

// Handle search functionality
const search = () => {
  // Implement search logic, you might want to filter based on input
  fetchMusicList();
};

// Handle delete music (implement backend delete logic)
const deleteMusic = (filename: string, uploadUser: string) => {
  service.delete(`/upload/music/${filename}.mp3`, {
    params: { uploadUser },
  }).then(() => {
    fetchMusicList(); // Re-fetch music list after deletion
  }).catch(err => {
    console.error('Error deleting music:', err);
  });
};

// Play music when a row is clicked

  const playMusic = (row: Music) => {
    selectedMusic.value = row;
    nextTick(() => { // Ensure DOM is updated before manipulating the audio element
      const player = audioPlayer.value;
      if (player) {
        player.load(); // Load the new source
        player.play().catch(error => {
          console.error('Error playing music:', error);
        });
      }
    });
  };


// Fetch music list on mount
onMounted(() => {
  init();
});
</script>

<style scoped>
.audio-player {
  margin-top: 20px;
}
</style>



