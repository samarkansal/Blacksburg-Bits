<script setup lang="ts">
import ConfirmationTable from "@/components/ConfirmationTable";

import { useCategoryStore } from "@/stores/CategoryStore";
const categoryStore = useCategoryStore();

import { useOrderDetailsStore } from "@/stores/OrderDetailsStore";
import { computed } from "vue";
const orderDetailsStore = useOrderDetailsStore();
const orderDetails = orderDetailsStore.orderDetails;

const orderDate = computed(function () {
  let date = new Date(orderDetails.order.dateCreated);
  return date.toLocaleTimeString();
});

const ccExpDate = computed(function (): Date {
  return new Date(orderDetails.customer.ccExpDate);
});

const ccExpYear = computed(function (): number {
  return ccExpDate.value.getFullYear();
});

const ccExpMonth = computed(function (): string {
  let month = ccExpDate.value.getMonth() + 1;
  let result = "" + month;
  if (month < 10) {
    result = "0" + month;
  }
  return result;
});
</script>

<style scoped>
#confirmationView {
  display: flex;
  flex-direction: column;
  align-items: flex-start;
  margin: 1em auto;
}
ul {
  text-align: left;
}
ul > li {
  margin: 0.25em;
}
</style>

<template>
  <div id="confirmationView">
    <ul>
      <li>Confirmation #: 123456789</li>
      <li>Time: 2020-08-05-18:33:56</li>
    </ul>
    <confirmation-table> </confirmation-table>
    <ul>
      <li>{{ orderDetails.customer.customerName }}</li>
      <li>{{ orderDetails.customer.address }}</li>
      <li>{{ orderDetails.customer.email }}</li>
      <li>703-555-1212</li>
      <li>**** **** **** 1111 (08-2020)</li>
    </ul>
    <div id="customerInfo"></div>
  </div>
</template>
