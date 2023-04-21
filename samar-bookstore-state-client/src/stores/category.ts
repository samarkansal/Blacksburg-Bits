import type { CategoryItem } from "@/types";
import { apiUrl } from "@/api";
import { defineStore } from "pinia";

export const useCategoryStore = defineStore("category", {
  state: () => ({
    categoryList: [] as CategoryItem[],
  }),
  actions: {
    async fetchCategories() {
      const response = await fetch(`${apiUrl}/categories`);
      const data = await response.json();
      this.categoryList = data as CategoryItem[];
    },
  },
  // getters
});
