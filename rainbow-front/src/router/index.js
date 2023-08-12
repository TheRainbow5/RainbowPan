import Vue from 'vue'
import VueRouter from 'vue-router'



Vue.use(VueRouter)

const routes = [
  // 登录界面路由
  {
    path: '/',
    component: () => import('@/views/Login/WelcomView.vue'),
    children: [
      { path: '/', name: 'welcome-loginPage', component: () => import('@/components/Login/LoginPage.vue') },
      { path: '/welcome-registerPage', name: 'registerPage', component: () => import('@/components/Login/RegisterPage.vue') },
      { path: '/welcome-forgetPasswordPage', name: 'forgetPassword', component: () => import('@/components/Login/ForgetPasswordPage.vue') }
    ]
  },
  // 首页
  {
    path: '/index',
    name: 'index',
    component: () => import('@/views/IndexView.vue'),
    children: [
      { path: '/', name: 'myPanPage', component: () => import('@/components/Index/MyPan.vue') },
      { path: '/Index-recentPage', name: 'recentPage', component: () => import('@/components/Index/RecentUse.vue') },
      { path: '/Index-trashPage', name: 'trashPage', component: () => import('@/components/Index/Trash.vue') }
    ]
  },

]

//初始化路由
const router = new VueRouter({
  routes
})
/**
 * 路由守卫
 */
router.beforeEach((to, from, next) => {
  var path = to.path;
  var token = localStorage.getItem('token');
  if (path === '/' && token != null) {
    next({ path: '/index' });
  } else if (path === '/welcome-registerPage' || path === '/welcome-forgetPasswordPage') {
    next();
  }
  else {
    if (path != '/' && token == null) {
      next({ path: '/' });
    } else {
      next();
    }
  }
});


export default router
