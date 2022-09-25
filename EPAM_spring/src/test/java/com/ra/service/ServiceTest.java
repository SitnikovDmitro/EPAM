package com.ra.service;

import com.ra.exceptions.DBException;
import com.ra.exceptions.InvalidParameterException;
import com.ra.model.entity.Cheque;
import com.ra.model.entity.ChequeLine;
import com.ra.model.entity.Product;
import com.ra.repository.ChequeLineRepository;
import com.ra.repository.ChequeRepository;
import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class ServiceTest {
    @Autowired
    private ChequeLineService chequeLineService;
    @Autowired
    private ChequeService chequeService;
    @Autowired
    private UserService userService;
    @Autowired
    private ProductService productService;

    @Autowired
    private ChequeRepository chequeRepository;
    @Autowired
    private ChequeLineRepository chequeLineRepository;

    @BeforeEach
    public void init() {
        List<Cheque> cheques = chequeRepository.findChequesSortedByPrice(0, Integer.MAX_VALUE);
        for (Cheque cheque : cheques) {
            cheque.setPrice(chequeLineService.computePrice(new ArrayList<>(chequeLineRepository.findChequeLinesByChequeId(cheque.getId()))));
            chequeRepository.save(cheque);
        }
    }



    @Test
    @Sql("/init.sql")
    public void addChequeLineToActiveChequeTest() throws InvalidParameterException, DBException {
        ArrayList<ChequeLine> chequeLines = new ArrayList<>();
        chequeLineService.addChequeLineToActiveCheque(chequeLines, "1", "2");

        Assertions.assertEquals(chequeLines.size(), 1);

        ChequeLine chequeLine = chequeLines.get(0);

        Assertions.assertEquals(chequeLine.getAmount(), 2);
        Assertions.assertEquals(chequeLine.getProduct().getCode(), 1);
        Assertions.assertEquals(chequeLine.getProduct().getTitle(), "Milk");
    }

    @Test
    @Sql("/init.sql")
    public void removeChequeLineFromActiveCheque() throws InvalidParameterException, DBException {
        ArrayList<ChequeLine> chequeLines = new ArrayList<>();
        chequeLineService.addChequeLineToActiveCheque(chequeLines, "1", "2");
        chequeLineService.removeChequeLineFromActiveCheque(chequeLines, "1");
        Assertions.assertEquals(chequeLines.size(), 0);
    }


    @Test
    @Sql("/init.sql")
    public void findProductsTest() throws InvalidParameterException, DBException {
        ArrayList<Product> products = new ArrayList<>();
        productService.findProducts("", "", products);

        Assertions.assertEquals(products.size(), 13);

        ArrayList<Product> pageProducts = new ArrayList<>();
        productService.findProducts("2", products, pageProducts, new ArrayList<>());

        Assertions.assertEquals(pageProducts.size(), 4);

        Assertions.assertEquals(products.get(4).getTitle(), pageProducts.get(0).getTitle());
        Assertions.assertEquals(products.get(5).getTitle(), pageProducts.get(1).getTitle());
        Assertions.assertEquals(products.get(6).getTitle(), pageProducts.get(2).getTitle());
        Assertions.assertEquals(products.get(7).getTitle(), pageProducts.get(3).getTitle());
    }

    @Test
    @Sql("/init.sql")
    public void getUserTest() throws DBException {
        Assertions.assertEquals(userService.getUser("SeC", "1234").getRole(), 2);
        Assertions.assertNull(userService.getUser("SeC", "12345"));
    }
}
