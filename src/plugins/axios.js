import axios from "axios";
import Element from "element-ui";
import router from "../router/index";

// axios.defaults.baseURL = "http://localhost:8081"
const request = axios.create({
    timeout: 5000, headers: {
        'Content-type': "application/json;charset=utf-8"
    }
})
//请求拦截
request.interceptors.request.use(config => {
    config.headers['Authorization'] = localStorage.getItem('token')
    return config
})

//响应拦截
request.interceptors.response.use(response => {
    let res = response.data
    if (res.statusCode === 200) {
        return response
    } else {
        Element.Message.error(res.msg ? "系统错误！" : res.msg)
        return Promise.reject(response.data.msg)
    }
}, error => {
    if (error.response.data) {
        error.message = error.response.data.msg
    }

    if (error.response.status === 401) {
        router.push('/login').then(r => {

        })
    }
    Element.Message.error(error.message)
    return Promise.reject(error)
})

export default request

