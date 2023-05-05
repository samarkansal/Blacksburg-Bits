<script setup lang="ts"></script>

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
.empty-container {
  padding: 150px 0 0 150px;
  min-height: 600px;
}
h1 {
  padding-top: 2px;
  font-size: 3rem;
  color: rgb(242, 242, 242);
  margin-bottom: 20px;
}
.secondary-button {
  font-size: 20px;
  background-color: #f3f3f3;
  border-radius: 6px;
  color: #005ab6;
  border: 2px solid;
  border-color: #005ab6;
  padding: 10px;
  margin-top: 10px;
  font-weight: 500;
  font-family: BlinkMacSystemFont, "Segoe UI", Roboto, Oxygen, Ubuntu, Cantarell,
    "Open Sans", "Helvetica Neue", sans-serif;
  margin-bottom: 2rem;
}
.secondary-button:hover {
  background-color: #ffffff;
  cursor: pointer;
  font-weight: 520;
}
</style>

<template>
  <section class="bg-404">
    <div class="dark-overlay">
      <div class="empty-container">
        <h1>404. Page not found !</h1>
        <router-link :to="{ name: 'category-view' }">
          <button class="secondary-button">
            <i class="fas fa-arrow-left"></i> Continue shopping
          </button>
        </router-link>
      </div>
    </div>
  </section>
</template>
