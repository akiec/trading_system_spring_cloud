<script setup>
//商品上传页面
import axios from 'axios';
function add() {
    console.log("开始上传");
    const url = "https://127.0.0.1/admin/add";
    console.log(document.getElementById("commodity_name"));
    const commodity_information = {
        goodsId: document.getElementById("commodity_name").value,
        name: document.getElementById("commodity_id").value,
        description: document.getElementById("commodity_description").value,
        price:document.getElementById("commodity_price").value,
        stock:document.getElementById("commodity_stock").value,
        image:document.getElementById("commodity_image").value,
        category: document.getElementById("commodity_category").value,
        createTime: 1,
        updateTime: 1,
        version: 1,
        productId: 1,
    }
    console.log(commodity_information);
    axios.post(url,commodity_information)
    .then(function (response) {
        console.log(response);
    })
    .catch(function (error) {
        console.log(error);
    });
    console.log("上传成功");
}
function show() {
    console.log("开始查询");
    const url = "127.0.0.1:8080/goods"    
    axios.get(url).then(function (response) {// 成功处理
        console.log(response);
    }).catch(function (error) {// 错误处理
        console.log(error);
    }).finally(function () {// 总是执行
    });
    console.log("查询成功");
}
</script>

<template>
    <ul class="commodity_detail">
        <p>商品名:</p><input name="" id="commodity_name"></input>
        <p>商品id:</p><textarea name="" id="commodity_id"></textarea>
        <p>商品描述:</p><textarea name="" id="commodity_description"></textarea>
        <p>商品价格:</p><textarea name="" id="commodity_price"></textarea>
        <p>商品库存:</p><textarea name="" id="commodity_stock"></textarea>
        <p>商品图片:</p><textarea name="" id="commodity_image"></textarea>
        <p>商品种类:</p><textarea name="" id="commodity_category"></textarea>
    </ul>
    <button @click="add()">确认添加信息</button>
    <button @click="show()">查询信息</button>
</template>

<style scoped>

.commodity_detail {
    display: flex;
}
</style>