import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
// 引入element-ui
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import 'element-ui/lib/theme-chalk/base.css';  //过度动画
// 引入axios
import axios from 'axios'
//引入vuex
import vuex from 'vuex'
// 导入共用组件
import global from './global/index.js'
import VueDialogs from 'vue-dialogs';
//导入全局api，会出现跨域问题
// import api from './api/request'

//注册vuex
Vue.use(vuex)
Vue.use(VueDialogs)
Vue.config.productionTip = false
// 注册element-ui
Vue.use(ElementUI)
// 注册axios
Vue.prototype.$axios = axios
//注册全局变量对象
Vue.prototype.$global = global
// Vue.prototype.$api = api

axios.defaults.baseURL = global.Url //设置全局的url地址


new Vue({
  el: '#app',
  router,
  store,
  render: h => h(App)
}).$mount('#app')


