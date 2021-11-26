<template>
  <div>
    <el-row type="flex" style="margin: 0;padding: 0;" class="row-bg" justify="space-between">
      <el-col :span="4" style="margin: 0;padding: 0;">
        <div class="toggle-btn" @click="toggle">
          <i class="el-icon-s-fold" @click="toggleSidebar" style="cursor:pointer;" ref="btn"></i>
        </div>
      </el-col>
      <el-col :span="6" style="margin: 0;padding: 0;">
        <strong>华寿物流管理系统</strong>
      </el-col>
      <el-col :span="8" style="margin: 0;padding: 0;">
        <div class="nav-avatar">
          <el-avatar :src="userInfo.avatar"></el-avatar>
        </div>
        <div class="nav-menu">
          <el-menu
              class="el-menu-demo"
              mode="horizontal"
              @select="handleSelect"
              background-color="#2d333b"
              text-color="#fff"
              active-text-color="#ffd04b">
            <el-submenu index="1">
              <template slot="title">{{ userInfo.username }}</template>
              <el-menu-item index="1-1" @click="addTab({title: '个人信息',name: 'settings'})">
                <router-link to="/main/settings">个人信息</router-link>
              </el-menu-item>
              <el-menu-item index="1-2" @click="logout">退出登录</el-menu-item>
            </el-submenu>
            <el-menu-item index="2" @click="addTab(item)">
              <router-link :to="item.path">订单管理</router-link>
            </el-menu-item>
            <el-menu-item index="3">处理中心</el-menu-item>
          </el-menu>
        </div>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import {mapState, mapActions} from 'vuex'

export default {
  name: "navbar",
  computed: {
    ...mapState({
      isCollapse: (state) => state.common.isCollapse
    })
  },
  data() {
    return {
      userInfo: {
        id: "",
        username: "",
        avatar: ""
      },
      item: {title: '订单管理', name: 'order', path: '/main/order'}
    };
  }, methods: {
    handleSelect(key, keyPath) {
      console.log(key, keyPath);
    },
    ...mapActions(['toggleSidebar']),
    toggle() {
      let flag = this.$store.state.common.isCollapse
      if (flag) {
        this.$refs.btn.className = "el-icon-s-unfold"
      } else {
        this.$refs.btn.className = "el-icon-s-fold"
      }
    },
    getUserInfo() {
      this.$axios.get('/main/userInfo').then(res => {
        this.userInfo = res.data.data
      })
    },
    logout() {
      this.$axios.post('/logout').then(res => {
        localStorage.clear()
        sessionStorage.clear()
        this.$store.commit("resetState")
        this.$router.push('/login')
      })
    },
    addTab(item) {
      this.$store.commit("addTab", item)
    }
  },
  created() {
    //初始化用户信息
    this.getUserInfo()
  }
}
</script>

<style scoped>


/*avatar样式*/
.nav-avatar {
  width: 60px;
  height: 60px;
  float: left;
  border: white;
  line-height: 60px;
  display: flex;
  position: fixed;
}

.el-avatar {
  height: 48px;
  width: 48px;
  margin-top: 6px;
}

/*去除nav下滑白线*/
.el-menu.el-menu--horizontal {
  border-bottom: 0;
}

.toggle-btn {
  float: left;
  position: relative;
  margin-left: 20px;
  font-size: 35px;
}

.nav-menu {
  margin-left: 60px;
}

/*动态修改汉堡样式*/


</style>