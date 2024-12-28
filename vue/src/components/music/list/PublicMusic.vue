<template>
  <div>
    <el-card>
      <el-table :data="musicList" style="width: 100%" @row-click="playMusic">
        <el-table-column prop="musicName" label="Music Name" />
        <el-table-column prop="uploadUser" label="Uploaded By" />
        <el-table-column label="Actions">
          <template #default="{ row }">
            <el-button @click.stop="addToPlaylist(row)" type="danger">加入歌单</el-button>
            <el-button @click.stop="downloadMusic(row)">下载歌曲</el-button>
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
    </el-card>
    <!-- Audio Player -->
    <div v-if="selectedMusic" class="audio-player">
      <h3>{{ selectedMusic.musicName }}</h3>
      <audio controls autoplay ref="audioPlayer">
        <source :src="`${service.defaults.baseURL}${selectedMusic.storageLocation}`" type="audio/mpeg">
        Your browser does not support the audio element.
      </audio>
    </div>
  </div>
</template>

<script setup lang="ts">
import {nextTick, onMounted, reactive, ref} from 'vue';
import service from '@/api/request'; // Assuming you have axios or similar API service
import {useStore} from "vuex";

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
  storageLocation: string; // Ensure this property exists in your Music interface
}

const user = ref<UserForm>();
const audioPlayer = ref<HTMLAudioElement | null>(null);
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
const store = useStore();

// Fetch music list on component mount
const fetchMusicList = async () => {
  try {
    const response = await service.get(`/upload/music/list`, {
      params:  queryInfo, // Adjust query params
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

// 添加进歌单
const addToPlaylist = (music) => {
  store.dispatch('addToPlaylist', music);
};

// 下载音乐
const downloadMusic = async (music: Music) => {
  try {
    const tokenName = localStorage.getItem("tokenName");
    const tokenValue = localStorage.getItem("tokenValue");

    if (!tokenName || !tokenValue) {
      console.error('Token not found');
      return;
    }

    // 构建下载链接
    const musicId = music.id;
    const downloadUrl = `${service.defaults.baseURL}/music/download/${musicId}`;

    // 发起带token的请求
    const response = await service({
      url: downloadUrl,
      method: 'GET',
      responseType: 'blob', // 重要：指定响应类型为blob
      headers: {
        [tokenName]: tokenValue, // 使用从localStorage获取的token
      }
    });

    // 创建一个Blob对象
    const blob = new Blob([response.data], { type: 'audio/mpeg' });

    // 创建一个临时的URL来触发下载
    const url = window.URL.createObjectURL(blob);
    const link = document.createElement('a');
    link.href = url;
    link.download = music.musicName || 'unknown'; // 设置下载文件名
    link.style.display = 'none';

    // 添加到DOM中并触发点击事件
    document.body.appendChild(link);
    link.click();

    // 移除 <a> 元素和释放URL对象
    document.body.removeChild(link);
    window.URL.revokeObjectURL(url);
  } catch (error) {
    console.error('Error downloading music:', error);
  }
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
.el-breadcrumb {
  margin-bottom: 15px;
  font-size: 12px;
}
.audio-player {
  margin-top: 20px;
}
</style>



