package com.ra.service;

import com.ra.model.entity.User;
import com.ra.exceptions.DBException;
import com.ra.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    /**
     * Returns user with giver username and password if exists or null otherwise
     * @param username username of user
     * @param password password of user
     * @return user base.entity
     */
    public User getUser(String username, String password) throws DBException {
        if (username == null || username.isEmpty() || password == null || password.isEmpty()) return null;
        try {
            Optional<User> userOptional = userRepository.findById(username);
            if (userOptional.isPresent() && userOptional.get().getPassword().equals(password)) {
                return userOptional.get();
            }
            return null;
        } catch (Exception exception) {
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
        User found = getUser(username, password);
        return found != null && found.getRole() == 2;
    }
}
