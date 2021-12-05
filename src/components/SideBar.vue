<template>
  <div class="sidebar-container" :class="{'collapse-width': isCollapse}">
    <div class="logo" :class="{'collapse-logo': isCollapse}">
      <el-image :src="require('@/assets/logo.png')" style="width: 100%"></el-image>
    </div>
    <el-menu
        :default-active="this.$store.state.sideNavigation.editableTabsValue"
        class="el-menu-vertical"
        background-color="#22272E"
        text-color="#9da7b3"
        active-text-color="#10B9D3"
        mode="vertical"
        :collapse="isCollapse"
        unique-opened
        router>
      <el-submenu :index="menu.name" v-for="menu in side_menu" :key="menu.name">

        <template slot="title">
          <i :class="menu.icon"></i>
          <span>{{ menu.title }}</span>
        </template>

        <router-link :to="item.path" v-for="item in menu.children">
          <el-menu-item :index="item.name" @click="selectMenuItem(item)">
            <template slot="title">
              <i :class="item.icon"></i>
              <span>{{ item.title }}</span>
            </template>
          </el-menu-item>
        </router-link>

      </el-submenu>
    </el-menu>
  </div>
</template>

<script>
import {mapActions, mapState} from "vuex";

export default {
  name: "SideBar",
  computed: {
    ...mapState({
      isCollapse: (state) => state.collapse.isCollapse
    }),
    side_menu: {
      get() {
        let menus = this.$store.state.sideNavigation.menus;
        console.log("Mock MenuList:", menus)
        return this.$store.state.sideNavigation.menus;
      }
    },
  },
  methods: {
    ...mapActions(['toggleSidebar']),
    selectMenuItem(item) {
      this.$store.commit("addTab", item)
    }
  }
}
</script>

<style scoped>
.sidebar-container {
  width: 200px;
  height: 100%;
  position: fixed;
  top: 0;
  bottom: 0;
  left: 0;
  transition: width 0.28s;
  z-index: 1001;
  overflow: hidden;
}

.el-menu-vertical:not(.el-menu--collapse) {
  width: 200px;
}

.el-menu {
  border: none;
  height: 100%;
  width: 100% !important;
  overflow-y: hidden;
}

.logo {
  width: 100%;
  height: 60px;
  line-height: 60px;
  font-size: 30px;
  text-align: center;
  background-color: #22272E;
  color: #fff;
}


.collapse-width {
  width: 64px !important;
}

a {
  text-decoration: none
}
</style>
