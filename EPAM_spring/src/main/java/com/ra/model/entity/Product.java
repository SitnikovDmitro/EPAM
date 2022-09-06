package com.ra.model.entity;

import javax.persistence.*;

@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "code", nullable = false, unique = true)
    private Integer code;

    @Column(name = "price", nullable = false)
    private int price;

    @Column(name = "totalAmount", nullable = false)
    private int totalAmount;

    @Column(name = "countable", nullable = false)
    private boolean countable;

    @Column(name = "removed", nullable = false)
    private boolean removed;

    @Column(name = "title", nullable = false, length = 100)
    private String title;

    public Product() {}

    public Product(Integer code, int price, int totalAmount, boolean countable, boolean removed, String title) {
        this.code = code;
        this.price = price;
        this.totalAmount = totalAmount;
        this.countable = countable;
        this.removed = removed;
        this.title = title;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(int totalAmount) {
        this.totalAmount = totalAmount;
    }

    public boolean isCountable() {
        return countable;
    }

    public void setCountable(boolean countable) {
        this.countable = countable;
    }

    public boolean isRemoved() {
        return removed;
    }

    public void setRemoved(boolean removed) {
        this.removed = removed;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
