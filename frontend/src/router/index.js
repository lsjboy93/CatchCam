import Vue from 'vue'
import VueRouter from 'vue-router'
import Home from '@/views/Home'
import Login from '@/views/member/Login'
import Signup from '@/views/member/Signup'
import DroneIntro from '@/views/DroneIntro'
import RentalList from '@/views/RentalList'

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'Home',
    component: Home
  },
  {
      path: '/login',
      name: 'Login',
      component: Login
  },
  {
      path: '/signup',
      name: 'Signup',
      component: Signup
  },
  {
    path: '/drone/1',
    name: 'DroneIntro',
    component: DroneIntro
  },
  {
    path: '/rental',
    name: 'RentalList',
    component: RentalList,
  }
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
