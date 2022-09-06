package db;

import entity.UserX;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAOImpl implements UserDAO {
    @Override
    public UserX findUserByUsername(String username) throws SQLException {
        try(Connection con = ConnectionPool.getInstance().getConnection();
            PreparedStatement stmt = con.prepareStatement("SELECT * FROM users WHERE username = ?;")) {
            stmt.setString(1, username);
            ResultSet set = stmt.executeQuery();

            if (set.next()) {
                UserX user = new UserX(
                        set.getInt("role"),
                        set.getString("password"),
                        set.getString("username"));
                set.close();
                return user;
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
