const sideNavigation = {
    state: {
        menus: [],
        authority: [],

        hasRoute: false,

        editableTabsValue: 'Index',
        editableTabs: [{
            title: '首页',
            name: 'Index',
        }],
    },
    mutations: {
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
            state.hasRoute = false
            state.editableTabsValue = 'Index'
            state.editableTabs = [{
                title: '首页',
                name: 'Index'
            }]
        }
    },
    actions: {},
    modules: {}
}

export default sideNavigation