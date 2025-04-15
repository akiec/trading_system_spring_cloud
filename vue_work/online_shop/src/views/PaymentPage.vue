<template>
    <div class="payment-container">
      <h2>商品结算</h2>
      <div class="goods-list">
        <div v-for="goods in props.paymentList" :key="goods.goodsId" class="goods-item">
          <div class="goods-info">
            <h3>{{ goods.goodsName }}</h3>
            <p>单价：¥{{ goods.price.toFixed(2) }}</p>
            <p>数量：{{ goods.count }}</p>
          </div>
        </div>
      </div>
  
      <div class="total-price">
        总价：¥{{ totalPrice }}
      </div>
  
      <form @submit.prevent="handleSubmit" class="payment-form">
        <div class="form-group">
          <label>收货地址：</label>
          <textarea v-model="formData.address" required></textarea>
        </div>
        
        <div class="form-group">
          <label>联系方式：</label>
          <input type="tel" v-model="formData.phone" required>
        </div>
  
        <div class="form-group">
          <label>支付方式：</label>
          <div class="radio-group">
            <label v-for="method in paymentMethods" :key="method">
              <input 
                type="radio" 
                v-model="formData.paymentMethod" 
                :value="method"
                required
              >
              {{ method }}
            </label>
          </div>
        </div>
  
        <button type="submit" class="submit-btn">提交订单</button>
      </form>
    </div>
  </template>
  
<script setup>
  import { ref, computed, onMounted, reactive } from 'vue'
  import { useRouter } from 'vue-router'
  import { useAuthStore } from '../stores/auth';
  import axios from 'axios';
  
  const props = defineProps(['userId', 'paymentList'])
  console.log(props.paymentList)
  const router = useRouter()
  const authStore = useAuthStore()
    const user = reactive({
        userid: null,
        username: '',
        phone: null,
        address: ''
    })
    // 表单数据
  const formData = reactive({
    address: '', // 默认地址
    phone: '',    // 默认联系方式
    paymentMethod: ''
  })
  
  // 支付方式选项
  const paymentMethods = ['微信', '支付宝', '银行卡']

    function getUserInformation() {
        // 从数据库获取用户数据
        user.userid = authStore.currentUserId
        const url = `http://localhost:8080/user/${user.userid}`
        axios.get(url)
        .then(function (response) {
            // console.log(response)
            user.username = response.data.data.userName
            user.phone = response.data.data.phone
            user.nickname = response.data.data.nickName
            formData.phone = response.data.data.phone
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
            formData.address = response.data.data
        })
        .catch(function (error) {
            console.log(error)
        })
    }
  // 计算总价
  const totalPrice = computed(() => {
  return props.paymentList.reduce((sum, item) => {
    // 添加安全数值转换
    const price = Number(item.price) || 0
    const count = Number(item.count) || 0
    return sum + (price * count)
  }, 0).toFixed(2) // 保留两位小数
})
    
// 创建订单函数
async function createOrders() {
  try {
    if (!props.paymentList?.length) {
      alert('未选择商品')
      return { success: false, message: '未选择商品' }
    }

    // 创建请求数组
    const requests = props.paymentList.map(item => {
      // 参数预处理
      const goodsId = String(item.goodsId)
      const count = Math.max(Number(item.count) || 0, 0)
      console.log(count)
      // 生成请求配置
      return axios.post(`http://localhost:8080/order/createOrder/${props.userId}/${goodsId}/${count}`)
    })

    // 并行发送所有请求
    const results = await Promise.allSettled(requests)
    
    // 处理结果
    const successOrders = []
    const failedOrders = []
    
    results.forEach((result, index) => {
      const item = props.paymentList[index]
      if (result.status === 'fulfilled') {
        successOrders.push({
          goodsId: item.goodsId,
          data: result.value.data
        })
      } else {
        failedOrders.push({
          goodsId: item.goodsId,
          error: result.reason.message
        })
      }
    })

    // 返回详细结果
    return {
      success: true,
      total: props.paymentList.length,
      successCount: successOrders.length,
      failedCount: failedOrders.length,
      successOrders,
      failedOrders
    }
    
  } catch (error) {
    console.error('订单创建异常:', error)
    return {
      success: false,
      message: '系统异常: ' + error.message
    }
  }
}

  // 提交订单
  const handleSubmit = async () => {
  const result = await createOrders()
  if (result.success) {
    if (result.failedCount > 0) {
      alert(`成功创建 ${result.successCount} 个订单，失败 ${result.failedCount} 个`)
      console.log('失败详情:', result.failedOrders)
    } else {
      alert('所有订单创建成功！')
      router.push('/profile')
    }
  } else {
    alert(result.message)
  }
}

  onMounted(() => {
    getUserInformation()
    getUserAddress()
    console.log(user)
  })
</script>
  
<style scoped>
  .payment-container {
    max-width: 800px;
    margin: 20px auto;
    padding: 20px;
    background-color: #f5f5f5;
    border-radius: 8px;
  }
  
  .goods-list {
    margin: 20px 0;
  }
  
  .goods-item {
    background: white;
    padding: 15px;
    margin-bottom: 10px;
    border-radius: 4px;
    box-shadow: 0 2px 4px rgba(0,0,0,0.1);
  }
  
  .form-group {
    margin: 15px 0;
  }
  
  .form-group label {
    display: block;
    margin-bottom: 5px;
    font-weight: bold;
  }
  
  .form-group input[type="tel"],
  .form-group textarea {
    width: 100%;
    padding: 8px;
    border: 1px solid #ddd;
    border-radius: 4px;
    box-sizing: border-box;
  }
  
  .radio-group label {
    margin-right: 20px;
  }
  
  .submit-btn {
    background-color: #007bff;
    color: white;
    padding: 10px 20px;
    border: none;
    border-radius: 4px;
    cursor: pointer;
    font-size: 16px;
  }
  
  .submit-btn:hover {
    background-color: #0056b3;
  }
  
  .total-price {
    font-size: 18px;
    font-weight: bold;
    color: #e4393c;
    margin: 20px 0;
    text-align: right;
  }
  </style>