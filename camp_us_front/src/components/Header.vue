<template>
    <div class="headerAll">
        <div class="logo">
            <h3 @click="navigateTo('/')">CAMP US</h3>
        </div>
        <div class="menu">
            <span @click="navigateTo('/campingMain')">캠핑장 조회</span>
            <span @click="handleBookmarkClick">찜 목록 조회</span>
            <span @click="navigateTo('/reservation')">예약 조회</span>
        </div>
        <div class="login">
            <button @click="handleAuthAction">{{ isLoggedIn ? 'Logout' : 'Login' }}</button>
        </div>
    </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue';
import { useRouter } from 'vue-router';

const router = useRouter();
const token = ref(localStorage.getItem('token') || '');

const isLoggedIn = computed(() => !!token.value);

const navigateTo = (path) => {
    router.push(path);
};

const handleAuthAction = () => {
    if (isLoggedIn.value) {
        // 로그아웃 로직
        localStorage.removeItem('token');
        token.value = '';
        alert('로그아웃 성공!');
        router.push('/');
    } else {
        // 로그인 페이지로 이동
        navigateTo('/login');
    }
};

const handleBookmarkClick = () => {
    if (!isLoggedIn.value) {
        alert('로그인 이후 사용 가능합니다.');
        navigateTo('/login');
    } else {
        navigateTo('/bookmarks');
    }
};

onMounted(() => {
    token.value = localStorage.getItem('token') || '';
});
</script>

<style scoped>
.headerAll {
    display: grid;
    grid-template-columns: 100px auto 100px;
    background-color: #E6E6E6;
    height: 70px;
}

.logo {
    align-self: center;
    justify-self: center;
    cursor: pointer;
}

.logo h3{
    margin: 0;
}

.menu {
    align-self: center;
}

.menu span{
    margin-left: 20px;
    cursor: pointer; 
    transition: color 0.1s;
}

.menu span:hover {
    color: #0056b3; 
}

.login {
    align-self: center;
    justify-self: center;
}

.login button {
    padding: 10px 20px;
    border: none;
    border-radius: 5px;
    background-color: #007BFF;
    color: white;
    cursor: pointer;
    transition: background-color 0.3s;
}

.login button:hover {
    background-color: #0056b3;
}
</style>
