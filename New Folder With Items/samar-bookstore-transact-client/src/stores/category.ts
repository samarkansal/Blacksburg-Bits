import type { CategoryItem } from "@/types";
import { apiUrl } from "@/api";
import { defineStore } from "pinia";

const CATEGORY_LIST_KEY = "categoryList";

export const useCategoryStore = defineStore("category", {
  state: () => ({
    categoryList: [] as CategoryItem[],
  }),

  getters: {},
  actions: {
    async fetchCategories() {
      const response = await fetch(`${apiUrl}/categories`);
      const data = await response.json();
      this.categoryList = data as CategoryItem[];
      const now = new Date();
      const expiration = now.getTime() + 3600 * 1000; // 1 Hour is milliseconds
      localStorage.setItem(
        CATEGORY_LIST_KEY,
        JSON.stringify({ data: this.categoryList, expiration })
      );
      return this.categoryList;
    },

    async getCategoryList() {
      // Get categories from localStorage
      const storedCategories = JSON.parse(
        localStorage.getItem(CATEGORY_LIST_KEY) || "null"
      );
      const now = new Date();
      let categories: CategoryItem[] = [];
      // Check if categories are still valid
      if (
        storedCategories &&
        storedCategories.expiration > now.getTime() &&
        Array.isArray(storedCategories.data)
      ) {
        // Use stored categories
        categories = storedCategories.data;
      } else {
        // Fetch new categories from API
        categories = await this.fetchCategories();
      }
      this.categoryList = categories;
      return categories;
    },
  },
});
