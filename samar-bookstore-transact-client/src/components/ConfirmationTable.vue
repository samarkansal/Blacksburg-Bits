<script setup lang="ts">
import type { BookItem } from "@/types";
import { asDollarsAndCents } from "@/utils";
import { useOrderDetailsStore } from "@/stores/orderDetails";
const orderDetailsStore = useOrderDetailsStore();
const orderDetails = orderDetailsStore.orderDetails;
console.log("HEYEYE", orderDetails.books.length);

const bookImageFileName = function (book: BookItem): string {
  console.log(book);
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
.cart-table {
  display: grid;
  grid-template-columns: auto 1fr 1fr 0.5fr 1fr;
  row-gap: 0.5em;
  margin: 0 auto;
  border-radius: 4px;
  background-color: rgba(0, 0, 0, 0.656);
  padding-bottom: 15px;
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
  border-top-left-radius: 4px;
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
  border-top-right-radius: 4px;
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
  width: 40px;
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
  text-align: right;
  display: flex;
  align-items: center;
  justify-content: center;
}

.cart-book-quantity {
  justify-content: center;
  display: flex;
  align-items: center;
}

.cart-book-subtotal {
  text-align: right;
  display: flex;
  align-items: center;
  justify-content: center;
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
  height: 0.1px;
  background-color: rgb(38, 38, 38);
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

      <template
        v-for="(item, index) in orderDetails.lineItems"
        :key="orderDetails.order.orderId + '-' + item.productId"
      >
        <li>
          <div class="cart-book-image">
            <img
              :src="bookImageUrl(bookImageFileName(orderDetails.books[index]))"
              :alt="orderDetails.books[index].title"
              height="auto"
            />
          </div>
          <div class="cart-book-title">
            {{ orderDetails.books[index].title }}
          </div>
          <div class="cart-book-price">
            {{ asDollarsAndCents(orderDetails.books[index].price) }}
          </div>
          <div class="cart-book-quantity">
            <span class="quantity">{{ item.quantity }}</span>
          </div>
          <div class="cart-book-subtotal">
            {{
              asDollarsAndCents(orderDetails.books[index].price * item.quantity)
            }}
          </div>
        </li>
        <li class="line-sep"></li>
      </template>

      <!-- <li>
        <div class="cart-book-image">
          <div class="rect narrow-rect"></div>
        </div>
        <div class="cart-book-title">Book Title</div>
        <div class="cart-book-price">$2.99</div>
        <div class="cart-book-quantity">
          <span class="quantity">2</span>&nbsp;
          <button class="icon-button inc-button">
            <i class="fas fa-plus-circle"></i>
          </button>
          <button class="icon-button dec-button">
            <i class="fas fa-minus-circle"></i>
          </button>
        </div>
        <div class="cart-book-subtotal">$3.98</div>
      </li>
      <li class="line-sep"></li>
      <li>
        <div class="cart-book-image">
          <img :src="`${bookImagePrefix}/dune.jpeg`" alt="Dune" />
        </div>
        <div class="cart-book-title">Book Title</div>
        <div class="cart-book-price">$2.99</div>
        <div class="cart-book-quantity">
          <button class="icon-button dec-arrow-button">
            <i class="fas fa-chevron-left"></i>
          </button>
          <span class="quantity">&nbsp;&nbsp;2&nbsp;&nbsp;</span>
          <button class="icon-button inc-arrow-button">
            <i class="fas fa-chevron-right"></i>
          </button>
        </div>
        <div class="cart-book-subtotal">$3.98</div>
      </li>
      <li class="line-sep"></li>
      <li>
        <div class="cart-book-image">
          <div class="rect square"></div>
        </div>
        <div class="cart-book-title">
          A Very Long Book Title That Goes On and On As Though the Author Were
          Very Impressed with Themself
        </div>
        <div class="cart-book-price">$43.50</div>
        <div class="cart-book-quantity">
          <input type="number" value="12" min="0" max="20" />
        </div>
        <div class="cart-book-subtotal">$522.00</div>
      </li>
      <li class="line-sep"></li>
      <li>
        <div class="cart-book-image">
          <div class="rect wide-rect"></div>
        </div>
        <div class="cart-book-title">Book Title</div>
        <div class="cart-book-price">$2.99</div>
        <div class="cart-book-quantity">
          <select id="quantity">
            <option value="0">Qty: 0</option>
            <option value="1">Qty: 1</option>
            <option value="2">Qty: 2</option>
            <option value="3">Qty: 3</option>
            <option value="4">Qty: 4</option>
            <option value="5">Qty: 5</option>
            <option value="6">Qty: 6</option>
            <option value="7">Qty: 7</option>
            <option value="8">Qty: 8</option>
            <option value="9">Qty: 9</option>
            <option value="10">Qty: 10</option>
          </select>
        </div>
        <div class="cart-book-subtotal">$3.98</div>
      </li> -->
    </ul>
  </div>
</template>
