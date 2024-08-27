<template>
    <div class="camping-detail">
      <div class="camping-title">
        <h1>{{ campingDetail.campName }}</h1>
      </div>
      <div class="camping-main-info">
        <img :src="campingDetail.firstImageUrl ? campingDetail.firstImageUrl : defaultImage" alt="Camping Image" class="camping-image" />
        <div class="camping-summary">
          <p class="line-intro">{{ campingDetail.lineIntro }}</p>
          <p class="feature-summary">{{ campingDetail.featureSummary }}</p>
          <p><strong>📍 주소:</strong> {{ campingDetail.doName + " " + campingDetail.sigunguName }}</p>
          <p><strong>📞 전화번호:</strong> {{ campingDetail.tel }}</p>
          <p><strong>🌐 홈페이지:</strong> <a :href="campingDetail.homepage" target="_blank">{{ campingDetail.homepage }}</a></p>
          <p><strong>🏕️ 글램핑 사이트 수:</strong> {{ campingDetail.glampingSiteCnt }}</p>
          <p><strong>🚐 카라반 사이트 수:</strong> {{ campingDetail.caravanSiteCnt }}</p>
          <p><strong>❤️ 찜한 수:</strong> {{ campingDetail.bookmarkCnt }}</p>
          <p><strong>📝 리뷰 수:</strong> {{ campingDetail.reviewCnt }}</p>
        </div>
      </div>
      <div class="camping-facilities">
        <h2>캠핑장 시설</h2>
        <ul>
          <li v-for="facility in campingDetail.facilities" :key="facility.campFacsId">
            <p><strong>🏢 시설 타입:</strong> {{ facility.facsTypeId }}</p>
            <p><strong>📋 내부 시설 목록:</strong> {{ facility.internalFacilitiesList }}</p>
            <p><strong>🚻 화장실 수:</strong> {{ facility.toiletCnt }}</p>
            <p><strong>🚿 샤워실 수:</strong> {{ facility.showerRoomCnt }}</p>
            <p><strong>🧼 싱크대 수:</strong> {{ facility.sinkCnt }}</p>
            <p><strong>🔥 화로대 등급:</strong> {{ facility.brazierClass }}</p>
            <p><strong>🚙 개인 트레일러 허용 여부:</strong> {{ facility.personalTrailerStatus ? '허용' : '불허' }}</p>
            <p><strong>🏞️ 개인 카라반 허용 여부:</strong> {{ facility.personalCaravanStatus ? '허용' : '불허' }}</p>
          </li>
        </ul>
      </div>
      <div class="camping-review">
        <h2>리뷰</h2>
        <div v-if="reviews.length">
            <div v-for="(review, index) in reviews" :key="index" class="review">
            <h3>Rating: {{ review.rating }} / 5</h3>
            <p>{{ review.reviewContent }}</p>
            </div>
        </div>
        <div v-else>
            <p>리뷰가 없습니다.</p>
        </div>
      </div>
      
    
    </div>
  </template>
  
  <script setup>
  import { ref, onMounted } from 'vue';
  import axios from 'axios';
  import { useRoute } from 'vue-router';
  import defaultImage from '@/assets/imageX.jpg';

  const route = useRoute();
  const campingDetail = ref({});
  
  const getCampingDetail = async () => {
    try {
      const response = await axios.get(`/api/v1/campings/${route.params.campId}`);
      campingDetail.value = response.data;
    } catch (error) {
      console.error('Error fetching camping details:', error);
    }
  };
  
  onMounted(() => {
  getCampingDetail();
  getReviews();  // 컴포넌트가 마운트될 때 리뷰를 가져옴
});

  const reviews = ref([]);
  const showReviewForm = ref(false);
  
  const getReviews = async () => {
  const campId = route.params.campId;  // campId를 params에서 가져옴
  try {
    const response = await axios.get('/api/v1/reviews', {
      params: { campId }
    });
    reviews.value = response.data.content;
  } catch (error) {
    console.error('Error:', error);
  }
};
  
  const toggleReviewForm = () => {
    showReviewForm.value = !showReviewForm.value;
  };

  </script>
  
  <style scoped>
  .camping-detail {
    max-width: 900px;
    margin: 0 auto;
    margin-top: 5%;
    margin-bottom: 5%;
    padding: 20px;
    background-color: #f0f4f8;
    border-radius: 15px;
    box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
    
  }
  
  .camping-title {
    text-align: left;
    margin-bottom: 20px;
    font-weight: bold;
    color: #34495e;
  }
  
  .camping-main-info {
    display: flex;
    flex-wrap: wrap;
    gap: 20px;
    background-color: #ffffff;
    border-radius: 10px;
    padding: 20px;
    box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
  }
  
  .camping-image {
    width: 100%;
    max-width: 400px;
    height: auto;
    object-fit: cover;
    border-radius: 10px;
  }
  
  .camping-summary {
    flex-grow: 1;
    font-size: 1.1em;
    color: #2c3e50;
  }
  
  .camping-summary p {
    margin-bottom: 10px;
  }
  
  .camping-summary a {
    color: #2980b9;
    text-decoration: none;
  }
  
  .camping-summary a:hover {
    text-decoration: underline;
  }
  
  .camping-facilities {
    margin-top: 30px;
  }
  
  .camping-facilities h2 {
    font-size: 1.5em;
    color: #34495e;
    margin-bottom: 15px;
  }
  
  .camping-facilities ul {
    list-style-type: none;
    padding: 0;
  }
  
  .camping-facilities li {
    background-color: #ffffff;
    margin-bottom: 15px;
    padding: 15px;
    border-radius: 10px;
    box-shadow: 0 2px 6px rgba(0, 0, 0, 0.1);
    font-size: 1.1em;
    color: #2c3e50;
  }
  
  .camping-facilities li p {
    margin: 5px 0;
  }
  
  .line-intro {
    font-style: italic;
    color: #7f8c8d;
    margin-bottom: 10px;
  }
  
  .feature-summary {
    margin-bottom: 15px;
    font-weight: bold;
    color: #2980b9;
  }

  .review {
    margin: 10px 0;
    background-color: #ffffff;
    border-radius: 10px;
    padding: 20px;
    box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
}

  .camping-review h2{
    font-size: 1.5em;
    color: #34495e;
    margin-bottom: 15px;
  }
  
  .button-container {
    display: flex;
    justify-content: flex-end;
    margin-bottom: 20px;
  }
  </style>
  