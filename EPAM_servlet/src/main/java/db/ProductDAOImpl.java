package db;

import entity.Product;

import java.sql.*;
import java.util.ArrayList;

public class ProductDAOImpl implements ProductDAO {
    @Override
    public Product findProductByCode(int code) throws SQLException {
        try(Connection con = ConnectionPool.getInstance().getConnection();
            PreparedStatement stmt = con.prepareStatement("SELECT * FROM products WHERE code = ?;")) {
            stmt.setInt(1, code);
            ResultSet set = stmt.executeQuery();

            if (set.next()) {
                Product master = new Product(
                        set.getInt("code"),
                        set.getInt("price"),
                        set.getInt("totalAmount"),
                        set.getBoolean("countable"),
                        set.getBoolean("removed"),
                        set.getString("title"));
                set.close();
                return master;
            } else {
                set.close();
                return null;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw ex;
        }
    }

    @Override
    public ArrayList<Product> findProductsBySubTitle(String subTitle) throws SQLException {
        String query = "SELECT * FROM products WHERE title LIKE ? AND removed = false;";

        try(Connection con = ConnectionPool.getInstance().getConnection();
            PreparedStatement stmt = con.prepareStatement(query)) {
            stmt.setString(1, "%"+subTitle+"%");
            ResultSet set = stmt.executeQuery();
            ArrayList<Product> result = new ArrayList<>();

            while (set.next()) {
                result.add(new Product(
                        set.getInt("code"),
                        set.getInt("price"),
                        set.getInt("totalAmount"),
                        set.getBoolean("countable"),
                        set.getBoolean("removed"),
                        set.getString("title")));
            }

            set.close();
            return result;
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw ex;
        }
    }

    @Override
    public int addProduct(Product product) throws SQLException {
        try(Connection con = ConnectionPool.getInstance().getConnection();
            PreparedStatement stmt = con.prepareStatement("INSERT INTO products (code, price, totalAmount, countable, removed, title) VALUES (?, ?, ?, ?, ?, ?);", Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, product.getCode());
            stmt.setInt(2, product.getPrice());
            stmt.setInt(3, product.getTotalAmount());
            stmt.setBoolean(4, product.isCountable());
            stmt.setBoolean(5, product.isRemoved());
            stmt.setString(6, product.getTitle());
            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            rs.next();
            return rs.getInt(1);
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw ex;
        }
    }

    @Override
    public void updateProduct(Product product) throws SQLException {
        try(Connection con = ConnectionPool.getInstance().getConnection();
            PreparedStatement stmt = con.prepareStatement("UPDATE products SET code = ?, price = ?, totalAmount = ?, countable = ?, removed = ?, title = ? WHERE code = ?;")) {
            stmt.setInt(1, product.getCode());
            stmt.setInt(2, product.getPrice());
            stmt.setInt(3, product.getTotalAmount());
            stmt.setBoolean(4, product.isCountable());
            stmt.setBoolean(5, product.isRemoved());
            stmt.setString(6, product.getTitle());
            stmt.setInt(7, product.getCode());

            stmt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw ex;
        }
    }
}
