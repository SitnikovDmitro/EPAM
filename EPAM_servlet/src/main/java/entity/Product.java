package entity;

public class Product {
    private int code;
    private int price;
    private int totalAmount;
    private boolean countable;
    private boolean removed;
    private String title;

    public Product() {}

    public Product(int code, int price, int totalAmount, boolean countable, boolean removed, String title) {
        this.code = code;
        this.price = price;
        this.totalAmount = totalAmount;
        this.countable = countable;
        this.removed = removed;
        this.title = title;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
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
