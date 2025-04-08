<script setup>
    import { useAuthStore } from '../stores/auth';
    import { onMounted } from 'vue';
    import { useRouter } from 'vue-router';
    const router = useRouter()
    // 模拟数据
    const mockProducts = [
        { product_id: 1, product_name: "商品1", price: 99.99, stock: 10 ,img: "/src/assets/commodity.png"},
        { product_id: 2, product_name: "商品2", price: 199.99, stock: 5 ,img: null},
        { product_id: 3, product_name: "商品3", price: 99.99, stock: 10 ,img: "/src/assets/vue.svg"},
        { product_id: 4, product_name: "商品4", price: 99.99, stock: 10 ,img: "/src/assets/commodity.png"},
        { product_id: 5, product_name: "商品5", price: 99.99, stock: 10 ,img: "/src/assets/commodity.png"},
        { product_id: 6, product_name: "商品6", price: 99.99, stock: 10 ,img: "/src/assets/commodity.png"},
        { product_id: 7, product_name: "商品7", price: 99.99, stock: 10 ,img: "/src/assets/commodity.png"},
        { product_id: 8, product_name: "商品8", price: 99.99, stock: 10 ,img: "/src/assets/commodity.png"},
        { product_id: 9, product_name: "商品9", price: 99.99, stock: 10 ,img: "/src/assets/commodity.png"},
        { product_id: 10, product_name: "商品10", price: 99.99, stock: 10 ,img: "/src/assets/commodity.png"},
        { product_id: 11, product_name: "商品11", price: 99.99, stock: 10, img: "/src/assets/commodity.png" },
        { product_id: 12, product_name: "商品12", price: 99.99, stock: 10 ,img: "/src/assets/commodity.png"},
    ];
    const authStore = useAuthStore()

    onMounted(() => {//弹窗登录警告函数
        if(!authStore.isLogged){
            alert("未登录，将跳转至登录界面!")
            router.push('/login')
        }  
    }) 
    function buy() {//商品结算函数
        
    }
    function selected() {//选择该商品函数
        
    }
</script>

<template>
    <div v-if="!authStore.isLogged">
        <!--todo 
        调用弹窗警告函数，跳转登录页面
        -->
    </div>

    <div class="product-grid" id="productList">
      <div v-for="product in mockProducts" class = "product-card">
        <div class = "card-text">
            <button @click="selected()">选择该商品</button>
            <h3>{{product.product_name}}</h3>
            <p>价格：{{product.price.toFixed(2)}}</p>
            <p>库存：${{product.stock}}件</p>
            <router-link :to="{path:'commodity',query:{product_id:product.product_id}}">
                <button >查看详情页</button>
            </router-link>
            <button @click="buy()">单独结算</button>
        </div>
        <div class = "card-img">
            <img src="/src/assets/commodity.png" alt="商品图片" width="80px" height="auto"></img>
        </div>
      </div>
    </div>
    <button @click="buy()">结算</button>


</template>

<style scoped>

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

</style>