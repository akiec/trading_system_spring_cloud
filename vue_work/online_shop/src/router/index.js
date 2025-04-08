import {createRouter, createWebHistory, createWebHashHistory} from 'vue-router'

import ProfilePage from '../views/ProfilePage.vue'
import HomePage from '../views/HomePage.vue'
import LoginPage from '../views/LoginPage.vue'
import ShoppingCartPage from '../views/ShoppingCartPage.vue'
import CommodityPage from '../views/CommodityPage.vue'
import OrderPage from '../views/OrderPage.vue'
import SearchPage from '../views/SearchPage.vue'
import UploadPage from '../views/UploadPage.vue'

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
            component:ProfilePage,
            children: [
                {
                    path: '/order',
                    component: OrderPage
                }
            ]
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
            path:'/',
            redirect:'/home'
        }
    ]
})

export default router