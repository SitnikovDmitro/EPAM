package service;

import db.ProductDAO;
import db.UserDAO;
import entity.Product;
import exceptions.DBException;
import exceptions.EmptyParameterException;
import exceptions.InvalidParameterException;

import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.sql.SQLException;
import java.util.ArrayList;

public class ProductService {
    private final ProductDAO productDAO;

    public ProductService(ProductDAO productDAO) {
        this.productDAO = productDAO;
    }


    /**
     * Finds products in database
     * @param name part of product or products title
     * @param code products code
     * @param products founded products
     */
    public void findProducts(String name, String code, ArrayList<Product> products) throws InvalidParameterException, DBException {
        try {
            products.clear();
            if (code == null || code.isEmpty()) {
                products.addAll(productDAO.findProductsBySubTitle(name==null?"":name));
            } else {
                Product found = productDAO.findProductByCode(Integer.parseInt(code));
                if (found != null && !found.isRemoved()) {
                    products.add(found);
                }
            }
        } catch (SQLException exception) {
            throw new DBException(exception);
        } catch (NumberFormatException exception) {
            throw new InvalidParameterException(exception);
        }
    }

    /**
     * Paginates products
     * @param page current page
     * @param source list of founded products
     * @param dest list of products on current page
     * @param pages list of pages which are available from current one
     */
    public void findProducts(String page, ArrayList<Product> source, ArrayList<Product> dest, ArrayList<Integer> pages) {
        if (source.isEmpty()) return;

        final int amount = 4;
        int pagesCount = source.size()/amount;
        if (source.size()%amount != 0) {
            pagesCount++;
        }
        int p = 1;
        try {
            p = Integer.parseInt(page);
            if (p < 1) {
                p = 1;
            }
            if (p > pagesCount) {
                p = pagesCount;
            }
        } catch (NumberFormatException ignored) {}



        for (int i = Math.max((p-1)*amount, 0); i < p*amount && i < source.size(); i++) {
            dest.add(source.get(i));
        }

        if (p != 1) pages.add(1);
        if (p > 2) pages.add(p-1);
        pages.add(p);
        if (pagesCount > 1 && p < pagesCount-1) pages.add(p+1);
        if (pagesCount > 0 && p != pagesCount) pages.add(pagesCount);
    }

    /**
     * Removes product
     * @param productCode code of product
     */
    public void setProductAsRemovedByCode(String productCode) throws EmptyParameterException, InvalidParameterException, DBException {
        if (productCode == null || productCode.isEmpty()) throw new EmptyParameterException();

        try {
            Product product = productDAO.findProductByCode(Integer.parseInt(productCode));
            if (product == null) throw new DBException("No such product");
            product.setRemoved(true);
            productDAO.updateProduct(product);
        } catch (NumberFormatException exception) {
            throw new InvalidParameterException(exception);
        } catch (SQLException exception) {
            throw new DBException(exception);
        }
    }

    /**
     * Creates a new product
     * @param pathToImages path to folder with product images
     * @param title title of the product
     * @param amount start amount of product (items or grams)
     * @param price price of product (dollars/item or dollars/kilogram)
     * @param countable product countable (in items or in grams)
     * @param image title of the product
     */
    public void createProduct(String pathToImages, String title, String amount, String price, String countable, Part image) throws InvalidParameterException, DBException, EmptyParameterException {
        if (title == null || title.isEmpty() || amount == null || amount.isEmpty() || price == null || price.isEmpty()) throw new EmptyParameterException();

        try {
            boolean productCountable = "enabled".equals(countable);
            int productAmount = productCountable?Integer.parseInt(amount):(int)(Double.parseDouble(amount)*1000.0);
            int productPrice = (int)(Double.parseDouble(price)*100.0);

            Product product = new Product(0, productPrice, productAmount, productCountable, false, title);

            int code = productDAO.addProduct(product);
            File file = new File(pathToImages+"\\"+code+".jpg");
            Files.copy(image.getInputStream(), file.toPath());
        } catch (SQLException | IOException exception) {
            throw new DBException(exception);
        } catch (NumberFormatException exception) {
            throw new InvalidParameterException(exception);
        }
    }

    /**
     * Makes new deliver of product
     * @param productCode code of product
     * @param productAmount amount of product
     */
    public void deliverProduct(String productCode, String productAmount) throws InvalidParameterException, DBException, EmptyParameterException {
        if (productCode == null || productCode.isEmpty() || productAmount == null || productAmount.isEmpty()) throw new EmptyParameterException();

        try {
            int code = Integer.parseInt(productCode);
            Product product = productDAO.findProductByCode(code);
            int amount = product.isCountable()?Integer.parseInt(productAmount):(int)(Double.parseDouble(productAmount)*1000.0);
            product.setTotalAmount(product.getTotalAmount()+amount);
            productDAO.updateProduct(product);
        } catch (NumberFormatException exception) {
            throw new InvalidParameterException(exception);
        } catch (SQLException exception) {
            throw new DBException(exception);
        }
    }

}
