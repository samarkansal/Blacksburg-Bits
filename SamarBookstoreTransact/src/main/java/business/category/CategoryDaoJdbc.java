package business.category;

import business.BookstoreDbException.BookstoreQueryDbException;
import business.JdbcUtils;
import business.book.Book;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CategoryDaoJdbc implements CategoryDao {

    private static final String FIND_ALL_SQL =
            "SELECT category_id, name " +
                    "FROM category";

    private static final String FIND_BY_CATEGORY_ID_SQL =
            "SELECT category_id, name " +
                    "FROM category " +
                    "WHERE category_id = ?";

    private static final String FIND_BY_NAME_SQL =
            "SELECT category_id, name " +
                    "FROM category " +
                    "WHERE name = ?";

    private static final String FIND_BOOKS_BY_CATEGORY_NAME_SQL =
            "SELECT book_id, title, author, price, is_public, book.category_id, description, is_featured, rating  " +
                    "FROM book, category " +
                    "WHERE book.category_id = category.category_id AND category.name = ?";

    private static final String FIND_RANDOM_BOOKS_BY_CATEGORY_NAME_SQL =
            "SELECT book_id, title, author, price, is_public, book.category_id, description, is_featured, rating  " +
                    "FROM book, category " +
                    "WHERE book.category_id = category.category_id AND category.name = ?" +
                    "ORDER BY RAND() " +
                    "LIMIT ?";

    @Override
    public List<Category> findAll() {
        List<Category> categories = new ArrayList<>();
        try (Connection connection = JdbcUtils.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_ALL_SQL);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                Category category = readCategory(resultSet);
                categories.add(category);
            }
        } catch (SQLException e) {
            throw new BookstoreQueryDbException("Encountered a problem finding all categories", e);
        }
        return categories;
    }

    @Override
    public Category findByCategoryId(long categoryId) {
        Category category = null;
        try (Connection connection = JdbcUtils.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_BY_CATEGORY_ID_SQL)) {
            statement.setLong(1, categoryId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    category = readCategory(resultSet);
                }
            }
        } catch (SQLException e) {
            throw new BookstoreQueryDbException("Encountered a problem finding category " + categoryId, e);
        }
        return category;
    }

    @Override
    public Category findByName(String name) {
        Category category = null;
        try (Connection connection = JdbcUtils.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_BY_NAME_SQL)) {
            statement.setString(1, name);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    category = readCategory(resultSet);
                }
            }
        } catch (SQLException e) {
            throw new BookstoreQueryDbException("Encountered a problem finding category " + name, e);
        }
        return category;
    }

    @Override
    public List<Book> findBooksByCategoryName(String name) {
        List<Book> books = new ArrayList<>();
        try(Connection connection = JdbcUtils.getConnection();
            PreparedStatement statement = connection.prepareStatement(FIND_BOOKS_BY_CATEGORY_NAME_SQL)){
            statement.setString(1, name);
            try(ResultSet resultSet = statement.executeQuery()) {
                while(resultSet.next()){
                    books.add(readBook(resultSet));
                }
            }
        } catch (SQLException e) {
            throw new BookstoreQueryDbException("Encountered a problem finding books associated with category name: " + name, e);
        }

        return books;
    }

    @Override
    public List<Book> findRandomBooksByCategoryName(String name, int limit) {
        List<Book> books = new ArrayList<>();
        try(Connection connection = JdbcUtils.getConnection();
            PreparedStatement statement = connection.prepareStatement(FIND_RANDOM_BOOKS_BY_CATEGORY_NAME_SQL)){
            statement.setString(1, name);
            statement.setInt(2, limit);
            try(ResultSet resultSet = statement.executeQuery()) {
                while(resultSet.next()){
                    books.add(readBook(resultSet));
                }
            }
        } catch (SQLException e) {
            throw new BookstoreQueryDbException("Encountered a problem finding books associated with category name: " + name, e);
        }

        return books;
    }

    private Category readCategory(ResultSet resultSet) throws SQLException {
        long categoryId = resultSet.getLong("category_id");
        String name = resultSet.getString("name");
        return new Category(categoryId, name);
    }

    private Book readBook(ResultSet resultSet) throws SQLException {
        long bookId = resultSet.getLong("book_id");
        String title = resultSet.getString("title");
        String author = resultSet.getString("author");
        int price = resultSet.getInt("price");
        boolean isPublic = resultSet.getBoolean("is_public");
        long categoryId = resultSet.getLong("category_id");
        String description = resultSet.getString("description");
        boolean isFeatured = resultSet.getBoolean("is_featured");
        int rating = resultSet.getInt("rating");
        return new Book(bookId, title, author, description, price, rating, isPublic, isFeatured, categoryId);
    }

}
