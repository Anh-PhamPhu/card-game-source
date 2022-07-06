import Vue from 'vue'
import router from './router'
import store from './store'
import api from '@/api/api'
import Element from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import VueClipboard from 'vue-clipboard2'
import echarts from 'echarts'
import App from './App'
import socketPublic from '@/utils/socket.js'
 
 




import global from './Common'
Vue.prototype.GLOBAL = global
Vue.prototype.$echarts = echarts
Vue.use(Element)
Vue.use(VueClipboard)
Vue.config.productionTip = false
//挂载
Vue.prototype.$socketPublic = socketPublic


// 路由跳转
Vue.prototype.$goRoute = function (index) {
  this.$router.push(index)
}

/* eslint-disable no-new */
new Vue({
  el: '#app',
  store,
  router,
  render: h => h(App)
})
