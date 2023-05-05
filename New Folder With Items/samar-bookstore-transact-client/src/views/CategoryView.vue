<script setup lang="ts">
import TheCategoryNav from "@/components/TheCategoryNav.vue";
import TheCategoryBookList from "@/components/TheCategoryBookList.vue";
import { useRoute } from "vue-router";
import { useBookStore } from "@/stores/book";

const route = useRoute();

const bookStore = useBookStore();
bookStore.fetchBooks(route.params.name as string);
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
        <the-category-book-list> </the-category-book-list>
      </div>
    </section>
  </div>
</template>
