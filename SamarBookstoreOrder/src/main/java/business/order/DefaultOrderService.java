package business.order;

import api.ApiException;
import business.book.Book;
import business.book.BookDao;
import business.cart.ShoppingCart;
import business.customer.CustomerForm;

import java.time.DateTimeException;
import java.time.YearMonth;
import java.util.Date;

public class DefaultOrderService implements OrderService {

	private BookDao bookDao;

	public void setBookDao(BookDao bookDao) {
		this.bookDao = bookDao;
	}

	@Override
	public OrderDetails getOrderDetails(long orderId) {
		// NOTE: THIS METHOD PROVIDED NEXT PROJECT
		return null;
	}

	@Override
    public long  placeOrder(CustomerForm customerForm, ShoppingCart cart) {

		validateCustomer(customerForm);
		validateCart(cart);

		// NOTE: MORE CODE PROVIDED NEXT PROJECT

		return -1;
	}


	private void validateCustomer(CustomerForm customerForm) {

		String name = customerForm.getName();
		if (name == null || name.isEmpty() || name.length() < 4 || name.length() > 45) {
			throw new ApiException.ValidationFailure("name", "Name field must be between 4 and 45 characters");
		}

		String address = customerForm.getAddress();
		if (address == null || address.isEmpty() || address.length() < 4 || address.length() > 45) {
			throw new ApiException.ValidationFailure("address", "Address field must be between 4 and 45 characters");
		}

		String phone = customerForm.getPhone();
		if (phone == null || phone.isEmpty() || phone.replaceAll("\\D","").length() != 10) {
			throw new ApiException.ValidationFailure("phone", "Invalid phone number");
		}

		String email = customerForm.getEmail();
		if (email == null || email.isEmpty() || email.contains(" ") || !email.contains("@") || email.charAt(email.length()-1) == '.') {
			throw new ApiException.ValidationFailure("email", "Invalid email field");
		}

		String ccNumber = customerForm.getCcNumber();
		if (ccNumber == null || ccNumber.isEmpty()) {
			throw new ApiException.ValidationFailure("ccNumber", "Credit card number is required");
		}
		ccNumber = ccNumber.replaceAll("\\s|-", "");
		if (ccNumber.length() < 14 || ccNumber.length() > 16) {
			throw new ApiException.ValidationFailure("ccNumber", "Invalid credit card number");
		}

		String ccExpiryMonth = customerForm.getCcExpiryMonth();
		String ccExpiryYear = customerForm.getCcExpiryYear();
		if (ccExpiryMonth == null || ccExpiryYear == null) {
			throw new ApiException.ValidationFailure("ccNumber", "Please enter a valid expiration date");
		}
		if (expiryDateIsInvalid(ccExpiryMonth, ccExpiryYear)) {
			throw new ApiException.ValidationFailure(null, "Please enter a valid expiration date");
		}
	}

	private boolean expiryDateIsInvalid(String ccExpiryMonth, String ccExpiryYear) {

		// TODO: return true when the provided month/year is before the current month/yeaR
		// HINT: Use Integer.parseInt and the YearMonth class

		try {
			YearMonth expiryDate = YearMonth.of(Integer.parseInt(ccExpiryYear), Integer.parseInt(ccExpiryMonth));
			YearMonth currentDate = YearMonth.now();
			return expiryDate.isBefore(currentDate);
		} catch (NumberFormatException e) {
			return true;
		}

	}

	/**
	 * Validates a shopping cart to ensure that it contains at least one item,
	 * each item has a valid quantity, price and category.
	 *
	 * @param cart the shopping cart to validate
	 * @throws ApiException.ValidationFailure if any validation error occurs
	 */
	private void validateCart(ShoppingCart cart) {
		if (cart.getItems().isEmpty()) {
			throw new ApiException.ValidationFailure("The shopping cart is empty.");
		}

		cart.getItems().forEach(item -> {
			int quantity = item.getQuantity();
			if (quantity < 1 || quantity > 99) {
				throw new ApiException.ValidationFailure("Invalid quantity for the book \"" + item.getBookForm().getTitle() + "\": " + quantity);
			}

			Book databaseBook = bookDao.findByBookId(item.getBookId());
			if (databaseBook == null) {
				throw new ApiException.ValidationFailure("Book \"" + item.getBookForm().getTitle() + "\" does not exist in the database.");
			}

			int price = item.getBookForm().getPrice();
			if (price != databaseBook.getPrice()) {
				throw new ApiException.ValidationFailure("Price mismatch for the book \"" + item.getBookForm().getTitle() + "\". Expected: " + databaseBook.getPrice() + ", Actual: " + price);
			}

			long categoryId = item.getBookForm().getCategoryId();
			if (categoryId != databaseBook.getCategoryId()) {
				throw new ApiException.ValidationFailure("Category mismatch for the book \"" + item.getBookForm().getTitle() + "\". Expected: " + databaseBook.getCategoryId() + ", Actual: " + categoryId);
			}
		});
	}



}
