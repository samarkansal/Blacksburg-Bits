<script setup lang="ts">
import { inject } from "vue";
import type { CategoryItem } from "@/types";
const categoryList = inject("categoryList") as CategoryItem[];

function bookImageUrl(imageFileName: String) {
  return new URL(
    `../assets/category-images/${imageFileName}-thumb.jpeg`,
    import.meta.url
  ).href;
}
</script>

<style scoped>
a {
  height: 100%;
  width: 100%;
  display: inline-block;
}
.nav-cat {
  padding-top: 20px;
  margin-top: 70px;
  height: 100%;
  background-color: #030303a8;
  display: flex;
  position: fixed;
  align-items: flex-end;
  justify-content: flex-start;
  flex-wrap: wrap;
  flex-direction: column;
  z-index: 1;
}

.cat-cont {
  height: 13vh;
  width: 16vw;
  display: flex;
  flex-wrap: wrap;
  justify-content: center;
  align-items: center;
}

.cat-thumb {
  height: 99%;
  display: flex;
  flex-wrap: wrap;
  justify-content: center;
  align-items: center;
}

.cat-thumb:hover {
  border: 1px solid rgb(215, 215, 215);
  border-radius: 4px;
}

.router-link-active {
  border: 1px solid rgb(215, 215, 215);
  border-radius: 4px;
}

.cat-thumb-img {
  height: 75%;
  width: 100%;
  display: flex;
  align-items: center;
  justify-content: center;
  margin: 0 28px;
  background-repeat: no-repeat;
  background-size: cover;
  background-position: center;
}
.cat-sep {
  width: 40%;
  border: 0.5px solid rgb(78, 78, 78);
  border-radius: 5px;
}

.thumb-dark-overlay {
  height: 100%;
  width: 100%;
  display: flex;
  background-color: rgba(21, 21, 21, 0.565);
  align-items: center;
  justify-content: center;
  color: rgb(237, 237, 237);
  font-weight: 800;
  font-size: 1.2em;
}
</style>

<template>
  <div class="nav-cat">
    <div
      class="cat-cont"
      v-for="category in categoryList"
      :key="category.categoryId"
    >
      <router-link
        class="cat-thumb"
        :to="'/category/' + category.name.toLowerCase()"
      >
        <div
          :style="{
            'background-image': 'url(' + bookImageUrl(category.name) + ')',
          }"
          class="cat-thumb-img"
        >
          <div class="thumb-dark-overlay">
            {{ category.name }}
          </div>
        </div>
      </router-link>
      <div class="cat-sep"></div>
    </div>
  </div>
</template>
