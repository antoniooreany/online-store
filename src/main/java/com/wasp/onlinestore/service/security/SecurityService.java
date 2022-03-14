package com.wasp.onlinestore.service.security;

import com.wasp.onlinestore.dao.UserDao;
import com.wasp.onlinestore.exception.UserAlreadyExistsException;
import com.wasp.onlinestore.exception.UserNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class SecurityService {
    private UserDao userDao;
    private List<String> tokens;
    private PasswordEncoder passwordEncoder;

    public SecurityService(UserDao userDao) {
        this.userDao = userDao;
        this.tokens = new ArrayList<>();
        this.passwordEncoder = new PasswordEncoder();
    }

    //check salted password in base
    public boolean userExists(String name, String password) {
        Optional<String> optionalSalt = userDao.getSalt(name);
        if (optionalSalt.isEmpty()) {
            return false;
        }
        String saltedHashedPassword = passwordEncoder.hashPassword(password + optionalSalt.get());
        return userDao.userExists(name, saltedHashedPassword);
    }

    public String generateToken() {
        String token = String.valueOf(UUID.randomUUID());
        tokens.add(token);
        return token;
    }

    public boolean isTokenValid(String value) {
        return tokens.contains(value);
    }

    public String login(String login, String password) {
        if (!userExists(login, password)) {
            throw new UserNotFoundException("Could not find user by name: " + login);
        }
        return generateToken();
    }

    public String register(String login, String password) {
        if (!userExists(login, password)) {
            saveUser(login, password, passwordEncoder.getSalt());
        } else {
            throw new UserAlreadyExistsException("User already exists by name: " + login);
        }
        return generateToken();
    }

    public void saveUser(String login, String password, String salt) {
        String saltedHashedPassword = passwordEncoder.hashPassword(password + salt);
        userDao.saveUser(login, saltedHashedPassword, salt);
    }
}
