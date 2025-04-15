import {createRouter, createWebHistory, createWebHashHistory} from 'vue-router'

import ProfilePage from '../views/ProfilePage.vue'
import HomePage from '../views/HomePage.vue'
import LoginPage from '../views/LoginPage.vue'
import ShoppingCartPage from '../views/ShoppingCartPage.vue'
import CommodityPage from '../views/CommodityPage.vue'
import OrderPage from '../views/OrderPage.vue'
import SearchPage from '../views/SearchPage.vue'
import UploadPage from '../views/UploadPage.vue'
import PaymentPage from '../views/PaymentPage.vue'

const router = createRouter({
    history:createWebHashHistory(),
    routes:[
        {
            path:'/home',
            component:HomePage
        },
        {
            path:'/shoppingcart',
            component:ShoppingCartPage
        },
        {
            path:'/login',
            component:LoginPage
        },
        {
            path:'/profile',
            component:ProfilePage
        },
        {
            name: 'Order',
            path: '/order',
            component: OrderPage,
            props(route) {
                return route.query
            }
        },
        {
            path: '/commodity',
            component:CommodityPage
        },
        {
            path: '/search',
            component:SearchPage
        },
        {
            path: '/upload',
            component:UploadPage
        },
        {
            path: '/payment',
            name: 'Payment',
            component:PaymentPage,
            props(route) {
                try {
                    return {
                      paymentList: route.query.paymentList 
                        ? JSON.parse(route.query.paymentList)
                        : [],
                      userId: route.query.userId
                    }
                  } catch (e) {
                    console.error('参数解析失败', e);
                    return { paymentList: [], userId: null };
                  }
                }
        },
        {
            path:'/',
            redirect:'/home'
        }
    ]
})

export default router