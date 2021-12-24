import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import axios from "./plugins/axios";
import './plugins/element.js'
import Router from "vue-router";
import hasPermission from './common/accessControl'



const originalPush = Router.prototype.push
Router.prototype.push = function push(location) {
    return originalPush.call(this, location).catch(error => error)
}

Vue.config.productionTip = false
Vue.prototype.$axios = axios
// require('./utils/mock')

new Vue({
    router,
    store,
    render: h => h(App)
}).$mount('#app')
