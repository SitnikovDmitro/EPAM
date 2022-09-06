package entity;

import java.sql.Date;

public class Cheque {
    private int id;
    private int price;
    private int state;
    private Date creationTime;

    public Cheque() {}

    public Cheque(int id, int price, int state, Date creationTime) {
        this.id = id;
        this.price = price;
        this.state = state;
        this.creationTime = creationTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    @Override
    public String toString() {
        return "Cheque{" +
                "id=" + id +
                ", price=" + price +
                ", state=" + state +
                ", creationTime=" + creationTime +
                '}';
    }
}
