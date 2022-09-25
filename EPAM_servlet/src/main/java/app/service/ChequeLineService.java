package app.service;

import app.dao.ProductDAO;
import app.entity.ChequeLine;
import app.entity.Product;
import app.exceptions.DBException;
import app.exceptions.InvalidParameterException;

import java.sql.SQLException;
import java.util.ArrayList;

public class ChequeLineService {
    private final ProductDAO productDAO;

    public ChequeLineService(ProductDAO productDAO) {
        this.productDAO = productDAO;
    }

    /**
     * Computes total price of the cheque
     * @param chequeLines cheque lines of cheque
     * @return total price in cents
     */
    public int computePrice(ArrayList<ChequeLine> chequeLines) {
        int total = 0;
        for (ChequeLine chequeLine : chequeLines) {
            if (chequeLine.getProduct().isCountable()) {
                total += chequeLine.getAmount()*chequeLine.getProduct().getPrice();
            } else {
                total += chequeLine.getAmount()*chequeLine.getProduct().getPrice()/1000;
            }
        }
        return total;
    }

    /**
     * Removes product from cheque
     * @param chequeLines lines of cheque
     * @param productCode code of removed product
     */
    public void removeChequeLineFromActiveCheque(ArrayList<ChequeLine> chequeLines, String productCode) throws InvalidParameterException {
        try {
            int code = Integer.parseInt(productCode);
            chequeLines.removeIf(chequeLine -> chequeLine.getProduct().getCode() == code);
        } catch (NumberFormatException exception) {
            throw new InvalidParameterException(exception);
        }
    }

    /**
     * Add product to cheque
     * @param chequeLines lines of cheque
     * @param productCode code of product
     * @param productAmount amount of product
     */
    public void addChequeLineToActiveCheque(ArrayList<ChequeLine> chequeLines, String productCode, String productAmount) throws InvalidParameterException, DBException {
        try {
            int code = Integer.parseInt(productCode);
            Product product = productDAO.findProductByCode(code);
            if (product == null) throw new DBException();
            int amount = product.isCountable()?Integer.parseInt(productAmount):(int)(Double.parseDouble(productAmount)*1000.0);
            if (amount < 0) throw new InvalidParameterException("Negative amount");

            for (ChequeLine chequeLine : chequeLines) {
                if (chequeLine.getProduct().getCode() == code) {
                    chequeLine.setAmount(chequeLine.getAmount() + amount);
                    if (chequeLine.getAmount() > product.getTotalAmount()) chequeLine.setAmount(product.getTotalAmount());
                    return;
                }
            }

            ChequeLine chequeLine = new ChequeLine(null, product, amount);
            if (chequeLine.getAmount() > product.getTotalAmount()) chequeLine.setAmount(product.getTotalAmount());
            if (chequeLine.getAmount() > 0) chequeLines.add(chequeLine);
        } catch (NumberFormatException exception) {
            throw new InvalidParameterException(exception);
        } catch (SQLException exception) {
            throw new DBException(exception);
        }
    }
}
