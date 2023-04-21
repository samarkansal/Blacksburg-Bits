import { defineStore } from "pinia";
import type { BookItem } from "@/types";
import { ShoppingCart } from "@/types";

export const useCartStore = defineStore("cart", {
  state: () => ({
    cart: new ShoppingCart(),
  }),

  getters: {
    count(): number {
      return this.cart.numberOfItems;
    },
  },

  actions: {
    clearCart() {
      this.cart.clear();
    },
    addToCart(book: BookItem) {
      this.cart.addBook(book);
    },
    updateBookQuantity(book: BookItem, quantity: number) {
      this.cart.update(book, quantity);
    },
  },
});
