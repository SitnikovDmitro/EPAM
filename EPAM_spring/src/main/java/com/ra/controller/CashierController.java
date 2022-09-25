package com.ra.controller;

import com.ra.model.data.CashierModel;
import com.ra.model.entity.*;
import com.ra.enums.Lang;
import com.ra.exceptions.DBException;
import com.ra.exceptions.InvalidParameterException;
import com.ra.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;


@Controller
@RequestMapping("/cashier")
@SessionAttributes("data")
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

    @ModelAttribute("data")
    public CashierModel init() {
        return new CashierModel();
    }


    @GetMapping("/showProducts")
    public String showProducts(@RequestParam(required = false) String page,
                               @ModelAttribute("data") CashierModel data,
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

        return "cashier/products";
    }




    @GetMapping("/finishCheque")
    public String finishCheque(@ModelAttribute("data") CashierModel data) throws DBException {

        chequeService.completeCheque(data.getActiveChequeLines());
        data.setChequesChanged(true);
        data.setProductsChanged(true);

        return "redirect:/cashier/showCheque";
    }

    @GetMapping("/removeCheque")
    @ResponseBody
    public BooleanResult removeCheque(@RequestParam(required = false) String password,
                                      @RequestParam(required = false) String username,
                                      @ModelAttribute("data") CashierModel data) throws DBException {

        if (userService.getAccess(username, password)) {
            data.getActiveChequeLines().clear();
            return new BooleanResult(true);
        }
        return new BooleanResult(false);
    }

    @PostMapping("/removeProductFromCheque")
    @ResponseBody
    public BooleanResult removeProductFromCheque(@RequestParam(required = false) String password,
                                                 @RequestParam(required = false) String username,
                                                 @RequestParam(required = false) String productCode,
                                                 @ModelAttribute("data") CashierModel data) throws DBException, InvalidParameterException {

        if (userService.getAccess(username, password)) {
            chequeLineService.removeChequeLineFromActiveCheque(data.getActiveChequeLines(), productCode);
            return new BooleanResult(true);
        }
        return new BooleanResult(false);
    }

    @PostMapping("/createReport")
    @ResponseBody
    public IntResult createReport(@RequestParam(required = false) String password,
                                  @RequestParam(required = false) String username,
                                  @RequestParam(required = false) String reportType,
                                  @ModelAttribute("data") CashierModel data) throws DBException, InvalidParameterException {

        if (userService.getAccess(username, password)) {
            int code = chequeService.createReport(reportType);
            data.setChequesChanged(true);
            return new IntResult(code);
        }
        return new IntResult(-1);
    }


    @GetMapping(path = "/getReport", produces = "application/vnd.openxmlformats-officedocument.wordprocessingml.document")
    public ResponseEntity<Resource> getReport(@RequestParam String number) throws IOException {
        File file = new File("src/main/webapp/files/reports/"+number+".docx");

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=\"Report.docx\"");

        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(file.length())
                .body(new InputStreamResource(new FileInputStream(file)));
    }

    @PostMapping("/addProductToCheque")
    public String addProductToCheque(@RequestParam(required = false) String productCode,
                                     @RequestParam(required = false) String amount,
                                     @ModelAttribute("data") CashierModel data) throws DBException, InvalidParameterException {

        chequeLineService.addChequeLineToActiveCheque(data.getActiveChequeLines(), productCode, amount);

        return "redirect:/cashier/showCheque";
    }

    @GetMapping("/showCheque")
    public String showCheque(@ModelAttribute("data") CashierModel data,
                             Model model) {

        model.addAttribute("price", chequeLineService.computePrice(data.getActiveChequeLines()));
        model.addAttribute("chequeLines", data.getActiveChequeLines());
        model.addAttribute("lang", data.getLang());
        model.addAttribute("text", TextService.getInstance());

        return "cashier/cheque";
    }

    @PostMapping("/setProducts")
    public String setProducts(@RequestParam(required = false) String name,
                              @RequestParam(required = false) String code,
                              @ModelAttribute("data") CashierModel data) throws InvalidParameterException, DBException {

        productService.findProducts(name, code, data.getProducts());
        data.setName(name);
        data.setCode(code);

        return "redirect:/cashier/showProducts";
    }

    @GetMapping("/showCheques")
    public String showCheques(@RequestParam(required = false) String page,
                              @ModelAttribute("data") CashierModel data,
                              Model model) throws SQLException {

        if (data.isChequesChanged()) {
            chequeService.findCheques(data.getFromPrice(), data.getToPrice(), data.getSortCriteria(), data.getCheques(), data.getChequeLines());
            data.setChequesChanged(false);
        }

        ArrayList<Cheque> pageCheques = new ArrayList<>();
        ArrayList<ArrayList<ChequeLine>> pageChequeLines = new ArrayList<>();
        ArrayList<Integer> pages = new ArrayList<>();

        chequeService.findCheques(page, data.getCheques(), data.getChequeLines(), pageCheques, pageChequeLines, pages);

        model.addAttribute("pageCheques", pageCheques);
        model.addAttribute("pageChequeLines", pageChequeLines);
        model.addAttribute("pages", pages);
        model.addAttribute("fromPrice", data.getFromPrice());
        model.addAttribute("toPrice", data.getToPrice());
        model.addAttribute("sortCriteria", data.getSortCriteria());
        model.addAttribute("lang", data.getLang());
        model.addAttribute("text", TextService.getInstance());

        return "cashier/cheques";
    }


    @PostMapping("/setCheques")
    public String setCheques(@RequestParam(required = false) String fromPrice,
                             @RequestParam(required = false) String toPrice,
                             @RequestParam(required = false) String sortCriteria,
                             @ModelAttribute("data") CashierModel data) throws InvalidParameterException, DBException, SQLException {

        chequeService.findCheques(fromPrice, toPrice, sortCriteria, data.getCheques(), data.getChequeLines());
        data.setFromPrice(fromPrice);
        data.setToPrice(toPrice);
        data.setSortCriteria(sortCriteria);

        return "redirect:/cashier/showCheques";
    }

    @GetMapping("/changeLanguage")
    public String changeLanguage(@RequestParam(required = false) String lang,
                                 @ModelAttribute("data") CashierModel data) {

        data.setLang("UK".equals(lang) ? Lang.UK : "RU".equals(lang) ? Lang.RU : Lang.EN);

        return "redirect:/cashier/showOptions";
    }

    @GetMapping("/showOptions")
    public String showOptions(@ModelAttribute("data") CashierModel data,
                              Model model) {

        model.addAttribute("lang", data.getLang());
        model.addAttribute("text", TextService.getInstance());

        return "cashier/options";
    }
}



