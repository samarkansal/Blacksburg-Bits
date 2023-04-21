<script setup lang="ts">
import CartTable from "@/components/CartTable.vue";
import { useCartStore } from "@/stores/cart";
import { asDollarsAndCents } from "@/utils";
const cartStore = useCartStore();
const clearCart = function () {
  cartStore.clearCart();
};
</script>
<style scoped>
.primary-button {
  background-color: #005ab6;
  border-radius: 6px;
  color: #fff;
  border: none;
  padding: 10px;
  margin-top: 10px;
  font-weight: 500;
  font-family: BlinkMacSystemFont, "Segoe UI", Roboto, Oxygen, Ubuntu, Cantarell,
    "Open Sans", "Helvetica Neue", sans-serif;
}
.primary-button:hover {
  background-color: #0069d2;
  cursor: pointer;
  font-weight: 510;
}

.secondary-button {
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
}
.secondary-button:hover {
  background-color: #ffffff;
  cursor: pointer;
  font-weight: 520;
}

.tertiary-button {
  background-color: #d9d9d9;
  border-radius: 6px;
  color: #0165ca;
  border: 1px solid;
  border-color: #005ab6;
  padding: 10px;
  margin-top: 10px;
  font-weight: 300;
  font-family: BlinkMacSystemFont, "Segoe UI", Roboto, Oxygen, Ubuntu, Cantarell,
    "Open Sans", "Helvetica Neue", sans-serif;
}
.tertiary-button:hover {
  background-color: #ffffff;
  cursor: pointer;
  font-weight: 320;
}
.bg-cart {
  position: relative;
  background-attachment: fixed;
  background-position: center center;
  background-repeat: no-repeat;
  background-size: cover;
  height: 100%;
  min-height: 650px;
  background-image: url("/site-images/cart-hero.jpeg");
}
.dark-overlay {
  background-color: rgba(0, 0, 0, 0.798);
}
.cart-container {
  align-items: flex-start;
  padding: 120px 0 50px 0;
  display: flex;
  margin: 0 100px;
}

.empty-container {
  align-items: center;
  padding: 220px 0 50px 0;
  display: flex;
  margin: 0 100px;
  flex-direction: column;
}

.item-container {
  flex-grow: 2;
  margin: 0 -30px;
}

.summary-container {
  margin-left: 20px;
  flex-grow: 1;
  background-color: rgba(0, 0, 0, 0.656);
  align-items: center;
  flex-direction: column;
  display: flex;
  border-radius: 6px;
  margin-top: 42px;
}

.item-actions {
  display: flex;
  justify-content: space-between;
  margin: 15px 40px;
}
h1 {
  margin-bottom: 0.5em;
  margin-left: 1.3em;
  font-size: 1.6em;
}
h2 {
  text-align: center;
  width: 100%;
  background-color: rgb(36, 36, 36);
  padding: 10px;
  border-radius: 6px 6px 0 0;
}
.subtotal {
  display: grid;
  grid-template-columns: 1fr 1fr;
  row-gap: 8px;
  padding: 20px;
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

.summary-box {
  display: flex;
  flex-direction: column;
  align-items: center;
}

.empty-header {
  margin-left: 0;
  margin-bottom: 0.7em;
  color: rgb(240, 240, 240);
  font-size: 2em;
}
</style>

<template>
  <div>
    <section class="bg-cart">
      <div class="dark-overlay">
        <div class="cart-container" v-if="cartStore.count > 0">
          <div class="item-container">
            <h1>
              Your cart ({{ cartStore.count }} item<span
                v-if="cartStore.count > 1"
                >s</span
              >)
            </h1>
            <cart-table></cart-table>
            <div class="item-actions">
              <router-link :to="{ name: 'category-view' }">
                <button class="secondary-button">
                  <i class="fas fa-arrow-left"></i> Continue shopping
                </button>
              </router-link>
              <button class="tertiary-button" @click="clearCart">
                <i class="far fa-trash-alt"></i> Clear cart
              </button>
            </div>
          </div>
          <div class="summary-box">
            <div class="summary-container">
              <h2>Summary</h2>
              <ul class="subtotal">
                <li>
                  Subtotal ({{ cartStore.count }} item<span
                    v-if="cartStore.count > 1"
                    >s</span
                  >)
                </li>
                <li>{{ asDollarsAndCents(cartStore.cart.subtotal) }}</li>
                <li>Shipping</li>
                <li>Calculated at checkout</li>
                <li>Taxes</li>
                <li>Calculated at checkout</li>
                <li class="line-sep"></li>
                <li class="line-sep"></li>
                <li></li>
                <li>{{ asDollarsAndCents(cartStore.cart.subtotal) }}</li>
              </ul>
            </div>
            <router-link :to="{ name: 'checkout-view' }">
              <button class="primary-button">Proceed to checkout</button>
            </router-link>
          </div>
        </div>
        <div class="empty-container" v-else>
          <h1 class="empty-header">Your cart is empty</h1>
          <router-link :to="{ name: 'category-view' }">
            <button class="secondary-button">
              <i class="fas fa-arrow-left"></i> Continue shopping
            </button>
          </router-link>
        </div>
      </div>
    </section>
  </div>
</template>
