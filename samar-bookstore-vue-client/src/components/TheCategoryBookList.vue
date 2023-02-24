<script setup lang="ts">
import { defineProps } from "vue"; // needed if you don't want an error below
import type { BookItem } from "@/types";
import CategoryBookListItem from "@/components/CategoryBookListItem.vue";
const props = defineProps<{
  bookList: BookItem[];
}>();
</script>

<style scoped>
.categoryItems {
  /* do wrap, and distribute space around the child items */
  flex-wrap: wrap;
  justify-content: space-between;
  display: flex;
  position: relative;
  gap: 40px 20px;
}

.catalog-items {
  display: flex;
  justify-content: center;
  position: relative;
  align-items: center;
  padding: 140px 50px 50px 21vw;
}
</style>

<template>
  <section class="catalog-items">
    <ul class="categoryItems">
      <template v-for="book in props.bookList" :key="book.bookId">
        <category-book-list-item :book="book"></category-book-list-item>
      </template>
    </ul>
  </section>
</template>
