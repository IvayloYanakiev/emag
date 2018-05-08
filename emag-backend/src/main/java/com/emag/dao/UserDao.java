package com.emag.dao;


import com.emag.exception.UserException;
import com.emag.model.User;

import java.sql.SQLException;
import java.util.HashSet;


public interface UserDao {
    User findUserById(Long id) throws UserException;
    User findUserByEmail(String email) throws UserException;
    User registerUser(User data,String token) throws UserException;
    void checkDoesGivenUserExists(String email, String password) throws UserException;
    void checkDoesGivenUserExists(String email) throws UserException;

    void activateAccount(String token) throws UserException;

    HashSet<User> checkForUnactivatedAccounts() throws UserException;
}
