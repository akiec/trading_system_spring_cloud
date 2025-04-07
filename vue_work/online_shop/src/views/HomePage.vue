<script setup>
import { onMounted } from 'vue';
// 模拟数据
const mockProducts = [
    { product_id: 1, product_name: "商品1", price: 99.99, stock: 10 ,img: null},
    { product_id: 2, product_name: "商品2", price: 199.99, stock: 5 ,img: null},
    { product_id: 3, product_name: "商品3", price: 99.99, stock: 10 ,img: null},
    { product_id: 4, product_name: "商品4", price: 99.99, stock: 10 ,img: null},
    { product_id: 5, product_name: "商品5", price: 99.99, stock: 10 ,img: null},
    { product_id: 6, product_name: "商品6", price: 99.99, stock: 10 ,img: null},
    { product_id: 7, product_name: "商品7", price: 99.99, stock: 10 ,img: null},
    { product_id: 8, product_name: "商品8", price: 99.99, stock: 10 ,img: null},
    { product_id: 9, product_name: "商品9", price: 99.99, stock: 10 ,img: null},
    { product_id: 10, product_name: "商品10", price: 99.99, stock: 10 ,img: null},
    { product_id: 11, product_name: "商品11", price: 99.99, stock: 10, img: null },
    { product_id: 12, product_name: "商品12", price: 99.99, stock: 10 ,img: null},
];

function renderProducts() {
  const container = document.getElementById('productList');
  //console.log("访问成功！");
  container.innerHTML = mockProducts.map(product => `
    <div class="product-card">
      <div class = "card-text">
        <h3>${product.product_name}</h3>
        <p>价格：¥${product.price.toFixed(2)}</p>
        <p>库存：${product.stock}件</p>
        <button onclick="addToCart(${product.product_id})">查看详情页</button>
        <button onclick="addToCart(${product.product_id})">加入购物车</button>
      </div>
      <div class = "card-img">
        <img src="/src/assets/commodity.png" alt="商品图片" width=80px heighth=auto></img>
      </div>
    </div>
  `).join('');
  
}

onMounted(() => {
  //console.log("挂载完毕");
  renderProducts();
})
</script>

<template>
  <div id="home" class="page">
    <div class = "product-recommend">
      <div class="product-category">
        <h2 >商品分类</h2>
        <ul>
          <li>服装</li>
          <li>食品</li>
          <li>家具</li>
          <li>电子产品</li>
        </ul>
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
    <div class="product-grid" id="productList"></div>
  </div>
 
</template>

<style scoped>
  /* 商品卡片 */
  .product-grid {
    display: grid;
    grid-template-columns: repeat(auto-fill, minmax(250px, 1fr));
    gap: 2rem;
    padding: 2rem;
  }
  .product-recommend {
    display: flex;
    justify-content: space-around;
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
  .product-card {
    display: flex;
    box-shadow: 8px 8px 8px rgba(0,0,0,0.4);
  }
  .card-text {
    border: 1px solid #ddd;
    border-radius: 8px;
    padding: 1rem;
    transition: transform 0.3s;
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
  }/*
*/
</style>