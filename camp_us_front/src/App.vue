<template>
  <div class="all">
    <div class="header">
      <Header></Header>
    </div>
    <div class="main">
      <div class="content">
        <RouterView></RouterView>
      </div>
    </div>
    <div class="footer">
      <Footer></Footer>
    </div>
  </div>
</template>

<script setup>
import Header from "../src/components/Header.vue";
import Footer from "../src/components/Footer.vue";
import { RouterLink, RouterView, useRouter } from 'vue-router';

</script>

<style scoped>
body {
  margin: 0;
  padding: 0;
  width: 100vw;
  height: 100vh;
}

  .all {
    display:grid;
    grid-template-columns: 1fr; /* 열을 하나만 사용 */
    grid-template-rows: auto 1fr auto; /* 헤더와 푸터는 자동 크기, 중간은 최대 크기 */
    height: 100vh; /* 화면 전체 높이 */
  }

  .header {
  grid-row-start: 1;
}

.main {
  grid-row-start: 2;
  display: grid;
  grid-template-columns: 100px auto 100px;
}

.content{
  grid-column-start: 2;
}

.footer {
  grid-row-start: 3;
}
</style>