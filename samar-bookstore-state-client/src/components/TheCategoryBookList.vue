<script setup lang="ts">
import CategoryBookListItem from "@/components/CategoryBookListItem.vue";
import { useBookStore } from "@/stores/book";
const bookStore = useBookStore();
</script>

<style scoped>
.categoryItems {
  /* do wrap, and distribute space around the child items */
  flex-wrap: wrap;
  display: flex;
  position: relative;
  gap: 40px;
}

.catalog-items {
  display: flex;
  justify-content: center;
  position: relative;
  align-items: center;
  padding: 140px 50px 50px 20vw;
}
</style>

<template>
  <section class="catalog-items">
    <ul class="categoryItems">
      <template v-for="book in bookStore.bookList" :key="book.bookId">
        <category-book-list-item :book="book"></category-book-list-item>
      </template>
    </ul>
  </section>
</template>
