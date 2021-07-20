import axios from 'axios'
import { getToken, removeToken } from '@/utils/auth'

const service = axios.create({
  baseURL: 'http://localhost:8080/',
  timeout: 30000// request timeout
})
service.interceptors.request.use(
  config => {
    if(getToken()){
      config.headers.common['X-Token'] = getToken()

      config.headers["X-Token"]=getToken()
    }
    return config
  },
  error => {
    return Promise.reject(error)
  }
)
service.interceptors.response.use(
  response => {
    if(response.data.code===401){
      console.log("登录失效")
      removeToken()
    }
    return response
  },
  error => {
    return Promise.reject(error)
  }
)

export default service
