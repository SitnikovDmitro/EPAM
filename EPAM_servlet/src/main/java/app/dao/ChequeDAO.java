package app.dao;

import app.entity.Cheque;

import java.sql.*;
import java.util.ArrayList;

public interface ChequeDAO {
    Cheque findChequeById(int id) throws SQLException;
    int addCheque(Cheque cheque) throws SQLException;
    void updateCheque(Cheque cheque) throws SQLException;
    void removeAll() throws SQLException;

    /**
     * Finds cheques whose price is between fromPrice and toPrice and sorts them by price
     * @param fromPrice start value of price
     * @param toPrice end value of price
     * @return list of cheques
     */
    ArrayList<Cheque> findChequesSortedByPrice(int fromPrice, int toPrice) throws SQLException;

    /**
     * Finds cheques whose price is between fromPrice and toPrice and sorts them by creation time
     * @param fromPrice start value of price
     * @param toPrice end value of price
     * @return list of cheques
     */
    ArrayList<Cheque> findChequesSortedByCreationTime(int fromPrice, int toPrice) throws SQLException;
}
