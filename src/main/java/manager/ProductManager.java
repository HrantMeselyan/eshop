package manager;

import db.DBConnectionProvider;
import model.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductManager {
    private static Connection connection = DBConnectionProvider.getInstance().getConnection();

    private static CategoryManager categoryManager = new CategoryManager();

    public void save(Product product) {
        String sql = "INSERT INTO product (name,description,price,quantity,category_id) Values(?,?,?,?,?)";
        try (PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, product.getName());
            ps.setString(2, product.getDescription());
            ps.setString(3, String.valueOf(product.getPrice()));
            ps.setString(4, String.valueOf(product.getQuantity()));
            ps.setString(5, String.valueOf(product.getCategory().getId()));
            ps.executeUpdate();
            ResultSet generatedKeys = ps.getGeneratedKeys();
            if (generatedKeys.next()) {
                product.setId(generatedKeys.getInt(1));
            }
            System.out.println("Product was inserted!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Product> getAll() {
        List<Product> productList = new ArrayList<>();
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM product");
            while (resultSet.next()) {
                productList.add(getProductFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return productList;
    }

    public void update(Product product) {
        String sql = "UPDATE product SET name = '%s', description = '%s', price = %d, quantity = %d WHERE id = %d";
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(String.format(sql, product.getName(), product.getDescription(), product.getPrice(), product.getQuantity(), product.getId()));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeById(int id) {
        String sql = "DELETE FROM product where id = " + id;
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Product getProductByCategory(int id) {
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT * from product where category_id = " + id);
            if (resultSet.next()) {
                return getProductFromResultSet(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public int getSum() {
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT COUNT(*) FROM product");
            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public int getMaxProductPrice() {
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT MAX(price) FROM product");
            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public int getMinProductPrice() {
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT MIN(price) FROM product");
            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public int getPriceAvg() {
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT AVG(price) FROM product");
            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    private Product getProductFromResultSet(ResultSet resultSet) throws SQLException {
        Product product = new Product();
        product.setId(resultSet.getInt("id"));
        product.setName(resultSet.getString("name"));
        product.setDescription(resultSet.getString("description"));
        product.setPrice(resultSet.getInt("price"));
        product.setQuantity(resultSet.getInt("quantity"));
        int categoryId = resultSet.getInt("category_id");
        product.setCategory(categoryManager.getById(categoryId));
        return product;
    }
}
