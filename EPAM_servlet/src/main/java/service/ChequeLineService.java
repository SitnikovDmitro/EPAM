package service;

import db.ChequeDAO;
import db.ChequeLineDAO;
import db.ProductDAO;
import entity.Cheque;
import entity.ChequeLine;
import entity.Product;
import exceptions.DBException;
import exceptions.InvalidParameterException;

import java.sql.SQLException;
import java.util.ArrayList;

public class ChequeLineService {
    private final ProductDAO productDAO;

    public ChequeLineService(ProductDAO productDAO) {
        this.productDAO = productDAO;
    }

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

    public void removeChequeLineFromActiveCheque(ArrayList<ChequeLine> chequeLines, String productCode) throws InvalidParameterException {
        try {
            int code = Integer.parseInt(productCode);
            chequeLines.removeIf(chequeLine -> chequeLine.getProduct().getCode() == code);
        } catch (NumberFormatException exception) {
            throw new InvalidParameterException(exception);
        }
    }

    public void addChequeLineToActiveCheque(ArrayList<ChequeLine> chequeLines, String productCode, String productAmount) throws InvalidParameterException, DBException {
        try {
            int code = Integer.parseInt(productCode);
            Product product = productDAO.findProductByCode(code);
            if (product == null) throw new DBException();
            int amount = product.isCountable()?Integer.parseInt(productAmount):(int)(Double.parseDouble(productAmount)*1000.0);

            for (ChequeLine chequeLine : chequeLines) {
                if (chequeLine.getProduct().getCode() == code) {
                    chequeLine.setAmount(chequeLine.getAmount() + amount);
                    return;
                }
            }

            chequeLines.add(new ChequeLine(null, product, amount));
        } catch (NumberFormatException exception) {
            throw new InvalidParameterException(exception);
        } catch (SQLException exception) {
            throw new DBException(exception);
        }
    }
}
