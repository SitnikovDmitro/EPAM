package com.ra.model.entity;

import javax.persistence.*;

@Entity
@Table(name = "chequeLines")
public class ChequeLine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private int id;

    @ManyToOne
    @JoinColumn(name = "chequeId", nullable = false, referencedColumnName = "id")
    private Cheque cheque;

    @ManyToOne
    @JoinColumn(name = "productCode", nullable = false, referencedColumnName = "code")
    private Product product;

    @Column(name = "amount", nullable = false)
    private int amount;

    public ChequeLine() {}

    public ChequeLine(Cheque cheque, Product product, int amount) {
        this.cheque = cheque;
        this.product = product;
        this.amount = amount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Cheque getCheque() {
        return cheque;
    }

    public void setCheque(Cheque cheque) {
        this.cheque = cheque;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

}
