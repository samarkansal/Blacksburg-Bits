<script setup lang="ts">
import { useOrderDetailsStore } from "@/stores/orderDetails";
const orderDetailsStore = useOrderDetailsStore();
</script>

<style scoped>
.bg-404 {
  position: relative;
  background-attachment: fixed;
  background-position: center center;
  background-repeat: no-repeat;
  background-size: cover;
  height: 100%;
  min-height: 650px;
  background-image: url("/site-images/404-hero.jpeg");
}
.dark-overlay {
  background-color: rgba(0, 0, 0, 0.505);
}
.confirmation-container {
  padding: 150px 0 0 150px;
  min-height: 600px;
}
h1 {
  padding-top: 2px;
  font-size: 3rem;
  color: rgb(242, 242, 242);
}
</style>

<template>
  <section class="bg-404">
    <div class="dark-overlay">
      <div class="confirmation-container">
        <h1>404. Page not found !</h1>
      </div>
    </div>
  </section>
</template>
