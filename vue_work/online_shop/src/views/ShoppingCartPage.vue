<script setup>
    import { useAuthStore } from '../stores/auth';

    const authStore = useAuthStore()
</script>

<template>
    <h1>hello</h1>
    <div v-show="authStore.isLogged"><h1>你好</h1></div>
</template>

<style scoped></style>