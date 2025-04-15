<script setup>
import { useAuthStore } from '../stores/auth';
import { ref,onMounted } from 'vue';
import { useRouter } from 'vue-router';
import axios from 'axios';
const router = useRouter()
const authStore = useAuthStore()
const Products = ref([])
const selectedNumber = ref(0)
let goodsList = []
let count =ref(0)
// 模拟数据
const mockProducts = [
    { goodsId: 1, name: "商品1", price: 99.99, stock: 10 ,img: "/src/assets/commodity.png"},
    { goodsId: 2, name: "商品2", price: 199.99, stock: 5 ,img: null},
    { goodsId: 3, name: "商品3", price: 99.99, stock: 10 ,img: "/src/assets/vue.svg"},
    { goodsId: 4, name: "商品4", price: 99.99, stock: 10 ,img: "/src/assets/commodity.png"},
    { goodsId: 5, name: "商品5", price: 99.99, stock: 10 ,img: "/src/assets/commodity.png"},
    { goodsId: 6, name: "商品6", price: 99.99, stock: 10 ,img: "/src/assets/commodity.png"},
    { goodsId: 7, name: "商品7", price: 99.99, stock: 10 ,img: "/src/assets/commodity.png"},
    { goodsId: 8, name: "商品8", price: 99.99, stock: 10 ,img: "/src/assets/commodity.png"},
    { goodsId: 9, name: "商品9", price: 99.99, stock: 10 ,img: "/src/assets/commodity.png"},
    { goodsId: 10, name: "商品10", price: 99.99, stock: 10 ,img: "/src/assets/commodity.png"},
    { goodsId: 11, name: "商品11", price: 99.99, stock: 10, img: "/src/assets/commodity.png" },
    { goodsId: 12, name: "商品12", price: 99.99, stock: 10 ,img: "/src/assets/commodity.png"},
];

async function searchGoods() {
  const url = "http://localhost:8080"
  const id = authStore.currentUserId
  try {
    console.log(url + `/shopCart/${id}`)
    let response = await axios.post(url + `/shopCart/${id}`)
    console.log(response.data.data.goodsList)
    return response.data.data.goodsList
  } catch (error) {
    console.error('搜索失败:', error)
    return mockProducts
  }
}
async function buy(id) {//商品结算函数
  console.log(goodsList)
  const url = "http://localhost:8080"
  try {
    console.log(url + `/shopCart/summary/${authStore.currentUserId}`)
    let response = await axios.post(url + `/shopCart/summary/${authStore.currentUserId}`, {
      
    })
    console.log(response)
  } catch (error) {
    console.error('结算失败:', error)
  }
}
function selected(id) {//选择该商品函数
  let index = goodsList.indexOf(id)
  if (index==-1) {
    goodsList.push(id);//不存在则加入
    //控件样式变化
  } else {
    goodsList.splice(index, 1);//存在则删除
    //控件样式变化
  }
  // console.log(goodsList)
  summary()
}
async function remove(id) {//移除购物车
  const url = "http://localhost:8080"
  try {
    console.log(url + `/shopCart/${id}`)
    let response = await axios.delete(url + `/shopCart/delete/${id}`)
    console.log(response)
  } catch (error) {
    console.error('删除失败:', error)
  }
}
async function summary() {//计算当前选中的商品的总价值
  let total = 0;
  for (let id of goodsList) {
    const url = "http://localhost:8080"
    try {
      // console.log(url + `/goods/details/${id}`)
      let response = await axios.post(url + `/goods/details/${id}`)
      // console.log(response)
      total+=response.data.data.price
    } catch (error) {
      console.error('删除失败:', error)
    }
  }
  // console.log(total)
  count.value = total;
}
onMounted( async() => {
    if(!authStore.isLogged){//弹窗登录警告函数
        alert("未登录，将跳转至登录界面!")
        router.push('/login')
  }  
  Products.value = await searchGoods();
}) 
</script>

<template>
    <div class="product-grid" id="productList">
      <div v-for="product in Products" class = "product-card">
        <div class = "card-text">
            <input type="checkbox" v-model="product.selected" @click="selected(product.goodsId, product.name, product.price)">
            <h3>{{product.name}}</h3>
            <p>价格：{{product.price}}</p>
            <p>库存：{{product.stock}}件</p>
            <router-link :to="{path:'commodity',query:{product_id:product.goodsId}}">
                <button >查看详情页</button>
            </router-link>
            <label>购买数量</label>
            <input type="number" v-model.number="selectedNumber" min="0" required>
            <button @click="buy(product.goodsId)">单独结算</button>
            <button @click="remove(product.goodsId)">移出购物车</button>
        </div>
        <div class = "card-img">
            <img :src="product.image" alt="商品图片" width="80px" height="auto"></img>
        </div>
      </div>
    </div>
    <p>所选商品总价为{{ count }}</p>
    <button @click="buy()">结算所选的商品</button>
</template>

<style scoped>
/* 商品网格容器 */
.product-grid {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(320px, 1fr));
  gap: 25px;
  padding: 20px;
  margin: 0 auto;
  max-width: 1200px;
}

/* 商品卡片样式 */
.product-card {
  display: flex;
  flex-direction: column;
  background-color: #fff;
  border-radius: 12px;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.08);
  overflow: hidden;
  transition: transform 0.3s ease, box-shadow 0.3s ease;
}

.product-card:hover {
  transform: translateY(-5px);
  box-shadow: 0 8px 20px rgba(0, 0, 0, 0.12);
}

/* 卡片内容区域 */
.card-text {
  padding: 20px;
  display: flex;
  flex-direction: column;
  gap: 12px;
}

.card-text h3 {
  margin: 0;
  color: #2c3e50;
  font-size: 1.2rem;
  font-weight: 600;
  line-height: 1.4;
}

.card-text p {
  margin: 0;
  color: #555;
  font-size: 0.95rem;
}

/* 价格特殊样式 */
.card-text p:contains("价格") {
  color: #e74c3c;
  font-weight: bold;
  font-size: 1.1rem;
}

/* 图片区域 */
.card-img {
  order: -1; /* 图片在上方 */
  display: flex;
  justify-content: center;
  align-items: center;
  padding: 15px;
  background-color: #f8f9fa;
  border-bottom: 1px solid #eee;
}

.card-img img {
  max-width: 100%;
  height: 120px;
  object-fit: contain;
  transition: transform 0.3s ease;
}

.card-img img:hover {
  transform: scale(1.05);
}

/* 复选框样式 */
input[type="checkbox"] {
  width: 18px;
  height: 18px;
  margin-right: 8px;
  cursor: pointer;
  accent-color: #3498db;
}

/* 按钮基础样式 */
button {
  padding: 8px 16px;
  border: none;
  border-radius: 6px;
  cursor: pointer;
  font-size: 0.9rem;
  font-weight: 500;
  transition: all 0.3s ease;
}

/* 查看详情按钮 */
.card-text a button {
  background-color: #3498db;
  color: white;
}

.card-text a button:hover {
  background-color: #2980b9;
}

/* 单独结算按钮 */
.card-text button:nth-of-type(2) {
  background-color: #2ecc71;
  color: white;
}

.card-text button:nth-of-type(2):hover {
  background-color: #27ae60;
}

/* 移出购物车按钮 */
.card-text button:nth-of-type(3) {
  background-color: #e74c3c;
  color: white;
}

.card-text button:nth-of-type(3):hover {
  background-color: #c0392b;
}

/* 数量输入框 */
input[type="number"] {
  padding: 8px 12px;
  border: 1px solid #ddd;
  border-radius: 4px;
  width: 70px;
  margin: 5px 0;
}

input[type="number"]:focus {
  outline: none;
  border-color: #3498db;
  box-shadow: 0 0 0 2px rgba(52, 152, 219, 0.2);
}

/* 底部结算区域 */
.product-grid + p {
  text-align: center;
  font-size: 1.3rem;
  margin: 30px 0 15px;
  color: #e74c3c;
  font-weight: bold;
}

.product-grid + p + button {
  display: block;
  margin: 0 auto 40px;
  padding: 12px 30px;
  background: linear-gradient(135deg, #f39c12, #e67e22);
  color: white;
  font-size: 1.1rem;
  font-weight: 600;
  border-radius: 8px;
  box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
  transition: all 0.3s ease;
}

.product-grid + p + button:hover {
  transform: translateY(-2px);
  box-shadow: 0 6px 15px rgba(230, 126, 34, 0.3);
}

/* 响应式设计 */
@media (max-width: 768px) {
  .product-grid {
    grid-template-columns: 1fr;
    gap: 20px;
  }
  
  .card-img {
    height: 150px;
  }
  
  .card-text {
    padding: 15px;
  }
}

/* 标签样式 */
label {
  font-size: 0.9rem;
  color: #555;
  margin-top: 5px;
  display: block;
}
</style>