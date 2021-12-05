<template>
  <div id="app">
    <router-view/>
  </div>
</template>

<script>
export default {
  name: 'app',
  watch: {
    $route(to, from) {
      if (to.path === "/main/menuInfo" || to.path === "/main/userInfo" || to.path === "/main/roleInfo" || to.path === "/main/products" || to.path === "/main/placeOrder" || to.path === "/main/orders" || to.path === "orderHistory") {
        let title = to.meta.title
        let name = to.name
        this.$store.commit('addTab', {title: title, name: name})
      }
    }
  }
}
</script>
<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  color: #2c3e50;
  height: 100%;
  margin: 0;
  padding: 0;
}

/*滚动条样式*/
::-webkit-scrollbar {
  width: 8px;
  height: 8px;
  background-color: #F1F6F7;
  border-radius: 4px;
}

/*滚动条滑块样式*/
::-webkit-scrollbar-thumb {
  background-color: #F1F6F7;
  border-radius: 4px;
}

/*鼠标悬浮*/
::-webkit-scrollbar-thumb:hover {
  background: #f9a825;
}
</style>
