/****   request.js   ****/
// 导入axios
import axios from 'axios'
// 使用element-ui Message做消息提醒
import { Message} from 'element-ui';
//1. 创建新的axios实例，
const service = axios.create({
  // 公共接口--这里注意后面会讲
  // baseURL: process.env.BASE_API,
  baseURL: 'https://online.engagement-card.com',//测试地址
  // baseURL: 'https://dev.engagement-card.com/dev',//测试地址
   // baseURL: 'https://dev.engagement-card.com',//正式地址
  // baseURL: 'https://cardgame.unismart.dev',//演示地址
  // 超时时间 单位是ms，这里设置了3s的超时时间
  timeout: 5 * 1000
})
// 2.请求拦截器
service.interceptors.request.use(config => {
    // 发请求前做的一些处理，数据转化，配置请求头，设置token,设置loading等，根据需求去添加
    config.data = JSON.stringify(config.data) // 数据转化, 也可以使用qs转换
    config.headers = {
        'Content-Type':'application/json;charset=UTF-8' // 配置请求头
    }
    // 注意使用 token 的时候需要引入 cookie 方法或者用本地 localStorage 等方法，推荐 js-cookie
    const token = localStorage.getItem('Authorization') // 这里取 token 之前，你肯定需要先拿到 token, 存一下
    if(token){
        // config.params = {'token':token} // 如果要求携带在参数中
        // 如果要求携带在请求头中
        config.headers = {
            'Content-Type':'application/json;charset=UTF-8',
            'X-Access-Token': token
        }
    }
    return config
}, error => {
    Promise.reject(error)
})

// 3.响应拦截器
service.interceptors.response.use(response => {
    //接收到响应数据并成功后的一些共有的处理，关闭loading等
    // console.log('r',response)
    if(response.status == 200) {
        let data = response.data;
        // console.log(data)
        if(data.code != 200 && data.code != 0 && data.code != 405) {
            Message.error(data.message)
            return false
        }
        return data
    } else {
		console.log("code",response.status)
        Message.error(response.statusText)
        return false
    }
    return response
}, error => {
    // console.log('e',error.response)
    /***** 接收到异常响应的处理开始 *****/
    if (error && error.response) {
        // 1.公共错误处理
        // 2.根据响应码具体处理
        switch (error.response.status) {
            case 400:
                error.message = 'Request Error'
                break;
            case 401:
                error.message = 'Unauthorized. Please log in'
                localStorage.removeItem('Authorization');
                localStorage.removeItem('homeowner');
                localStorage.removeItem('userInfo');
                break;
            case 403:
                error.message = 'Access Refused'
                break;
            case 404:
                error.message = 'Request Error. Resource not found'
                window.location.href = "/NotFound"
                break;
            case 405:
                error.message = 'Request way not allowed'
                break;
            case 408:
                error.message = 'Request timed out'
                break;
            case 500:
                localStorage.removeItem('Authorization');
                localStorage.removeItem('homeowner');
                localStorage.removeItem('userInfo');
                window.location.href = '/#/login'
                error.message = 'Server error'
                break;
            case 501:
                error.message = 'Not implemented'
                break;
            case 502:
                error.message = 'Network error'
                break;
            case 503:
                error.message = 'Service unavailable'
                break;
            case 504:
                error.message = 'Network timed out'
                break;
            case 505:
                error.message = 'http version not supported'
                break;
            default:
                error.message = `Connection error${error.response.status}`
        }
    } else {
        // 超时处理
        if (JSON.stringify(error).includes('timeout')) {
            Message.error('Server response timed out. Please refresh this page.')
        }
        error.message = 'Server connection error'
    }
    Message.error(error.message)
    /***** 处理结束 *****/
    // 如果不需要错误处理，以上的处理过程都可省略
    return Promise.resolve(error.response)
})
//4.导入文件
export default service
