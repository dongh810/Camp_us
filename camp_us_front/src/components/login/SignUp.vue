<template>
    <div class="signupAll">
        <div class="signup-box">
            <h2>회원가입</h2>
            <form @submit.prevent="handleSignup">
                <div class="input-group">
                    <label for="email">이메일</label>
                    <input type="email" id="email" v-model="email" required />
                </div>
                <div class="input-group">
                    <label for="password">비밀번호</label>
                    <input type="password" id="password" v-model="password" required />
                </div>
                <div class="input-group">
                    <label for="name">이름</label>
                    <input type="text" id="name" v-model="name" required />
                </div>
                <div class="input-group">
                    <label for="nickname">닉네임</label>
                    <input type="text" id="nickname" v-model="nickname" required />
                </div>
                <div class="input-group">
                    <label for="birthDay">생년월일</label>
                    <input type="date" id="birthDay" v-model="birthDay" required />
                </div>
                <div class="input-group">
                    <label for="phoneNumber">전화번호</label>
                    <input type="tel" id="phoneNumber" v-model="phoneNumber" required />
                </div>
                <div class="input-group">
                    <label for="userAddr">주소</label>
                    <input type="text" id="userAddr" v-model="userAddr" required />
                </div>
                <button type="submit">회원가입</button>
            </form>
            <p class="login-link">이미 계정이 있으신가요? <a @click="navigateToLogin">로그인</a></p>
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
const name = ref('');
const nickname = ref('');
const birthDay = ref('');
const phoneNumber = ref('');
const userAddr = ref('');

const handleSignup = async() => {
    try {
        const response = await axios.post('/api/v1/users', {
            email: email.value,
            password: password.value,
            name: name.value,
            nickname: nickname.value,
            birthDay: birthDay.value,
            phoneNumber: phoneNumber.value,
            userAddr: userAddr.value,
        });
        console.log(response);
        if(response.status === 200) {
            alert('회원가입 성공!');
            router.push('/login');
        }
    } catch (error) {
        console.error('Error:', error);
        alert('회원가입 실패. 다시 시도해주세요.');
    }
};

const navigateToLogin = () => {
    router.push('/login');
};
</script>

<style scoped>
.signupAll {
    display: flex;
    justify-content: center;
    align-items: center;
    height: 100vh;
}

.signup-box {
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
    width: 100%; /* 그룹의 너비를 form 안으로 제한 */
    box-sizing: border-box; /* 패딩 포함 */
}

.input-group label {
    display: block;
    margin-bottom: 5px;
    color: #555;
}

.input-group input {
    width: 100%; /* 입력 필드가 부모 요소의 너비에 맞도록 조정 */
    padding: 10px;
    border: 1px solid #ddd;
    border-radius: 5px;
    box-sizing: border-box; /* 패딩 포함 */
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

.login-link {
    margin-top: 15px;
    color: #555;
}

.login-link a {
    color: #007BFF;
    cursor: pointer;
    text-decoration: none;
}

.login-link a:hover {
    text-decoration: underline;
}
</style>
