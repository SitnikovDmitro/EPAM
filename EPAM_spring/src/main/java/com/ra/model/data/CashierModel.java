package com.ra.model.data;

import com.ra.model.entity.Cheque;
import com.ra.model.entity.ChequeLine;
import com.ra.model.entity.Product;
import com.ra.model.enums.Lang;

import java.util.ArrayList;

public class CashierModel {
    private ArrayList<Product> products = new ArrayList<>();
    private ArrayList<Cheque> cheques = new ArrayList<>();
    private ArrayList<ArrayList<ChequeLine>> chequeLines = new ArrayList<>();
    private ArrayList<ChequeLine> activeChequeLines = new ArrayList<>();
    private String name, code, fromPrice, toPrice, sortCriteria;
    private boolean productsChanged = true;
    private boolean chequesChanged = true;
    private Lang lang = Lang.EN;

    public ArrayList<Product> getProducts() {
        return products;
    }

    public void setProducts(ArrayList<Product> products) {
        this.products = products;
    }

    public ArrayList<Cheque> getCheques() {
        return cheques;
    }

    public void setCheques(ArrayList<Cheque> cheques) {
        this.cheques = cheques;
    }

    public ArrayList<ArrayList<ChequeLine>> getChequeLines() {
        return chequeLines;
    }

    public void setChequeLines(ArrayList<ArrayList<ChequeLine>> chequeLines) {
        this.chequeLines = chequeLines;
    }

    public ArrayList<ChequeLine> getActiveChequeLines() {
        return activeChequeLines;
    }

    public void setActiveChequeLines(ArrayList<ChequeLine> activeChequeLines) {
        this.activeChequeLines = activeChequeLines;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getFromPrice() {
        return fromPrice;
    }

    public void setFromPrice(String fromPrice) {
        this.fromPrice = fromPrice;
    }

    public String getToPrice() {
        return toPrice;
    }

    public void setToPrice(String toPrice) {
        this.toPrice = toPrice;
    }

    public String getSortCriteria() {
        return sortCriteria;
    }

    public void setSortCriteria(String sortCriteria) {
        this.sortCriteria = sortCriteria;
    }

    public boolean isProductsChanged() {
        return productsChanged;
    }

    public void setProductsChanged(boolean productsChanged) {
        this.productsChanged = productsChanged;
    }

    public boolean isChequesChanged() {
        return chequesChanged;
    }

    public void setChequesChanged(boolean chequesChanged) {
        this.chequesChanged = chequesChanged;
    }

    public Lang getLang() {
        return lang;
    }

    public void setLang(Lang lang) {
        this.lang = lang;
    }
}
