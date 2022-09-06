package db;

import entity.Cheque;
import entity.ChequeLine;
import entity.Product;

import java.sql.*;
import java.util.ArrayList;

public interface ChequeDAO {
    Cheque findChequeById(int id) throws SQLException;
    ArrayList<Cheque> findChequesSortedByPrice(int fromPrice, int toPrice) throws SQLException;
    ArrayList<Cheque> findChequesSortedByCreationTime(int fromPrice, int toPrice) throws SQLException;
    int addCheque(Cheque cheque) throws SQLException;
    void updateCheque(Cheque cheque) throws SQLException;
}
