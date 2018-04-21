package com.emag.dao;

import com.emag.config.Constants;
import com.emag.exceptions.AccountException;
import com.emag.model.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

@Repository
public class AccountDaoImpl implements AccountDao {

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public Account findAccountById(Long id) throws SQLException, AccountException {

        String getUserById = Constants.FIND_USER_BY_ID;

        HashMap<String, Object> userParams = new HashMap<>();
        userParams.put("id", id);

        Account userById = jdbcTemplate.query(getUserById, userParams, new ResultSetExtractor<Account>() {

            @Override
            public Account extractData(ResultSet rs) throws SQLException {
                Account userById = new Account();
                if (rs.next()) {
                    try {
                        userById.setName(rs.getString("name"));
                        userById.setEmail(rs.getString("email"));
                    } catch (AccountException e) {
                        System.out.println(e.getMessage());
                    }
                }
                return userById;
            }
        });
        return userById;
    }

    @Override
    public Account findAccountByEmail(String email) throws SQLException, AccountException {
        checkDoesGivenUserExists(email);
        String getUserByEmail = Constants.FIND_USER_BY_EMAIL;

        HashMap<String, Object> userParams = new HashMap<>();
        userParams.put("email", email);

        Account user = jdbcTemplate.query(getUserByEmail, userParams, new ResultSetExtractor<Account>() {

            @Override
            public Account extractData(ResultSet rs) throws SQLException {
                Account user = new Account();
                if (rs.next()) {
                    user.setId(rs.getLong("id"));
                    try {
                        user.setName(rs.getString("name"));
                        user.setEmail(rs.getString("email"));
                    } catch (AccountException e) {
                        System.out.println(e.getMessage());
                    }
                }
                return user;
            }
        });
        return user;
    }

    @Override
    public Account createAccount(Account account) throws SQLException, AccountException {
        String registerUser = Constants.ADD_USER;
        HashMap<String, Object> userParams = new HashMap<>();
        userParams.put("name", account.getName());
        userParams.put("email", account.getEmail());
        userParams.put("password", account.getPassword());
        boolean checker = false;
        try {
            checkDoesGivenUserExists(account.getEmail());
            checker = true;
        } catch (AccountException e) {
            jdbcTemplate.update(registerUser, userParams);
        }
        if (checker) throw new AccountException(Constants.USER_ALREADY_EXISTS);
        return account;
    }


    public void checkDoesGivenUserExists(String email) throws SQLException, AccountException {
        String checkForUserRequest = Constants.FIND_USER_BY_EMAIL;

        HashMap<String, Object> userParams = new HashMap<>();
        userParams.put("email", email);

        Boolean checkForUser = jdbcTemplate.query(checkForUserRequest, userParams, new ResultSetExtractor<Boolean>() {

            @Override
            public Boolean extractData(ResultSet rs) throws SQLException {
                if (rs.next()) {
                    return true;
                }
                return false;
            }
        });
        if (!checkForUser) throw new AccountException(Constants.NO_SUCH_USER);
    }


    public void checkDoesGivenUserExists(String email, String password) throws SQLException, AccountException {
        String checkForUserRequest = Constants.SELECT_USER_BY_EMAIL_AND_PASS;

        HashMap<String, Object> userParams = new HashMap<>();
        userParams.put("email", email);
        userParams.put("password", password);

        Boolean checkForUser = jdbcTemplate.query(checkForUserRequest, userParams, new ResultSetExtractor<Boolean>() {

            @Override
            public Boolean extractData(ResultSet rs) throws SQLException {
                if (rs.next()) {
                    return true;
                }
                return false;
            }
        });
        if (!checkForUser) throw new AccountException(Constants.WRONG_USERNAME_OR_PASSWORD);
    }
}
