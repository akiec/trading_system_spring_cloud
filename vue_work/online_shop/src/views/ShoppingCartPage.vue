<script setup>
import { useAuthStore } from '../stores/auth';
import { ref,onMounted } from 'vue';
import { useRouter } from 'vue-router';
import axios from 'axios';
const router = useRouter()
const authStore = useAuthStore()
const Products = ref([])
let goodsList = []
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
function buy() {//商品结算函数
    
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
  console.log(goodsList)
}
function remove() {//移除购物车

}
onMounted( async() => {//弹窗登录警告函数
    if(!authStore.isLogged){
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
            <button @click="selected(product.goodsId)">选择该商品</button>
            <h3>{{product.name}}</h3>
            <p>价格：{{product.price}}</p>
            <p>库存：{{product.stock}}件</p>
            <router-link :to="{path:'commodity',query:{product_id:product.goodsId}}">
                <button >查看详情页</button>
            </router-link>
            <button @click="buy()">单独结算</button>
            <button @click="remove()">移出购物车</button>
        </div>
        <div class = "card-img">
            <img src="/src/assets/commodity.png" alt="商品图片" width="80px" height="auto"></img>
        </div>
      </div>
    </div>
    <button @click="buy()">结算所选的商品</button>


</template>

<style scoped>


/* 商品网格容器 */
.product-grid {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(280px, 1fr));
  gap: 24px;
  padding: 20px;
  max-width: 1200px;
  margin: 0 auto;
}

/* 商品卡片样式 */
.product-card {
  display: flex;
  flex-direction: column;
  border-radius: 12px;
  overflow: hidden;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.08);
  transition: all 0.3s ease;
  background: white;
  height: 100%;
}

.product-card:hover {
  transform: translateY(-5px);
  box-shadow: 0 8px 16px rgba(0, 0, 0, 0.12);
}

/* 卡片内容区域 */
.card-text {
  padding: 16px;
  display: flex;
  flex-direction: column;
  flex-grow: 1;
}

.card-img {
  padding: 16px;
  text-align: center;
  background: #f9f9f9;
  border-top: 1px solid #eee;
}

.card-img img {
  border-radius: 8px;
  object-fit: cover;
  transition: transform 0.3s;
}

.product-card:hover .card-img img {
  transform: scale(1.03);
}

/* 文字样式 */
h3 {
  margin: 0 0 8px 0;
  font-size: 18px;
  font-weight: 600;
  color: #333;
}

p {
  margin: 4px 0;
  font-size: 14px;
  color: #666;
}

/* 按钮样式 */
button {
  margin-top: 12px;
  padding: 8px 16px;
  border: none;
  border-radius: 6px;
  font-size: 14px;
  font-weight: 500;
  cursor: pointer;
  transition: all 0.2s;
}

button:first-of-type {
  background-color: #f0f0f0;
  color: #333;
}

button:last-of-type {
  background-color: #42b983;
  color: white;
}

button:hover {
  opacity: 0.9;
  transform: translateY(-1px);
}

/* 响应式设计 */
@media (max-width: 768px) {
  body{
    .product-grid {
      grid-template-columns: repeat(auto-fill, minmax(240px, 1fr));
      gap: 16px;
      padding: 12px;
    }
  }
}

@media (max-width: 480px) {
  body{
    .product-grid {
      grid-template-columns: 1fr;
    }
    
    .product-card {
      flex-direction: row;
    }
    
    .card-img {
      border-top: none;
      border-left: 1px solid #eee;
      display: flex;
      align-items: center;
      min-width: 100px;
    }
    
    .card-img img {
      width: 100%;
      height: auto;
    }
  }
}
</style>