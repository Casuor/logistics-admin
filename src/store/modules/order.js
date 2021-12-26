const order = {
    state: {
        userInfo: {},
        orderCount: 0,
    },
    mutations: {
        setUserInfo(state, userInfo) {
            state.userInfo = userInfo
        },
        setOrderCount(state, count) {
            state.orderCount = count
        }
    },
    actions: {
        setOrderCount({commit}) {
            commit('setOrderCount')
        }
    },
    modules: {},
}
export default order