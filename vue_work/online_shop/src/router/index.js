import {createRouter, createWebHistory, createWebHashHistory} from 'vue-router'

import ProfilePage from '../views/ProfilePage.vue'
import HomePage from '../views/HomePage.vue'
import LoginPage from '../views/LoginPage.vue'
import ShoppingCartPage from '../views/ShoppingCartPage.vue'

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
            path:'/profile',
            component:ProfilePage
        },
    ]
})

export default router