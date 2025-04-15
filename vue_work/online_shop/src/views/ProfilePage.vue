<script setup>
    import { reactive, ref, toRaw, onBeforeMount, computed } from 'vue';
    import { useRouter, RouterLink } from 'vue-router';
    import { useAuthStore } from '../stores/auth';
    import axios from 'axios';

    const showUpdateDialog = ref(false)
    const activeTab = ref('all')

    const tabs = [
    { id: 'all', label: '全部订单' },
    { id: 'paid', label: '已支付订单' },
    { id: 'unpaid', label: '未支付订单' }
    ]

    // 计算属性过滤订单
    const filteredOrders = computed(() => {
    if (activeTab.value === 'paid') {
        return orders.filter(o => o.status === 1) // 假设1是已支付状态
    }
    if (activeTab.value === 'unpaid') {
        return orders.filter(o => o.status === 0) // 假设0是未支付状态
    }
    return orders
    })

    // 状态显示处理
    const statusText = (status) => {
    const statusMap = { 0: '未支付', 1: '已支付', 2: '已取消' }
    return statusMap[status] || '未知状态'
    }

    const statusClass = (status) => {
    return {
        'unpaid': status === 0,
        'paid': status === 1,
        'canceled': status === 2
    }
    }

    // 订单详情跳转
    const viewOrderDetail = (order) => {
    router.push({
        path:'/order',
        query: {
        order: order.orderId,
        goods: order.goodsId,
        product: order.productId,
        count: order.count,
        totalPrice: order.totalPrice,
        address: order.address,
        status: order.status,
        customer: order.customerId
        }
    })
    }

    const router = useRouter()
    const authStore = useAuthStore()
    const user = reactive({
        userid: null,
        username: '',
        phone: null,
        address: ''
    })
    
    let orders = reactive([])

    function getUserInformation() {
        // 从数据库获取用户数据
        user.userid = authStore.currentUserId
        const url = `http://localhost:8080/user/${user.userid}`
        axios.get(url)
        .then(function (response) {
            console.log(response)
            user.username = response.data.data.userName
            user.phone = response.data.data.phone
            user.nickname = response.data.data.nickName
        })
        .catch(function (error) {
            console.log(error)
        })

    }

    function getUserAddress() {
        const url = `http://localhost:8080/user/getAddress/${user.userid}`
        axios.get(url)
        .then(function (response) {
            // console.log(response)
            user.address = response.data.data
        })
        .catch(function (error) {
            console.log(error)
        })
    }

    async function getOrderInformation() {
        // 从数据库获取订单数据     
        // console.log(toRaw(user).userid)
        const url = `http://localhost:8080/order/check/${toRaw(user).userid}`
        let response = await axios.get(url)
        for(let i = 0;i < response.data.total;i++){
            orders.unshift(response.data.data[i])
            // console.log(response.data.data[i])
        }
        // console.log(orders)
    }

    onBeforeMount(() => {
        getUserInformation()
        getOrderInformation()
        getUserAddress()
        // orders = toRaw(orders)
        console.log(orders)
    })

    function updateInformation() {
        const json = {
            userId: user.userid,
            isAdmin: false,       
            phone: user.phone, 
            token: '',
            nickName: user.nickname,
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
        // console.log(user.address)
        const address_url = "http://localhost:8080/user/address"
        const json = {
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
    <div class="container">
      <!-- 用户信息展示区 -->
      <div class="profile-card">
        <div class="avatar-section">
          <img src="../assets/vue.svg" class="user-avatar">
          <div class="user-meta">
            <h2 class="user-id">用户ID: {{ user.userid }}</h2>
            <h1 class="username">{{ user.nickname }}</h1>
          </div>
        </div>
        
        <div class="action-buttons">
          <button class="btn secondary" @click="showUpdateDialog = true">更新信息</button>
          <router-link to="/upload" class="btn primary">
            上传商品
          </router-link>
          <button class="btn warning" @click="changeAddress">更新地址</button>
          <button class="btn danger" @click="Logout">退出登录</button>
        </div>
      </div>
  
      <!-- 订单导航 -->
      <div class="order-navigation">
        <button class="nav-btn" 
                v-for="tab in tabs" 
                :key="tab.id"
                :class="{ active: activeTab === tab.id }"
                @click="activeTab = tab.id">
          {{ tab.label }}
        </button>
      </div>
  
      <!-- 订单列表 -->
      <div class="order-container">
        <ul class="order-list" v-if="filteredOrders.length">
          <li v-for="order in filteredOrders" :key="order.orderId" class="order-item">
            <div class="order-summary">
              <span>订单号: {{ order.orderId }}</span>
              <span class="order-status" :class="statusClass(order.status)">
                {{ statusText(order.status) }}
              </span>
              <button class="detail-btn" @click="viewOrderDetail(order)">查看详情</button>
            </div>
          </li>
        </ul>
        <div v-else class="empty-orders">
          暂无相关订单
        </div>
      </div>
  
      <!-- 更新信息对话框 -->
      <div v-if="showUpdateDialog" class="modal-mask">
        <div class="modal-wrapper">
          <div class="modal-container">
            <h3 class="modal-title">更新用户信息</h3>
            <form @submit.prevent="updateInformation">
              <div class="form-group">
                <label>手机号码</label>
                <input type="tel" v-model="user.phone" required>
              </div>
              <div class="form-group">
                <label>收货地址</label>
                <input type="text" v-model="user.address" required>
              </div>
              <div class="form-actions">
                <button type="button" class="btn" @click="showUpdateDialog = false">取消</button>
                <button type="submit" class="btn primary">保存更改</button>
              </div>
            </form>
          </div>
        </div>
      </div>
    </div>
  </template>

<style scoped>
.container {
  max-width: 1200px;
  margin: 2rem auto;
  padding: 0 1rem;
}

.profile-card {
  background: white;
  border-radius: 12px;
  box-shadow: 0 2px 8px rgba(0,0,0,0.1);
  padding: 2rem;
  margin-bottom: 2rem;
}

.avatar-section {
  display: flex;
  align-items: center;
  gap: 1.5rem;
  margin-bottom: 2rem;
}

.user-avatar {
  width: 80px;
  height: 80px;
  border-radius: 50%;
  border: 3px solid #e0e0e0;
}

.user-meta {
  flex: 1;
}

.user-id {
  font-size: 0.9rem;
  color: #666;
  margin: 0;
}

.username {
  font-size: 1.8rem;
  margin: 0.5rem 0 0;
  color: #333;
}

.action-buttons {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(120px, 1fr));
  gap: 1rem;
}

.btn {
  padding: 0.8rem 1.2rem;
  border: none;
  border-radius: 6px;
  cursor: pointer;
  transition: all 0.2s;
  font-weight: 500;
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 0.5rem;
}

.btn.primary {
  background: #007bff;
  color: white;
}

.btn.secondary {
  background: #6c757d;
  color: white;
}

.btn.warning {
  background: #ffc107;
  color: black;
}

.btn.danger {
  background: #dc3545;
  color: white;
}

.btn:hover {
  opacity: 0.9;
  transform: translateY(-1px);
}

.order-navigation {
  display: flex;
  gap: 1rem;
  margin-bottom: 2rem;
}

.nav-btn {
  padding: 0.8rem 1.5rem;
  border: none;
  border-radius: 6px;
  background: #f8f9fa;
  color: #666;
  cursor: pointer;
  transition: all 0.2s;
}

.nav-btn.active {
  background: #007bff;
  color: white;
}

.order-container {
  background: white;
  border-radius: 12px;
  box-shadow: 0 2px 8px rgba(0,0,0,0.1);
  padding: 1.5rem;
}

.order-list {
  list-style: none;
  padding: 0;
  margin: 0;
}

.order-item {
  padding: 1rem;
  border-bottom: 1px solid #eee;
}

.order-summary {
  display: flex;
  align-items: center;
  justify-content: space-between;
  gap: 1rem;
}

.order-status {
  font-size: 0.85rem;
  padding: 0.3rem 0.8rem;
  border-radius: 1rem;
}

.order-status.unpaid {
  background: #fff3cd;
  color: #856404;
}

.order-status.paid {
  background: #d4edda;
  color: #155724;
}

.order-status.canceled {
  background: #f8d7da;
  color: #721c24;
}

.detail-btn {
  background: none;
  border: 1px solid #007bff;
  color: #007bff;
  padding: 0.4rem 1rem;
  border-radius: 4px;
  cursor: pointer;
}

.detail-btn:hover {
  background: #007bff;
  color: white;
}

.modal-mask {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: rgba(0,0,0,0.5);
  display: flex;
  align-items: center;
  justify-content: center;
  z-index: 1000;
}

.modal-container {
  background: white;
  border-radius: 8px;
  padding: 2rem;
  width: 90%;
  max-width: 500px;
}

.modal-title {
  margin: 0 0 1.5rem;
  color: #333;
}

.form-group {
  margin-bottom: 1rem;
}

.form-group label {
  display: block;
  margin-bottom: 0.5rem;
  color: #666;
}

.form-group input {
  width: 100%;
  padding: 0.8rem;
  border: 1px solid #ddd;
  border-radius: 4px;
  font-size: 1rem;
}

.form-actions {
  display: flex;
  gap: 1rem;
  margin-top: 1.5rem;
  justify-content: flex-end;
}

.empty-orders {
  text-align: center;
  color: #666;
  padding: 2rem;
}
</style>