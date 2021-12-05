<template>
  <div>
    <el-row type="flex" style="margin: 0;padding: 0;" class="row-bg" justify="space-between">
      <el-col :span="4" style="margin: 0;padding: 0;">
        <div class="toggle-btn" @click="toggle">
          <i class="el-icon-s-fold" @click="toggleSidebar" style="cursor:pointer;" ref="btn"></i>
        </div>
      </el-col>
      <el-col :span="6" style="margin: 0;padding: 0;">
        <strong>物流管理系统</strong>
      </el-col>
      <el-col :span="8" style="margin: 0;padding: 0;">
        <div class="nav-avatar">
          <el-avatar :src="userInfo.avatar"></el-avatar>
        </div>
        <div class="nav-menu">
          <el-menu
              mode="horizontal"
              @select="handleSelect"
              background-color="#2d333b"
              text-color="#fff"
              active-text-color="#ffd04b">
            <el-submenu index="userInfo">

              <template slot="title">{{ userInfo.username }}</template>
              <el-menu-item index="userInfoSettings" @click="addTab({title: '个人信息',name: 'settings'})">
                <i>个人信息</i>
              </el-menu-item>
              <el-menu-item index="userLogout">
                <i>退出登录</i>
              </el-menu-item>

            </el-submenu>

            <el-menu-item index="orders">
              <i>订单管理</i>
            </el-menu-item>
            <el-menu-item index="orderHistory">
              <i>
                订单记录
                <el-badge :value="99" class="item">
                </el-badge>
              </i>
            </el-menu-item>
          </el-menu>
        </div>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import {mapState, mapActions} from 'vuex'
import collapse from "@/store/modules/toggleSidebar";

export default {
  name: "NavBar",
  computed: {
    ...mapState({
      isCollapse: (state) => state.collapse.isCollapse
    })
  },
  data() {
    return {
      userInfo:
          {
            id: "",
            username: "",
            avatar: ""
          },
    };
  },
  methods: {
    handleSelect(key, keyPath) {
      console.log(key, keyPath);
      if (key === "userInfoSettings") {
        this.$router.push('/main/settings')
      } else if (key === "userLogout") {
        this.$axios.post('/logout').then(res => {
          localStorage.clear()
          sessionStorage.clear()
          this.$store.commit("resetState")
          this.$router.push('/login')
        })
      } else if (key === "orders") {
        this.$router.push('/main/orders')
        this.$store.commit("addTab", {title: '订单管理', name: 'orders', path: '/main/orders'})
      } else if (key === "orderHistory") {
        this.$router.push('/main/orderHistory')
        this.$store.commit("addTab", {title: '订单记录', name: 'orderHistory', path: '/main/orderHistory'})
      }
    },
    ...mapActions(['toggleSidebar']),
    toggle() {
      let flag = this.$store.state.collapse.isCollapse
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
  line-height: 60px;
  display: flex;
  position: fixed;
}

.el-avatar {
  height: 48px;
  width: 48px;
  margin-top: 6px;
  box-shadow: -2px 2px 6px #000,
  2px -2px 6px #000;
}

/*去除nav下滑白线*/
.el-menu.el-menu--horizontal {
  border-bottom: 0;
}


.toggle-btn {
  float: left;
  position: relative;
  font-size: 35px;
}

.nav-menu {
  margin-left: 60px;
}


</style>