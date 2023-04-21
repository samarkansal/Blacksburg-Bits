<script setup lang="ts">
import TheHomeCategoryList from "@/components/TheHomeCategoryList.vue";
</script>

<style scoped>
.bgimg-inner {
  font-size: larger;
  font-weight: bold;
  color: rgb(208, 208, 208);
  height: 100%;
  display: flex;
  flex-direction: column;
  margin: auto;
  align-items: center;
  justify-content: center;
  text-align: center;
}

.main-content {
  width: 40%;
  background-color: #0d0d0df0;
  padding: 1em;
  border-radius: 12px;
  justify-content: center;
  display: flex;
  flex-direction: row;
}

.sub-content {
  padding: 1em 0.4em 0 1em;
  display: flex;
  flex-direction: column;
  flex-wrap: wrap;
  /* background-image: url("/site-images/sub-content-bg.jpeg"); */
}

.x-large {
  margin-bottom: 0.7em;
  font-size: 1.8rem;
  line-height: 1.2;
  font-weight: 700;
  font-family: -apple-system, BlinkMacSystemFont, "Segoe UI", Roboto, Oxygen,
    Ubuntu, Cantarell, "Open Sans", "Helvetica Neue", sans-serif;
}

.content-img {
  width: 134px;
  height: 200px;
  align-self: center;
}

.cta-button-container {
  display: flex;
  justify-content: center;
}

.quote-author {
  font-size: 0.8em;
  font-family: cursive, -apple-system, BlinkMacSystemFont, "Segoe UI", Roboto,
    Oxygen, Ubuntu, Cantarell, "Open Sans", "Helvetica Neue", sans-serif;
}

.bg-hero {
  position: relative;
  background-attachment: fixed;
  background-position: center center;
  background-repeat: no-repeat;
  background-size: cover;
  height: 75vh;
  background-image: url("/site-images/main-hero4.jpeg");
}

.cta-button {
  background-color: #005ab6;
  border-radius: 6px;
  color: #fff;
  border: none;
  padding: 10px;
  margin-top: 10px;
  font-weight: 500;
  font-family: BlinkMacSystemFont, "Segoe UI", Roboto, Oxygen, Ubuntu, Cantarell,
    "Open Sans", "Helvetica Neue", sans-serif;
}

.dark-overlay {
  height: 100%;
  width: 100%;
  min-height: 650px;
  top: 0;
  left: 0;
  background-color: rgba(15, 15, 15, 0.562);
}

.cta-button:hover {
  background-color: #0069d2;
  cursor: pointer;
  font-weight: 550;
}
</style>
<template>
  <section class="bg-hero">
    <div class="dark-overlay">
      <div class="bgimg-inner">
        <div class="main-content">
          <img class="content-img" src="/site-images/ezra_pound.jpeg" />
          <div class="sub-content">
            <h1 class="x-large">
              "The book should be a ball of light in one's hand."
              <div class="quote-author">--Ezra Pound--</div>
            </h1>
            <router-link class="cta-button-container" to="/category/sci-fi">
              <div class="cta-button">Browse Books</div>
            </router-link>
          </div>
        </div>
      </div>
    </div>
  </section>
</template>
