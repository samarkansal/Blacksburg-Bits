<script setup lang="ts">
import { defineProps } from "vue";
import type { BaseValidation } from "@vuelidate/core";

const props = defineProps<{
  fieldName: BaseValidation;
}>();
</script>

<style scoped>
form > .error {
  color: rgb(90, 90, 250);
  text-align: right;
  margin-top: 4px;
  margin-right: 10rem;
  font-size: 1rem;
}
</style>

<template>
  <template v-if="fieldName.$error">
    <span class="error" v-for="error of fieldName.$errors" :key="error.$uid">{{
      error.$message
    }}</span>
  </template>
</template>
