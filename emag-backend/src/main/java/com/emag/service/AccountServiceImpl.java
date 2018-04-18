package com.emag.service;

import com.emag.dao.AccountDao;
import com.emag.exceptions.AccountException;
import com.emag.model.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    AccountDao accountDao;

    @Override
    public Account findAccountById(Long id) throws AccountException, SQLException {
        return accountDao.findAccountById(id);
    }

    @Override
    public Account createAccount(Account data) throws AccountException, SQLException {
        return accountDao.createAccount(data);
    }

    @Override
    public Account findByAccountEmail(String email) throws AccountException, SQLException {
        return accountDao.findAccountByEmail(email);
    }

    @Override
    public void checkDoesGivenUserExists(String email, String password) throws SQLException, AccountException {
        accountDao.checkDoesGivenUserExists(email, password);
    }

    @Override
    public void checkDoesGivenUserExists(String email) throws SQLException, AccountException {
        accountDao.checkDoesGivenUserExists(email);
    }


}
