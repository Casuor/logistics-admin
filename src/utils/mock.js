const Mock = require('mockjs')
const Random = Mock.Random

let result = {
    statusCode: 200,
    msg: '操作成功',
    data: null
}


/*
* name:mock验证码
* url:/checkCode
* type:get
* return:生成一段随机的 Base64 图片编码。
* */
Mock.mock('/captcha', 'get', () => {
    result.data = {
        token: Random.string(32),
        codeImage: Random.dataImage('120x40', '12345')
    }
    return result
})

/*
* name:mock登录
* url:/login
* type:post
* return:
* */

Mock.mock('/login', 'post', () => {
    result.statusCode = 200
    // result.msg = "验证码错误！"
    return result
})

/*
* name:退出登录
* url:/logout
* type:get
* return:
* */
Mock.mock('/logout', 'post', () => {
    return result
})


/*
* name:初始化个人信息
* url:/main/userInfo
* type:get
* return: {
        id: "用户id",
        username: "用户名",
        avatar: "用户头像"
    }
* */
Mock.mock('/main/userInfo', 'get', () => {
    result.data = {
        id: "111",
        username: "aki",
        avatar: "https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png"
    }
    return result
})


//mock init Side navigation

Mock.mock('/system/sideNavigation', 'get', () => {

    let menus = [
        {
            title: "系统管理",
            name: 'system',
            icon: "el-icon-s-operation",
            children: [
                {
                    name: "menuInfo",
                    path: "/main/menuInfo",
                    component: "system/MenuInfo",
                    icon: "el-icon-menu",
                    title: "菜单管理",
                },
                {
                    title: "用户管理",
                    name: "userInfo",
                    icon: "el-icon-user",
                    path: "/main/userInfo",
                    component: "system/UserInfo",
                },
                {
                    title: "角色管理",
                    name: "roleInfo",
                    icon: "el-icon-rank",
                    path: "/main/roleInfo",
                    component: "system/RoleInfo",
                }
            ]
        },
        {
            title: "产品管理",
            name: 'production',
            icon: "el-icon-truck",
            children: [
                {
                    title: "产品列表",
                    name: "products",
                    icon: "el-icon-burger",
                    path: "/main/products",
                    component: "product/Products",
                }
            ]
        },
        {
            title: "订单管理",
            name: 'logistics',
            icon: "el-icon-s-order",
            children: [
                {
                    title: "我要去下单",
                    name: "placeOrder",
                    icon: "el-icon-eleme",
                    path: "/main/placeOrder",
                    component: "logistics/PlaceOrder",
                },
                {
                    title: "订单列表",
                    name: "orders",
                    icon: "el-icon-shopping-cart-full",
                    path: "/main/orders",
                    component: "logistics/Orders",
                }, {
                    title: "订单记录",
                    name: "orderHistory",
                    icon: "el-icon-position",
                    path: "/main/orderHistory",
                    component: "logistics/OrderHistory",
                },
            ]
        }
    ]
    let authority = [
        "system:userInfo:insert",
        "system:menuInfo:insert",
        "system:roleInfo:insert",
        "product:product:insert",
    ]
    result.data = {
        menus: menus,
        authority: authority
    }
    return result
})


//mock初始化菜单表格

Mock.mock('/main/menuList', 'get', () => {

    result.data = [
        {
            id: 1,
            name: '测试',
            authority: '测试',
            icon: '测试',
            url: '/main/menu',
            component: '/main/menu',
            type: 0,
            status: 0,
            sort: 1
        },
        {
            id: 2,
            name: '测试',
            authority: '测试',
            icon: '测试',
            url: '/main/menu',
            component: '/main/menu',
            type: 0,
            status: 0,
            sort: 1,
            children: [
                {
                    id: 21,
                    name: '测试',
                    authority: '测试',
                    icon: '测试',
                    url: '/main/menu',
                    component: '/main/menu',
                    type: 0,
                    status: 0,
                    sort: 1
                },
            ]
        }
    ]
    return result
})

Mock.mock('/system/userInfo/init', 'get', () => {
    result.data = [
        {
            userId: '1',
            userName: '李大宝',
            phoneNumber: '110',
            userRole: '超级管理员',
            signDate: '2021-11-30',
            userStatus: '启用'
        },
        {
            userId: '2',
            userName: '秦明',
            phoneNumber: '110',
            userRole: '超级管理员',
            signDate: '2021-11-30',
            userStatus: '启用'
        },
        {
            userId: '3',
            userName: '林涛',
            phoneNumber: '110',
            userRole: '超级管理员',
            signDate: '2021-11-30',
            userStatus: '禁用'
        },
        {
            userId: '1',
            userName: '李大宝',
            phoneNumber: '110',
            userRole: '超级管理员',
            signDate: '2021-11-30',
            userStatus: '启用'
        },
        {
            userId: '2',
            userName: '秦明',
            phoneNumber: '110',
            userRole: '超级管理员',
            signDate: '2021-11-30',
            userStatus: '启用'
        },
        {
            userId: '3',
            userName: '林涛',
            phoneNumber: '110',
            userRole: '超级管理员',
            signDate: '2021-11-30',
            userStatus: '禁用'
        }, {
            userId: '1',
            userName: '李大宝',
            phoneNumber: '110',
            userRole: '超级管理员',
            signDate: '2021-11-30',
            userStatus: '启用'
        },
        {
            userId: '2',
            userName: '秦明',
            phoneNumber: '110',
            userRole: '超级管理员',
            signDate: '2021-11-30',
            userStatus: '启用'
        },
        {
            userId: '3',
            userName: '林涛',
            phoneNumber: '110',
            userRole: '超级管理员',
            signDate: '2021-11-30',
            userStatus: '禁用'
        },
        {
            userId: '1',
            userName: '李大宝',
            phoneNumber: '110',
            userRole: '超级管理员',
            signDate: '2021-11-30',
            userStatus: '启用'
        },
        {
            userId: '2',
            userName: '秦明',
            phoneNumber: '110',
            userRole: '超级管理员',
            signDate: '2021-11-30',
            userStatus: '启用'
        },
        {
            userId: '3',
            userName: '林涛',
            phoneNumber: '110',
            userRole: '超级管理员',
            signDate: '2021-11-30',
            userStatus: '禁用'
        }
    ]
    return result
})

Mock.mock('/system/roleInfo/init', 'get', () => {
    result.data = [
        {
            roleName: '李大宝',
            roleId: 'admin',
            roleStatus: '启用',
            roleDescription: '超级管理员，拥有最高权限',
        },
        {
            roleName: '秦明',
            roleId: 'admin',
            roleStatus: '启用',
            roleDescription: '超级管理员，拥有最高权限',
        },
        {
            roleName: '林涛',
            roleId: 'generalUser',
            roleStatus: '启用',
            roleDescription: '普通用户',
        }
    ]
    return result
})