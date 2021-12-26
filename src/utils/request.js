import axios from 'axios'
import { MessageBox, Message } from 'element-ui'
import store from '@/store'
import { getToken, setToken } from '@/utils/auth'
import router from '@/router'
import defaultSettings from '@/settings'

// create an axios instance
const service = axios.create({
  baseURL: "http://"+defaultSettings.baseURL+"/", // url = base url + request url
  // baseURL: "http://192.168.9.103:8080/", // url = base url + request url
  // baseURL: "http://www.zxl.link:8080/", // url = base url + request url
  // baseURL: process.env.VUE_APP_BASE_API, // url = base url + request url
  // withCredentials: true, // send cookies when cross-domain requests
  //http://www.zxl.link:8088/ac
  timeout: 50000 // request timeout
})

// request interceptor
service.interceptors.request.use(
  config => {
    // do something before request is sent

    // debugger
    if (getToken()) {
      // let each request carry token
      // ['X-Token'] is a custom headers key
      // please modify it according to the actual situation
      config.headers.common['X-Token'] = getToken()

      config.headers["X-Token"]=getToken()
    }

    // config.headers['Content-Type']='application/x-www-form-urlencoded'
    // console.log(config)
    return config
  },
  error => {
    // do something with request error
    console.log(error) // for debug
    return Promise.reject(error)
  }
)

// response interceptor
service.interceptors.response.use(
  /**
   * If you want to get http information such as headers or status
   * Please return  response => response
  */

  /**
   * Determine the request status by custom code
   * Here is just an example
   * You can also judge the status by HTTP Status Code
   */
  response => {
    const res = response.data

    // if the custom code is not 20000, it is judged as an error.
    if (res.code === 401 ) {
      Message({
        message: res.msg || '登录已过期',
        type: 'error',
        duration: 5 * 1000
      })
      setToken("")
      setTimeout(()=>{
        router.push("/login")
      },500)
      return Promise.reject(response)


    }

    else {
      if(res.code===0&&res.msg!=="success"){
        Message({
          message: res.msg,
          type: 'success',
        })
      }


      return response
    }
  },
  error => {
    // console.log(error.message) // for debug
    if("Request failed with status code 403"===error.message)
    {
      Message({
        message: "权限不足",
        type: 'error',
        duration: 5 * 1000
      })
    }
    return Promise.reject(error)
  }
)

export default service
