package app.dao;

import app.entity.ChequeLine;

import java.sql.SQLException;
import java.util.ArrayList;

public interface ChequeLineDAO {
    ArrayList<ChequeLine> findChequeLinesByChequeId(int id) throws SQLException;
    void addChequeLine(ChequeLine chequeLine) throws SQLException;
    void deleteChequeLine(int chequeId, int productCode) throws SQLException;
}
