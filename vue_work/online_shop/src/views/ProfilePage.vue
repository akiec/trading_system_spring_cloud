<script setup>
    import { reactive, toRefs } from 'vue';
    import { useRouter, RouterLink, useRoute } from 'vue-router';
    import { useAuthStore } from '../stores/auth';
    const router = useRouter()
    const route = useRoute()
    const authStore = useAuthStore()
    const user = toRefs(getUserInformation())
    const orders = getOrderInformation()

    function getUserInformation() {
        // 从数据库获取用户数据
        return reactive({
            userid : 'uuid1',
            username : 'redsuperhand',
            phone : '18888888888',
        })
    }

    function getOrderInformation() {
        // 从数据库获取订单数据
        return [
            {order_id: 'uoid1', product_id: 'upid1', seller_id: 'uuid1'},
            {order_id: 'uoid2', product_id: 'upid2', seller_id: 'uuid2'},
            {order_id: 'uoid3', product_id: 'upid3', seller_id: 'uuid3'},
        ]
    }

    function updateInformation(new_name, new_phone) {
        user.username = new_name
        user.phone = new_phone
        alert('改变成功！')
        console.log(user.username,user.phone)
        router.push('/home')
    }
    
    function Logout() {
        authStore.logout()
        router.push('/home')
    }
</script>

<template>
    <div class="profile">
        <div class="auth-box">
            <img src="../assets/vue.svg" class="auth-img">
            <form class="auth-form">
                <div class="form-group">
                    <label>用户id：</label>
                    <span>{{ user.userid }}</span>
                </div>
                <div class="form-group">
                    <label>用户名：</label>
                    <input type="text" v-model="user.username.value"></input>
                </div>
                <div class="form-group">
                    <label>手机号：</label>
                    <input type="text" v-model="user.phone.value"></input>
                </div>
            </form>
            <div class="auth-btn">
                <button @click="updateInformation(user.username.value, user.phone.value)">更新信息</button>
                <button @click="Logout">退出登录</button>
            </div>
        </div>
        <div class="orders">
            <ul class="nav-order">
                <li v-for="order in orders" :key="order.order_id">
                    <router-link to="/order">{{ order.order_id }}</router-link>
                </li>
            </ul>
            <main class="order-content">
                <router-view></router-view>
            </main>
        </div>
    </div>    
</template>

<style scoped>
    .auth-box {
        display: flex;
        justify-content: space-between;
        border: 1px solid;
        position: relative;
        margin: 20px;
        padding: 10px 50px;
        border-radius: 10px;
        background-color: rgb(213, 240, 246);
        text-align: center;
        align-items: center;
        font-size: 24px;
    }

    .auth-img {
        border: 1px solid;
        width: 50px;
        height: 50px;
        border-radius: 100%;
    }

    .auth-form {
        display: flex;
        justify-content: space-around;
        min-width: 1000px;
        margin: 2rem auto;
        padding: 1rem;
        border-radius: 8px;
    }

    .auth-btn {
        display: flex;
        flex-direction: column;
        border: 1px solid;
        line-height: 10px;
        border-radius: 4px;
    }

    .orders {
        display: flex;
        border: 1px solid black;
    }

    .nav-order {
        display: flex;
        flex-direction: column;
        line-height: 50px;
        width: 100px;
        margin: 20px;
        padding: 20px;
        border: 1px solid blue;
        font-size: 20px;
        list-style-type: none;
        unicode-bidi: isolate;
        text-align: center;
    }
    
    .order-content {
        margin: 20px;
        padding: 20px;
        border: 1px solid;
    }

    input[type="text"] {
        width: 50%;
        padding: 0.5rem;
        margin-top: 0.3rem;
        font-size: 18px;
        text-align: center;
    }
     
    button {
        background: skyblue;
        color: black;
        border: 0.5px solid;
        padding: 0.5rem 1rem;
        border-radius: 4px;
        cursor: pointer;
        font-size: 18px;
    }
</style>