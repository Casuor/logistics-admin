const Mock = require('mockjs')
const Index = require("../views");
const Random = Mock.Random

let result = {
    statusCode: 200,
    msg: '操作成功',
    data: null
}
//mock checkCode
Mock.mock('/checkCode', 'get', () => {
    result.data = {
        token: Random.string(32),
        codeImage: Random.dataImage('120x40', '12345')
    }
    return result
})

//mock login

Mock.mock('/login', 'post', () => {
    // result.statusCode = 400
    // result.msg = "验证码错误！"
    return result
})


//mock getUserInfo

Mock.mock('/main/userInfo', 'get', () => {
    result.data = {
        id: "111",
        username: "aki",
        avatar: "https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png"
    }
    return result
})


//mock logout

Mock.mock('/logout', 'post', () => {
    return result
})


//mock get_menu_authority

Mock.mock('/main/menus', 'get', () => {

    let menus = [
        {
            title: "系统管理",
            name: 'system',
            icon: "el-icon-s-operation",
            path: "",
            component: "",
            children: [
                {
                    title: "菜单管理",
                    name: "menu",
                    icon: "el-icon-menu",
                    path: "/main/menu",
                    component: "menu",
                },
                {
                    title: "用户管理",
                    name: "user",
                    icon: "el-icon-user",
                    path: "/main/user",
                    component: "user",
                },
                {
                    title: "角色管理",
                    name: "role",
                    icon: "el-icon-rank",
                    path: "/main/role",
                    component: "role",
                }
            ]
        },
        {
            title: "产品管理",
            name: 'production',
            icon: "el-icon-truck",
            path: "",
            component: "",
            children: [
                {
                    title: "产品管理",
                    name: "product",
                    icon: "el-icon-burger",
                    path: "/main/product",
                    component: "product",
                }
            ]
        },
        {
            title: "物流管理",
            name: 'logistics',
            icon: "el-icon-map-location",
            path: "",
            component: "",
            children: [
                {
                    title: "信息管理",
                    name: "info",
                    icon: "el-icon-position",
                    path: "/main/info",
                    component: "info",
                },
                {
                    title: "订单管理",
                    name: "order",
                    icon: "el-icon-shopping-cart-full",
                    path: "/main/order",
                    component: "order",
                }
            ]
        }
    ]
    let authority = []
    result.data = {
        menus: menus,
        authority: authority
    }
    return result
})