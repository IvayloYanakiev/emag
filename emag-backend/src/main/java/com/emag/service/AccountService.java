package com.emag.service;

import com.emag.exceptions.AccountException;
import com.emag.model.Account;

import java.sql.SQLException;

public interface AccountService {
     Account findAccountById(Long id) throws AccountException, SQLException;
     Account createAccount(Account data) throws AccountException, SQLException;
     Account findByAccountEmail(String email) throws AccountException, SQLException ;
     void checkDoesGivenUserExists(String email,String password) throws SQLException, AccountException;
     void checkDoesGivenUserExists(String email) throws SQLException, AccountException;
}
