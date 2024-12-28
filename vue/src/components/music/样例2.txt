<template>
  <div id="body" ref="body" :style="{ backgroundImage: `url(${img})` }">
    <div class="upper-container">
      <!-- 唱片背景 -->
      <div class="record-container">
        <div class="record-bg">
          <div id="record-img" class="rotate-play" :style="{ backgroundImage: `url(${record})` }"></div>
        </div>
      </div>
      <!-- 音乐介绍 -->
      <div class="introduction-container">
        <div class="text-container">
          <div id="music-title">{{ musicTitle }}</div>
          <div class="author-container">上传用户：
            <span id="author-name">{{ author }}</span>
          </div>
        </div>
      </div>
    </div>

    <!-- 音乐播放器主要内容 -->
    <div class="audio-box">
      <div class="audio-container">
        <audio ref="audioTag" id="audioTag" @ended="onAudioEnded" @timeupdate="updateProgress" @canplay="getDuration" preload="auto"></audio>

        <!-- 进度条 -->
        <div class="a-progress" @click="seekToPosition">
          <div class="pgs-total" id="progress-total">
            <div class="pgs-play" :style="{ width: `${progressWidth}%` }"></div>
          </div>
        </div>

        <!-- 下排控制按钮 -->
        <div class="a-controls">
          <!-- 播放时长 -->
          <div class="time-container">
            <span class="played-time">{{ playedTime }}</span>&nbsp;/&nbsp;
            <span class="audio-time">{{ audioTime }}</span>
          </div>

          <!-- 中间按钮 -->
          <div class="center-button-container">
            <!-- 播放模式 -->
            <div id="playMode" ref="playMode" class="center-icon mode" @click="changePlayMode"></div>
            <!-- 上一首 -->
            <div id="skipForward" class="center-icon s-right" @click="prevSong"></div>
            <!-- 暂停按钮 -->
            <div id="playPause" ref="playPause" class="icon-play" @click="togglePlayPause"></div>
            <!-- 下一首 -->
            <div id="skipBackward" class="center-icon s-left" @click="nextSong"></div>
            <!-- 音量调节 -->
            <div id="volume" class="center-icon volume" @click="toggleMute"></div>
            <!-- 音量悬浮窗 -->
            <input type="range" id="volumn-togger" ref="volumnTogger" name="change" v-model="volume" min="0" max="100" step="1">
          </div>

          <!-- 后部按钮 -->
          <div class="bottom-button-container">
            <!-- 列表 -->
            <div id="list" class="bottom-icon list" @click="showList"></div>
            <!-- 倍速 -->
            <div id="speed" class="speed" @click="changeSpeed">{{ speedText }}</div>
            <!-- MV -->
            <div id="MV" class="bottom-icon MV" @click="openMV"></div>
          </div>
        </div>
      </div>
    </div>

    <!-- 音乐列表 -->
    <div class="close-list" ref="close_list"></div>
    <div class="music-list" id="music-list" ref="list">
      <div class="music-list-container">
        <div class="music-list-title">播放队列</div>
        <hr class="line">
        <div class="all-list">
          <playlist ref="playlistComponent" @preSong="selectMusic" @nextSong="selectMusic" @select="selectMusic"></playlist>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted, computed, watch, nextTick } from 'vue';
import Playlist from "@/components/music/list/Playlist.vue";
import service from "@/api/request";

interface Music {
  id: number;
  musicName: string;
  uploadUser: string;
  storageLocation: string;
}

// 定义响应式数据
const playlistComponent = ref<any>(null);
const audioTag = ref(new Audio());
const musicId = ref(0);
const musicTitle = ref('');
const author = ref('');
const playedTime = ref('00:00'); // 已播放时间
const currentTime = ref(0); // 当前播放时间
const audioTime = ref('00:00');
const isPlaying = ref(false);
const isMuted = ref(false);
const volume = ref(70);
const modeId = ref(1);
const speedText = ref('1.0X');
const list = ref(); // 列表
const close_list = ref(); // 关闭按钮
const playPause = ref(); // 播放暂停按钮
const duration = ref(0); // 音频时长
const volumnTogger = ref(); // 音乐滑块操作dom,未使用
const playMode = ref(); // 播放模式按钮
const selectedMusic = ref<Music | null>(null);

const getDuration = () => {
  duration.value = audioTag.value.duration;
  console.log("duration", duration.value);
};

// 默认背景
const img = ref(new URL(`./img/bg0.png`, import.meta.url).href);
const record = ref(new URL(`./img/record0.jpg`, import.meta.url).href);

// 监听 audioTag 变化，确保 duration 或 currentTime 改变时更新 progressWidth
watch(duration, (newDuration) => {
  if (newDuration > 0)
    audioTime.value = transTime(duration.value);
  console.log('audioTag的duration变化', newDuration);
});

// 计算属性
const progressWidth = computed(() => {
  if (audioTag.value && duration.value) {
    return (currentTime.value / duration.value) * 100;
  }
  return 0;
});

// 播放,暂停按钮方法
const togglePlayPause = () => {
  if (audioTag.value.paused) {
    audioTag.value.play();
    rotateRecord();
    isPlaying.value = true;
    playPause.value.classList.remove('icon-play');
    playPause.value.classList.add('icon-pause');
  } else {
    audioTag.value.pause();
    rotateRecordStop();
    isPlaying.value = false;
    playPause.value.classList.add('icon-play');
    playPause.value.classList.remove('icon-pause');
  }
};

// 更新时间回调方法,顺便获取currentTime到vue,vue内部无法监听
const updateProgress = () => {
  if (audioTag.value && duration.value) {
    playedTime.value = transTime(audioTag.value.currentTime);
    currentTime.value = audioTag.value.currentTime;
  }
};

const transTime = (value) => {
  let time = '';
  const h = Math.floor(value / 3600);
  value %= 3600;
  const m = Math.floor(value / 60);
  const s = Math.floor(value % 60);
  if (h > 0) {
    time = formatTime(`${h}:${m}:${s}`);
  } else {
    time = formatTime(`${m}:${s}`);
  }
  return time;
};

const formatTime = (value) => {
  let time = '';
  const s = value.split(':');
  for (let i = 0; i < s.length - 1; i++) {
    time += s[i].length === 1 ? `0${s[i]}` : s[i];
    time += ':';
  }
  time += s[s.length - 1].length === 1 ? `0${s[s.length - 1]}` : s[s.length - 1];
  return time;
};

// 定位进度条
const seekToPosition = (event) => {
  if (audioTag.value && !audioTag.value.paused && audioTag.value.currentTime !== 0) {
    const pgsWidth = parseFloat(window.getComputedStyle(event.target, null).width.replace('px', ''));
    const rate = event.offsetX / pgsWidth;
    audioTag.value.currentTime = audioTag.value.duration * rate;
    updateProgress();
  }
};

const showList = () => {
  list.value.classList.remove("list-card-hide");
  list.value.classList.add("list-card-show");
  list.value.style.display = "flex";
  close_list.value.style.display = "flex";
  close_list.value.addEventListener('click', closeListBoard);
};

const closeListBoard = () => {
  list.value.classList.remove("list-card-show");
  list.value.classList.add("list-card-hide");
  close_list.value.style.display = "none";
};

const selectMusic = (music: Music) => {
  selectedMusic.value = music;
  initAndPlay();
};

const initMusic = () => {
  if (!selectedMusic.value) return;

  // Set the new source
  audioTag.value.src = new URL(`${service.defaults.baseURL}${selectedMusic.value.storageLocation}`, import.meta.url).href;

  // Wait for the DOM to be updated and the new source to be loaded
  audioTag.value.load();

  // Set metadata and other properties after the load
  musicTitle.value = selectedMusic.value.musicName;
  author.value = selectedMusic.value.uploadUser;
  audioTime.value = transTime(duration.value);
  audioTag.value.currentTime = 0;
  // Update progress and rotation
  updateProgress();
  refreshRotate();
};

const initAndPlay = () => {
  initMusic();
  isPlaying.value = true;
  togglePlayPause(); // 初始化为默认播放
  rotateRecord();
};

const changePlayMode = () => {
  modeId.value = (modeId.value % 3) + 1;
  const url = new URL(`./img/mode${modeId.value}.png`, import.meta.url).href;
  console.log("模式图片url", url);
  playMode.value.style.backgroundImage = `url(${url})`;
};

const onAudioEnded = () => {
  if (modeId.value === 2) {
    nextSong();
  } else if (modeId.value === 3) {
    const oldId = musicId.value;
    while (true) {
      const newId = Math.floor(Math.random() * 4);
      if (newId !== oldId) {
        musicId.value = newId;
        break;
      }
    }
  }
  initAndPlay();
};

// 上一首
const prevSong = () => {
  if (playlistComponent.value) {
    playlistComponent.value.playPrevious();
  }
};

// 下一首
const nextSong = () => {
  if (playlistComponent.value) {
    playlistComponent.value.playNext();
  }
};

const changeSpeed = () => {
  switch (speedText.value) {
    case '1.0X':
      speedText.value = '1.5X';
      audioTag.value.playbackRate = 1.5;
      console.log("速度", audioTag.value.playbackRate);
      break;
    case '1.5X':
      speedText.value = '2.0X';
      audioTag.value.playbackRate = 2.0;
      break;
    case '2.0X':
      speedText.value = '0.5X';
      audioTag.value.playbackRate = 0.5;
      break;
    case '0.5X':
      speedText.value = '1.0X';
      audioTag.value.playbackRate = 1.0;
      break;
  }
};

const openMV = () => {
  /*const storageList = window.sessionStorage;
  storageList.setItem('musicId', musicId.value);
  const url=new URL(`./video.html`, import.meta.url).href;
  window.open(url);*/
};

const refreshRotate = () => {
  const recordImg = document.getElementById('record-img');
  recordImg.classList.add('rotate-play');
};

const rotateRecord = () => {
  const recordImg = document.getElementById('record-img');
  recordImg.style.animationPlayState = 'running';
};

const rotateRecordStop = () => {
  const recordImg = document.getElementById('record-img');
  recordImg.style.animationPlayState = 'paused';
};

watch(volume, () => {
  updateVolume();
});

const updateVolume = () => {
  audioTag.value.volume = volume.value / 100;
  console.log("更新音量", audioTag.value.volume);
};

const toggleMute = () => {
  if (volume.value === 0) {
    volume.value = 70;
  } else {
    volume.value = 0;
  }
  updateVolume();
};

// 生命周期钩子
onMounted(async () => {
  await nextTick();
  if (playlistComponent.value && playlistComponent.value.playlist.length > 0) {
    selectedMusic.value = playlistComponent.value.playlist[0];
    initAndPlay();
  }
});
</script>

<style scoped>
/* 添加你的样式 */
@import url("./css/audio.css");
</style>



