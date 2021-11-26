import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)
const menus = {
    state: {
        menus: [],
        authority: [],
        hasRoute: false,
        editableTabsValue: 'index',
        editableTabs: [{
            title: '首页',
            name: 'index',
        }],
    }, mutations: {
        setMenuList(state, menus) {
            state.menus = menus
        },
        setPermissionsList(state, authority) {
            state.authority = authority
        },
        changeRouteStatus(state, hasRoute) {
            state.hasRoute = hasRoute
            sessionStorage.setItem("hasRoute", hasRoute)
        },
        addTab(state, tab) {
            let index = state.editableTabs.findIndex(e => e.name === tab.name)
            console.log('index:')
            console.log(index)
            console.log(state.editableTabs)
            if (index === -1) {
                state.editableTabs.push({
                    title: tab.title,
                    name: tab.name,
                });
            }
            state.editableTabsValue = tab.name;
        },
        resetState: (state) => {
            state.menus = ""
            state.authority = ""
            state.hasRoute = ""
            state.editableTabs = [{title: '首页', name: 'index'}]
            state.editableTabsValue = 'index'
        }
    },
    actions: {},
    modules: {}
}

export default menus