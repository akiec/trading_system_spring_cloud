<template>
    <div class="page">
        <img src="../assets/commodity.png">
        <form class="order-content">
            <h1>订单编号：{{ order }}</h1>
            <h1 id="product_id" @click="searchProduct(product)">产品编号：{{ product }}</h1>
            <h1>卖家编号：{{ seller }}</h1>
        </form>
    </div>
</template>

<script setup>
    import { useRouter } from 'vue-router';
    defineProps(['order', 'product', 'seller'])
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

    #product_id {
        font-style: italic;
    }

    #product_id:hover {
        color: orange;
        text-decoration: underline;
    }
</style>