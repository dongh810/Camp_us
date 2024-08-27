<template>
    <div class="campingMainAll">
        <div class="campingTitle">
            <h1>리뷰 조회할 캠핑장 선택</h1>
        </div>
        <div class="campingCardContainer">
            <div class="campingCard" v-for="(camping, index) in campingList" :key="index"
            @click="selectCamping(camping.campId)">
                <div class="campingImageContainer">
                    <img :src="camping.firstImageUrl ? camping.firstImageUrl : defaultImage" alt="Camping Image" class="campingImage"/>
                </div>
                <div class="campingInfo">
                    <h2>{{ camping.campName }}</h2>
                    <p>{{ camping.lineIntro }}</p>
                </div>
            </div>
        </div>
        <div class="pagination">
            <button @click="goToPreviousPage" :disabled="currentPage === 0">Previous</button>
            <span>Page {{ currentPage }}</span>
            <button @click="goToNextPage" >Next</button>
        </div>
    </div>
</template>

<script setup>
import { onMounted, ref } from 'vue';
import axios from 'axios';
import { useRouter } from 'vue-router'; 
import defaultImage from '@/assets/imageX.jpg';

const currentPage = ref(0);
const totalPages = ref(0);
const campingList = ref([]);
const campId = ref(null);
const router = useRouter();

const getCampingList = async () => {
    try {
        const response = await axios.get('/api/v1/campings', {
            params: {
                page: currentPage.value, // API 요청 시 페이지 번호는 1부터 시작하게 전송
                size: 12
            }
        });
        campingList.value = response.data; 
    } catch(error) {
        console.error("Error:", error);
    }
};

const selectCamping = (id) => {
  campId.value = id;  // campId 업데이트
  console.log('Selected campId:', campId.value);  // 확인을 위한 콘솔 출력
  router.push({ name: 'Review', query: { campId: id } });
};

const goToNextPage = () => {
        currentPage.value++;
        getCampingList(); // 페이지 변경 후 다시 데이터 로드
};

const goToPreviousPage = () => {
    if (currentPage.value > 0) {
        currentPage.value--;
        getCampingList(); // 페이지 변경 후 다시 데이터 로드
    }
};


onMounted(() => {
    getCampingList();
});
</script>

<style scoped>
.campingTitle {
    text-align: center;
    margin-top: 3%;
}

.campingCardContainer {
    display: flex;
    flex-wrap: wrap;
    justify-content: center;
    gap: 50px;
    padding: 20px;
}

.campingCard {
    background-color: white;
    border-radius: 10px;
    box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
    overflow: hidden;
    width: 300px;
    text-align: center;
}

.campingImage {
    width: 100%;
    height: 200px;
    object-fit: cover;
}

.campingInfo {
    padding: 20px;
}

.pagination {
    text-align: center;
    margin-top: 20px;
    margin-bottom: 20px;
}

.pagination button {
    padding: 10px 20px;
    margin: 0 10px;
    border: none;
    border-radius: 5px;
    background-color: #007BFF;
    color: white;
    cursor: pointer;
}

.pagination button:disabled {
    background-color: #cccccc;
    cursor: not-allowed;
}
</style>