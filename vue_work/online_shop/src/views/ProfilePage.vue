<script setup>
    import { reactive, ref, toRaw, onBeforeMount } from 'vue';
    import { useRouter, RouterLink } from 'vue-router';
    import { useAuthStore } from '../stores/auth';
    import axios from 'axios';
    const router = useRouter()
    const authStore = useAuthStore()
    const user = reactive({
        userid: null,
        username: '',
        phone: null,
        address: ''
    })
    // let orders = [{orderId: 'uoid1', productId: '1', seller_id: 'uuid1'},
    //         {orderId: 'uoid2', productId: '2', seller_id: 'uuid2'},
    //         {orderId: 'uoid3', productId: '3', seller_id: 'uuid3'}]
    let orders = reactive([
        {orderId: 'uoid1', goodsId: '1', productId: 'upid1', count: 2, totalPrice: 593.99, address: '大连市金州区', status: 1},
        {orderId: 'uoid2', goodsId: '2', productId: 'upid2', count: 3, totalPrice: 5.99, address: '晋中市太谷区', status: 1},
        {orderId: 'uoid3', goodsId: '3', productId: 'upid3', count: 5, totalPrice: 93.99, address: '北京市海淀区', status: 2}
    ])

    function getUserInformation() {
        // 从数据库获取用户数据
        user.userid = authStore.currentUserId
        const url = `http://localhost:8080/user/${Number(user.userid)}`
        axios.get(url)
        .then(function (response) {
            console.log(response)
            user.username = response.data.data.userName
            user.phone = response.data.data.phone
            user.address = response.data.data.address
        })
        .catch(function (error) {
            console.log(error)
        })
    }

    async function getOrderInformation() {
        // 从数据库获取订单数据     
        console.log(toRaw(user).userid)
        const url = `http://localhost:8080/order/check/${Number(toRaw(user).userid)}`
        let response = await axios.get(url)
        for(let i = 0;i < response.data.total;i++){
            orders.unshift(response.data.data[i])
            // console.log(response.data.data[i])
        }

        // orders.unshift({order_id: 'uoid1', product_id: '1', seller_id: 'uuid1'},
        //     {order_id: 'uoid2', product_id: '2', seller_id: 'uuid2'},
        //     {order_id: 'uoid3', product_id: '3', seller_id: 'uuid3'})
        console.log(orders)
    }

    onBeforeMount(() => {
        getUserInformation()
        getOrderInformation()
        // orders = toRaw(orders)
        console.log(orders)
    })

    function updateInformation() {
        const json = {
            userId: user.userid,
            userName: user.username,
            isAdmin: false,       
            phone: user.phone, 
            token: '',
            nickName: '',
            address: user.address,
        }
        
        const update_url = "http://localhost:8080/user/detail"
        axios.post(update_url, json).then(function (response) {
            alert('信息更新成功！')
            console.log(user.username,user.phone)
            router.push('/home')
        }).catch(function (error) {
            console.log(error)
        })  
    }

    function changeAddress() {
        console.log(user.address)
        const address_url = "http://localhost:8080/user/address"
        const json = {
            userId: user.userid,
            userName: user.username,
            isAdmin: true,       
            phone: user.phone, 
            token: '',
            nickName: '',
            address: user.address,
        }
        axios.post(address_url, json).then(function (response) {
            alert("地址修改成功")
            console.log(response)
        }).catch(function (error) {
            console.log(error)
        })
    }
    
    function Logout() {
        authStore.logout()
        router.push('/home')
    }
</script>

<template>
    <div class="page">
        <div class="auth-box">
            <img src="../assets/vue.svg" class="auth-img">
            <form class="auth-form">
                <div class="form-group">
                    <label>用户id：</label>
                    <span>{{ user.userid }}</span>
                </div>
                <div class="form-group">
                    <label>用户名：</label>
                    <input id="username-input" type="text" v-model="user.username"></input>
                </div>
                <div class="form-group">
                    <label>手机号：</label>
                    <input id="phone-input" type="text" v-model="user.phone"></input>
                </div>
                <div class="form-group">
                    <label>地址：</label>
                    <input id="address-input" type="text" v-model="user.address"></input>
                </div>
            </form>
            <router-link :to="{path:'upload'}">
                <button class="button_search">上传商品</button>
            </router-link>
            <div class="auth-btn">
                <button @click="updateInformation()">更新信息</button>
                <button @click="Logout">退出登录</button>
                <button @click="changeAddress">更新地址</button>
            </div>
        </div>
        <div class="orders">
            <ul class="nav-order">
                <li v-for="order in orders" :key="order.orderId" class="nav-order-tip">
                    <router-link :to="{
                        path:'/order',
                        query:{
                            order: order.orderId,
                            goods: order.goodsId,
                            product: order.productId,
                            count: order.count,
                            totalPrice: order.totalPrice,
                            address: order.address,
                            status: order.status
                        }
                    }">{{ order.orderId }}</router-link>
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
        margin: 2rem;
        padding: 1rem;
        text-align: center;
        align-items: center;
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
        width: 150px;
        margin: 20px;
        padding: 20px;
        border: 1px solid;
        font-size: 30px;
        list-style-type: none;
        unicode-bidi: isolate;
        text-align: center;
    }

    .nav-order a {
        text-decoration: none;
    }

    .nav-order a:active {
        color: orange;
        font-weight: 900;
        text-shadow: 0 0 1px black;
    }

    .nav-order-tip {
        border: 1px solid;
        background-color: aquamarine;
        opacity:0.7;
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