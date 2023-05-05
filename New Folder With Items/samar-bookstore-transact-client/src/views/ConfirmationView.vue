<script setup lang="ts">
import ConfirmationTable from "@/components/ConfirmationTable.vue";
import { useOrderDetailsStore } from "@/stores/orderDetails";
import { useCartStore } from "@/stores/cart";
import type { BookItem } from "@/types";
import { asDollarsAndCents, getMonthYear } from "@/utils";
const orderDetailsStore = useOrderDetailsStore();
const orderDetails = orderDetailsStore.orderDetails;
const cartStore = useCartStore();
const bookIdQuantityMap = {} as any;
let orderSubTotal = 0;
if (Object.keys(orderDetails).length != 0) {
  for (const lineItem of orderDetails.lineItems) {
    bookIdQuantityMap[`${lineItem.bookId}`] = lineItem.quantity;
  }
  orderSubTotal = orderDetails.books.reduce((acc: number, item: BookItem) => {
    return (acc += bookIdQuantityMap[item.bookId] * item.price);
  }, 0);
}
</script>

<style scoped>
.bg-confirmation {
  position: relative;
  background-attachment: fixed;
  background-position: center center;
  background-repeat: no-repeat;
  background-size: cover;
  height: 100%;
  min-height: 650px;
  background-image: url("/site-images/confirmation-hero.jpeg");
}
.dark-overlay {
  background-color: rgba(0, 0, 0, 0.738);
}
.confirmation-container {
  padding-top: 120px;
  min-height: 600px;
  display: flex;
  flex-direction: column;
  align-items: center;
}
h1 {
  padding-top: 2px;
}

.summary-box .checkoutStatusBox {
  color: rgb(232, 232, 232);
  font-weight: 800;
  font-size: 1.2rem;
  text-align: right;
  margin-left: 2rem;
}

.summary-box {
  grid-row: 1 / span 1;
  grid-column: 2 / span 2;
  display: flex;
  flex-direction: column;
  align-items: center;
  width: 100%;
}

h1 {
  font-size: 1.4em;
  font-weight: 500;
  margin-bottom: 10px;
}
h2 {
  text-align: left;
  width: 100%;
  background-color: rgb(21, 21, 21);
  padding: 10px 20px;
  border-radius: 6px 6px 0 0;
  font-size: 1.2em;
}
.subtotal {
  display: grid;
  grid-template-columns: 1fr 1fr;
  row-gap: 8px;
  padding: 10px 30px 20px 30px;
  column-gap: 2rem;
  width: 100%;
}

.subtotal li:nth-child(odd) {
  font-weight: bold;
  text-align: left;
}
.line-sep {
  display: block;
  height: 1px;
  background-color: gray;
  grid-column: 1 / -1;
}

.subtotal li:nth-child(even) {
  text-align: right;
}

.confirmation-grid {
  display: grid;
  grid-template-columns: 1fr 1fr;
  grid-template-rows: auto auto;
  gap: 10px 20px; /* gap between the grid cells */
}
.title {
  font-size: 3rem;
  margin-bottom: 10px;
  font-weight: 700;
}

.time {
  font-size: 1rem;
  margin-bottom: 25px;
  margin-top: 5px;
}

.customer-box {
  grid-row: 1 / span 1;
  grid-column: 1 / span 1;
  flex-grow: 1;
  background-color: rgba(0, 0, 0, 0.656);
  flex-direction: row;
  display: flex;
  border-radius: 6px;
  justify-content: center;
  width: 100%;
}

.summary-container {
  flex-grow: 1;
  background-color: rgba(0, 0, 0, 0.656);
  align-items: center;
  flex-direction: column;
  display: flex;
  border-radius: 6px;
  width: 100%;
}
.shipping {
  display: flex;
  flex-direction: column;
  width: 100%;
}

.payment {
  display: flex;
  flex-direction: column;
  width: 100%;
}

.shipping > h2 {
  border-top-right-radius: 0;
}

.payment > h2 {
  border-top-left-radius: 0;
}

.shipping > ul {
  padding: 10px 20px 20px 25px;
}
.shipping > ul > li {
  margin-bottom: 8px;
}

.payment > ul > li {
  margin-bottom: 8px;
}

.payment > ul {
  padding: 10px 25px 20px 15px;
}

.table {
  grid-row: span 2;
  grid-column: span 2;
  margin-bottom: 2rem;
  margin-left: 10px;
  width: 100%;
}

.order-title {
  margin-top: 10px;
  font-size: 1.5rem;
  font-weight: 600;
  padding-left: 10px;
}

.empty-message {
  font-size: 2rem;
  font-weight: 600;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
}

.empty-img {
  margin: 60px 0;
  position: relative;
  background-position: center center;
  background-repeat: no-repeat;
  background-size: contain;
  height: 150px;
  width: 200px;
  background-image: url("/site-images/no-order.png");
}

.secondary-button {
  font-size: 20px;
  background-color: #f3f3f3;
  border-radius: 6px;
  color: #005ab6;
  border: 2px solid;
  border-color: #005ab6;
  padding: 10px;
  margin-top: 10px;
  font-weight: 500;
  font-family: BlinkMacSystemFont, "Segoe UI", Roboto, Oxygen, Ubuntu, Cantarell,
    "Open Sans", "Helvetica Neue", sans-serif;
  margin-bottom: 2rem;
}
.secondary-button:hover {
  background-color: #ffffff;
  cursor: pointer;
  font-weight: 520;
}
</style>

<template>
  <section class="bg-confirmation">
    <div class="dark-overlay">
      <div
        v-if="Object.keys(orderDetails).length != 0"
        class="confirmation-container"
      >
        <div class="title">Thank you !</div>
        <h1>
          Your order with confirmation
          <b>#{{ orderDetails.order.confirmationNumber }}</b>
          has been placed!
        </h1>
        <div class="time">
          <b><i class="far fa-clock"></i> Time Placed:</b>
          {{ new Date(orderDetails.order.dateCreated) }}
        </div>
        <div class="confirmation-grid">
          <div class="customer-box">
            <div class="shipping">
              <h2><i class="fas fa-map-marker-alt"></i> Shipping</h2>
              <ul>
                <li>
                  <b>{{ orderDetails.customer.customerName }}</b>
                </li>
                <li>{{ orderDetails.customer.address }}</li>
                <li>{{ orderDetails.customer.phone }}</li>
                <li>{{ orderDetails.customer.email }}</li>
              </ul>
            </div>
            <div class="payment">
              <h2><i class="far fa-credit-card"></i> Billing</h2>
              <ul>
                <li>
                  Ending with
                  {{ orderDetails.customer.ccNumber.substring(12) }}
                </li>
                <li>
                  Expiry: {{ getMonthYear(orderDetails.customer.ccExpDate) }}
                </li>
              </ul>
            </div>
          </div>

          <div class="table">
            <h1 class="order-title">Order list</h1>
            <ConfirmationTable />
          </div>
          <div class="summary-box">
            <div class="summary-container">
              <h2>Order Summary</h2>
              <ul class="subtotal">
                <li>Subtotal</li>
                <li>
                  {{ asDollarsAndCents(orderSubTotal) }}
                </li>
                <li>Shipping</li>
                <li>
                  {{ asDollarsAndCents(cartStore.cart.surcharge) }}
                </li>
                <li>Taxes</li>
                <li>$0</li>
                <li class="line-sep"></li>
                <li class="line-sep"></li>
                <li>Total</li>
                <li>
                  {{ asDollarsAndCents(orderDetails.order.amount) }}
                </li>
              </ul>
            </div>
          </div>
        </div>
        <router-link :to="{ name: 'category-view' }">
          <button class="secondary-button">
            <i class="fas fa-arrow-left"></i> Continue shopping
          </button>
        </router-link>
      </div>
      <div v-else class="confirmation-container">
        <div class="empty-message">
          We are sorry, the order you requested could not be found.
          <div class="empty-img"></div>
          <router-link :to="{ name: 'category-view' }">
            <button class="secondary-button">
              <i class="fas fa-arrow-left"></i> Continue shopping
            </button>
          </router-link>
        </div>
      </div>
    </div>
  </section>
</template>
