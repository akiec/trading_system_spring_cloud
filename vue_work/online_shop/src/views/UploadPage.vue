<script setup>
//商品上传页面
import axios from 'axios';

import { useAuthStore } from '../stores/auth';
const authStore = useAuthStore()
function add() {
    console.log("开始上架");
    const url = "http://localhost:8080/admin/add";
    axios.post(url+`/${authStore.currentUserId}` ,{
        name: document.getElementById("commodity_name").value,
        description: document.getElementById("commodity_description").value,
        price:document.getElementById("commodity_price").value,
        stock:document.getElementById("commodity_stock").value,
        image:document.getElementById("commodity_image").value,
        category: document.getElementById("commodity_category").value,
    })
    .then(function (response) {
        console.log(response);
    })
    .catch(function (error) {
        console.log(error);
    });
}
function del(id){
    console.log("开始下架");
    const url = "http://localhost:8080/admin/delete";
    axios.post(url,{
        goodsId: id     
    })
    .then(function (response) {
        console.log(response);
        console.log("下架成功")
    })
    .catch(function (error) {
        console.log(error);
    });
}
function alter() {
    console.log("开始上传");
    const url = "http://localhost:8080/admin/updateGoods";
    axios.post(url,{
        name: document.getElementById("commodity_name").value,
        description: document.getElementById("commodity_description").value,
        price:document.getElementById("commodity_price").value,
        stock:document.getElementById("commodity_stock").value,
        image:document.getElementById("commodity_image").value,
        category: document.getElementById("commodity_category").value,
    })
    .then(function (response) {
        console.log(response);
    })
    .catch(function (error) {
        console.log(error);
    });
}
function show() {
    console.log("开始上传");
    const url = "http://localhost:8080/admin/add";
    axios.post(url,{
        name: document.getElementById("commodity_name").value,
        description: document.getElementById("commodity_description").value,
        price:document.getElementById("commodity_price").value,
        stock:document.getElementById("commodity_stock").value,
        image:document.getElementById("commodity_image").value,
        category: document.getElementById("commodity_category").value,
    })
    .then(function (response) {
        console.log(response);
    })
    .catch(function (error) {
        console.log(error);
    });
}

</script>

<template>
    <ul class="commodity_detail">
        <p>商品名:</p><input name="" id="commodity_name"></input>
        <p>商品描述:</p><input name="" id="commodity_description"></input>
        <p>商品价格:</p><input name="" id="commodity_price"></input>
        <p>商品库存:</p><input name="" id="commodity_stock"></input>
        <p>商品图片:</p><input name="" id="commodity_image"></input>
        <p>商品种类:</p><input name="" id="commodity_category"></input>
    </ul>
    <button @click="add()">确认添加信息</button>
    <router-link :to="{path:'home'}">
        <button class="button_search">返回主页</button>
    </router-link>
    <router-link :to="{path:'profile'}">
        <button class="button_search">返回个人信息页</button>
    </router-link>
    
</template>

<style scoped>

.commodity_detail {
    display: flex;
}
</style>