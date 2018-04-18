package com.emag.dao;

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

        StringBuilder getUserById = new StringBuilder();
        getUserById.append("select * from users");
        getUserById.append(" where id=:id");

        HashMap<String, Object> userParams = new HashMap<>();
        userParams.put("id", id);

        Account userById = jdbcTemplate.query(getUserById.toString(), userParams, new ResultSetExtractor<Account>() {

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
        StringBuilder getUserByEmail = new StringBuilder();
        getUserByEmail.append("select * from users");
        getUserByEmail.append(" where email=:email");

        HashMap<String, Object> userParams = new HashMap<>();
        userParams.put("email", email);

        Account user = jdbcTemplate.query(getUserByEmail.toString(), userParams, new ResultSetExtractor<Account>() {

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
        StringBuilder registerUser = new StringBuilder();
        registerUser.append("insert into users(name,email,password)");
        registerUser.append(" values(:name,:email,sha1(:password))");
        HashMap<String, Object> userParams = new HashMap<>();
        userParams.put("name", account.getName());
        userParams.put("email", account.getEmail());
        userParams.put("password", account.getPassword());
        boolean checker = false;
        try {
            checkDoesGivenUserExists(account.getEmail());
            checker = true;
        } catch (AccountException e) {
            jdbcTemplate.update(registerUser.toString(), userParams);
        }
        if (checker) throw new AccountException("User already exists");
        return account;
    }


    public void checkDoesGivenUserExists(String email) throws SQLException, AccountException {
        StringBuilder checkForUserRequest = new StringBuilder();
        checkForUserRequest.append("select * from users");
        checkForUserRequest.append(" where email=:email");

        HashMap<String, Object> userParams = new HashMap<>();
        userParams.put("email", email);

        Boolean checkForUser = jdbcTemplate.query(checkForUserRequest.toString(), userParams, new ResultSetExtractor<Boolean>() {

            @Override
            public Boolean extractData(ResultSet rs) throws SQLException {
                if (rs.next()) {
                    return true;
                }
                return false;
            }
        });
        if (!checkForUser) throw new AccountException("No such user");
    }


    public void checkDoesGivenUserExists(String email, String password) throws SQLException, AccountException {
        StringBuilder checkForUserRequest = new StringBuilder();
        checkForUserRequest.append("select * from users");
        checkForUserRequest.append(" where email=:email and password = sha1(:password);");

        HashMap<String, Object> userParams = new HashMap<>();
        userParams.put("email", email);
        userParams.put("password", password);

        Boolean checkForUser = jdbcTemplate.query(checkForUserRequest.toString(), userParams, new ResultSetExtractor<Boolean>() {

            @Override
            public Boolean extractData(ResultSet rs) throws SQLException {
                if (rs.next()) {
                    return true;
                }
                return false;
            }
        });
        if (!checkForUser) throw new AccountException("Wrong username or password");
    }
}
