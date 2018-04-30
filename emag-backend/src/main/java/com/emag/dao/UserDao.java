package com.emag.dao;


import com.emag.exception.UserException;
import com.emag.model.User;

import java.sql.SQLException;


public interface UserDao {
    User findUserById(Long id) throws SQLException,UserException;
    User findUserByEmail(String email) throws SQLException,UserException;
    User registerUser(User data) throws SQLException,UserException;
    void checkDoesGivenUserExists(String email, String password) throws SQLException,UserException;
    void checkDoesGivenUserExists(String email) throws SQLException,UserException;
}
