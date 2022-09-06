package db;

import entity.Cheque;
import entity.ChequeLine;
import entity.Product;
import entity.UserX;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.*;
import java.util.ArrayList;

public class DAOTest {
    private UserDAO userDAO;
    private ProductDAO productDAO;
    private ChequeDAO chequeDAO;
    private ChequeLineDAO chequeLineDAO;

    public DAOTest() {
        userDAO = new UserDAOImpl();
        productDAO = new ProductDAOImpl();
        chequeDAO = new ChequeDAOImpl();
        chequeLineDAO = new ChequeLineDAOImpl(new ChequeDAOImpl());
    }

    @BeforeEach
    public void initialize() throws SQLException {
        fill();
    }

    private void fill() throws SQLException {
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

    @Test
    public void findUserByIdTest() throws SQLException {
        UserX user1 = userDAO.findUserByUsername("Cas");
        UserX user2 = userDAO.findUserByUsername("SeC");
        UserX user3 = userDAO.findUserByUsername("Mer");


        Assertions.assertEquals(user1.getRole(), 1);
        Assertions.assertEquals(user2.getRole(), 2);
        Assertions.assertEquals(user3.getRole(), 3);
    }

    @Test
    public void findProductByCodeTest() throws SQLException {
        Product product1 = productDAO.findProductByCode(1);
        Product product2 = productDAO.findProductByCode(3);
        Product product3 = productDAO.findProductByCode(5);
        Product product4 = productDAO.findProductByCode(7);

        Assertions.assertEquals(product1.getTitle(), "Milk");
        Assertions.assertEquals(product2.getTitle(), "Butter");
        Assertions.assertEquals(product3.getTitle(), "Pork");
        Assertions.assertEquals(product4.getTitle(), "Potato");
    }

    @Test
    public void findProductBySubtitleTest() throws SQLException {
        Assertions.assertEquals(productDAO.findProductsBySubTitle("m").size(), 2);
        Assertions.assertEquals(productDAO.findProductsBySubTitle("on").size(), 2);
        Assertions.assertEquals(productDAO.findProductsBySubTitle("s").size(), 5);
    }

    @Test
    public void addProductTest() throws SQLException {
        productDAO.addProduct(new Product(14, 100, 100, true, false,"Rise"));
        productDAO.addProduct(new Product(15, 200, 100, true, false,"Honey"));
        Product product1 = productDAO.findProductByCode(14);
        Product product2 = productDAO.findProductByCode(15);

        Assertions.assertEquals(product1.getTitle(), "Rise");
        Assertions.assertEquals(product2.getTitle(), "Honey");
    }

    @Test
    public void updateProductTest() throws SQLException {
        Product product = productDAO.findProductByCode(8);
        product.setTitle("Tea");
        productDAO.updateProduct(product);
        Product product1 = productDAO.findProductByCode(8);
        Assertions.assertEquals(product1.getTitle(), "Tea");
        product1.setTitle("Coffee");
        productDAO.updateProduct(product1);
        Product product2 = productDAO.findProductByCode(8);
        Assertions.assertEquals(product2.getTitle(), "Coffee");
    }

    @Test
    public void findProductsBySubTitleTestAnotherCase() throws SQLException {
        productDAO.addProduct(new Product(0, 200, 100, true, false, "Abcd"));
        productDAO.addProduct(new Product(0, 200, 100, true, false, "Mabc"));
        productDAO.addProduct(new Product(0, 200, 100, true, false, "Mama"));
        productDAO.addProduct(new Product(0, 200, 100, true, false, "Abcabc"));
        productDAO.addProduct(new Product(0, 200, 100, true, false, "Dab"));

        ArrayList<Product> list1 = productDAO.findProductsBySubTitle("abc");
        ArrayList<Product> list2 = productDAO.findProductsBySubTitle("ma");

        Assertions.assertTrue(list1.stream().allMatch(product -> product.getTitle().toLowerCase().contains("abc")));
        Assertions.assertTrue(list2.stream().allMatch(product -> product.getTitle().toLowerCase().contains("ma")));
        Assertions.assertEquals(list1.size(), 3);
        Assertions.assertEquals(list2.size(), 2);
    }

    @Test
    public void findChequeLinesByChequeIdTest() throws SQLException {
        ArrayList<ChequeLine> list = chequeLineDAO.findChequeLinesByChequeId(3);

        Assertions.assertEquals(list.size(), 3);
        Assertions.assertEquals(list.get(1).getCheque().getId(), 3);

        Assertions.assertTrue(list.stream().allMatch(chequeLine -> chequeLine.getProduct().getCode() == 7 || chequeLine.getProduct().getCode() == 8 || chequeLine.getProduct().getCode() == 9));

        ArrayList<ChequeLine> list1 = chequeLineDAO.findChequeLinesByChequeId(9);
        Assertions.assertEquals(list1.size(), 5);
    }

    @Test
    public void addChequeLineTest() throws SQLException {
        Product product = productDAO.findProductByCode(13);
        Cheque cheque = chequeDAO.findChequeById(12);
        chequeLineDAO.addChequeLine(new ChequeLine(cheque, product, 3));

        ArrayList<ChequeLine> list = chequeLineDAO.findChequeLinesByChequeId(12);
        Assertions.assertTrue(list.stream().anyMatch(chequeLine -> chequeLine.getProduct().getCode() == 13 && chequeLine.getCheque().getId() == 12 && chequeLine.getAmount() == 3));


    }


    @Test
    public void findChequeByIdTest() throws SQLException {
        Cheque cheque1 = chequeDAO.findChequeById(3);
        Cheque cheque2 = chequeDAO.findChequeById(4);
        Cheque cheque3 = chequeDAO.findChequeById(5);
        Cheque cheque4 = chequeDAO.findChequeById(7);

        Assertions.assertEquals(cheque1.getId(), 3);
        Assertions.assertEquals(cheque2.getId(), 4);
        Assertions.assertEquals(cheque3.getId(), 5);
        Assertions.assertEquals(cheque4.getId(), 7);
    }

    @Test
    public void addChequeAndUpdateChequeTest() throws SQLException {
        Cheque cheque = new Cheque(100, 223344, 0, Date.valueOf("2019-11-22"));

        chequeDAO.addCheque(cheque);
        Assertions.assertEquals(chequeDAO.findChequeById(100).getPrice(), cheque.getPrice());

        cheque.setPrice(113355);

        chequeDAO.updateCheque(cheque);
        Assertions.assertEquals(chequeDAO.findChequeById(100).getPrice(), cheque.getPrice());
    }



    @Test
    public void findChequesSortedByPrice() throws SQLException {
        ArrayList<Cheque> cheques = chequeDAO.findChequesSortedByPrice(0, Integer.MAX_VALUE);

        for (int i = 0; i < cheques.size()-1; i++) {
            int prev = cheques.get(i).getPrice(), next = cheques.get(i+1).getPrice();
            Assertions.assertTrue(prev >= next);
        }
    }


}