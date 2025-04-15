<script setup>
import { RouterLink} from 'vue-router';
import { ref, onMounted } from 'vue';
import axios from 'axios';
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
const Products = ref([])
async function searchGoods() {
  const url = "http://localhost:8080"
  try {
    let response = await axios.post(url+'/goods')
    return response.data.data
    
  } catch (error) {
    console.error('搜索失败:', error)
    return mockProducts
  }
}
onMounted( async() => {
  Products.value = await searchGoods();
})

</script>

<template>
  <div id="home" class="page">
    <div class = "product-recommend">
      <div class="product-category">
        <h2 class="search_sheet">商品分类</h2>
          <router-link :to="{path:'search',query:{content:'服装',page:1}}">
            <button class="button_search">服装</button>
          </router-link>
          <router-link :to="{path:'search',query:{content:'食品',page:1}}">
            <button class="button_search">食品</button>
          </router-link>
          <router-link :to="{path:'search',query:{content:'家具',page:1}}">
            <button class="button_search">家具</button>
          </router-link>
          <router-link :to="{path:'search',query:{content:'电子产品',page:1}}">
            <button class="button_search">电子产品</button>
          </router-link>
      </div>
      <div class="popular">
        <h2>热门推荐</h2>
        <div class="slide">
          <ul class="list">
            <li class="item"><img src="/src/assets/vue.svg" alt=""></li>
            <li class="item"><img src="/src/assets/vue.svg" alt=""></li>
            <li class="item"><img src="/src/assets/vue.svg" alt=""></li>
          </ul>
        </div>
      </div>
    </div>
    <div class="product-grid" id="productList">
      <div v-for="product in Products" class = "product-card">
        <div class = "card-text">
          <h3>{{product.name}}</h3>
          <p>价格：{{product.price}}</p>
          <p>库存：{{product.stock}}件</p>
          <router-link :to="{path:'commodity',query:{product_id:product.goodsId}}">
            <button >查看详情页</button>
          </router-link>
        </div>
        <div class = "card-img">
          <img src="/src/assets/commodity.png" alt="商品图片" width="80px" height="auto"></img>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
/* 基础样式 */
.page {
    font-family: 'PingFang SC', 'Microsoft YaHei', sans-serif;
    background-color: #f8f9fa;
    color: #333;
    line-height: 1.6;
    padding: 20px;
    max-width: 1200px;
    margin: 0 auto;
}

/* 商品推荐区域 */
.product-recommend {
    display: grid;
    grid-template-columns: 250px 1fr;
    gap: 30px;
    margin-top: 20px;
}

@media (max-width: 768px) {
    .product-recommend {
        grid-template-columns: 1fr;
    }
}

/* 商品分类区域 */
.product-category {
    background: white;
    border-radius: 12px;
    padding: 25px;
    box-shadow: 0 5px 15px rgba(0, 0, 0, 0.05);
    height: fit-content;
    position: sticky;
    top: 20px;
}

.product-category h2 {
    color: #2c3e50;
    font-size: 1.5rem;
    margin-bottom: 20px;
    padding-bottom: 10px;
    border-bottom: 2px solid #f1f1f1;
    position: relative;
}

.product-category h2::after {
    content: '';
    position: absolute;
    bottom: -2px;
    left: 0;
    width: 60px;
    height: 2px;
    background: linear-gradient(90deg, #3498db, #9b59b6);
}

/* 分类按钮样式 */
.button_search {
    display: block;
    width: 100%;
    padding: 12px 15px;
    margin-bottom: 12px;
    background: #f8f9fa;
    border: none;
    border-radius: 8px;
    color: #2c3e50;
    font-size: 1rem;
    font-weight: 500;
    text-align: left;
    cursor: pointer;
    transition: all 0.3s ease;
    box-shadow: 0 2px 5px rgba(0, 0, 0, 0.05);
}

.button_search:hover {
    background: linear-gradient(90deg, #f1f8ff, #e6f0ff);
    color: #3498db;
    transform: translateX(5px);
    box-shadow: 0 5px 10px rgba(52, 152, 219, 0.2);
}

/* 热门推荐区域 */
.popular {
    background: white;
    border-radius: 12px;
    padding: 25px;
    box-shadow: 0 5px 15px rgba(0, 0, 0, 0.05);
}

.popular h2 {
    color: #2c3e50;
    font-size: 1.5rem;
    margin-bottom: 20px;
    padding-bottom: 10px;
    border-bottom: 2px solid #f1f1f1;
    position: relative;
}

.popular h2::after {
    content: '';
    position: absolute;
    bottom: -2px;
    left: 0;
    width: 60px;
    height: 2px;
    background: linear-gradient(90deg, #e74c3c, #f39c12);
}

/* 轮播图区域 */
.slide {
    position: relative;
    overflow: hidden;
    border-radius: 10px;
    height: 300px;
    background: linear-gradient(135deg, #f5f7fa, #c3cfe2);
}

.list {
    display: flex;
    height: 100%;
    padding: 0;
    margin: 0;
    list-style: none;
    transition: transform 0.5s ease;
}

.item {
    min-width: 100%;
    height: 100%;
    display: flex;
    justify-content: center;
    align-items: center;
    font-size: 2rem;
    font-weight: bold;
    color: rgba(255, 255, 255, 0.8);
    text-shadow: 0 2px 5px rgba(0, 0, 0, 0.2);
}

.item:nth-child(1) {
    background: linear-gradient(135deg, #667eea, #764ba2);
}

.item:nth-child(2) {
    background: linear-gradient(135deg, #f093fb, #f5576c);
}

.item:nth-child(3) {
    background: linear-gradient(135deg, #4facfe, #00f2fe);
}

/* 动画效果 */
@keyframes slide {
    0% { transform: translateX(0); }
    30% { transform: translateX(0); }
    33% { transform: translateX(-100%); }
    63% { transform: translateX(-100%); }
    66% { transform: translateX(-200%); }
    97% { transform: translateX(-200%); }
    100% { transform: translateX(0); }
}

.slide .list {
    animation: slide 12s infinite;
}

/* 悬停时暂停动画 */
.slide:hover .list {
    animation-play-state: paused;
}

/* 响应式调整 */
@media (max-width: 576px) {
    .slide {
        height: 200px;
    }
    
    .item {
        font-size: 1.5rem;
    }
}

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