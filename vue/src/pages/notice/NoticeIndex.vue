<template>
  <div>
    <div class="notice-con" v-loading="loading">
      <div class="title text-c">{{ notice.noticeTitle }}</div>
      <div class="tips text-c">{{ notice.noticeSubject }}</div>
      <div class="time text-c">发布时间：{{ notice.noticeStartTime }}</div>
      <div class="notice-info" v-html="notice.noticeContent">

      </div>
      <el-button class="btn-close" @click="close">关闭</el-button>
    </div>
  </div>
</template>

<script>
import * as Notice from '@/api/modules/notice'

export default {
  name: 'VnNoticeIndex',
  data: function() {
    return {
      loading: false,
      notice: {
        noticeTitle: '',
        noticeSubject: '',
        noticeStartTime: '',
        noticeContent: ''
      }
    }
  },
  mounted() {
  },
  methods: {
    initData(id) {
      console.log("init " + id)
      this.loading = true
      Notice.queryDetail(id).then(rsp => {
        this.notice = rsp.data
      }).catch().finally(e => {
        this.loading = false
      })
    },
    close() {
      this.$emit("detail")
    }
  }
}

</script>
<style scoped lang="scss">
.notice-con {
  display: flex;
  flex-direction: column; /* 纵向排列子元素 */
  align-items: center; /* 水平居中子元素 */
  text-align: center; /* 使文本居中 */
}

@import url('@/styles/usersystem/notice.css');
</style>
