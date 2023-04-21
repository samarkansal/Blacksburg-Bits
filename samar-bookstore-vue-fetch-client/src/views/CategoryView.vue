<script setup lang="ts">
//import sourceData from "@/db.json";
import type { BookItem } from "@/types";
import { useRoute } from "vue-router";
import TheCategoryNav from "@/components/TheCategoryNav.vue";
import TheCategoryBookList from "@/components/TheCategoryBookList.vue";
const route = useRoute();
const routeName = route.params.name;
//const bookList = sourceData.books as BookItem[];

const apiUrl =
  `${location.protocol}//${location.hostname}:` +
  `${location.port === "5173" ? "8080" : location.port}` +
  `${import.meta.env.BASE_URL}/api`;

let response = await fetch(`${apiUrl}/categories/name/${routeName}/books`);
let data = await response.json();
let bookList = data as BookItem[];
</script>

<style scoped>
.bg-cat {
  position: relative;
  background-attachment: fixed;
  background-position: center center;
  background-repeat: no-repeat;
  background-size: cover;
  height: 100%;
  min-height: 650px;
  background-image: url("/site-images/cat-hero.jpeg");
}
</style>

<template>
  <div>
    <section class="bg-cat">
      <div class="dark-overlay">
        <the-category-nav></the-category-nav>
        <the-category-book-list :bookList="bookList"> </the-category-book-list>
      </div>
    </section>
  </div>
</template>
