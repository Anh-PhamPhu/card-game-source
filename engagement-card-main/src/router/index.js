import Vue from 'vue'
import Router from 'vue-router'
// 主体
import Home from '../App.vue'
import Login from '../views/index/Login.vue'
import Register from '../views/index/Register.vue'
import My from '../views/index/My.vue'
import Info from '../views/index/Info.vue'
import Workspace from '../views/index/Workspace.vue'
import Waiting from '../views/index/Waiting.vue'
import Rooms from '../views/room/Index.vue'
import RoomInfo from '../views/room/Info.vue'
import Logs from '../views/room/Logs.vue'
import Plan from '../views/pay/Index.vue'
import Game from '../views/game/Index.vue'
import Game1 from '../views/game/Index1.vue'
import Game2 from '../views/game/Index2.vue'
import Game3 from '../views/game/Index3.vue'
import GameResult from '../views/game/Result.vue'
import Seturl from '../views/pay/Seturl.vue' //添加url
import Choose from '../views/pay/Choose.vue' //选择添加url
import Feedback from '../views/feedback/Feedback.vue' //提交问题
import Analysis from '../views/game/analysis.vue' //雷达图分析
import Resu from '../views/room/resu.vue'
Vue.use(Router)

const router = new Router({
  routes: [
    { path: '/', redirect: '/my' },
    { path: '/login',       name: 'login',     component: Login },
    { path: '/register',    name: 'register',  component: Register },
    { path: '/my',          name: 'my',        component: My },
    { path: '/info',        name: 'userinfo',  component: Info },
    { path: '/workspace',   name: 'workspace', component: Workspace },
    { path: '/waiting',     name: 'waiting',   component: Waiting },
    { path: '/room/index',  name: 'invite',    component: Rooms },
    { path: '/room/info',   name: 'room',      component: RoomInfo },
    { path: '/room/logs',   name: 'logs',      component: Logs },
    { path: '/pay/index',   name: 'plan',      component: Plan },
    { path: '/pay/seturl',  name: 'seturl',    component: Seturl },
    { path: '/game/index',  name: 'game',      component: Game },
    { path: '/game/result', name: 'gameresult',component: GameResult },
    { path: '/pay/choose',  name: 'choose',    component: Choose },
    { path: '/feedback/feedback',  name: 'feedback',    component: Feedback },
    { path: '/game/index2',  name: 'game2',    component: Game2 },
    { path: '/game/index3',  name: 'game3',    component: Game3 },
    { path: '/game/index1',  name: 'game1',    component: Game1 },
    { path: '/game/analysis',  name: 'analysis',    component: Analysis },
	{ path: '/room/resu',  name: 'resu',    component: Resu }
  ]
})

// 导航守卫
// 使用 router.beforeEach 注册一个全局前置守卫，判断用户是否登陆
router.beforeEach((to, from, next) => {
    if (to.path === '/login' || to.path === '/register') {
        localStorage.removeItem('Authorization');
        localStorage.removeItem('homeowner');
        localStorage.removeItem('userEmail');
        next();
    } else {
        let token = localStorage.getItem('Authorization');
        let query = to.query, newQuery = {}, roomId = 0, invite = '', code = ''
        if(query.hasOwnProperty('roomId')) {
            roomId = query.roomId
            newQuery.roomId = roomId
        }
        if(query.hasOwnProperty('invite')) {
            invite = query.invite
            newQuery.invite = invite
        }
        if(query.hasOwnProperty('code')) {
            code = query.code
            newQuery.code = code
        }
        // console.log(token)
        if (token === null || token === '') {
            localStorage.removeItem('Authorization');
            localStorage.removeItem('homeowner');
            localStorage.removeItem('userEmail');
            next({path: '/login', query: newQuery});
        } else {
            next();
        }
    }
});

export default router;
