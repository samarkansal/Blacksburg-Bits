<script setup lang="ts">
import { defineProps } from "vue";
import type { BookItem } from "@/types";
import { useCartStore } from "@/stores/cart";
const cartStore = useCartStore();
const props = defineProps<{
  book: BookItem;
}>();
const bookImageFileName = function (book: BookItem): string {
  let name = book.title.toLowerCase();
  name = name.replace(/ /g, "_");
  name = name.replace(/'/g, "");
  name += "_thumb";
  return `${name}.jpeg`;
};

function bookImageUrl(imageFileName: String) {
  return new URL(`../assets/book-images/${imageFileName}`, import.meta.url)
    .href;
}
</script>

<style scoped>
.item-container {
  width: 340px;
  height: 240px;
  background-color: rgba(22, 22, 22, 0.96);
  border-radius: 4px;
  display: flex;
  padding: 25px 20px 20px 20px;
  align-items: center;
}

/* li.item-container:last-child {
  margin-bottom: 250px;
} */

.book-title {
  font-size: 1.4em;
  font-weight: 700;
}
.book-author {
  font-size: 0.8em;
}

.img-container {
  opacity: 0.9;
  height: 200px;
  width: 100%;
  background-position: center center;
  background-repeat: no-repeat;
  background-size: cover;
  display: flex;
  flex-direction: column;
  position: relative;
}

img {
  height: 100%;
  width: 100%;
}

.read-now-button {
  height: 32px;
  width: 32px;
  align-self: flex-end;
  position: absolute;
  top: -15px;
  right: -15px;
}
.read-now-button:hover {
  height: 36px;
  width: 36px;
  cursor: pointer;
}
.book-price {
  margin: 10px 0px;
  font-weight: 500;
  color: #dadada;
}

.item-content {
  margin-left: 25px;
  display: flex;
  flex-direction: column;
  text-align: center;
  justify-content: space-between;
  height: 90%;
}

.addToCart {
  background-color: #005ab6;
  border-radius: 6px;
  color: #fff;
  border: none;
  padding: 10px;
  margin-top: 10px;
  font-weight: 400;
  width: 130px;
  font-family: BlinkMacSystemFont, "Segoe UI", Roboto, Oxygen, Ubuntu, Cantarell,
    "Open Sans", "Helvetica Neue", sans-serif;
}

.item-actions {
  display: flex;
  align-items: center;
  justify-content: center;
  bottom: 0px;
}

.addToCart:hover {
  background-color: #0069d2;
  cursor: pointer;
  font-weight: 450;
}
.book-box {
  display: flex;
  flex-direction: column;
  padding: 1em;
  background-color: var(--card-background-color);

  gap: 0.25em;
}
</style>

<template>
  <li class="item-container">
    <div class="img-container">
      <img
        v-if="book.isPublic"
        class="read-now-button"
        src="/logo-images/read-now.png"
        title="Read Now"
      />
      <img
        :src="bookImageUrl(bookImageFileName(props.book))"
        :alt="book.title"
      />
    </div>
    <div class="item-content">
      <h2 class="book-title">{{ book.title }}</h2>
      <h4 class="book-author">by {{ book.author }}</h4>
      <h3 class="book-price">${{ (book.price / 100).toFixed(2) }}</h3>
      <ul class="item-actions">
        <!-- wrap this "read" li with if statement for visibility control -->
        <li>
          <button class="addToCart" @click="cartStore.addToCart(book)">
            &plus; Add to bag
          </button>
        </li>
      </ul>
    </div>
  </li>
</template>
