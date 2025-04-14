<script setup>
//商品上传页面
import { ref,onMounted,computed } from 'vue';
import axios from 'axios';
// 模拟数据
const products=ref([])
const mockProducts = [
    { goodsId: 1, name: "商品1", price: 99.99, stock: 10 ,img: "/src/assets/commodity.png" , status: true, selected: false },
    { goodsId: 2, name: "商品2", price: 199.99, stock: 5 ,img: null , status: true, selected: false },
    { goodsId: 3, name: "商品3", price: 99.99, stock: 10 ,img: "/src/assets/vue.svg", status: true, selected: false },
    { goodsId: 4, name: "商品4", price: 99.99, stock: 10 ,img: "/src/assets/commodity.png", status: true, selected: false },
    { goodsId: 5, name: "商品5", price: 99.99, stock: 10 ,img: "/src/assets/commodity.png", status: true, selected: false },
    { goodsId: 6, name: "商品6", price: 99.99, stock: 10 ,img: "/src/assets/commodity.png", status: true, selected: false },
    { goodsId: 7, name: "商品7", price: 99.99, stock: 10 ,img: "/src/assets/commodity.png", status: true, selected: false },
    { goodsId: 8, name: "商品8", price: 99.99, stock: 10 ,img: "/src/assets/commodity.png", status: true, selected: false },
    { goodsId: 9, name: "商品9", price: 99.99, stock: 10 ,img: "/src/assets/commodity.png", status: true, selected: false },
    { goodsId: 10, name: "商品10", price: 99.99, stock: 10 ,img: "/src/assets/commodity.png", status: true, selected: false },
    { goodsId: 11, name: "商品11", price: 99.99, stock: 10, img: "/src/assets/commodity.png" , status: true, selected: false },
    { goodsId: 12, name: "商品12", price: 99.99, stock: 10 ,img: "/src/assets/commodity.png", status: true, selected: false },
];
import { useAuthStore } from '../stores/auth';
const authStore = useAuthStore()
function add(product) {
    console.log("开始上架");
    const url = "http://localhost:8080/admin/add";
    axios.post(url + `/${authStore.currentUserId}`, {
        product,
        productId: authStore.currentUserId,
        version:1.0
    })
    .then(function (response) {
        console.log(response);
        console.log("上架完成");
        console.log(response.data.data)
        return response.data.data;
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
function alter(product) {
    console.log("开始修改");
    const url = "http://localhost:8080/admin/updateGoods";
    axios.post(url,product)
    .then(function (response) {
        console.log(response);
        console.log("修改完成");
    })
    .catch(function (error) {
        console.log(error);
    });
}
async function show() {
  const url = "http://localhost:8080"
  try {
    let response = await axios.post(url+`/goods/search/product/${authStore.currentUserId}`)
    return response.data.data
    
  } catch (error) {
    console.error('搜索失败:', error)
    return mockProducts
  }
}
onMounted(async () => {
    products.value = await show();
})



// 对话框状态
const showAddDialog = ref(false)
const showEditDialog = ref(false)
const showDeleteConfirm = ref(false)

// 当前操作的商品
const currentProduct = ref({
  goodsId: null,
  name: '',
  price: 0,
  stock: 0,
  category: '电子产品',
  status: true
})

// 待删除的ID数组
const deleteIds = ref([])

// 计算属性
const selectedProducts = computed(() => {
  return products.value.filter(p => p.selected)
})

const selectAll = computed({
  get: () => products.value.length > 0 && products.value.every(p => p.selected),
  set: (value) => products.value.forEach(p => p.selected = value)
})

// 方法
function toggleSelectAll() {
  products.value.forEach(p => p.selected = selectAll.value)
}

function openAddDialog() {
  currentProduct.value = {
    goodsId: null,
    name: '',
    price: 0,
    stock: 0,
    category: '电子产品',
    status: true
  }
  showAddDialog.value = true
}

function editProduct(product) {
  currentProduct.value = { ...product }
  showEditDialog.value = true
}

function closeDialog() {
  showAddDialog.value = false
  showEditDialog.value = false
}

async function saveProduct() {
  if (currentProduct.value.goodsId) {
    // 更新现有商品
    const index = products.value.findIndex(p => p.goodsId === currentProduct.value.goodsId)
    if (index !== -1) {
        products.value[index] = { ...currentProduct.value }
        alter(currentProduct.value)
    }
  } else {
    // 添加新商品
    const newId = await add(currentProduct.value)
    products.value.push({
      ...currentProduct.value,
      goodsId: newId,
      selected: false
    })
  }
  closeDialog()
}

function confirmDelete(id) {
  deleteIds.value = [id]
  showDeleteConfirm.value = true
}

function deleteSelected() {
  deleteIds.value = selectedProducts.value.map(p => p.goodsId)
  showDeleteConfirm.value = true
}

function executeDelete() {
    //console.log(deleteIds)
    products.value = products.value.filter(p => !deleteIds.value.includes(p.goodsId))
    showDeleteConfirm.value = false
    for (let id of deleteIds._rawValue) {
        console.log(id)
        del(id)
    }
}



</script>

<template>
    <div class="product-management">
    <h1>商品信息管理</h1>
    
    <!-- 操作按钮区 -->
    <div class="action-buttons">
      <button @click="showAddDialog = true">新增商品</button>
      <button @click="deleteSelected" :disabled="!selectedProducts.length">
        删除选中({{ selectedProducts.length }})
      </button>
    </div>
    
    <!-- 商品表格 -->
    <div class="product-table-container">
      <table class="product-table">
        <thead>
          <tr>
            <th><input type="checkbox" v-model="selectAll" @change="toggleSelectAll"></th>
            <th>ID</th>
            <th>商品名称</th>
            <th>价格</th>
            <th>库存</th>
            <th>分类</th>
            <th>上架状态</th>
            <th>操作</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="product in products" :key="product.goodsId">
            <td><input type="checkbox" v-model="product.selected"></td>
            <td>{{ product.goodsId }}</td>
            <td>{{ product.name }}</td>
            <td>¥{{ product.price}}</td>
            <td>{{ product.stock }}</td>
            <td>{{ product.category }}</td>
            <td>
              <span :class="['status', product.status ? 'active' : 'inactive']">
                {{ product.status ? '已上架' : '已下架' }}
              </span>
            </td>
            <td>
              <button @click="editProduct(product)">编辑</button>
              <button @click="confirmDelete(product.goodsId)">删除</button>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
    
    <!-- 新增/编辑对话框 -->
    <div class="dialog-overlay" v-if="showAddDialog || showEditDialog">
      <div class="dialog">
        <h2>{{ currentProduct.goodsId ? '编辑商品' : '新增商品' }}</h2>
        <form @submit.prevent="saveProduct">
          <div class="form-group">
            <label>商品名称</label>
            <input v-model="currentProduct.name" required>
          </div>
          <div class="form-group">
            <label>价格</label>
            <input type="number" v-model.number="currentProduct.price" min="0" step="0.01" required>
          </div>
          <div class="form-group">
            <label>库存</label>
            <input type="number" v-model.number="currentProduct.stock" min="0" required>
          </div>
          <div class="form-group">
            <label>分类</label>
            <select v-model="currentProduct.category" required>
              <option value="电子产品">电子产品</option>
              <option value="服装">服装</option>
              <option value="食品">食品</option>
              <option value="家居">家居</option>
            </select>
          </div>
          <div class="form-group">
            <label>上架状态</label>
            <label class="switch">
              <input type="checkbox" v-model="currentProduct.status">
              <span class="slider round"></span>
            </label>
          </div>
          <div class="dialog-buttons">
            <button type="button" @click="closeDialog">取消</button>
            <button type="submit">保存</button>
          </div>
        </form>
      </div>
    </div>
    
    <!-- 删除确认对话框 -->
    <div class="dialog-overlay" v-if="showDeleteConfirm">
      <div class="dialog confirm-dialog">
        <p>确定要删除这{{ deleteIds.length }}个商品吗？</p>
        <div class="dialog-buttons">
          <button @click="showDeleteConfirm = false">取消</button>
          <button @click="executeDelete" class="danger">确定删除</button>
        </div>
      </div>
    </div>
  </div> 


</template>

<style scoped>

/* 基础样式 */
.product-management {
  max-width: 1200px;
  margin: 0 auto;
  padding: 20px;
  font-family: Arial, sans-serif;
}

h1 {
  color: #333;
  margin-bottom: 20px;
}

/* 操作按钮区 */
.action-buttons {
  margin-bottom: 20px;
}

.action-buttons button {
  padding: 8px 16px;
  margin-right: 10px;
  background-color: #42b983;
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}

.action-buttons button:disabled {
  background-color: #cccccc;
  cursor: not-allowed;
}

/* 表格样式 */
.product-table-container {
  overflow-x: auto;
}

.product-table {
  width: 100%;
  border-collapse: collapse;
  margin-bottom: 20px;
}

.product-table th, .product-table td {
  border: 1px solid #ddd;
  padding: 12px;
  text-align: left;
}

.product-table th {
  background-color: #f2f2f2;
  position: sticky;
  top: 0;
}

.product-table tr:nth-child(even) {
  background-color: #f9f9f9;
}

.product-table tr:hover {
  background-color: #f1f1f1;
}

.product-table input[type="checkbox"] {
  cursor: pointer;
}

/* 状态标签 */
.status {
  padding: 4px 8px;
  border-radius: 4px;
  font-size: 12px;
}

.status.active {
  background-color: #e1f3d8;
  color: #67c23a;
}

.status.inactive {
  background-color: #fde2e2;
  color: #f56c6c;
}

/* 对话框样式 */
.dialog-overlay {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background-color: rgba(0, 0, 0, 0.5);
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 1000;
}

.dialog {
  background-color: white;
  padding: 20px;
  border-radius: 8px;
  width: 500px;
  max-width: 90%;
}

.dialog h2 {
  margin-top: 0;
  color: #333;
}

.form-group {
  margin-bottom: 15px;
}

.form-group label {
  display: block;
  margin-bottom: 5px;
  font-weight: bold;
}

.form-group input,
.form-group select {
  width: 100%;
  padding: 8px;
  border: 1px solid #ddd;
  border-radius: 4px;
  box-sizing: border-box;
}

.dialog-buttons {
  display: flex;
  justify-content: flex-end;
  margin-top: 20px;
}

.dialog-buttons button {
  padding: 8px 16px;
  margin-left: 10px;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}

.dialog-buttons button:first-child {
  background-color: #f4f4f4;
}

.dialog-buttons button:last-child {
  background-color: #42b983;
  color: white;
}

.dialog-buttons .danger {
  background-color: #f56c6c;
  color: white;
}

/* 开关样式 */
.switch {
  position: relative;
  display: inline-block;
  width: 60px;
  height: 34px;
}

.switch input {
  opacity: 0;
  width: 0;
  height: 0;
}

.slider {
  position: absolute;
  cursor: pointer;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background-color: #ccc;
  transition: .4s;
  border-radius: 34px;
}

.slider:before {
  position: absolute;
  content: "";
  height: 26px;
  width: 26px;
  left: 4px;
  bottom: 4px;
  background-color: white;
  transition: .4s;
  border-radius: 50%;
}

input:checked + .slider {
  background-color: #42b983;
}

input:checked + .slider:before {
  transform: translateX(26px);
}
</style>