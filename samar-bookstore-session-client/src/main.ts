import { createApp } from "vue";
import { createPinia } from "pinia";

import App from "./App.vue";
import router from "./router";

import "./assets/global.css";

const app = createApp(App);

app.use(createPinia());
app.use(router);
// From https://flaviocopes.com/how-to-format-number-as-currency-javascript/
const PriceFormatter = new Intl.NumberFormat("en-US", {
  style: "currency",
  currency: "USD",
  minimumFractionDigits: 2,
});

export const asDollarsAndCents = function (cents: number) {
  return PriceFormatter.format(cents / 100.0);
};
app.mount("#app");
