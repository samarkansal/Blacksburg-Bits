import { createRouter, createWebHistory } from "vue-router";
import HomeView from "../views/HomeView.vue";
import CategoryView from "@/views/CategoryView.vue";
import CartView from "@/views/CartView.vue";
import CheckoutView from "@/views/CheckoutView.vue";
import ConfirmationView from "@/views/ConfirmationView.vue";
import NotFoundView from "@/views/NotFoundView.vue";
import { useBookStore } from "@/stores/book";
import { useCategoryStore } from "@/stores/category";
const router = createRouter({
  scrollBehavior() {
    // always scroll to top
    return { top: 0 };
  },
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: "/home",
      name: "home-view",
      component: HomeView,
      alias: ["/", "/index.html"],
    },
    {
      path: "/category/:name?",
      name: "category-view",
      component: CategoryView,
      beforeEnter: async (to, from, next) => {
        const bookStore = useBookStore();
        const categoryStore = useCategoryStore();
        const categoryList = await categoryStore.getCategoryList();
        const category =
          categoryList
            ?.find((category) => category.name.toLowerCase() === to.params.name)
            ?.name.toLowerCase() ||
          bookStore.currentCategory ||
          "sci-fi";
        console.log(category);
        //to.params.name = category; // this won't update the browser url
        if (category == to.params.name) next(); // to avoid infinite route loop
        else
          next({
            name: "category-view",
            params: { name: category },
            replace: true, // use replace to update URL without adding a new entry to the history stack
          });
      },
    },
    {
      path: "/cart",
      name: "cart-view",
      component: CartView,
    },
    {
      path: "/checkout",
      name: "checkout-view",
      component: CheckoutView,
    },
    {
      path: "/confirmation",
      name: "confirmation-view",
      component: ConfirmationView,
    },
    { path: "/:pathMatch(.*)*", name: "not-found", component: NotFoundView },
  ],
});

export default router;
