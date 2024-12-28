<template>
  <div class="header-right">
    <!--    <el-button class="btn-gray ft-12" type="primary">管理员</el-button>-->
    <el-dropdown>
      <el-badge :is-dot="isDot" class="circle-red">
        <i class="el-icon-message-solid ft-20 mr-20 color-d7d7d7"></i>
      </el-badge>
      <el-dropdown-menu slot="dropdown" class="w-450">
        <el-dropdown-item>
          <el-row type="flex" class="row-bg" justify="space-between">
            <span class="trapezoid">消息通知</span>
            <a href="/#/feedBack">更多></a>
          </el-row>
        </el-dropdown-item>
        <el-dropdown-item v-for="(item, index) in notReadList" :key="index" divided>
          <el-row type="flex" class="row-bg" justify="space-between">
            <div class="ft-13 text-l">
              <p class="color-000000">{{ item.title }}</p>
              <p class="color-8a8c93">{{ item.content }}</p>
            </div>
            <div class="col-right fr">
              <el-button class="btn-red ft-12 mr-10">{{ item.status }}</el-button>
              <div class="color-8a8c93 ft-12 fr text-r">
                <p class="lh-120">{{ item.date }}</p>
                <p class="lh-120">{{ item.time }}</p>
              </div>
            </div>
          </el-row>
        </el-dropdown-item>
      </el-dropdown-menu>
    </el-dropdown>
    <el-dropdown trigger="click">
      <span class="el-dropdown-link">{{ this.userInfo.userName}}<i class="el-icon-arrow-down el-icon--right"></i></span>
      <el-dropdown-menu slot="dropdown" >
        <el-dropdown-item disabled>{{ this.userInfo.userName }}</el-dropdown-item>
        <el-dropdown-item disabled>{{ this.userInfo.userPhone }}</el-dropdown-item>
        <el-dropdown-item divided ><a href="/#/account">账号信息</a></el-dropdown-item>
        <el-dropdown-item @click.native="logOut">安全退出</el-dropdown-item>
      </el-dropdown-menu>
    </el-dropdown>
  </div>
</template>

<script>
import * as feedback from '@/api/modules/feedback'
import * as Account from '@/api/modules/Account'
import * as Login from '@/api/modules/login'

export default {
  name: 'VnHead',
  data: function() {
    return {
      notReadList: [],
      userInfo: {
        userName: '',
        userPhone: ''
      }
    }
  },
  mounted() {
    this.getNotReadList()
    this.getUserInfo()

  },
  computed: {
    isDot() {
      // 根据 showDot 的值返回是否使用点样式
      return this.notReadList.length > 0
    }
  },
  methods: {
    getNotReadList() {
      feedback.queryNotReadList()
        .then(rspData => {
          this.notReadList = rspData.data
        })
        .catch(() => {
        })
    },
    getUserInfo() {
      Account.info().then(rspData => {
        this.userInfo.userPhone= rspData.phone
        this.userInfo.userName = rspData.username
      }).catch()
    },
    logOut() {
      Login.logout()
      this.$router.push('/login')
    }
  }
}
</script>

<style scoped lang="scss">
@import url('~@/styles/usersystem/main.css');
</style>
