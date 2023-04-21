import { createRouter, createWebHistory } from "vue-router";
import HomeView from "../views/HomeView.vue";
import CategoryView from "@/views/CategoryView.vue";
import CartView from "@/views/CartView.vue";
import CheckoutView from "@/views/CheckoutView.vue";
import { useBookStore } from "@/stores/book";
const router = createRouter({
  scrollBehavior() {
    // always scroll to top
    return { top: 0 };
  },
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: "/",
      name: "home",
      component: HomeView,
    },
    {
      path: "/category/:name?",
      name: "category-view",
      component: CategoryView,
      beforeEnter: async (to, from, next) => {
        const bookStore = useBookStore();
        const category =
          to.params.name || bookStore.currentCategory || "sci-fi";
        //to.params.name = category; // this won't update the browser url
        if (to.params.name) next(); // to avoid infinite route loop
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
  ],
});

export default router;
