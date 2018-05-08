package com.emag.service;

import com.emag.exception.UserException;
import com.emag.model.User;

import java.sql.SQLException;

public interface UserService {
     User findUserById(Long id) throws UserException;
     User registerUser(User data) throws UserException;
     User findUserByEmail(String email) throws UserException ;
     void checkDoesGivenUserExists(String email,String password) throws  UserException;
     void checkDoesGivenUserExists(String email) throws  UserException;

     void activateAccount(String token) throws UserException;
}
