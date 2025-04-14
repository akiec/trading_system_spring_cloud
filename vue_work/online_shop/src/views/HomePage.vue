<script setup>
import { RouterLink } from 'vue-router';
import { ref, onMounted } from 'vue';
import { useAuthStore } from '../stores/auth';
import axios from 'axios';
const authStore = useAuthStore()
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
async function getProductById(goodsId) {
    const url = "http://localhost:8080"
  console.log(url + `/goods/details/${(goodsId)}`)
  try {
      let response = await axios.post(url + `/goods/details/${(goodsId)}`)
    console.log("搜索成功")
    return response
  } catch (error) {
    console.error('失败:', error)
  }
}
//加入购物车
function addToCart(goodsId) {
  //登录校验
  if(!authStore.isLogged){
    alert("未登录，将跳转至登录界面!")
    router.push('/login')
  }  
  const userid = authStore.currentUserId
  if (addGoods(goodsId,userid)) {
    //提示弹窗
    alert("已加入购物车")
  } else {
    console.log("加入失败")
  }
}
async function addGoods(goodsId,userid) {
  const url = "http://localhost:8080"
  const Goods = await getProductById(goodsId)
  console.log("查询成果")
  console.log(Goods)
  try {
    let response = await axios.post(url + `/shopCart/add${(userid)}`, Goods.data.data)
    console.log(response)
    console.log("加入成功")  
    return true  
  } catch (error) {
    console.error('加入失败:', error)
    return false
  }
}
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
const Products = ref([])
onMounted( async() => {
  Products.value = await searchGoods();
})

</script>

<template>
  <div id="home" class="page">
    <div class = "product-recommend">
      <div class="product-category">
        <h2 class="search_sheet">商品分类</h2>
          <router-link :to="{path:'search',query:{content:'服装'}}">
            <button class="button_search">服装</button>
          </router-link>
          <router-link :to="{path:'search',query:{content:'食品'}}">
            <button class="button_search">食品</button>
          </router-link>
          <router-link :to="{path:'search',query:{content:'家具'}}">
            <button class="button_search">家具</button>
          </router-link>
          <router-link :to="{path:'search',query:{content:'电子产品'}}">
            <button class="button_search">电子产品</button>
          </router-link>
      </div>
      <div class="popular">
        <h2>热门推荐</h2>
        <div class="slide">
          <ul class="list">
            <li class="item">轮播图1</li>
            <li class="item">轮播图2</li>
            <li class="item">轮播图3</li>
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
          <button @click="addToCart(product.goodsId)">加入购物车</button>
        </div>
        <div class = "card-img">
          <img src="/src/assets/commodity.png" alt="商品图片" width="80px" height="auto"></img>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
  /* 商品卡片 */
  .product-grid  {
    display: grid;
    grid-template-columns: repeat(auto-fill, minmax(250px, 1fr));
    gap: 2rem;
    padding: 2rem;
  }
  .product-recommend {
    display: flex;
    justify-content: space-around;
    background-color:rgb(255, 236, 209);
  }
  .product-category {
    display: flex;
    align-items: center;
    flex-direction: column;
    border: 1px solid #ddd;
  }
  .popular {
    display: flex;
    border: 1px solid #ddd;
    flex-direction: column;
  }
  .slide {
    display: flex;
    flex-direction: column;
  }
  .button_search {
    padding: 10px;
    border-radius: 10px 10px 10px 10px;
  }
  
  /*
*/

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