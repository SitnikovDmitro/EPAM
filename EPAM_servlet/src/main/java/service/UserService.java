package service;

import db.ProductDAO;
import db.UserDAO;
import entity.UserX;
import exceptions.DBException;

import java.sql.SQLException;

public class UserService {
    private final UserDAO userDAO;

    public UserService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public UserX getUser(String username, String password) throws DBException {
        try {
            UserX found = userDAO.findUserByUsername(username);
            if (found != null && found.getPassword().equals(password)) {
                return found;
            }
            return null;
        } catch (SQLException exception) {
            throw new DBException(exception);
        }
    }

    public boolean getAccess(String username, String password) throws DBException {
        UserX found = getUser(username, password);
        return found != null && found.getRole() == 2;
    }
}
