<script setup>
import { ref } from 'vue';
const count = ref(0)
function updateProfile() {
  count.value++
  console.log(count.value)
}
</script>

<template>
  <div id="profile" class="page">
        <div class="auth-form">
            <h2>个人信息</h2>
            <div class="form-group">
                <label>用户名：</label>
                <span id="profileUsername">{{ count }}</span>
            </div>
            <div class="form-group">
                <label>手机号：</label>
                <input type="text" id="profilePhone">
            </div>
            <button @click="updateProfile()">更新信息</button>
        </div>
    </div>    
</template>

<style>
.auth-form {
    max-width: 400px;
    margin: 2rem auto;
    padding: 2rem;
    border: 1px solid #ddd;
    border-radius: 8px;
}

.form-group {
    margin-bottom: 1rem;
}

input[type="text"] {
    width: 100%;
    padding: 0.5rem;
    margin-top: 0.3rem;
}
button {
    background: skyblue;
    color: black;
    border: none;
    padding: 0.5rem 1rem;
    border-radius: 4px;
    cursor: pointer;
}
</style>