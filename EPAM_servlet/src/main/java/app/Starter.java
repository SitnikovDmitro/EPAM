package app;


import app.dao.ConnectionPool;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class Starter {
    public static void main(String[] args) throws SQLException {
        fill();
    }

    private static void fill() throws SQLException {
        try(Connection con = ConnectionPool.getInstance().getConnection();
            Statement stmt = con.createStatement()) {

            stmt.addBatch("DELETE FROM chequeLines;");
            stmt.addBatch("DELETE FROM users;");
            stmt.addBatch("DELETE FROM products;");
            stmt.addBatch("DELETE FROM cheques;");

            stmt.addBatch("INSERT INTO users (role, password, username) VALUES (1, 'abcd', 'Cas'), (2, '1234', 'SeC'), (3, '0000', 'Mer');");
            stmt.addBatch("INSERT INTO products (code, price, totalAmount, countable, removed, title) VALUES (1, 300, 235, true, false, 'Milk'), (2, 450, 10000, false, false, 'Cheese'), (3, 600, 400, true, false, 'Butter'), (4, 250, 290, true, false, 'Egg'), (5, 400, 70000, false, false, 'Pork'), (6, 1050, 2000, false, false, 'Salmon'), (7, 400, 255000, false, false, 'Potato'), (8, 100, 85000, false, false, 'Carrot'), (9, 120, 65000, false, false, 'Onion'), (10, 200, 125, true, false, 'Soap'), (11, 340, 55000, false, false, 'Sugar'), (12, 120, 10000, false, false, 'Salt'), (13, 200, 200, true, false, 'Foil');");
            stmt.addBatch("INSERT INTO cheques (id, price, state, creationTime) VALUES (1, 0, 1, DATE '2021-12-12'), (2, 0, 1, DATE '2019-09-14'), (3, 0, 1, DATE '2020-03-09'), (4, 0, 1, DATE '2021-07-22'), (5, 0, 1, DATE '2018-09-21'), (6, 0, 1, DATE '2029-01-16'), (7, 0, 1, DATE '2021-03-18'), (8, 0, 1, DATE '2020-10-08'), (9, 0, 1, DATE '2021-11-11'), (10, 0, 1, DATE '2017-08-12'), (11, 0, 1, DATE '2019-09-27'), (12, 0, 1, DATE '2017-11-22');");
            stmt.addBatch("INSERT INTO chequeLines (chequeId, productCode, amount) VALUES (1, 1, 2),  (1, 2, 200), (1, 3, 1), (1, 4, 1), (2, 5, 2000), (2, 6, 500), (3, 7, 1000), (3, 8, 1000), (3, 9, 2000), (4, 10, 2), (4, 11, 200), (4, 12, 1000), (4, 13, 1), (5, 1, 2), (5, 2, 200), (5, 3, 3), (5, 4, 2), (5, 5, 1000), (6, 6, 500), (6, 7, 200), (6, 8, 1500), (7, 9, 2500), (7, 10, 2), (7, 11, 1000), (7, 12, 4000), (7, 13, 3), (7, 1, 1), (7, 2, 200), (8, 3, 2), (8, 4, 1), (8, 5, 400), (8, 6, 1500), (9, 7, 2500), (9, 8, 1400), (9, 9, 1000), (9, 10, 1), (9, 11, 1200), (10, 12, 2900), (10, 13, 5), (11, 1, 2), (11, 2, 1200), (11, 3, 1), (11, 4, 1), (12, 5, 6000);");

            stmt.executeBatch();
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw ex;
        }
    }
}
