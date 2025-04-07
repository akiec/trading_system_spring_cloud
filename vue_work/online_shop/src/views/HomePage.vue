<script setup>
// 模拟数据
const mockProducts = [
    { product_id: 1, product_name: "商品1", price: 99.99, stock: 10 },
    { product_id: 2, product_name: "商品2", price: 199.99, stock: 5 }
];/*
// 商品列表渲染
const container = document.getElementById('productList');
container.innerHTML = Products.map(product => `
    <div class="product-card">
        <h3>${product.product_name}</h3>
        <p>价格：¥${product.price.toFixed(2)}</p>
        <p>库存：${product.stock}件</p>
        <button onclick="addToCart(${product.product_id})">加入购物车</button>
    </div>
`).join('');
*/
/**
 * 渲染产品列表
*/
 function renderProducts(products, container) {
    // 清空容器
    container.textContent = '';
    
    // 创建文档片段减少重绘
    const fragment = document.createDocumentFragment();
    
    products.forEach(product => {
        const card = createProductCard(product);
        fragment.appendChild(card);
    });
    
    container.appendChild(fragment);
}

/**
 * 创建单个产品卡片
 * @param {Object} product - 产品对象
 * @returns {HTMLElement} 产品卡片元素
 */
function createProductCard(product) {
    const card = document.createElement('div');
    card.className = 'product-card';
    
    // 安全地设置内容
    const name = document.createElement('h3');
    name.textContent = product.product_name;
    
    const price = document.createElement('p');
    price.textContent = `价格：¥${product.price.toFixed(2)}`;
    
    const stock = document.createElement('p');
    stock.textContent = `库存：${product.stock}件`;
    
    const button = document.createElement('button');
    button.textContent = '加入购物车';
    button.addEventListener('click', () => addToCart(product.product_id));
    
    // 组装卡片
    card.append(name, price, stock, button);
    
    return card;
}

// 使用示例
const productListContainer = document.getElementById('productList');
renderProducts(mockProducts, productListContainer);

/*
// 使用示例
const productListContainer = document.getElementById('productList');
renderProducts(mockProducts, productListContainer);*/

</script>

<template>
  <div id="home" class="page">
    <p>Hello!</p>
  </div>
</template>

<style>
  /* 商品卡片 *//*
  .product-grid {
    display: grid;
    grid-template-columns: repeat(auto-fill, minmax(250px, 1fr));
    gap: 2rem;
    padding: 2rem;
  }

  .product-card {
    border: 1px solid #ddd;
    border-radius: 8px;
    padding: 1rem;
    transition: transform 0.3s;
  }

  .product-card:hover {
    transform: translateY(-5px);
}
*/
</style>