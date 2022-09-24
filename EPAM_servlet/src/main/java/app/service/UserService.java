package app.service;

import app.dao.UserDAO;
import app.entity.UserX;
import app.exceptions.DBException;

import java.sql.SQLException;

public class UserService {
    private final UserDAO userDAO;

    public UserService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    /**
     * Returns user with giver username and password if exists or null otherwise
     * @param username username of user
     * @param password password of user
     * @return user base.entity
     */
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

    /**
     * Checks if user with given username and password exists in the database
     * @param username username of user
     * @param password password of user
     * @return result
     */
    public boolean getAccess(String username, String password) throws DBException {
        UserX found = getUser(username, password);
        return found != null && found.getRole() == 2;
    }
}
