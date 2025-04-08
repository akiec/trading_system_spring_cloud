<script setup>
    import { useRoute } from 'vue-router';
    const route = useRoute()
    console.log(route.query.islogged)
</script>

<template>
    <h1>hello</h1>
    <li v-if="route.query.islogged"><h1>你好</h1></li>
</template>

<style scoped></style>