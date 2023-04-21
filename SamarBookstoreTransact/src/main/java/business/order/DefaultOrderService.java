package business.order;

import api.ApiException;
import business.BookstoreDbException;
import business.JdbcUtils;
import business.book.Book;
import business.book.BookDao;
import business.cart.ShoppingCart;
import business.cart.ShoppingCartItem;
import business.customer.Customer;
import business.customer.CustomerDao;
import business.customer.CustomerForm;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.DateTimeException;
import java.time.YearMonth;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class DefaultOrderService implements OrderService {

	private BookDao bookDao;

	private LineItemDao lineItemDao;

	private CustomerDao customerDao;

	private Random random = new Random();

	public void setLineItemDao(LineItemDao lineItemDao) {
		this.lineItemDao = lineItemDao;
	}

	public void setCustomerDao(CustomerDao customerDao) {
		this.customerDao = customerDao;
	}

	public void setOrderDao(OrderDao orderDao) {
		this.orderDao = orderDao;
	}

	private OrderDao orderDao;

	public void setBookDao(BookDao bookDao) {
		this.bookDao = bookDao;
	}

	@Override
	public OrderDetails getOrderDetails(long orderId) {
		Order order = orderDao.findByOrderId(orderId);
		Customer customer = customerDao.findByCustomerId(order.getCustomerId());
		List<LineItem> lineItems = lineItemDao.findByOrderId(orderId);
		List<Book> books = lineItems
				.stream()
				.map(lineItem -> bookDao.findByBookId(lineItem.getBookId()))
				.collect(Collectors.toList());
		return new OrderDetails(order, customer, lineItems, books);
	}

	@Override
    public long  placeOrder(CustomerForm customerForm, ShoppingCart cart) {

		validateCustomer(customerForm);
		validateCart(cart);

		try (Connection connection = JdbcUtils.getConnection()) {
			Date date = getDate(
					customerForm.getCcExpiryMonth(),
					customerForm.getCcExpiryYear());
			return performPlaceOrderTransaction(
					customerForm.getName(),
					customerForm.getAddress(),
					customerForm.getPhone(),
					customerForm.getEmail(),
					customerForm.getCcNumber(),
					date, cart, connection);
		} catch (SQLException e) {
			throw new BookstoreDbException("Error during close connection for customer order", e);
		}

		// NOTE: MORE CODE PROVIDED NEXT PROJECT

		//return -1;
	}

	private Date getDate(String monthString, String yearString) {
		int month = Integer.parseInt(monthString);
		int year = Integer.parseInt(yearString);
		return new Date(year-1900, month-1, 1); // TODO Implement this correctly
	}

	private long performPlaceOrderTransaction(
			String name, String address, String phone,
			String email, String ccNumber, Date date,
			ShoppingCart cart, Connection connection) {
		try {
			connection.setAutoCommit(false);
			long customerId = customerDao.create(
					connection, name, address, phone, email,
					ccNumber, date);
			long customerOrderId = orderDao.create(
					connection,
					cart.getComputedSubtotal() + cart.getSurcharge(),
					generateConfirmationNumber(), customerId);
			for (ShoppingCartItem item : cart.getItems()) {
				lineItemDao.create(connection, customerOrderId, item.getBookId(), item.getQuantity());
			}
			connection.commit();
			return customerOrderId;
		} catch (Exception e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				throw new BookstoreDbException("Failed to roll back transaction", e1);
			}
			return 0;
		}
	}

	private int generateConfirmationNumber() {
		return random.nextInt(999999999);
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
