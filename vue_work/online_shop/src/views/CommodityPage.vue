<script setup>
import axios from 'axios';
import { useRoute,useRouter } from 'vue-router';
import { useAuthStore } from '../stores/auth';
import { onMounted, reactive } from 'vue';
import { ref } from 'vue';
const authStore = useAuthStore()
const route = useRoute()
const router = useRouter()
const goodsId = route.query.product_id
const selectedNumber = ref(0)
let product = ref([])
const defaultImage = "/src/assets/vue.svg"
const userid = authStore.currentUserId
//根据商品id调数据库查询商品其余详细信息
async function getProductById() {
    const url = "http://localhost:8080"
  console.log(url + `/goods/details/${(goodsId)}`)
  try {
      let response = await axios.post(url + `/goods/details/${(goodsId)}`)
      console.log("搜索成功")
    //   console.log(response.data.data)
    return response.data.data
  } catch (error) {
    console.error('失败:', error)
  }
}

//加入购物车
function addToCart() {
  //登录校验
  if(!authStore.isLogged){
    alert("未登录，将跳转至登录界面!")
    router.push('/login')
    return 
  }  
  const userid = authStore.currentUserId
  if (addGoods(userid)) {
    //提示弹窗
    alert("已加入购物车")
  } else {
    console.log("加入失败")
  }
}
async function addGoods(userid) {
  const url = "http://localhost:8080"
    const Goods = await getProductById()
    console.log("查询成果")
  console.log(Goods)
  try {
    
    let response = await axios.post(url + `/shopCart/add/${(userid)}`, Goods)
      return true;
    
  } catch (error) {
    console.error('搜索失败:', error)
      return false;
  }
}
// 跳转支付界面
function buy() {
    const paymentList = reactive([{
        goodsId: goodsId,
        goodsName: product.value.name,
        price: product.value.price,
        count: selectedNumber.value
    }])
    router.push({
        path: '/payment',
        query: {
            userId: String(userid),
            paymentList: JSON.stringify(paymentList)
        }
    })
    // console.log(paymentList)
}
onMounted(async () => {
    product.value = await getProductById()
    // console.log(product)
 //   loadImage(product.value.image,"img")
})

const handleImageError = (event) => {
  event.target.src = defaultImage; // 替换为备用图片
};
</script>

<template>
    <div class="main">
        <div class="view"> 
            <img :src="product.image" alt="商品图片" width=360px heighth=auto id="img" @error="handleImageError($event, product)"></img>
        </div>
        <div class="content">
            <div class="detail">
                <p>商品名称:{{ product.name }}</p>
                <p>商品详细信息:{{ product.description }}</p>
                <p>商品价格:{{ product.price }}</p>
                <p>库存:{{ product.stock }}</p>
                <div class="form-group">
                  <label>购买数量</label>
                  <input type="number" v-model.number="selectedNumber" min="0" required>
                </div>
            </div>
            <div class="button">
                <button @click="buy()" class="button_buy">立即购买</button>
                <button @click="addToCart()" class="button_addToCart">加入购物车</button>
            </div>
        </div>
    </div>
</template>

<style scoped>
/* 基础样式 */
body {
    font-family: 'PingFang SC', 'Microsoft YaHei', sans-serif;
    background-color: #f8f9fa;
    color: #333;
    line-height: 1.6;
    padding: 20px;
    max-width: 1200px;
    margin: 0 auto;
}

h1 {
    color: #2c3e50;
    text-align: center;
    margin-bottom: 30px;
    font-size: 2.5rem;
    font-weight: 600;
    position: relative;
    padding-bottom: 15px;
}

h1::after {
    content: '';
    position: absolute;
    bottom: 0;
    left: 50%;
    transform: translateX(-50%);
    width: 80px;
    height: 4px;
    background: linear-gradient(90deg, #3498db, #9b59b6);
    border-radius: 2px;
}

h2 {
    color: #7f8c8d;
    text-align: center;
    font-size: 1.2rem;
    margin-bottom: 40px;
    font-weight: 400;
}

/* 主内容区域 */
.main {
    display: flex;
    flex-wrap: wrap;
    gap: 40px;
    background: white;
    border-radius: 12px;
    box-shadow: 0 10px 30px rgba(0, 0, 0, 0.08);
    padding: 40px;
    transition: transform 0.3s ease;
}

.main:hover {
    transform: translateY(-5px);
}

/* 商品图片区域 */
.view {
    flex: 1;
    min-width: 300px;
    display: flex;
    justify-content: center;
    align-items: center;
    background: #f8f9fa;
    border-radius: 8px;
    padding: 30px;
    box-shadow: inset 0 0 10px rgba(0, 0, 0, 0.05);
}

.view img {
    max-width: 100%;
    height: auto;
    object-fit: contain;
    transition: transform 0.3s ease;
}

.view img:hover {
    transform: scale(1.05);
}

/* 商品详情区域 */
.content {
    flex: 2;
    min-width: 300px;
    display: flex;
    flex-direction: column;
    justify-content: space-between;
}

.detail p {
    margin-bottom: 20px;
    font-size: 1.1rem;
    position: relative;
    padding-left: 20px;
}

.detail p::before {
    content: '•';
    color: #3498db;
    position: absolute;
    left: 0;
    font-size: 1.5rem;
    line-height: 1;
}

.detail p:first-child {
    font-size: 1.8rem;
    font-weight: 600;
    color: #2c3e50;
    margin-bottom: 25px;
}

.detail p:first-child::before {
    display: none;
}

.detail p:nth-child(3) {
    color: #e74c3c;
    font-size: 1.5rem;
    font-weight: 700;
}

/* 按钮区域 */
.button {
    display: flex;
    gap: 20px;
    margin-top: 30px;
    flex-wrap: wrap;
}

button {
    padding: 12px 30px;
    border: none;
    border-radius: 50px;
    font-size: 1rem;
    font-weight: 600;
    cursor: pointer;
    transition: all 0.3s ease;
    box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
}

.button_buy {
    background: linear-gradient(135deg, #3498db, #2980b9);
    color: white;
    flex: 1;
    min-width: 180px;
}

.button_buy:hover {
    background: linear-gradient(135deg, #2980b9, #3498db);
    transform: translateY(-2px);
    box-shadow: 0 6px 15px rgba(41, 128, 185, 0.3);
}

.button_addToCart {
    background: linear-gradient(135deg, #2ecc71, #27ae60);
    color: white;
    flex: 1;
    min-width: 180px;
}

.button_addToCart:hover {
    background: linear-gradient(135deg, #27ae60, #2ecc71);
    transform: translateY(-2px);
    box-shadow: 0 6px 15px rgba(46, 204, 113, 0.3);
}

/* 响应式设计 */
@media (max-width: 768px) {
    .main {
        flex-direction: column;
        padding: 20px;
    }
    
    .view, .content {
        min-width: 100%;
    }
    
    h1 {
        font-size: 2rem;
    }
    
    .button {
        flex-direction: column;
    }
    
    button {
        width: 100%;
    }
}
</style>