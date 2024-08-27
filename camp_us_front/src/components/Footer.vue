<template>
    <div class="footerAll">
        <div class="main">
            <p class="top">(주)CAMP US | 대표이사: 손세림 | 사업자 등록번호: 111-11-11111 | 메일:comm1t@comm1t.com</p>
            <span>이용약관</span>
            <span>개인정보처리방침</span>
            <span>운영정책</span>
            <span>공지사항</span>
            <p class="bottom">&copy; COMM1T. All rights reserved.</p>
        </div>
    </div>
</template>

<script setup>

</script>

<style scoped>
    .footerAll {
    display: grid;
    height: 120px;
    grid-template-columns: 100px auto 100px;
    background-color: #E6E6E6;
}

.main {
    grid-column-start: 2;
    align-self: center;
}

.main span {
    margin-right: 10px;
    font-size: 12px;
}

.main p {
    font-size: 13px;
}

.top {
    margin-bottom: 20px;
    margin-top: 0;
}

.bottom {
    margin-top: 20px;
    margin-bottom: 0;
}
</style>