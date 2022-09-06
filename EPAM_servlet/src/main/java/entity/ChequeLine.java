package entity;

public class ChequeLine {
    private Cheque cheque;
    private Product product;
    private int amount;

    public ChequeLine() {}

    public ChequeLine(Cheque cheque, Product product, int amount) {
        this.cheque = cheque;
        this.product = product;
        this.amount = amount;
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

    @Override
    public String toString() {
        return "ChequeLine{" +
                "cheque=" + cheque +
                ", product=" + product +
                ", amount=" + amount +
                '}';
    }
}
