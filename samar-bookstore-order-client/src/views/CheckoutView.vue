<script setup lang="ts">
import { reactive, ref } from "vue";
import { useRouter } from "vue-router";
import useVuelidate from "@vuelidate/core";
import {
  helpers,
  maxLength,
  minLength,
  required,
  email,
} from "@vuelidate/validators";
import { useCartStore } from "@/stores/cart";
import { isCreditCard, isMobilePhone } from "@/validators";
import CheckoutFieldError from "@/components/CheckoutFieldError.vue";
import { asDollarsAndCents } from "@/utils";
import type { OrderDetails, ServerErrorResponse } from "@/types";

const defaultServerErrorMessage =
  "An unexpected error occurred, please try again.";
const serverErrorMessage = ref(defaultServerErrorMessage);

const router = useRouter();
const cartStore = useCartStore();
const cart = cartStore.cart;

const months: string[] = [
  "January",
  "February",
  "March",
  "April",
  "May",
  "June",
  "July",
  "August",
  "September",
  "October",
  "November",
  "December",
];

const form = reactive({
  name: "",
  address: "",
  phone: "",
  email: "",
  ccNumber: "",
  ccExpiryMonth: new Date().getMonth() + 1,
  ccExpiryYear: new Date().getFullYear(),
  checkoutStatus: "",
});

const rules = {
  name: {
    required: helpers.withMessage("Please provide a name.", required),
    minLength: helpers.withMessage(
      "Name must have at least 4 letters.",
      minLength(4)
    ),
    maxLength: helpers.withMessage(
      "Name can have at most 45 letters.",
      maxLength(45)
    ),
  },
  address: {
    required: helpers.withMessage("Please provide an address.", required),
    minLength: helpers.withMessage(
      "Address must have at least 4 letters.",
      minLength(4)
    ),
    maxLength: helpers.withMessage(
      "Address can have at most 45 letters.",
      maxLength(45)
    ),
  },
  phone: {
    required: helpers.withMessage("Please provide a phone number.", required),
    phone: helpers.withMessage(
      "Please provide a valid phone number.",
      (value: string) => !helpers.req(value) || isMobilePhone(value)
    ),
  },
  email: {
    required: helpers.withMessage("Please provide an email address.", required),
    email: helpers.withMessage("Please provide a valid email address.", email),
  },
  ccNumber: {
    required: helpers.withMessage("Please provide a credit card.", required),
    ccNumber: helpers.withMessage(
      "Please provide a valid credit card.",
      (value: string) => !helpers.req(value) || isCreditCard(value)
    ),
  },
  ccExpiryMonth: {},
  ccExpiryYear: {},
  //   TODO: Add more validations for these and other fields that need more validation.
};
const v$ = useVuelidate(rules, form);

async function submitOrder() {
  console.log("Submit order");
  const isFormCorrect = await v$.value.$validate();
  if (!isFormCorrect) {
    form.checkoutStatus = "ERROR";
  } else {
    try {
      form.checkoutStatus = "PENDING";
      serverErrorMessage.value = defaultServerErrorMessage;

      const placeOrderResponse: OrderDetails | ServerErrorResponse =
        await cartStore.placeOrder({
          name: form.name,
          address: form.address,
          phone: form.phone,
          email: form.email,
          ccNumber: form.ccNumber,
          ccExpiryMonth: form.ccExpiryMonth,
          ccExpiryYear: form.ccExpiryYear,
        });

      if ("error" in placeOrderResponse) {
        form.checkoutStatus = "SERVER_ERROR";
        serverErrorMessage.value = placeOrderResponse.message;
        console.log("Error placing order", placeOrderResponse);
      } else {
        form.checkoutStatus = "OK";
        await router.push({ name: "confirmation-view" });
      }
    } catch (e) {
      form.checkoutStatus = "SERVER_ERROR";
      serverErrorMessage.value = defaultServerErrorMessage;
      console.log("Error placing order", e);
    }
  }
}

/* NOTE: For example yearFrom(0) == <current_year> */
function yearFrom(index: number) {
  return new Date().getFullYear() + index;
}
</script>

<style scoped>
/* TODO: Adapt these styles to your page */
.checkout-page {
  background: rgba(105, 100, 100, 0.5);
  color: #7aed32;
  padding-top: 200px;
}
.checkout-page-body {
  display: flex;
  padding: 1em;
}

form {
  display: flex;
  flex-direction: column;
}

form > div {
  justify-content: flex-end;
  display: flex;
  margin-bottom: 1.2em;
  margin: 1.2rem 10rem 0rem 9rem;
}

form > div > label {
  padding: 6px 0;
  font-size: 1.3rem;
  font-weight: 500;
}

form > div > input,
form > div > select {
  background-color: rgb(32, 32, 32);
  margin-left: 0.8em;
  border: none;
  border-radius: 6px;
  width: 15.8rem;
  padding: 6px 0.5rem;
  font-size: 1.15rem;
}

.checkoutStatusBox {
  margin-top: 1em;
  text-align: center;
  font-weight: 600;
  color: rgb(0, 111, 223);
}

.summary-box .checkoutStatusBox {
  color: rgb(232, 232, 232);
  font-weight: 800;
  font-size: 1.2rem;
  text-align: right;
  margin-left: 2rem;
}

.primary-button {
  background-color: #005ab6;
  border-radius: 6px;
  color: #fff;
  border: none;
  padding: 10px;
  margin-top: 15px;
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
.bg-checkout {
  position: relative;
  background-attachment: fixed;
  background-position: center center;
  background-repeat: no-repeat;
  background-size: cover;
  height: 100%;
  min-height: 650px;
  background-image: url("/site-images/checkout-hero2.jpeg");
}
.dark-overlay {
  background-color: rgba(0, 0, 0, 0.738);
}
.checkout-container {
  align-items: flex-start;
  padding: 120px 0 50px 0;
  display: flex;
  margin: 0 100px;
  justify-content: center;
  column-gap: 2rem;
}

.empty-container {
  align-items: center;
  padding: 220px 0 50px 0;
  display: flex;
  margin: 0 100px;
  flex-direction: column;
}

.item-container {
}

.summary-container {
  margin-left: 20px;
  flex-grow: 1;
  background-color: rgba(0, 0, 0, 0.656);
  align-items: center;
  flex-direction: column;
  display: flex;
  border-radius: 6px;
  margin-top: 60px;
}

.item-actions {
  display: flex;
  justify-content: space-between;
  margin: 15px 40px;
}
h1 {
  font-size: 1.6em;
}
h2 {
  text-align: left;
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

.form-box {
  border-radius: 6px;
  background-color: rgba(0, 0, 0, 0.656);
  padding-bottom: 1.5rem;
}
.form-items {
  width: 100%;
}

.form-box h2 {
  font-size: 1.4rem;
  margin: 2rem 0 0.5rem 0;
  font-weight: 400;
  padding-left: 2rem;
}
.form-items .primary-button {
  margin: 2rem 10rem 1.2rem 17.2rem;
  width: 15.8rem;
}

.checkout-details {
  margin-right: -3rem;
  text-align: center;
  font-weight: 500;
  margin-bottom: 0.5rem;
}

.expiry-month {
  width: 10rem;
}
.expiry-year {
  width: 4.8rem;
}
</style>

<template>
  <section class="bg-checkout">
    <div class="dark-overlay">
      <section class="checkout-container" v-if="!cart.empty">
        <div class="item-container">
          <h1>
            Checkout ({{ cartStore.count }} item<span v-if="cartStore.count > 1"
              >s</span
            >)
          </h1>
          <div class="form-box">
            <h2>Shipping Details</h2>
            <form @submit.prevent="submitOrder" class="form-items">
              <div>
                <label for="name">Name</label>
                <input
                  type="text"
                  size="20"
                  id="name"
                  name="name"
                  v-model.lazy="v$.name.$model"
                />
              </div>
              <checkout-field-error :fieldName="v$.name" />
              <!-- TODO: Add address input and validation messages -->
              <div>
                <label for="address">Address</label>
                <input
                  type="text"
                  size="20"
                  id="address"
                  name="address"
                  v-model.lazy="v$.address.$model"
                />
              </div>
              <checkout-field-error :fieldName="v$.address" />
              <div>
                <label for="phone">Phone</label>
                <input
                  class="textField"
                  type="text"
                  size="20"
                  id="phone"
                  name="phone"
                  v-model.lazy="v$.phone.$model"
                />
              </div>
              <checkout-field-error :fieldName="v$.phone" />
              <!-- TODO: Add phone validation message(s) -->

              <div>
                <label for="email">Email</label>
                <input
                  type="text"
                  size="20"
                  id="email"
                  name="email"
                  v-model.lazy="v$.email.$model"
                />
              </div>
              <checkout-field-error :fieldName="v$.email" />

              <!-- TODO: Add email validation message(s) -->
              <h2>Payment Details</h2>
              <div>
                <label for="ccNumber">Credit card</label>
                <input
                  type="text"
                  size="20"
                  id="ccNumber"
                  name="ccNumber"
                  v-model.lazy="v$.ccNumber.$model"
                />
              </div>
              <checkout-field-error :fieldName="v$.ccNumber" />

              <!-- TODO: Add credit card validation message(s) -->

              <div>
                <label>Expiry</label>
                <select v-model="v$.ccExpiryMonth.$model" class="expiry-month">
                  <option
                    v-for="(month, index) in months"
                    :key="index"
                    :value="index + 1"
                  >
                    {{ month }} ({{ index + 1 }})
                  </option>
                </select>
                <select v-model="v$.ccExpiryYear.$model" class="expiry-year">
                  <option
                    v-for="ind in 15"
                    :key="ind"
                    :value="yearFrom(ind - 1)"
                  >
                    {{ yearFrom(ind - 1) }}
                  </option>
                </select>
              </div>

              <!-- TODO (style): Use a single label for both month and date and put the on the same line. -->
              <!-- TODO (style): For example: Exp Date {Month} {Year}, with space between month/year selectors. -->

              <input
                type="submit"
                name="submit"
                class="primary-button"
                :disabled="form.checkoutStatus === 'PENDING'"
                value="Place your order"
                @click.prevent="submitOrder"
              />
              <!-- TODO (style): The submit button should not take up the entire width of the form. -->
              <!-- TODO (style): The submit button should be styled consistent with your own site. -->
            </form>
            <!-- TODO (style): Fix error message placement so they nearer to the correct fields. -->
            <!-- TODO (style): HINT: Use another <div> and label, input, and error, and use flexbox to style. -->

            <!-- TODO: Display the cart total, subtotal and surcharge. -->
            <section class="checkout-details">
              Your credit card will be charged
              {{ asDollarsAndCents(cart.subtotal + cart.surcharge) }}
            </section>
            <section
              v-show="form.checkoutStatus !== ''"
              class="checkoutStatusBox"
            >
              <div v-if="form.checkoutStatus === 'ERROR'">
                Error: Please fix the problems above and try again.
              </div>

              <div v-else-if="form.checkoutStatus === 'PENDING'">
                Processing...
              </div>

              <div v-else-if="form.checkoutStatus === 'OK'">
                Order placed...
              </div>

              <div v-else>An unexpected error occurred, please try again.</div>
            </section>
            <!-- <div class="item-actions">
            <router-link :to="{ name: 'category-view' }">
              <button class="secondary-button">
                <i class="fas fa-arrow-left"></i> Continue shopping
              </button>
            </router-link>
            <button class="tertiary-button" @click="clearCart">
              <i class="far fa-trash-alt"></i> Clear cart
            </button>
          </div> -->
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
              <li>{{ asDollarsAndCents(cartStore.cart.surcharge) }}</li>
              <li>Taxes</li>
              <li>$0</li>
              <li class="line-sep"></li>
              <li class="line-sep"></li>
              <li>Total</li>
              <li>{{ asDollarsAndCents(cartStore.cart.total) }}</li>
            </ul>
          </div>
          <input
            type="submit"
            name="submit"
            class="primary-button"
            :disabled="form.checkoutStatus === 'PENDING'"
            value="Place your order"
            @click.prevent="submitOrder"
          />
          <section
            v-show="form.checkoutStatus !== ''"
            class="checkoutStatusBox"
          >
            <div v-if="form.checkoutStatus === 'ERROR'">
              Error: Please fix the problems and try again.
            </div>

            <div v-else-if="form.checkoutStatus === 'PENDING'">
              Processing...
            </div>

            <div v-else-if="form.checkoutStatus === 'OK'">Order placed...</div>

            <div v-else>An unexpected error occurred, please try again.</div>
          </section>
        </div>
      </section>
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
</template>
