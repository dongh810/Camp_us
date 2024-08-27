<template>
    <div class="loginAll">
        <div class="login-box">
            <h2>Camp us</h2>
            <form @submit.prevent="handleLogin">
                <div class="input-group">
                    <label for="email">이메일</label>
                    <input type="email" id="email" v-model="email" required />
                </div>
                <div class="input-group">
                    <label for="password">비밀번호</label>
                    <input type="password" id="password" v-model="password" required />
                </div>
                <button type="submit">로그인</button>
            </form>
            <div class="additional-options">
                <p class="signup-link">계정이 없으신가요? <a @click="navigateToSignup">회원가입</a></p>
                <div id="kakao-login-btn" @click="loginWithKakao">
                    <img src="https://k.kakaocdn.net/14/dn/btroDszwNrM/I6efHub1SN5KCJqLm1Ovx1/o.jpg" width="100" alt="카카오 로그인 버튼" />
                </div>
            </div>
        </div>
    </div>
</template>

<script setup>
import { ref } from 'vue';
import { useRouter } from 'vue-router';
import axios from 'axios';

const router = useRouter();

const email = ref('');
const password = ref('');
const errorMessage = ref(''); 

const handleLogin = async() => {
    try {
    const confirmed = window.confirm('로그인 하시겠습니까?');
    if(confirmed) {
        const response = await axios.post('/api/login', {
        email: email.value,
        password: password.value,
        });
        if(response.status == 200) {
            const token = response.headers.authorization.split(' ')[1];
            localStorage.setItem('token', token);
            alert('로그인 성공!');
            window.location.href = '/';
        }
    }
    } catch (error) {
        console.error('Error:', error);
        alert('잘못된 로그인 정보입니다!');
        window.location.reload();
    }
};

const loginWithKakao = () => {
    window.location.href = 'https://kauth.kakao.com/oauth/authorize?response_type=code&client_id=74c81051576be2126396ca67ae865bbf&redirect_uri=http://localhost:8080/login/oauth2/code/kakao';
};

const navigateToSignup = () => {
    router.push('/signup');
};
</script>

<style scoped>
.loginAll {
    display: flex;
    justify-content: center;
    align-items: center;
    height: 100vh;
}

.login-box {
    background-color: white;
    padding: 40px;
    border-radius: 10px;
    box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
    width: 300px;
    text-align: center;
}

h2 {
    margin-bottom: 20px;
    color: #333;
}

.input-group {
    margin-bottom: 15px;
    text-align: left;
    width: 100%;
    box-sizing: border-box;
}

.input-group label {
    display: block;
    margin-bottom: 5px;
    color: #555;
}

.input-group input {
    width: 100%;
    padding: 10px;
    border: 1px solid #ddd;
    border-radius: 5px;
    box-sizing: border-box;
}

button {
    width: 100%;
    padding: 10px;
    background-color: #007BFF;
    border: none;
    border-radius: 5px;
    color: white;
    font-size: 16px;
    cursor: pointer;
    transition: background-color 0.3s;
}

button:hover {
    background-color: #0056b3;
}

.additional-options {
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin-top: 20px;
}

.signup-link {
    margin: 0;
    color: #555;
    text-align: left;
    flex: 1; /* Flexbox에서 공간을 차지하도록 설정 */
}

.signup-link a {
    color: #007BFF;
    cursor: pointer;
    text-decoration: none;
}

.signup-link a:hover {
    text-decoration: underline;
}

#kakao-login-btn {
    text-align: right;
    flex-shrink: 0; /* 이미지 크기를 유지하도록 설정 */
}
</style>
