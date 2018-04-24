package com.emag.service;

import com.emag.exceptions.UserException;
import com.emag.model.User;

import java.sql.SQLException;

public interface UserService {
     User findUserById(Long id) throws UserException, SQLException;
     User registerUser(User data) throws UserException, SQLException;
     User findUserByEmail(String email) throws UserException, SQLException ;
     void checkDoesGivenUserExists(String email,String password) throws SQLException, UserException;
     void checkDoesGivenUserExists(String email) throws SQLException, UserException;
}
