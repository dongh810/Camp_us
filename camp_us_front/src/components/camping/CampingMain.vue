<template>
    <div class="campingMainAll">
        <div class="campingTitle">
            <h1>캠핑장 리스트</h1>
        </div>
        <div class="campingCardContainer">
            <div 
                class="campingCard" 
                v-for="(camping, index) in campingList" 
                :key="index"
                @click="goToDetail(camping.campId)"
            >
                <div class="campingImageContainer">
                    <img :src="camping.firstImageUrl ? camping.firstImageUrl : defaultImage" alt="Camping Image" class="campingImage"/>
                </div>
                <div class="campingInfo">
                    <h2>{{ camping.campName }}</h2>
                    <span class="rating">
                        <i v-for="star in 5" :key="star" class="star" :class="{'filled': star <= camping.rating}">&#9733;</i>
                    </span>
                    <p>{{ camping.lineIntro }}</p>
                </div>
                <button 
                    v-if="isLoggedIn" 
                    :class="{'wishlist-button': true, 'wishlist-button-remove': wishlist.has(camping.campId)}" 
                    @click.stop="toggleWishlist(camping.campId)">
                    {{ wishlist.has(camping.campId) ? '찜 취소' : '찜하기' }}
                </button>
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
import { useRouter } from 'vue-router'; // vue-router import
import defaultImage from '@/assets/imageX.jpg';

const currentPage = ref(0);
const totalPages = ref(0);
const campingList = ref([]);
const wishlist = ref(new Set()); // 찜 목록을 저장할 Set
const isLoggedIn = ref(false); 
const router = useRouter(); // router 사용을 위한 인스턴스 생성

const checkLoginStatus = () => {
    const token = localStorage.getItem('token');
    isLoggedIn.value = !!token; // 토큰이 있으면 true, 없으면 false
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


const getCampingList = async () => {
    try {
        const response = await axios.get('/api/v1/campings', {
            params: {
                page: currentPage.value,
                size: 12
            }
        });

        const campings = response.data;

        // 각 캠핑장에 대한 별점 계산
        for (const camping of campings) {
            camping.rating = await getAverageRatingForCamping(camping.campId);
        }
        
        campingList.value = campings;

        
        if (isLoggedIn.value) {
            checkWishlist(); // 로그인된 경우에만 찜 상태 확인
        }
    } catch(error) {
        console.error("Error:", error);
    }
};

const checkWishlist = async () => {
    try {
        const token = localStorage.getItem('token');
        const response = await axios.get('/api/v1/bookmarks', {
            headers: {
                Authorization: `Bearer ${token}`,
            },
        });
        const wishlistItems = response.data;
        wishlist.value = new Set(wishlistItems.map(item => item.campId)); // 찜 목록을 Set으로 저장
    } catch (error) {
        console.error('Error fetching wishlist:', error);
    }
};

const toggleWishlist = async (campingId) => {
    try {
        const token = localStorage.getItem('token');
        if (wishlist.value.has(campingId)) {
            const confirmed = window.confirm('찜을 취소하시겠습니까?');
            if (confirmed) {
                await axios.delete(`/api/v1/bookmarks`, {
                    headers: {
                        Authorization: `Bearer ${token}`,
                    },
                    params: {
                        campId: campingId // RequestParam으로 campId 전달
                    }
                });
                wishlist.value.delete(campingId);
            }
        } else {
            const confirmed = window.confirm('찜 목록에 추가하시겠습니까?');
            if (confirmed) {
                await axios.post(`/api/v1/bookmarks`, { campId: campingId }, {
                    headers: {
                        Authorization: `Bearer ${token}`,
                    },
                });
                wishlist.value.add(campingId);
                alert('찜 목록에 추가되었습니다.');
                window.location.reload();
            }
        }
    } catch (error) {
        console.error('Error toggling wishlist:', error);
    }
};

// 캠핑장 상세 페이지로 이동하는 메서드
const goToDetail = (campId) => {
    router.push(`/campingDetail/${campId}`);
};

const goToNextPage = () => {
    currentPage.value++;
    getCampingList();
};

const goToPreviousPage = () => {
    if (currentPage.value > 0) {
        currentPage.value--;
        getCampingList();
    }
};

onMounted(() => {
    checkLoginStatus(); // 컴포넌트 마운트 시 로그인 상태 확인
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
    display: flex;
    flex-direction: column;
    justify-content: space-between;
    position: relative;
    cursor: pointer;
}

.campingCard:hover {
    transform: scale(1.05);
    transition: transform 0.3s;
}

.campingImage {
    width: 100%;
    height: 200px;
    object-fit: cover;
}

.campingInfo {
    padding: 20px;
    flex-grow: 1;
}

.wishlist-button {
    width: 100%;
    padding: 10px;
    background-color: #007BFF;
    border: none;
    border-radius: 0 0 10px 10px;
    color: white;
    cursor: pointer;
    transition: background-color 0.3s;
    margin-top: auto;
}

.wishlist-button-remove {
    background-color: #ff4d4d;
}

.wishlist-button:hover {
    background-color: #0056b3;
}

.wishlist-button-remove:hover {
    background-color: #ff1a1a;
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
  }
  
  .review-image {
    max-width: 100%;
    height: auto;
    margin-top: 10px;
  }
  
  .button-container {
    display: flex;
    justify-content: flex-end;
    margin-bottom: 20px;
  }
  
  button {
    padding: 10px 20px;
    background-color: #007BFF;
    color: white;
    border: none;
    border-radius: 5px;
    cursor: pointer;
  }
  
  button:hover {
    background-color: #0056b3;
  }
  
  .review-form {
    margin-bottom: 20px;
  }
  
  .review-form textarea, 
  .review-form input {
    width: 100%;
    margin-bottom: 10px;
    padding: 10px;
    border: 1px solid #ccc;
    border-radius: 5px;
  }
  
  .review-form textarea {
    resize: vertical;
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