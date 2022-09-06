package com.ra.model.entity;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "cheques")
public class Cheque {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private Integer id;

    @Column(name = "price", nullable = false)
    private int price;

    @Column(name = "state", nullable = false)
    private int state;

    @Column(name = "creationTime", nullable = false)
    private Date creationTime;

    public Cheque() {}

    public Cheque(Integer id, int price, int state, Date creationTime) {
        this.id = id;
        this.price = price;
        this.state = state;
        this.creationTime = creationTime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public Date getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }
}
