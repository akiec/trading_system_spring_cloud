<script setup>
import axios from 'axios';
import { useRoute } from 'vue-router';
import { useAuthStore } from '../stores/auth';
const authStore = useAuthStore()
const route = useRoute()
const goodsId = route.query.product_id
//根据商品id调数据库查询商品其余详细信息
async function getProductById() {
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
function addToCart() {
  //登录校验
  if(!authStore.isLogged){
    alert("未登录，将跳转至登录界面!")
    router.push('/login')
  }  
  const userid = authStore.currentUserId
  if (addGoods(userid)) {
    //提示弹窗
    alert("已加入购物车")
  } else {
    console.log("加入失败")
  }
}
async function addGoods(userid) {
  const url = "http://localhost:8080"
    const Goods = await getProductById()
    console.log("查询成果")
  console.log(Goods)
  try {
    
    let response = await axios.post(url + `/shopCart/add/${(userid)}`, Goods.data.data)
      return true;
    
  } catch (error) {
    console.error('搜索失败:', error)
      return false;
  }
}
//立即购买
function buy() {
    
}
</script>

<template>
    <h1>店铺名称</h1>
    <h2>你选中了第{{this.$route.query.product_id}}件商品</h2>
    <div class="main">
        <div class="view"> 
            <img src="/src/assets/commodity.png" alt="商品图片" width=120px heighth=auto></img>
        </div>
        <div class="content">
            <div class="detail">
                <p>商品名称</p>
                <p>商品详细信息</p>
                <p>商品价格</p>
                <p>购买数量</p>
            </div>
            <div class="button">
                <button class="button_buy">立即购买</button>
                <button @click="addToCart()" class="button_addToCart">加入购物车</button>
            </div>
        </div>
    </div>
</template>

<style scoped>
.main {
    display: flex;
    flex-direction: row;
    justify-content: space-around;
}
.view {
    border: 1px solid #ddd;
}
.detail {
    display: flex;
    flex-direction: column;
    justify-content: space-around;
    border: 1px solid #ddd;
    line-height: 400%;
}
.button_buy {
    background-color:red;
    color: white ;
    padding: 20px;
    border-radius: 20px 0px 0px 20px;
}
.button_addToCart {
    background-color:rgb(247, 166, 53); 
    color: white;
    padding: 20px;
    border-radius: 0px 20px 20px 0px;
}
</style>