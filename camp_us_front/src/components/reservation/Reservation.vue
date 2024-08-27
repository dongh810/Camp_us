<template>
    <div class="reservation-container">
      <h2>예약 조회</h2>
      <div v-if="reservations.length === 0">
        <p>예약이 없습니다.</p>
      </div>
      <div v-else class="reservation-cards">
        <div 
          class="reservation-card" 
          v-for="(reservation, index) in reservations" 
          :key="index"
        >
          <div class="camping-image-container">
            <img :src="reservation.firstImageUrl ? reservation.firstImageUrl : defaultImage" alt="Camping Image" class="camping-image"/>
          </div>
          <h3>{{ reservation.campName }}</h3>
          <p><strong>예약 신청날짜:</strong> {{ reservation.reservationDate }}</p>
          <p><strong>숙박 기간:</strong> {{ reservation.formattedEntryDate + " ~ " + reservation.formattedLeavingDate }}</p>
          <p><strong>예약 상태:</strong> {{ reservation.reservationStatus }}</p>
        </div>
      </div>
    </div>
</template>
  
<script setup>
import { ref, onMounted } from 'vue';
import axios from 'axios';
import { useRouter } from 'vue-router';
import defaultImage from '@/assets/imageX.jpg';

const reservations = ref([]);
const router = useRouter();

const getReservations = async () => {
  try {
    const token = localStorage.getItem('token');
    const response = await axios.get('/api/v1/reservations', {
      headers: {
        Authorization: `Bearer ${token}`,
      },
    });
    
    // 캠핑장 정보 조회 및 예약 정보와 병합
    reservations.value = await Promise.all(response.data.map(async reservation => {
      const campInfo = await getCampInfo(reservation.campId);
      const formattedEntryDate = formatDate(reservation.entryDate);
      const formattedLeavingDate = formatDate(reservation.leavingDate);
      
      if (reservation.reservationStatus === 'confirmation') {
        reservation.reservationStatus = '확정';
      }

      return {
        ...reservation,
        campName: campInfo.campName,
        firstImageUrl: campInfo.firstImageUrl,
        formattedEntryDate,
        formattedLeavingDate,
      };
    }));

    console.log(reservations.value);
  } catch (error) {
    console.error('Error fetching reservations:', error);
  }
};

const getCampInfo = async (campId) => {
  try {
    const response = await axios.get(`/api/v1/campings/${campId}`);
    return response.data;
  } catch (error) {
    console.error('Error fetching camping info:', error);
    return {};
  }
};

const formatDate = (dateArray) => {
  return dateArray.map(num => num.toString().padStart(2, '0')).join('-');
};

onMounted(() => {
  getReservations();
});
</script>

<style scoped>
.reservation-container {
  padding: 20px;
}

.reservation-cards {
  display: flex;
  flex-wrap: wrap;
  gap: 20px;
  justify-content: center;
}

.reservation-card {
  background-color: white;
  border-radius: 10px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
  padding: 20px;
  width: 300px;
  text-align: center;
  position: relative;
  transition: transform 0.3s;
}

.reservation-card:hover {
  transform: scale(1.05);
}

.camping-image-container {
  width: 100%;
  height: 200px;
  overflow: hidden;
  border-radius: 10px 10px 0 0;
}

.camping-image {
  width: 100%;
  height: 100%;
  object-fit: cover;
  object-position: center;
}

button {
  padding: 10px 20px;
  background-color: #007BFF;
  border: none;
  border-radius: 5px;
  color: white;
  cursor: pointer;
  transition: background-color 0.3s;
}

button:hover {
  background-color: #0056b3;
}
</style>
