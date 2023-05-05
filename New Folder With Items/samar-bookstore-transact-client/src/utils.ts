import type { BookItem } from "@/types";

// From https://flaviocopes.com/how-to-format-number-as-currency-javascript/
const PriceFormatter = new Intl.NumberFormat("en-US", {
  style: "currency",
  currency: "USD",
  minimumFractionDigits: 2,
});

export const asDollarsAndCents = function (cents: number) {
  return PriceFormatter.format(cents / 100.0);
};

export const getMonthYear = function (dateInMs: number) {
  const date = new Date(dateInMs);
  const month = date.toLocaleString("default", { month: "short" });
  const year = date.getFullYear();
  return `${month}/${year}`;
};

const bookImageFileName = function (book: BookItem): string {
  let name = book.title.toLowerCase();
  name = name.replace(/ /g, "_");
  name = name.replace(/'/g, "");
  name += "_thumb";
  return `${name}.jpeg`;
};
function bookImageUrl(imageFileName: String) {
  return new URL(`./assets/book-images/${imageFileName}`, import.meta.url).href;
}

export const getBookImageUrl = function (book: BookItem) {
  return bookImageUrl(bookImageFileName(book));
};
