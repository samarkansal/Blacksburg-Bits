<script setup lang="ts">
import { provide } from "vue";
import sourceData from "@/db.json";
import type { BookItem } from "@/types";
import TheCategoryNav from "@/components/TheCategoryNav.vue";
import TheCategoryBookList from "@/components/TheCategoryBookList.vue";
const bookList = sourceData.books as BookItem[];
provide("bookList", bookList);
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
