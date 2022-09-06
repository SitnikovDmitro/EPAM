package com.ra.controller;

import com.ra.model.entity.Product;
import com.ra.model.enums.Lang;
import com.ra.model.exceptions.DBException;
import com.ra.model.exceptions.InvalidParameterException;
import com.ra.model.service.ChequeLineService;
import com.ra.model.service.ChequeService;
import com.ra.model.service.ProductService;
import com.ra.model.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/merchandiser")
@SessionAttributes({"products"})
public class MerchandiserController {
    @Autowired
    private ProductService productService;
    @Autowired
    private ChequeService chequeService;
    @Autowired
    private UserService userService;
    @Autowired
    private ChequeLineService chequeLineService;

    private final Logger logger = LoggerFactory.getLogger(MerchandiserController.class);


    //@ModelAttribute("products")
    //public ArrayList<Product> createProducts() {
    //    return new ArrayList<>();
    //}

    @GetMapping("/showProducts")
    public String showProducts(@RequestParam(required = false) String page,
                               Model model, HttpSession session) throws InvalidParameterException, DBException {

        ArrayList<Product> products = (ArrayList<Product>)session.getAttribute("products");
        if (products == null) {
            products = new ArrayList<>();
            productService.findProducts(null, null, products);
            session.setAttribute("products", products);
        }

        ArrayList<Product> pageProducts = new ArrayList<>();
        ArrayList<Integer> pages = new ArrayList<>();

        productService.findProducts(page, products, pageProducts, pages);

        model.addAttribute("pageProducts", pageProducts);
        model.addAttribute("pages", pages);

        return "merchandiser/products";
    }

    @PostMapping("/setProducts")
    public String setProducts(@RequestParam(required = false) String name,
                              @RequestParam(required = false) String code,
                              @ModelAttribute("products") ArrayList<Product> products,
                              HttpSession session) throws InvalidParameterException, DBException {

        productService.findProducts(name, code, products);

        session.setAttribute("name", name);
        session.setAttribute("code", code);

        return "redirect:/merchandiser/showProducts";
    }

    @GetMapping("/showProduct")
    public String showProduct() {
        return "merchandiser/product";
    }

    @PostMapping("/createProduct")
    public String createProduct(@RequestParam(required = false) String title,
                                @RequestParam(required = false) String countable,
                                @RequestParam(required = false) String amount,
                                @RequestParam(required = false) String price,
                                @RequestParam("image") MultipartFile file,
                                HttpSession session) throws InvalidParameterException, DBException, IOException {

        productService.createProduct(title, amount, price, countable, file);
        session.setAttribute("products", null);

        return "redirect:/merchandiser/showProducts";
    }

    @PostMapping("/deliverProduct")
    public String deliverProduct(@RequestParam(required = false) String productCode,
                                 @RequestParam(required = false) String amount,
                                 HttpSession session) throws InvalidParameterException, DBException {

        productService.deliverProduct(productCode, amount);
        session.setAttribute("products", null);

        return "redirect:/merchandiser/showProducts";
    }

    @GetMapping("/changeLanguage")
    public String changeLanguage(@RequestParam(required = false) String lang,
                                 HttpSession session) {

        session.setAttribute("lang", "UK".equals(lang) ? Lang.UK : "RU".equals(lang) ? Lang.RU : Lang.EN);

        return "redirect:/merchandiser/showOptions";
    }

    @GetMapping("/showOptions")
    public String showOptions(){
        return "merchandiser/options";
    }
}
