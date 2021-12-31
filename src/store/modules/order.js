const order = {
    state: {
        orderCount: 0,
    },
    mutations: {
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