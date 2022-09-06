package com.ra.controller;

import com.ra.model.entity.*;
import com.ra.model.enums.Lang;
import com.ra.model.exceptions.DBException;
import com.ra.model.exceptions.InvalidParameterException;
import com.ra.model.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.ArrayList;


@Controller
@RequestMapping("/cashier")
@SessionAttributes({"products", "cheques", "chequeLines", "activeChequeLines"})
public class CashierController {
    @Autowired
    private ProductService productService;
    @Autowired
    private ChequeService chequeService;
    @Autowired
    private UserService userService;
    @Autowired
    private ChequeLineService chequeLineService;

    private final Logger logger = LoggerFactory.getLogger(CashierController.class);

    @ModelAttribute("products")
    public ArrayList<Product> createProducts() {
        return new ArrayList<>();
    }
    @ModelAttribute("cheques")
    public ArrayList<Product> createCheques() {
        return new ArrayList<>();
    }
    @ModelAttribute("chequeLines")
    public ArrayList<Product> createChequeLines() {
        return new ArrayList<>();
    }
    @ModelAttribute("activeChequeLines")
    public ArrayList<Product> createActiveChequeLines() {
        return new ArrayList<>();
    }


    @GetMapping("/showProducts")
    public String showProducts(@RequestParam(required = false) String page,
                               Model model,
                               HttpSession session) throws InvalidParameterException, DBException {

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

        return "cashier/products";
    }




    @GetMapping("/finishCheque")
    public String finishCheque(@RequestParam(required = false) String name,
                               @RequestParam(required = false) String code,
                               @ModelAttribute("activeChequeLines") ArrayList<ChequeLine> activeChequeLines,
                               HttpSession session) throws InvalidParameterException, DBException {

        chequeService.completeCheque(activeChequeLines);
        session.setAttribute("activeChequeLines", null);
        session.setAttribute("chequeLines", null);
        session.setAttribute("cheques", null);

        return "redirect:/cashier/showCheque";
    }

    @GetMapping("/removeCheque")
    @ResponseBody
    public BooleanResult removeCheque(@RequestParam(required = false) String password,
                                      @RequestParam(required = false) String username,
                                      HttpSession session) throws DBException {

        if (userService.getAccess(username, password)) {
            session.setAttribute("activeChequeLines", null);
            return new BooleanResult(true);
        }
        return new BooleanResult(false);
    }

    @PostMapping("/removeProductFromCheque")
    @ResponseBody
    public BooleanResult removeProductFromCheque(@RequestParam(required = false) String password,
                                                 @RequestParam(required = false) String username,
                                                 @RequestParam(required = false) String productCode,
                                                 @ModelAttribute("activeChequeLines") ArrayList<ChequeLine> activeChequeLines) throws DBException, InvalidParameterException {

        if (userService.getAccess(username, password)) {
            chequeLineService.removeChequeLineFromActiveCheque(activeChequeLines, productCode);
            return new BooleanResult(true);
        }
        return new BooleanResult(false);
    }

    @PostMapping("/createReport")
    @ResponseBody
    public IntResult createReport(@RequestParam(required = false) String password,
                                  @RequestParam(required = false) String username,
                                  @RequestParam(required = false) String reportType) throws DBException, InvalidParameterException {

        if (userService.getAccess(username, password)) {
            int code = -1;//CommonService.getInstance().createReport(reportType, filePath);
            return new IntResult(code);
        }
        return new IntResult(-1);
    }

    @PostMapping("/addProductToCheque")
    public String addProductToCheque(@RequestParam(required = false) String productCode,
                                     @RequestParam(required = false) String amount,
                                     @ModelAttribute("activeChequeLines") ArrayList<ChequeLine> activeChequeLines) throws DBException, InvalidParameterException {

        chequeLineService.addChequeLineToActiveCheque(activeChequeLines, productCode, amount);

        return "redirect:/cashier/showCheque";
    }

    @GetMapping("/showCheque")
    public String showCheque(@ModelAttribute("activeChequeLines") ArrayList<ChequeLine> activeChequeLines,
                             Model model) {

        model.addAttribute("price", chequeLineService.computePrice(activeChequeLines));
        model.addAttribute("chequeLines", activeChequeLines);

        return "cashier/cheque";
    }

    @PostMapping("/setProducts")
    public String setProducts(@RequestParam(required = false) String name,
                              @RequestParam(required = false) String code,
                              @ModelAttribute("products") ArrayList<Product> products,
                              HttpSession session) throws InvalidParameterException, DBException {

        productService.findProducts(name, code, products);

        session.setAttribute("name", name);
        session.setAttribute("code", code);

        return "redirect:/cashier/showProducts";
    }

    @GetMapping("/showCheques")
    public String showCheques(@RequestParam(required = false) String page,
                              Model model,
                              HttpSession session) throws SQLException {

        ArrayList<Cheque> cheques = (ArrayList<Cheque>)session.getAttribute("cheques");
        ArrayList<ArrayList<ChequeLine>> chequeLines = (ArrayList<ArrayList<ChequeLine>>)session.getAttribute("chequeLines");

        if (cheques == null || chequeLines == null) {
            cheques = new ArrayList<>();
            chequeLines = new ArrayList<>();
            chequeService.findCheques(null, null, null, cheques, chequeLines);
            session.setAttribute("cheques", cheques);
            session.setAttribute("chequeLines", chequeLines);
        }

        ArrayList<Cheque> pageCheques = new ArrayList<>();
        ArrayList<ArrayList<ChequeLine>> pageChequeLines = new ArrayList<>();
        ArrayList<Integer> pages = new ArrayList<>();

        chequeService.findCheques(page, cheques, chequeLines, pageCheques, pageChequeLines, pages);

        model.addAttribute("pageCheques", pageCheques);
        model.addAttribute("pageChequeLines", pageChequeLines);
        model.addAttribute("pages", pages);

        return "cashier/cheques";
    }


    @PostMapping("/setCheques")
    public String setCheques(@RequestParam(required = false) String fromPrice,
                             @RequestParam(required = false) String toPrice,
                             @RequestParam(required = false) String sortCriteria,
                             @ModelAttribute("cheques") ArrayList<Cheque> cheques,
                             @ModelAttribute("chequeLines") ArrayList<ArrayList<ChequeLine>> chequeLines,
                             HttpSession session) throws InvalidParameterException, DBException, SQLException {

        chequeService.findCheques(fromPrice, toPrice, sortCriteria, cheques, chequeLines);

        session.setAttribute("fromPrice", fromPrice);
        session.setAttribute("toPrice", toPrice);
        session.setAttribute("sortCriteria", sortCriteria);

        return "redirect:/cashier/showCheques";
    }

    @GetMapping("/changeLanguage")
    public String changeLanguage(@RequestParam(required = false) String lang,
                                 HttpSession session){
        session.setAttribute("lang", "UK".equals(lang) ? Lang.UK : "RU".equals(lang) ? Lang.RU : Lang.EN);

        return "redirect:/cashier/showOptions";
    }

    @GetMapping("/showOptions")
    public String showOptions(){
        return "cashier/options";
    }
}



