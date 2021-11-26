<template>
  <div id="app">
    <router-view></router-view>
  </div>
</template>

<script>


export default {
  name: 'app',
  watch: {
    $route(to, from) {
      if (to.path !== '/login' || to.name !== null) {
        // this.$store.commit('addTab', {name: to.name, title: to.title})
      }
    }
  },
  created() {
  },
  methods: {}
}
</script>

<style>
#app {
  font-family: 'Avenir', Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  /*text-align: center;*/
  color: #2c3e50;
  height: 100%;
  margin: 0;
  padding: 0;
}

::-webkit-scrollbar {
  width: 8px;
  height: 8px;
  background-color: #F1F6F7;
  border-radius: 12px;
}

/*滚动条的滑块*/
::-webkit-scrollbar-thumb {
  background-color: #F1F6F7;
  border-radius: 8px;
}

::-webkit-scrollbar-thumb:hover {
  background:#ef9c2a;
  cursor: pointer;
}

</style>
