<script setup lang="ts">
import type { BookItem } from "@/types";
import { useCartStore } from "@/stores/cart";
import { asDollarsAndCents, getBookImageUrl } from "@/utils";
const cartStore = useCartStore();

const updateCart = function (book: BookItem, quantity: number) {
  cartStore.cart.update(book, quantity);
};
</script>

<style scoped>
.cart-table {
  display: grid;
  grid-template-columns: max-content minmax(10em, 20em) minmax(5em, 8em) repeat(
      2,
      max-content
    );
  row-gap: 1em;
  width: fit-content;
  margin: 0 auto;
  border-radius: 4px;
  background-color: rgba(0, 0, 0, 0.656);
}

ul {
  display: contents;
}

ul > li {
  display: contents;
}

.table-heading {
  background-color: #333;
  color: rgb(234, 234, 234);
}

.table-heading > * {
  background-color: rgb(36, 36, 36);
  padding: 0.5em 1em;
  font-size: 1.1em;
  font-weight: 500;
}

.heading-book {
  grid-column: 1 / 3;
  border-top-left-radius: 6px;
}

.heading-price {
  grid-column: 3 / 4;
  text-align: center;
}

.heading-quantity {
  grid-column: 4 / 5;
  text-align: center;
}

.heading-subtotal {
  text-align: center;
  border-top-right-radius: 6px;
}

.cart-book-image {
  padding: 0 1em;
}

.cart-book-image > * {
  margin-left: auto;
  margin-right: 0;
}

img {
  display: block;
  width: 100px;
  height: auto;
}

.rect {
  background-color: var(--primary-color-dark);
}

.narrow-rect {
  width: 75px;
  height: 100px;
}

.square {
  width: 100px;
  height: 100px;
}

.wide-rect {
  width: 125px;
  height: 100px;
}

.cart-book-price {
  padding-right: 2em;
  text-align: right;
  display: flex;
  align-items: center;
  justify-content: flex-end;
}

.cart-book-quantity {
  padding-left: 1em;
  padding-right: 1em;
  display: flex;
  align-items: center;
}

.cart-book-subtotal {
  text-align: right;
  padding-left: 1.3em;
  display: flex;
  align-items: center;
}

.cart-book-title {
  align-items: center;
  display: flex;
  align-items: center;
  font-size: 1.1em;
}

/* Row separators in the table */

.line-sep {
  display: block;
  height: 2px;
  background-color: gray;
  grid-column: 1 / -1;
}

.iconic-button {
  background: none;
  border: none;
  padding: 0;
}

.iconic-button > i {
  font-size: 1.1em;
  color: #0066cb;
  margin: 0.5em;
}

.iconic-button:hover {
  cursor: pointer;
}

.iconic-button > i:hover {
  color: #0077ee;
}

/* Increment/decrement buttons */

input[type="number"] {
  width: 4em;
}

select {
  background-color: var(--primary-color);
  color: white;
  border: 2px solid var(--primary-color-dark);
  border-radius: 3px;
}
</style>

<template>
  <div class="cart-table">
    <ul>
      <li class="table-heading">
        <div class="heading-book">Book</div>
        <div class="heading-price">Price</div>
        <div class="heading-quantity">Quantity</div>
        <div class="heading-subtotal">Amount</div>
      </li>

      <template v-for="item in cartStore.cart.items" :key="item.book.bookId">
        <li>
          <div class="cart-book-image">
            <img
              :src="getBookImageUrl(item.book)"
              :alt="item.book.title"
              width="100px"
              height="auto"
            />
          </div>
          <div class="cart-book-title">
            {{ item.book.title }}
          </div>

          <div class="cart-book-price">
            {{ asDollarsAndCents(item.book.price) }}
          </div>
          <div class="cart-book-quantity">
            <button
              class="iconic-button"
              @click="updateCart(item.book, item.quantity - 1)"
            >
              <i class="fas fa-minus-circle"></i>
            </button>
            &nbsp;
            <span class="quantity">{{ item.quantity }}</span>
            &nbsp;
            <button
              class="iconic-button"
              @click="updateCart(item.book, item.quantity + 1)"
            >
              <i class="fas fa-plus-circle"></i>
            </button>
          </div>
          <div class="cart-book-subtotal">
            {{ asDollarsAndCents(item.book.price * item.quantity) }}
          </div>
        </li>
        <li class="line-sep"></li>
      </template>
    </ul>
  </div>
</template>
