package app.dao;

import app.entity.ChequeLine;

import java.sql.SQLException;
import java.util.ArrayList;

public interface ChequeLineDAO {
    void addChequeLine(ChequeLine chequeLine) throws SQLException;

    /**
     * Finds all cheque lines by their cheque id
     * @param id id of cheque
     * @return list of cheque lines
     */
    ArrayList<ChequeLine> findChequeLinesByChequeId(int id) throws SQLException;
}
