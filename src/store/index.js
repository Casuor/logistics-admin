import Vue from 'vue'
import Vuex from 'vuex'
import collapse from "@/store/modules/toggleSidebar";
import sideNavigation from "@/store/modules/sideNavigation";
import order from "@/store/modules/order";

Vue.use(Vuex)

export default new Vuex.Store({
    state: {},
    mutations: {
        SET_TOKEN: (state, token) => {
            state.token = token
            localStorage.setItem("token", token)
        }
    },
    actions: {},
    modules: {
        collapse,
        sideNavigation,
        order
    }
})
