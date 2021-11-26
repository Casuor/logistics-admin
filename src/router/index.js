import Vue from 'vue'
import VueRouter from 'vue-router'
import Console from '../views/console.vue'
import login from "../views/login";
import Settings from '../views/settings.vue'
import Index from '../views/index'
import User from '../views/main/user'
import Role from "../views/main/role";
import Product from "../views/main/product";
import Info from "../views/main/info";
import Order from "../views/main/order";
import axios from "axios";
import store from "../store";

Vue.use(VueRouter)

const routes = [
    {
        path: '/',
        name: 'console',
        component: Console,
        children: [
            {
                path: '/main/index',
                name: 'Index',
                component: Index
            },
            // {
            //     path: '/main/user',
            //     name: 'User',
            //     component: User
            // },
            // {
            //     path: '/main/role',
            //     name: 'Role',
            //     component: Role
            // }, {
            //     path: '/main/product',
            //     name: 'Product',
            //     component: Product
            // }, {
            //     path: '/main/info',
            //     name: 'Info',
            //     component: Info
            // }, {
            //     path: '/main/order',
            //     name: 'Order',
            //     component: Order
            // },
            {
                path: '/main/settings',
                name: 'Settings',
                component: () => import('../views/settings')
            }
        ]
    },
    {
        path: '/login',
        name: 'login',
        component: () => import('../views/login')
    }
]

const router = new VueRouter({
    mode: 'history', base: process.env.BASE_URL, routes
})

router.beforeEach((to, from, next) => {
    let hasRoute = store.state.menus.hasRoute
    // console.log('hasRoute:')
    // console.log(hasRoute)
    if (!hasRoute) {
        axios.get('/main/menus', {
            headers: {
                Authorization: localStorage.getItem('token'),
            }
        }).then(res => {
            //获取menu列表
            //状态管理共享menu
            store.commit("setMenuList", res.data.data.menus)
            //获取用户权限
            store.commit("setPermissionsList", res.data.data.authority)
            //动态绑定路由
            let new_router = router.options.routes
            res.data.data.menus.forEach(menu => {
                if (menu.children) {
                    menu.children.forEach(e => {
                        //menu列表转换为routes
                        //如果component为空，返回空
                        if (!e.component) {
                            return null
                        }
                        let route = {
                            name: e.name,
                            path: e.path,
                            component: () => import('../views/main/' + e.component),
                            title: e.title,
                            icon: e.icon,
                        }
                        new_router[0].children.push(route);
                    })
                }
            })
            router.addRoutes(new_router)
            hasRoute = true
            store.commit("changeRouteStatus", hasRoute)
            // let final_router = router.options.routes
            // console.log(final_router)
        })
    }
    next()
})

//清除之前的路由

export default router