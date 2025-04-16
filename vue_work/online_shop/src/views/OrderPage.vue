<template>
    <div class="page">
        <div class="product-visual">
            <img src="../assets/commodity.png" class="product-image">
        </div>
        
        <div class="order-details">
            <div class="detail-grid">
                <div class="detail-item">
                    <label>产品编号</label>
                    <p class="clickable" @click="searchProduct(goods)">{{ goods }}</p>
                </div>
                <div class="detail-item">
                    <label>卖家编号</label>
                    <p>{{ product }}</p>
                </div>
                <div class="detail-item">
                    <label>商品数量</label>
                    <p>{{ count }} 件</p>
                </div>
                <div class="detail-item">
                    <label>交易总额</label>
                    <p class="price">¥{{ totalPrice }}</p>
                </div>
                <div class="detail-item full-width">
                    <label>收货地址</label>
                    <p class="address">{{ address }}</p>
                </div>
            </div>

            <div class="action-buttons" v-if="order_info.status != 1">
                <button @click="payOrder" class="action-button pay-btn">
                    <span class="button-text">支付订单</span>
                    <span class="button-icon">✓</span>
                </button>
                <button @click="cancelOrder" class="action-button cancel-btn">
                    <span class="button-text">取消订单</span>
                    <span class="button-icon">×</span>
                </button>
            </div>
            
            <!-- 已支付状态显示提示 -->
            <div v-if="order_info.status == 1" class="status-message">
                订单已完成支付
            </div>
        </div>
    </div>
</template>

<script setup>
    import { useRouter } from 'vue-router';
    import axios from 'axios';
    import { reactive } from 'vue';
    
    const order_info = defineProps(['order', 'goods', 'product', 'count', 'totalPrice', 'address', 'status', 'customer', 'payment'])
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
            goodsId: String(order_info.goods),
            count: order_info.count,
            id: String(order_info.order),
            status: order_info.status,
            userId: order_info.customer
        }).then((response) => {
            // console.log(response)
            alert("取消成功！")
            router.push({
                path: '/profile'
            })
        }).catch((error) => {
            console.log(error)
        })
    }
    function payOrder() {
        const url = "http://localhost:8080/payment/payOrder"
        console.log(order_info)
        axios.post(url,{
            totalPrice: Number(order_info.totalPrice),
            paymentId: String(order_info.payment),
            orderId: String(order_info.order),            
            userId: String(order_info.customer),
            paymentStatus: order_info.status,
            productId: String(order_info.product)
        }).then((response) => {
            console.log(response)
            alert("支付成功！")
            router.push({
                path: '/profile'
            })
        }).catch((error) => {
            console.log(error)
        })
    }
</script>

<style scoped>
.page {
    display: grid;
    grid-template-columns: 1fr 2fr;
    gap: 2rem;
    max-width: 1200px;
    margin: 2rem auto;
    padding: 2rem;
    background: #f8f9fa;
    border-radius: 1.5rem;
    box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
}

.product-visual {
    display: flex;
    align-items: center;
    justify-content: center;
    background: white;
    border-radius: 1rem;
    padding: 1.5rem;
    box-shadow: 0 2px 4px rgba(0, 0, 0, 0.05);
}

.product-image {
    max-width: 100%;
    height: auto;
    border-radius: 0.5rem;
    transition: transform 0.3s ease;
}

.product-image:hover {
    transform: scale(1.02);
}

.order-details {
    background: white;
    padding: 2rem;
    border-radius: 1rem;
    box-shadow: 0 2px 4px rgba(0, 0, 0, 0.05);
}

.order-header {
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin-bottom: 2rem;
    padding-bottom: 1rem;
    border-bottom: 2px solid #eee;
}

.order-number {
    font-size: 1.8rem;
    color: #2c3e50;
    margin: 0;
}

.status-badge {
    padding: 0.5rem 1rem;
    border-radius: 2rem;
    font-weight: 600;
    text-transform: uppercase;
    font-size: 0.85rem;
}

/* 根据状态设置不同颜色 */
.status-badge[data-status="0"] { background: #4CAF50; color: white; } /* 已完成 */
.status-badge[data-status="1"] { background: #2196F3; color: white; } /* 进行中 */
.status-badge[data-status="2"] { background: #f44336; color: white; } /* 已取消 */

.detail-grid {
    display: grid;
    grid-template-columns: repeat(2, 1fr);
    gap: 1.5rem;
    margin-bottom: 2rem;
}

.detail-item {
    background: #f8f9fa;
    padding: 1.2rem;
    border-radius: 0.5rem;
}

.detail-item label {
    display: block;
    font-size: 0.9rem;
    color: #7f8c8d;
    margin-bottom: 0.5rem;
}

.detail-item p {
    margin: 0;
    font-size: 1.1rem;
    color: #34495e;
    font-weight: 500;
}

.price {
    color: #e74c3c;
    font-size: 1.3rem !important;
}

.address {
    line-height: 1.6;
}

.clickable {
    cursor: pointer;
    color: #3498db !important;
    transition: color 0.2s;
}

.clickable:hover {
    color: #2980b9 !important;
    text-decoration: underline;
}

.action-buttons {
    display: flex;
    gap: 1rem;
    margin-top: 2rem;
    justify-content: flex-end;
}

/* 通用按钮样式调整 */
.action-button {
    display: inline-flex;
    align-items: center;
    padding: 0.8rem 1.5rem;
    border: none;
    border-radius: 0.5rem;
    cursor: pointer;
    transition: all 0.3s ease;
    font-weight: 600;
    min-width: 120px;
    justify-content: space-between;
}

/* 支付按钮样式 */
.pay-btn {
    background: linear-gradient(135deg, #4CAF50 0%, #45a049 100%);
    color: white;
}

.pay-btn:hover {
    box-shadow: 0 4px 6px rgba(76, 175, 80, 0.3);
    transform: translateY(-2px);
}

/* 取消按钮样式调整 */
.cancel-btn {
    background: linear-gradient(135deg, #ff6b6b 0%, #ff5252 100%);
    color: white;
}

.cancel-btn:hover {
    box-shadow: 0 4px 6px rgba(255, 82, 82, 0.3);
    transform: translateY(-2px);
}

.button-icon {
margin-left: 0.8rem;
font-size: 1.2rem;
font-weight: 700;
}

.status-message {
    margin-top: 2rem;
    padding: 1rem;
    background: #e8f5e9;
    border: 1px solid #a5d6a7;
    border-radius: 0.5rem;
    color: #2e7d32;
    text-align: center;
    font-weight: 500;
}

/* 响应式设计 */
@media (max-width: 768px) {
    .action-buttons {
        flex-direction: column;
    }
    
    .action-button {
        width: 100%;
        justify-content: center;
    }
    
    .button-icon {
        margin-left: 0.5rem;
    }

    .page {
        grid-template-columns: 1fr;
        padding: 1rem;
    }
    
    .detail-grid {
        grid-template-columns: 1fr;
    }
    
    .order-header {
        flex-direction: column;
        align-items: flex-start;
    }
    
    .status-badge {
        margin-top: 1rem;
    }
}
</style>