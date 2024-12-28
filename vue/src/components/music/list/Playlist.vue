<template>
  <div class="playlist-container">
    <ul v-if="playlist.length" class="playlist-list">
      <li
          v-for="(music, index) in playlist"
          :key="music.id"
          class="playlist-item"
          :class="{ 'selected': selectedMusicIndex === index }"
          @click="handleSelect(index)"
      >
        <span class="music-name">{{ music.musicName }}</span>
        <span class="upload-user">- {{ music.uploadUser }}</span>
      </li>
    </ul>
    <p v-else class="empty-playlist">播放列表为空。</p>
  </div>
</template>

<script setup lang="ts">
import { computed, ref } from 'vue';
import { useStore } from 'vuex';

const store = useStore();
const emit = defineEmits(['select', 'preSong', 'nextSong']);
const playlist = computed(() => store.getters.getPlaylist);
const selectedMusicIndex = ref<number | null>(null);

const handleSelect = (index: number) => {
  selectedMusicIndex.value = index;
  const music = playlist.value[index];
  emit('select', music);
};

const playPrevious = () => {
  if (selectedMusicIndex.value !== null && selectedMusicIndex.value > 0) {
    selectedMusicIndex.value--;
    const music = playlist.value[selectedMusicIndex.value];
    emit('preSong', music);
  }
};

const playNext = () => {
  if (selectedMusicIndex.value !== null && selectedMusicIndex.value < playlist.value.length - 1) {
    selectedMusicIndex.value++;
    const music = playlist.value[selectedMusicIndex.value];
    emit('nextSong', music);
  }
};

defineExpose({
  playPrevious,
  playNext
});
</script>

<style scoped>
.playlist-container {
  max-width: 800px;
  margin: 0 auto;
  padding: 20px;
 /* background-color: #f9f9f9;*/
/*  border-radius: 8px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);*/
}

.playlist-list {
  list-style-type: none;
  padding: 0;
}

.playlist-item {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 15px;
  border-bottom: 1px solid #ddd;
  cursor: pointer; /* 添加鼠标指针样式 */
}

.playlist-item:last-child {
  border-bottom: none;
}

.playlist-item.selected {
  background-color: #e0e0e0;
}

.music-name {
  font-weight: bold;
}

.upload-user {
  color: #666;
}

.empty-playlist {
  text-align: center;
  color: #aaa;
}
</style>



