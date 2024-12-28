import axios from 'axios'
/*import {Message} from 'element-plus'*/
import {getToken} from '@/utils/auth'
import store from '@/store/index.js'
// 创建axios实例
const service = axios.create({
  baseURL: 'http://localhost:8001', // api的base_url
  timeout: 15000                  // 请求超时时间
})
// request拦截器
service.interceptors.request.use(config => {
  /*let token = getToken();
  if (token) {
    config.headers.token = token;
  }*/
  const tokenName =localStorage.getItem("tokenName");
  const tokenValue= localStorage.getItem("tokenValue");
  const saData= localStorage.getItem("saData");
  config.headers['tokenName'] = tokenName;
  config.headers['tokenValue'] = tokenValue;
  config.headers['token']=saData;
  config.headers[tokenName]=tokenValue;
  // config.headers[tokenName]=JSON.stringify(tokenValue);
  // 可以在这里添加请求头等信息
  // 例如：config.headers['Authorization'] = 'Bearer your-token';
  return config;
}, error => {
  // Do something with request error
  console.error(error) // for debug
  Promise.reject(error)
})
// respone拦截器
service.interceptors.response.use(
  response => {
    const res = response.data;
    console.log("api接口所接收到的数据",res)
    if (res.code === '200') {
      return res.info;
    } else if (res.code === "20011") {
      /*Message({
        showClose: true,
        message: res.msg,
        type: 'error',
        duration: 500,
        onClose: () => {
          store.dispatch('FedLogOut').then(() => {
            location.reload()// 为了重新实例化vue-router对象 避免bug
          })
        }
      });*/
      return Promise.reject("未登录")
    } else {
     /* Message({
        message: res.msg,
        type: 'error',
        duration: 3 * 1000
      })*/
      return Promise.reject(res)
    }
  },
  error => {
    console.error('err' + error)// for debug
    /*Message({
      message: error.message,
      type: 'error',
      duration: 3 * 1000
    })*/
    return Promise.reject(error)
  }
)
export default service

