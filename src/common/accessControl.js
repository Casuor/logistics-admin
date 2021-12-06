import Vue from "vue"

Vue.mixin({
    methods: {
        hasPermission(permission) {
            let authority = this.$store.state.sideNavigation.authority
            return authority.indexOf(permission) > -1
        }
    }
})