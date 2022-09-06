package db;

import entity.Cheque;
import entity.ChequeLine;
import entity.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ChequeLineDAOImpl implements ChequeLineDAO {
    private final ChequeDAO chequeDAO;

    public ChequeLineDAOImpl(ChequeDAO chequeDAO) {
        this.chequeDAO = chequeDAO;
    }

    @Override
    public ArrayList<ChequeLine> findChequeLinesByChequeId(int id) throws SQLException {
        Cheque cheque = chequeDAO.findChequeById(id);
        String query = "SELECT * FROM chequeLines INNER JOIN products ON chequeLines.productCode = products.code WHERE chequeLines.chequeId = ?;";

        try(Connection con = ConnectionPool.getInstance().getConnection();
            PreparedStatement stmt = con.prepareStatement(query)) {
            stmt.setInt(1, id);
            ResultSet set = stmt.executeQuery();
            ArrayList<ChequeLine> result = new ArrayList<>();

            while (set.next()) {
                Product product = new Product(
                        set.getInt("code"),
                        set.getInt("price"),
                        set.getInt("totalAmount"),
                        set.getBoolean("countable"),
                        set.getBoolean("removed"),
                        set.getString("title"));

                result.add(new ChequeLine(cheque, product, set.getInt("amount")));
            }

            set.close();
            return result;
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw ex;
        }
    }

    @Override
    public void addChequeLine(ChequeLine chequeLine) throws SQLException {
        try(Connection con = ConnectionPool.getInstance().getConnection();
            PreparedStatement stmt = con.prepareStatement("INSERT INTO chequeLines (chequeId, productCode, amount) VALUES (?, ?, ?);")) {
            stmt.setInt(1, chequeLine.getCheque().getId());
            stmt.setInt(2, chequeLine.getProduct().getCode());
            stmt.setInt(3, chequeLine.getAmount());
            stmt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw ex;
        }
    }

    @Override
    public void deleteChequeLine(int chequeId, int productCode) throws SQLException {
        try(Connection con = ConnectionPool.getInstance().getConnection();
            PreparedStatement stmt = con.prepareStatement("DELETE FROM chequeLines WHERE chequeId = ? AND productCode = ?;")) {
            stmt.setInt(1, chequeId);
            stmt.setInt(2, productCode);
            stmt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw ex;
        }
    }
}
