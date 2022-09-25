package com.ra.service;

import com.ra.model.entity.Product;
import com.ra.exceptions.DBException;
import com.ra.exceptions.InvalidParameterException;
import com.ra.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

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
                if (name == null) name = "";
                products.addAll(productRepository.findProductsByPattern("%"+name+"%"));
            } else {
                Optional<Product> productOptional = productRepository.findById(Integer.parseInt(code));
                if (productOptional.isPresent() && !productOptional.get().isRemoved()) {
                    products.add(productOptional.get());
                }
            }
            products.removeIf(Product::isRemoved);
        } catch (NumberFormatException exception) {
            throw new InvalidParameterException(exception);
        } catch (Exception exception) {
            throw new DBException(exception);
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
    public void setProductAsRemovedByCode(String productCode) throws InvalidParameterException, DBException {
        try {
            Product product = productRepository.findById(Integer.parseInt(productCode)).orElseThrow(() -> new DBException("No such product"));
            product.setRemoved(true);
            productRepository.save(product);
        } catch (NumberFormatException exception) {
            throw new InvalidParameterException(exception);
        } catch (Exception exception) {
            throw new DBException(exception);
        }
    }

    /**
     * Creates a new product
     * @param title title of the product
     * @param amount start amount of product (items or grams)
     * @param price price of product (dollars/item or dollars/kilogram)
     * @param countable product countable (in items or in grams)
     * @param image title of the product
     */
    public void createProduct(String title, String amount, String price, String countable, MultipartFile image) throws InvalidParameterException, DBException {
        try {
            boolean productCountable = "enabled".equals(countable);
            int productAmount = productCountable?Integer.parseInt(amount):(int)(Double.parseDouble(amount)*1000.0);
            int productPrice = (int)(Double.parseDouble(price)*100.0);

            Product product = new Product(null, productPrice, productAmount, productCountable, false, title);
            productRepository.save(product);

            File file = new File("src/main/webapp/files/images/"+product.getCode()+".jpg");
            Files.copy(image.getInputStream(), file.toPath());
        } catch (IOException exception) {
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
    public void deliverProduct(String productCode, String productAmount) throws InvalidParameterException, DBException {
        try {
            Product product = productRepository.findById(Integer.parseInt(productCode)).orElseThrow(() -> new DBException("No such product"));
            int amount = product.isCountable() ? Integer.parseInt(productAmount) : (int)(Double.parseDouble(productAmount)*1000.0);
            product.setTotalAmount(product.getTotalAmount()+amount);
            productRepository.save(product);
        } catch (NumberFormatException exception) {
            throw new InvalidParameterException(exception);
        }
    }

}
