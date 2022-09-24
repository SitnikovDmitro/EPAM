package com.ra.controller;

import com.ra.model.data.MerchandiserModel;
import com.ra.model.entity.Product;
import com.ra.enums.Lang;
import com.ra.exceptions.DBException;
import com.ra.exceptions.InvalidParameterException;
import com.ra.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;


@Controller
@RequestMapping("/merchandiser")
@SessionAttributes("data")
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

    @ModelAttribute("data")
    public MerchandiserModel init() {
        return new MerchandiserModel();
    }

    @GetMapping("/showProducts")
    public String showProducts(@RequestParam(required = false) String page,
                               @ModelAttribute("data") MerchandiserModel data,
                               Model model) throws InvalidParameterException, DBException {

        if (data.isProductsChanged()) {
            productService.findProducts(data.getName(), data.getCode(), data.getProducts());
            data.setProductsChanged(false);
        }

        ArrayList<Product> pageProducts = new ArrayList<>();
        ArrayList<Integer> pages = new ArrayList<>();

        productService.findProducts(page, data.getProducts(), pageProducts, pages);

        model.addAttribute("pageProducts", pageProducts);
        model.addAttribute("pages", pages);
        model.addAttribute("name", data.getName());
        model.addAttribute("code", data.getCode());
        model.addAttribute("lang", data.getLang());
        model.addAttribute("text", TextService.getInstance());

        return "merchandiser/products";
    }

    @PostMapping("/setProducts")
    public String setProducts(@RequestParam(required = false) String name,
                              @RequestParam(required = false) String code,
                              @ModelAttribute("data") MerchandiserModel data) throws InvalidParameterException, DBException {

        productService.findProducts(name, code, data.getProducts());
        data.setCode(code);
        data.setName(name);

        return "redirect:/merchandiser/showProducts";
    }

    @GetMapping("/showProduct")
    public String showProduct(@ModelAttribute("data") MerchandiserModel data,
                              Model model) {

        model.addAttribute("lang", data.getLang());
        model.addAttribute("text", TextService.getInstance());

        return "merchandiser/product";
    }

    @PostMapping("/createProduct")
    public String createProduct(@RequestParam(required = false) String title,
                                @RequestParam(required = false) String countable,
                                @RequestParam(required = false) String amount,
                                @RequestParam(required = false) String price,
                                @RequestParam("image") MultipartFile file,
                                @ModelAttribute("data") MerchandiserModel data) throws InvalidParameterException, DBException {

        productService.createProduct(title, amount, price, countable, file);
        data.setProductsChanged(true);

        return "redirect:/merchandiser/showProducts";
    }

    @PostMapping("/deleteProduct")
    public String deleteProduct(@RequestParam(required = false) String productCode,
                                @ModelAttribute("data") MerchandiserModel data) throws InvalidParameterException, DBException, IOException {

        productService.setProductAsRemovedByCode(productCode);
        data.setProductsChanged(true);

        return "redirect:/merchandiser/showProducts";
    }

    @PostMapping("/deliverProduct")
    public String deliverProduct(@RequestParam(required = false) String productCode,
                                 @RequestParam(required = false) String amount,
                                 @ModelAttribute("data") MerchandiserModel data) throws InvalidParameterException, DBException {

        productService.deliverProduct(productCode, amount);
        data.setProductsChanged(true);

        return "redirect:/merchandiser/showProducts";
    }

    @GetMapping("/changeLanguage")
    public String changeLanguage(@RequestParam(required = false) String lang,
                                 @ModelAttribute("data") MerchandiserModel data) {

        data.setLang("UK".equals(lang) ? Lang.UK : "RU".equals(lang) ? Lang.RU : Lang.EN);

        return "redirect:/merchandiser/showOptions";
    }

    @GetMapping("/showOptions")
    public String showOptions(@ModelAttribute("data") MerchandiserModel data,
                              Model model) {

        model.addAttribute("lang", data.getLang());
        model.addAttribute("text", TextService.getInstance());

        return "merchandiser/options";
    }
}
