import { defineStore } from "pinia";
import type { BookItem, CategoryItem } from "@/types";
import { apiUrl } from "@/api";
import { useCategoryStore } from "@/stores/category";

export const useBookStore = defineStore("book", {
  state: () => ({
    bookList: [] as BookItem[],
    currentCategory: "",
  }),
  actions: {
    async fetchBooks(categoryName: string) {
      const categoryStore = useCategoryStore();

      const selectedCategoryName =
        categoryStore.categoryList?.find(
          (category) => category.name === categoryName
        )?.name || categoryName;

      if (this.currentCategory !== selectedCategoryName) {
        this.bookList = await fetch(
          `${apiUrl}/categories/name/${selectedCategoryName}/books`
        ).then((response) => response.json());
        this.currentCategory = selectedCategoryName;
      }
    },
  },
  // getters
});
