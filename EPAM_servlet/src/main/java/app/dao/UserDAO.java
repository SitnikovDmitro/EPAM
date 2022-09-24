package app.dao;

import app.entity.UserX;

import java.sql.SQLException;

public interface UserDAO {
    UserX findUserByUsername(String username) throws SQLException;
}
