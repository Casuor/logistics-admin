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

            <el-menu-item index="placeOrder">
              <i>我要去下单</i>
            </el-menu-item>
            <el-menu-item index="orderHistory">
              <i>
                未处理订单
                <el-badge :value="count" class="item">
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

export default {
  name: "NavBar",
  computed: {
    ...mapState({
      isCollapse: (state) => state.collapse.isCollapse,
    }),
    count: {
      get() {
        return this.$store.state.order.orderCount;
      },
      set(value) {
        this.$store.state.order.orderCount = value;
      }
    }
  },
  data() {
    return {
      userInfo: {}
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
      } else if (key === "placeOrder") {
        this.$router.push('/main/placeOrder')
        this.$store.commit("addTab", {title: '我要去下单', name: 'orders', path: '/main/placeOrder'})
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
      this.$axios.get('/sys/userInfo').then(res => {
        this.userInfo = res.data.data
        console.log("userInfo",res.data.data)
        localStorage.setItem("id", res.data.data.id);
        localStorage.setItem("username", res.data.data.username);
      })
    },
    getOrderCount() {
      this.$axios.get('/sys/order/count').then(res => {
        this.$store.commit("setOrderCount", res.data.data.length)
        this.count = res.data.data.length
      })
    }
  },
  created() {
    //初始化用户信息
    this.getUserInfo()
    this.getOrderCount()
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