package app.dao;

import app.entity.Cheque;
import app.entity.ChequeLine;
import app.entity.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ChequeLineDAOImpl implements ChequeLineDAO {
    @Override
    public ArrayList<ChequeLine> findChequeLinesByChequeId(int id) throws SQLException {
        Cheque cheque = findChequeById(id);
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



    private Cheque findChequeById(int id) throws SQLException {
        try(Connection con = ConnectionPool.getInstance().getConnection();
            PreparedStatement stmt = con.prepareStatement("SELECT * FROM cheques WHERE id = ?;")) {
            stmt.setInt(1, id);
            ResultSet set = stmt.executeQuery();

            if (set.next()) {
                Cheque cheque = new Cheque(
                        set.getInt("id"),
                        set.getInt("price"),
                        set.getInt("state"),
                        set.getDate("creationTime"));
                set.close();
                return cheque;
            } else {
                set.close();
                return null;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw ex;
        }
    }
}
