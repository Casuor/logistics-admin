<template>
  <el-container>
    <SideBar></SideBar>
    <el-container class="right-container" :class="{'collapse-ml': isCollapse}">
      <el-header>
        <NavBar></NavBar>
      </el-header>
      <el-main style="padding: 0;">
        <NavTab></NavTab>
        <div class="main-container">
          <router-view/>
        </div>
      </el-main>
    </el-container>
  </el-container>
</template>

<script>

import {mapState} from "vuex";
import NavBar from '@/components/NavBar'
import NavTab from "@/components/NavTab";
import SideBar from '@/components/SideBar'

export default {
  name: 'Home',
  components: {
    NavBar,
    SideBar,
    NavTab
  },
  computed: {
    ...mapState({
      isCollapse: (state) => state.collapse.isCollapse
    })
  },
}
</script>

<style>
.el-container {
  padding: 0;
  margin: 0;
  height: 100%;
}

.el-header, .el-footer {
  background-color: #2d333b;
  color: #9da7b3;
  text-align: center;
  line-height: 60px;
  box-shadow: 0 1px 1px rgba(0, 0, 0, 0.15) !important;
}

.el-aside {
  background-color: #22272e;
  color: #9da7b3;
  text-align: center;
  line-height: 200px;
}

.el-aside::-webkit-scrollbar {
  display: none;
}


.right-container {
  width: calc(100% - 200px);
  height: 100%;
  margin-left: 200px;
  transition: 0.28s;
}

.collapse-ml {
  width: calc(100% - 64px);
  margin-left: 64px;
}

.main-container {
  padding-left: 10px;
  padding-right: 10px;
  height: calc(100% - 64px);
}
</style>