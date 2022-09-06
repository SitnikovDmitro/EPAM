package com.ra.model.service;

import com.ra.model.entity.User;
import com.ra.model.exceptions.DBException;
import com.ra.model.repository.ChequeRepository;
import com.ra.model.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

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

    public boolean getAccess(String username, String password) throws DBException {
        User found = getUser(username, password);
        return found != null && found.getRole() == 2;
    }
}
