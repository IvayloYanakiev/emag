package com.emag.dao;


import com.emag.exception.UserException;
import com.emag.model.User;

public interface UserDao {
    User findUserById(Long id) throws UserException;
    User findUserByEmail(String email) throws UserException;
    User registerUser(User data) throws UserException;
    void checkDoesGivenUserExists(String email, String password) throws UserException;
    void checkDoesGivenUserExists(String email) throws UserException;
}
