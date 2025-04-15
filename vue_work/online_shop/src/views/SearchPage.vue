<script setup>
//通过query传入content参数作为搜索依据，随后显示对应搜索结果
import { ref, onMounted, watchEffect, watch } from 'vue';
import axios from 'axios';
import { useRoute } from 'vue-router';
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
//加入购物车
function addToCart() {
  //提示弹窗
  alert("已加入购物车");
  //加入逻辑
}
function search() {
  console.log("开始查询");
  const url = "http://localhost:8080/goods"    
  axios.post(url).then(function (response) {// 成功处理
    console.log(response.data.data);
    Products.value = response.data.data;
    console.log("查询成功");
  }).catch(function (error) {// 错误处理
    Products.value = mockProducts;
    console.log(error);
  }).finally(function () {// 总是执行
  });
}

async function searchGoods() {
  const url = "http://localhost:8080"
  try {
    console.log(url+`/goods/search/${content}`)
    // 1. 首先尝试名称搜索
    
    let response = await axios.post(url + `/goods/search/${String(content)}`)
    if (response.data.data.length > 0) return response.data.data
    console.log("名称检索无结果")
    // 2. 如果没有结果，尝试类别搜索
    response = await axios.post(url + `/goods/type/${String(content)}`)
    if (response.data.data.length > 0) return response.data.data
    console.log("类别检索无结果")
    // 3. 最后尝试全部检索
    response = await axios.post(url + '/goods', {
      search: content, page: currentPage.value
    })

    return response.data.data
    
  } catch (error) {
    console.error('搜索失败:', error)
    return mockProducts
  }
}

const Products = ref([])
const route = useRoute()
const content = route.query.content
const currentPage = ref(route.query.page)
console.log(Number(currentPage.value)+1)
console.log(content)//输入的搜索信息

onMounted( async() => {
  Products.value = await searchGoods();
})

watch(
  () => ({
    content: route.query.content,
    page: route.query.page || 0
  }),
  async (newValue, oldValue) => {
    try {
      if (newValue.content != oldValue.content) { 
        Products.value = await searchGoods()
      }
      if (newValue.page != oldValue.page) {
        currentPage.value = Number(page) || 0
      }
    } catch (error) {
      console.error('搜索失败:', error)
    }
  }
)

</script>

<template>
    <div class="product-grid" id="productList">
      <div v-for="product in Products" class = "product-card">
        <div class = "card-text">
            <h3>{{product.name}}</h3>
            <p>价格：{{product.price}}</p>
            <p>库存：{{product.stock}}件</p>
            <router-link :to="{path:'commodity',query:{product_id:product.goodsId}}">
                <button >查看详情页</button>
            </router-link>
            <button @click="addToCart()">加入购物车</button>
        </div>
        <div class = "card-img">
            <img src="/src/assets/commodity.png" alt="商品图片" width="80px" height="auto"></img>
        </div>
      </div>
    </div>
    <router-link :to="{path:'search',query:{content:content,page:Number(currentPage)-1}}">
        <button >上一页</button>
    </router-link>
    <router-link :to="{path:'search',query:{content:content,page:Number(currentPage)+1}}">
        <button >下一页</button>
    </router-link>

</template>

<style scoped>
/*
.product-card {
    display: flex;
    box-shadow: 8px 8px 8px rgba(0,0,0,0.4);
  }
  .card-text {
    border: 1px solid #ddd;
    border-radius: 8px;
    padding: 1rem;
    transition: transform 0.3s;
    align-items: stretch;
  }
  .card-img {
    border: 1px solid #ddd;
    border-radius: 8px;
    padding: 1rem;
    transition: transform 0.3s;
  }

  .product-card:hover {
    transform: translateY(-5px);
    box-shadow: 8px 18px 18px rgba(0,0,0,0.3);
  }
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