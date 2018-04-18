package com.emag.dao;


import com.emag.exceptions.AccountException;
import com.emag.model.Account;

import java.sql.SQLException;


public interface AccountDao {
    Account findAccountById(Long id) throws SQLException,AccountException;

    Account findAccountByEmail(String email) throws SQLException,AccountException;

    Account createAccount(Account data) throws SQLException,AccountException;
    void checkDoesGivenUserExists(String email, String password) throws SQLException,AccountException;
    void checkDoesGivenUserExists(String email) throws SQLException,AccountException;
}
