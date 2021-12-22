import axios from "axios";
import Element from "element-ui";
import router from "../router/index";

axios.defaults.baseURL = "http://localhost:8090"
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
        Element.Message.error(!res.message ? "系统错误！" : res.message)
        return Promise.reject(response.data.message)
    }
}, error => {
    if (error.response.data) {
        error.message = error.response.data.message
    }

    if (error.response.data.statusCode === 401) {
        router.push('/login')
    }
    Element.Message.error(error.response.data.message, {duration: 5000})
    return Promise.reject(error)
})

export default request

