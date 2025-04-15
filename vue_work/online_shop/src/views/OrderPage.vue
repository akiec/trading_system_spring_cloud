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
        <button @click="cancelOrder" class="cancel-btn">取消订单</button>
    </div>
</template>

<script setup>
    import { useRouter } from 'vue-router';
    import axios from 'axios';
    import { reactive } from 'vue';
    const order_info = defineProps(['order', 'goods', 'product', 'count', 'totalPrice', 'address', 'status', 'customer'])
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
    function cancelOrder() {
        const url = `http://localhost:8080/order/delete/${String(order_info.order)}/${Number(order_info.status)}`
        // console.log(order_info)
        axios.delete(url,{
            goodsId: order_info.goods,
            count: order_info.count,
            id: order_info.order,
            status: order_info.status,
            userId: order_info.customer
        }).then((response) => {
            console.log(response)
            router.push({
                path: '/home'
            })
        }).catch((error) => {
            console.log(error)
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