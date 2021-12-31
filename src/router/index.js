import Vue from 'vue'
import VueRouter from 'vue-router'
import Home from '../views/Home.vue'
import Index from "@/views/Index";
import axios from "@/plugins/axios";
import store from '@/store/index'
import Settings from "@/views/Settings";

Vue.use(VueRouter)

const routes = [
    {
        path: '/',
        name: 'Home',
        component: Home,
        children: [
            {
                path: '/main/index',
                name: 'Index',
                component: Index,
            },
            {
                path: '/main/settings',
                name: 'Settings',
                component: Settings
            }
        ]
    },
    {
        path: '/login',
        name: 'Login',
        component: () => import('../views/Login')
    },
]

const router = new VueRouter({
    mode: 'history',
    base: process.env.BASE_URL,
    routes
})
router.beforeEach((to, from, next) => {

    let hasRoute = store.state.sideNavigation.hasRoute

    let token = localStorage.getItem("token")

    if (to.path === '/login') {
        next()

    } else if (!token) {
        next({path: '/login'})
    } else if (token && !hasRoute) {
        axios.get("/sys/menu/sideMenu", {
            headers: {
                Authorization: localStorage.getItem("token")
            }
        }).then(res => {


            // 拿到menuList
            store.commit("setMenuList", res.data.data.menus)

            // 拿到用户权限
            store.commit("setPermissionsList", res.data.data.authority)


            // 动态绑定路由
            let newRoutes = router.options.routes

            res.data.data.menus.forEach(menu => {
                if (menu.children) {
                    menu.children.forEach(e => {

                        // 转成路由
                        let route = menuToRoute(e)
                        // 路由添加到路由管理中
                        if (route) {
                            newRoutes[0].children.push(route)
                        }
                    })
                }
            })

            for (let i = 0; i < newRoutes.length; i++) {
                let newRoute = newRoutes[i]
                router.addRoute(newRoute)
            }
            // router.addRoutes(newRoutes)

            hasRoute = true
            store.commit("changeRouteStatus", hasRoute)
        })
    }
    next()
})


// 导航转成路由
const menuToRoute = (menu) => {

    if (!menu.component) {
        return null
    }

    let route = {
        name: menu.name,
        path: menu.path,
        meta: {
            icon: menu.icon,
            title: menu.title
        }
    }
    route.component = () => import('@/views/' + menu.component)

    return route
}

export default router
