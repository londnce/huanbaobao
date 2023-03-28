import Vue from 'vue'
import VueRouter from 'vue-router'
import store from "@/store";

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: '后台管理',
    component: () => import(/* webpackChunkName: "Manage" */ '../views/Manage.vue'),
    redirect: "/home",
    children: [
      {
        path: '/home',
        name: '主页',
        component: () => import(/* webpackChunkName: "Home" */ '../views/Home.vue'),
      },
      {
        path: '/userManage',
        name: '用户管理',
        component: () => import(/* webpackChunkName: "userManage" */ '../views/UserManage.vue')
      },
      {
        path: '/productManage',
        name: '商品管理',
        component: () => import(/* webpackChunkName: "productManage" */ '../views/ProductManage.vue'),
      },
      {
        path: '/categoryManage',
        name: '分类管理',
        component: () => import(/* webpackChunkName: "categoryManage" */ '../views/CategoryManage.vue')
      },
      {
        path: '/orderManage',
        name: '订单管理',
        component: () => import(/* webpackChunkName: "orderManage" */ '../views/OrderManage.vue')
      },
      {
        path: '/dataManage',
        name: '数据报表',
        component: () => import(/* webpackChunkName: "sale" */ '../views/DataManage.vue')
      },
      {
        path: '/personal',
        name: '个人信息',
        component: () => import(/* webpackChunkName: "personal" */ '../views/Personal.vue')
      },
      {
        path: 'editPw',
        name: '修改密码',
        component: () => import(/* webpackChunkName: "editPw" */'../views/EditPw.vue')
      },
      {
        path: 'productManage/addPro',
        name: '添加商品',
        component: () => import(/* webpackChunkName: "addPro" */'../views/AddPro.vue')
      }
    ]
  },
  {
    path: '/login',
    name: '后台登录',
    // route level code-splitting
    // this generates a separate chunk (about.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    component: () => import(/* webpackChunkName: "login" */ '../views/Login.vue')
  },
]

const router = new VueRouter({
  routes
})

// 提供一个重置路由的方法
export const resetRouter = () => {
  router.matcher = new VueRouter({
    routes
  })
}

// 注意：刷新页面会导致页面路由重置
export const setRoutes = () => {
  const storeMenus = localStorage.getItem("menus");
  if (storeMenus) {

    // 获取当前的路由对象名称数组
    const currentRouteNames = router.getRoutes().map(v => v.name)
    if (!currentRouteNames.includes('Manage')) {
      // 拼装动态路由
      const manageRoute = { path: '/', name: 'home', component: () => import('../views/Home.vue'), redirect: "/home", children: [
          { path: 'person', name: '个人信息', component: () => import('../views/Personal.vue')},
          { path: 'password', name: '修改密码', component: () => import('../views/EditPw.vue')},
          {path: 'productManage/addPro', name: '添加商品', component: () => import('../views/AddPro.vue')},
        ] }
      const menus = JSON.parse(storeMenus)
      menus.forEach(item => {
        if (item.path) {  // 当且仅当path不为空的时候才去设置路由
          let itemMenu = { path: item.path.replace("/", ""), name: item.name, component: () => import('../views/' + item.pagePath + '.vue')}
          manageRoute.children.push(itemMenu)
        } else if(item.children.length) {
          item.children.forEach(item => {
            if (item.path) {
              let itemMenu = { path: item.path.replace("/", ""), name: item.name, component: () => import('../views/' + item.pagePath + '.vue')}
              manageRoute.children.push(itemMenu)
            }
          })
        }
      })
      // 动态添加到现在的路由对象中去
      router.addRoute(manageRoute)
    }

  }
}

// 重置我就再set一次路由
setRoutes()

router.beforeEach((to, from, next) => {
  localStorage.setItem("currentPathName", to.name)  // 设置当前的路由名称
  store.commit("setPath")

  // // 未找到路由的情况
  // if (!to.matched.length) {
  //   const storeMenus = localStorage.getItem("menus")
  //   if (storeMenus) {
  //     next("/404")
  //   } else {
  //     // 跳回登录页面
  //     next("/login")
  //   }
  // }
  // 其他的情况都放行
  next()
})


export default router
