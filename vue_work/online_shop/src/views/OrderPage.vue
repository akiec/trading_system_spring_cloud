<template>
    <div class="page">
        <img src="../assets/commodity.png">
        <form class="order-content">
            <h1>订单编号：{{ order }}</h1>
            <h1 id="goods_id" @click="searchProduct(goods)">产品编号：{{ goods }}</h1>
            <h1>卖家编号：{{ product }}</h1>
            <h1>商品数量：{{ count }}</h1>
            <h1>交易总额：{{ totalPrice }}</h1>
            <h1>收货地址：{{ address }}</h1>
            <h1>状态码：{{ status }}</h1>
        </form>
    </div>
</template>

<script setup>
    import { useRouter } from 'vue-router';
    defineProps(['order', 'goods', 'product', 'count', 'totalPrice', 'address', 'status'])
    const router = useRouter()
    function searchProduct(product) {
        console.log(product)
        router.push({
            path: '/commodity',
            query: {
                product_id: product
            }
        })
    }
</script>

<style scoped>
    img {
        border: 1px solid #ddd;
        border-radius: 8px;
        padding: 1rem;
        transition: transform 0.3s;
        width: 30%;
    }

    .page {
        display: flex;
        justify-content: left;
    }

    .order-content {
        padding: 20px;
        line-height: 100px;
    }

    #goods_id {
        font-style: italic;
    }

    #goods_id:hover {
        color: orange;
        text-decoration: underline;
    }
</style>