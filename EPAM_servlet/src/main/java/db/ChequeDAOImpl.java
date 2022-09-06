package db;

import entity.Cheque;

import java.sql.*;
import java.util.ArrayList;

public class ChequeDAOImpl implements ChequeDAO{
    @Override
    public Cheque findChequeById(int id) throws SQLException {
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


    @Override
    public ArrayList<Cheque> findChequesSortedByPrice(int fromPrice, int toPrice) throws SQLException {
        try(Connection con = ConnectionPool.getInstance().getConnection();
            PreparedStatement stmt = con.prepareStatement("SELECT * FROM cheques WHERE price >= ? AND price <= ? ORDER BY price;")) {
            stmt.setInt(1, fromPrice);
            stmt.setInt(2, toPrice);
            ResultSet set = stmt.executeQuery();
            ArrayList<Cheque> result = new ArrayList<>();

            while (set.next()) {
                result.add(new Cheque(
                        set.getInt("id"),
                        set.getInt("price"),
                        set.getInt("state"),
                        set.getDate("creationTime")));
            }

            set.close();
            return result;
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw ex;
        }
    }

    @Override
    public ArrayList<Cheque> findChequesSortedByCreationTime(int fromPrice, int toPrice) throws SQLException {
        try(Connection con = ConnectionPool.getInstance().getConnection();
            PreparedStatement stmt = con.prepareStatement("SELECT * FROM cheques WHERE price >= ? AND price <= ? ORDER BY creationTime;")) {
            stmt.setInt(1, fromPrice);
            stmt.setInt(2, toPrice);
            ResultSet set = stmt.executeQuery();
            ArrayList<Cheque> result = new ArrayList<>();

            while (set.next()) {
                result.add(new Cheque(
                        set.getInt("id"),
                        set.getInt("price"),
                        set.getInt("state"),
                        set.getDate("creationTime")));
            }

            set.close();
            return result;
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw ex;
        }
    }


    @Override
    public int addCheque(Cheque cheque) throws SQLException {
        try(Connection con = ConnectionPool.getInstance().getConnection();
            PreparedStatement stmt = con.prepareStatement("INSERT INTO cheques (id, price, state, creationTime) VALUES (?, ?, ?, ?);", Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, cheque.getId());
            stmt.setInt(2, cheque.getPrice());
            stmt.setInt(3, cheque.getState());
            stmt.setDate(4, cheque.getCreationTime());
            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            rs.next();
            return rs.getInt(1);
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw ex;
        }
    }

    @Override
    public void updateCheque(Cheque cheque) throws SQLException {
        try(Connection con = ConnectionPool.getInstance().getConnection();
            PreparedStatement stmt = con.prepareStatement("UPDATE cheques SET id = ?, price = ?, state = ?, creationTime = ? WHERE id = ?;")) {
            stmt.setInt(1, cheque.getId());
            stmt.setInt(2, cheque.getPrice());
            stmt.setInt(3, cheque.getState());
            stmt.setDate(4, cheque.getCreationTime());
            stmt.setInt(5, cheque.getId());

            stmt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw ex;
        }
    }
}
