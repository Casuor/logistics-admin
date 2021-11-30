<template>
  <el-container>
    <Sidebar></Sidebar>
    <el-container class="right-container" :class="{'collapse-ml': isCollapse}">
      <el-header style="margin: 0;padding: 0;">
        <Navbar></Navbar>
      </el-header>
      <el-main>
        <Tabs></Tabs>
        <div class="main-container">
          <router-view>
          </router-view>
        </div>
      </el-main>
    </el-container>
  </el-container>
</template>

<script>
import Tabs from "../components/tabs";
import Sidebar from "../components/sidebar";
import Navbar from "../components/navbar";
import {mapState} from 'vuex';

export default {
  name: "console",
  computed: {
    ...mapState({
      isCollapse: (state) => state.common.isCollapse
    })
  },
  components: {
    Sidebar,
    Navbar,
    Tabs,
  },
  data() {
    return {};
  },
  methods: {},
  watch: {}
}
</script>

<style scoped>


/*容器样式*/
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

.el-main {
  /*color: red;*/
  /*text-align: center;*/
  padding: 0;
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
  height: calc(100% - 66px);
}
</style>