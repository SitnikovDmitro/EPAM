package com.ra.model.data;

import com.ra.model.entity.Product;
import com.ra.model.enums.Lang;
import org.springframework.lang.NonNull;

import java.util.ArrayList;

public class MerchandiserModel {
    private ArrayList<Product> products = new ArrayList<>();
    private String name, code;
    private boolean productsChanged = true;
    private Lang lang = Lang.EN;

    public ArrayList<Product> getProducts() {
        return products;
    }

    public void setProducts(ArrayList<Product> products) {
        this.products = products;
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

    public boolean isProductsChanged() {
        return productsChanged;
    }

    public void setProductsChanged(boolean productsChanged) {
        this.productsChanged = productsChanged;
    }

    public Lang getLang() {
        return lang;
    }

    public void setLang(Lang lang) {
        this.lang = lang;
    }
}
