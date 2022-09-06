package com.ra.model.service;

import com.ra.model.entity.ChequeLine;
import com.ra.model.entity.Product;
import com.ra.model.exceptions.DBException;
import com.ra.model.exceptions.InvalidParameterException;
import com.ra.model.repository.ChequeLineRepository;
import com.ra.model.repository.ChequeRepository;
import com.ra.model.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.function.Supplier;

@Service
public class ChequeLineService {
    @Autowired
    private ProductRepository productRepository;

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
            Product product = productRepository.findById(code).orElseThrow(DBException::new);
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
        } catch (Exception exception) {
            throw new DBException(exception);
        }
    }
}
