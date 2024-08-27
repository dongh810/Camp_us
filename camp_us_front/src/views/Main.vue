<template>
    <div class="main-container">
      <!-- 배너 이미지 -->
      <div class="banner">
        <img src="@/assets/banner.jpg" alt="Site Banner" class="banner-image" />
      </div>
  
      <!-- 사이트 소개 문구 -->
      <div class="site-introduction">
        <h2>🏕️Camp us와 함께 캠핑을 더욱 편하게 이용해보세요🏕️</h2>
        <p v-html="introductionText">
          
        </p>
      </div>
    </div>
  </template>
  
  <script setup>
    const introductionText = 
    `우리 Camp us를 통해 전국 모든 캠핑장의 정보를 한 눈에 조회하고, <br> 
    해당 캠핑장을 예약까지 할 수 있습니다! <br>
    <br>
    그동안 캠핑을 좋아하지만 캠핑장 정보가 부족했던 분들! <br>
    Camp us를 통해 더욱 쉽고 편하게 캠핑을 떠나보세요~`
  </script>
  
  <style scoped>
  .main-container {
    text-align: center;
    padding: 50px;
  }
  
  .banner {
    margin-bottom: 100px;
  }
  
  .banner-image {
    width: 100%;
    max-width: 2000px; /* 배너 이미지의 최대 너비를 설정 */
    height: auto;
    border-radius: 10px;
    box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
  }
  
  .site-introduction {
    margin-top: 20px;
  }
  
  .site-introduction h2 {
    font-size: 2.5em;
    margin-bottom: 20px;
  }
  
  .site-introduction p {
    font-size: 1.2em;
    line-height: 1.6;
    max-width: 800px;
    margin: 0 auto;
  }
  </style>
  