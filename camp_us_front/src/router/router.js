import { createRouter, createWebHistory } from 'vue-router'

const routes = [
  {
    path: '/',
    component: () => import('../views/Main.vue')
  },
  {
    path: '/campingMain',
    component: () => import('../views/CampingMainView.vue')
  },
  {
    path: '/login',
    component: () => import('../views/LoginView.vue')
  },
  {
    path: '/signup',
    component: () => import('../views/SignupView.vue')
  },
  {
    path: '/bookmarks',
    component: () => import('../views/BookmarkView.vue')
  },
  {
    path: '/campingDetail/:campId',
    component: () => import('../views/CampingDetailView.vue')
  },
  {
    path: '/campingReview',
    component: () => import('../views/CampingReviewView.vue')
  },
  {
    path: '/review',
    name: 'Review',
    component: () => import('../views/ReviewView.vue')
  },
  {
    path: '/reservation',
    component: () => import('../views/ReservationView.vue')
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

export default router  // 여기에서 default로 내보냅니다

