package db;

import entity.UserX;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public interface UserDAO {
    UserX findUserByUsername(String username) throws SQLException;
}
