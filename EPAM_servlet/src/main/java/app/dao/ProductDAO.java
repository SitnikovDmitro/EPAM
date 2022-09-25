package app.dao;

import app.entity.Product;

import java.sql.*;
import java.util.ArrayList;

public interface ProductDAO {
    Product findProductByCode(int code) throws SQLException;
    int addProduct(Product product) throws SQLException;
    void updateProduct(Product product) throws SQLException;

    /**
     * Finds products whose title contains given substring
     * @param subTitle substring of title
     * @return list of products
     */
    ArrayList<Product> findProductsBySubTitle(String subTitle) throws SQLException;
}
