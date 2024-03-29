import Vue from 'vue'
import VueRouter from 'vue-router'

Vue.use(VueRouter)

const routes = [
  {
    path: '/home',
    name: 'home',
    component: () => import('../views/Home.vue')
  },
  {
    path: '/userProfile',
    name: 'userProfile',
    component: () => import('../views/UserProfile.vue')
  },
  {
    path: '/riskReport',
    name: 'riskReport',
    component: () => import('../views/RiskReport.vue')
  },
  {
    path: '/webCrawler',
    name: 'riskReport',
    component: () => import('../views/WebCrawler.vue')
  },
  {
    path: '/accountManagement',
    name: 'accountManagement',
    component: () => import('../views/infoManagement/AccountManagement.vue')
  },
  {
    path: '/loanInfo',
    name: 'loanInfo',
    component: () => import('../views/infoManagement/LoanInfo.vue')
  },
  {
    path: '/personInfo',
    name: 'personInfo',
    component: () => import('../views/infoManagement/PersonInfo.vue')
  },
  {
    path: '/finHealth',
    name: 'finHealth',
    component: () => import('../views/infoManagement/FinHealth.vue')
  }
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router