package db;

import entity.Cheque;
import entity.ChequeLine;
import entity.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public interface ChequeLineDAO {
    ArrayList<ChequeLine> findChequeLinesByChequeId(int id) throws SQLException;
    void addChequeLine(ChequeLine chequeLine) throws SQLException;
    void deleteChequeLine(int chequeId, int productCode) throws SQLException;
}
