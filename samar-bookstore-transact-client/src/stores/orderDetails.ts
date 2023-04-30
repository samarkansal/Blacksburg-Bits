import type { OrderDetails } from "@/types";
import { defineStore } from "pinia";

const ORDER_DETAILS_STORAGE_KEY = "orderDetails";

export const useOrderDetailsStore = defineStore("OrderDetails", {
  state: () => {
    let orderDetails = {} as OrderDetails;
    const initOrderString: string | null = sessionStorage.getItem(
      ORDER_DETAILS_STORAGE_KEY
    );
    if (initOrderString !== null) {
      const orderFromStorage = JSON.parse(initOrderString) as OrderDetails;
      orderDetails = Object.assign({}, orderFromStorage);
    }
    return {
      orderDetails: orderDetails,
    };
  },

  actions: {
    clearOrderDetails() {
      sessionStorage.removeItem(ORDER_DETAILS_STORAGE_KEY);
      this.orderDetails = {} as OrderDetails;
    },
    setOrderDetails(orderDetails: OrderDetails) {
      this.orderDetails = orderDetails;
      sessionStorage.setItem(
        ORDER_DETAILS_STORAGE_KEY,
        JSON.stringify(orderDetails)
      );
    },
    hasOrderDetails() {
      return Object.keys(this.orderDetails).length === 0;
    },
  },
});
