<script setup lang="ts"></script>

<style scoped>
.bg-confirmation {
  position: relative;
  background-attachment: fixed;
  background-position: center center;
  background-repeat: no-repeat;
  background-size: cover;
  height: 100%;
  min-height: 650px;
  background-image: url("/site-images/confirmation-hero.jpeg");
}
.dark-overlay {
  background-color: rgba(0, 0, 0, 0.738);
}
.confirmation-container {
  min-height: 600px;
}
h1 {
  padding: 200px;
}
</style>

<template>
  <section class="bg-confirmation">
    <div class="dark-overlay">
      <div class="confirmation-container">
        <h1>Hello confirmation</h1>
      </div>
    </div>
  </section>
</template>
