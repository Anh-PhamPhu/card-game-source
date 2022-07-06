import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
      loader:     true,  // 加载状态
      footerShow: true,  // 脚部是否显示
      isJoin:     false, // 是否被邀请
      Authorization: localStorage.getItem('Authorization') ? localStorage.getItem('Authorization') : '',  // 存储token
      homeowner:     localStorage.getItem('homeowner')     ? localStorage.getItem('homeowner')     : '',  // 存储 用户ID
      userEmail:     localStorage.getItem('userEmail')     ? localStorage.getItem('userEmail')     : '',  // 存储 用户邮箱
      userName:      localStorage.getItem('userName')      ? localStorage.getItem('userName')      : ''   // 存储 用户名
  },
  mutations: {
      // 更新状态
      updateLoader (state, val) {
          state.loader = val ? true : false
      },
      updateJoin (state, val) {
          state.isJoin = val ? true : false
      },
      updateFooter (state, val) {
          state.footerShow = val ? true : false
      },
      // 修改token，并将token存入localStorage
      changeLogin (state, user) {
          state.Authorization = user.Authorization;
          state.homeowner     = user.homeowner;
          state.userEmail     = user.userEmail;
          state.userName      = user.userName;
          console.log(user.Authorization)
          localStorage.setItem('Authorization', user.Authorization);
          localStorage.setItem('homeowner',     user.homeowner);
          localStorage.setItem('userEmail',     user.userEmail);
          localStorage.setItem('userName',      user.userName);
      }
  },
  actions: {
  },
  modules: {
  }
})
