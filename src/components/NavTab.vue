<template>
  <el-tabs v-model="editableTabsValue"
           type="card"
           closable
           @tab-remove="removeTab"
           @tab-click="tabRouter">
    <el-tab-pane
        v-for="(item, index) in editableTabs" :key="item.name" :label="item.title" :name="item.name">
    </el-tab-pane>
  </el-tabs>
</template>
<script>


export default {
  name: "NavTab",
  data() {
    return {}
  },
  computed: {
    editableTabs: {
      get() {
        return this.$store.state.sideNavigation.editableTabs;
      },
      set(value) {
        return this.$store.state.sideNavigation.editableTabs = value;
      }
    },
    editableTabsValue: {
      get() {
        return this.$store.state.sideNavigation.editableTabsValue;
      },
      set(value) {
        return this.$store.state.sideNavigation.editableTabsValue = value;
      }
    }
  },
  methods: {
    removeTab(targetName) {
      console.log(targetName)
      if (targetName === "Index") {
        return
      }
      let tabs = this.editableTabs;
      let activeName = this.editableTabsValue;
      if (activeName === targetName) {
        tabs.forEach((tab, index) => {
          if (tab.name === targetName) {
            let nextTab = tabs[index + 1] || tabs[index - 1];
            if (nextTab) {
              activeName = nextTab.name;
            }
          }
        });
      }
      this.editableTabsValue = activeName;
      this.editableTabs = tabs.filter(tab => tab.name !== targetName);

      this.$router.push(activeName).catch(error => error)
    },
    tabRouter(target) {
      console.log("targetName:", target)
      this.$router.push(target.name).catch(error => error)
    }
  }
}
</script>

<style scoped>

</style>