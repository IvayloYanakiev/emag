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

    public static final String FIND_USER_BY_ID = "select * from users where id=:id";
    public static final String FIND_USER_BY_EMAIL = "select * from users where email=:email";
    public static final String ADD_USER = "insert into users(name,email,password) values(:name,:email,sha1(:password))";

    public static final String SELECT_USER_BY_EMAIL_AND_PASS = "select * from users where email=:email and password = sha1(:password);";

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public Account findAccountById(Long id) throws SQLException, AccountException {

        String getUserById = FIND_USER_BY_ID;

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
        String getUserByEmail = FIND_USER_BY_EMAIL;

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
        String registerUser = ADD_USER;
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
        String checkForUserRequest = FIND_USER_BY_EMAIL;

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
        String checkForUserRequest = SELECT_USER_BY_EMAIL_AND_PASS;

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
