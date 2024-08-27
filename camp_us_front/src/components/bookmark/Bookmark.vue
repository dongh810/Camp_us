<template>
    <div class="wishlist-container">
      <h2>찜한 캠핑장 리스트</h2>
      <div class="wishlist-cards">
        <div
          class="wishlist-card"
          v-for="(camping, index) in wishlist"
          :key="index"
          @click="goToDetail(camping.campId)"
        >
          <img
            :src="camping.firstImageUrl ? camping.firstImageUrl : fallbackImage"
            alt="Camping Image"
            class="wishlist-image"
          />
          <div class="wishlist-info">
            <h3>{{ camping.campName }}</h3>
            <span class="rating">
                <i v-for="star in 5" :key="star" class="star" :class="{'filled': star <= camping.rating}">&#9733;</i>
            </span>
          </div>
          <button @click.stop="removeFromWishlist(camping.campId)">찜 목록에서 제거</button>
        </div>
      </div>
    </div>
  </template>
  
  <script setup>
  import { ref, onMounted } from 'vue';
  import axios from 'axios';
  import { useRouter } from 'vue-router'; // router 객체 사용을 위해 추가
  import fallbackImage from '@/assets/imageX.jpg';
  
  const wishlist = ref([]);
  const router = useRouter(); // router 인스턴스 생성
  
  const getWishlist = async () => {
    try {
      const token = localStorage.getItem('token');
      const response = await axios.get('/api/v1/bookmarks', {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      });

      const bookmarks = response.data;

      for (const bookmark of bookmarks) {
        bookmark.rating = await getAverageRatingForCamping(bookmark.campId);
      }

      wishlist.value = bookmarks;

    } catch (error) {
      console.error('Error fetching wishlist:', error);
    }
  };

  const getAverageRatingForCamping = async (campId) => {
    try {
        const response = await axios.get('/api/v1/reviews', {
            params: { campId }
        });
        const reviews = response.data.content;
        
        if (reviews.length === 0) return 0;

        const totalRating = reviews.reduce((sum, review) => sum + review.rating, 0);
        return totalRating / reviews.length;
    } catch (error) {
        console.error('Error fetching reviews:', error);
        return 0;
    }
};
  
  const removeFromWishlist = async (campingId) => {
    try {
      const confirmed = window.confirm('찜 목록에서 이 캠핑장을 제거하시겠습니까?');
      if (confirmed) {
        const token = localStorage.getItem('token');
        await axios.delete('/api/v1/bookmarks', {
          headers: {
            Authorization: `Bearer ${token}`,
          },
          params: {
            campId: campingId,
          }
        });
        wishlist.value = wishlist.value.filter((item) => item.campId !== campingId);
        alert('찜 목록에서 제거되었습니다.');
        window.location.reload();
      }
    } catch (error) {
      console.error('Error removing from wishlist:', error);
    }
  };
  
  const goToDetail = (campId) => {
    router.push(`/campingDetail/${campId}`);
  };
  
  onMounted(() => {
    getWishlist();
  });
  </script>
  
  <style scoped>
  .wishlist-container {
    padding: 20px;
  }
  
  .wishlist-cards {
    display: flex;
    flex-wrap: wrap;
    gap: 20px;
    justify-content: center;
  }
  
  .wishlist-card {
    background-color: white;
    border-radius: 10px;
    box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
    overflow: hidden;
    width: 300px;
    text-align: center;
    position: relative;
    cursor: pointer;
    transition: transform 0.3s; /* 카드에 애니메이션 추가 */
  }
  
  .wishlist-card:hover {
    transform: scale(1.05); /* 카드 확대 효과 */
  }
  
  .wishlist-image {
    width: 100%;
    height: 200px;
    object-fit: cover;
  }
  
  .wishlist-info {
    padding: 20px;
  }
  
  button {
    width: 100%;
    padding: 10px;
    background-color: #ff4d4d;
    border: none;
    border-radius: 0 0 10px 10px;
    color: white;
    cursor: pointer;
    transition: background-color 0.3s;
  }
  
  button:hover {
    background-color: #ff3333;
  }

  .rating {
    margin-left: 10px;
}

.star {
    color: #ccc;
    font-size: 16px;
}

.star.filled {
    color: #FFD700; /* 금색 */
}
  </style>
  