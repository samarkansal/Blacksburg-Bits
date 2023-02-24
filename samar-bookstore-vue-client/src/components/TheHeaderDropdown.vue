<script setup lang="ts">
import { inject } from "vue";
import type { CategoryItem } from "@/types";
const categoryList = inject("categoryList") as CategoryItem[];
</script>

<style scoped>
.header-dropdown {
  position: relative;
}

.header-dropdown ul {
  display: none;
  background-color: #171717;
  /* opacity: 0.92; */
  color: black;
  justify-content: center;
}

.header-dropdown li {
  padding: 0.25em 0.5em;
}

.header-dropdown a {
  color: #cdcdcc;
  text-decoration: none;
  font-size: 0.9em;
}

.header-dropdown a:hover {
  color: white;
  font-size: 1em;
}

.header-dropdown:hover ul {
  position: absolute;
  z-index: 1;
  display: block;
  min-width: 8em;
  cursor: pointer;
  border-radius: 8px;
  padding: 0.4em;
}
.categories-button {
  background: none;
  padding: 0;
  margin: 0;
  border: none;
  color: #cdcdcc;
}

.categories-button:hover {
  color: white;
  background: none;
  cursor: pointer;
}
.drop-cat {
  height: 10px;
}
</style>

<template>
  <div class="header-dropdown">
    <button class="button categories-button">
      Categories <i class="fas fa-caret-down"></i>
    </button>
    <div class="drop-cat"></div>
    <ul>
      <li v-for="category in categoryList" :key="category.categoryId">
        <router-link :to="'/category/' + category.name.toLowerCase()">
          {{ category.name }}
        </router-link>
      </li>
    </ul>
  </div>
</template>
