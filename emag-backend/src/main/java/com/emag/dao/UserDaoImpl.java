package com.emag.dao;

import com.emag.config.ConstantsErrorMessages;
import com.emag.config.ConstantsSQL;
import com.emag.exception.UserException;
import com.emag.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

@Repository
public class UserDaoImpl implements UserDao {

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public User findUserById(Long id) throws UserException {

        String getUserById = ConstantsSQL.FIND_USER_BY_ID;

        HashMap<String, Object> userParams = new HashMap<>();
        userParams.put("id", id);
        User userById = null;
        try {
            userById = jdbcTemplate.query(getUserById, userParams, new ResultSetExtractor<User>() {

                @Override
                public User extractData(ResultSet rs) throws SQLException {
                    User userById = new User();
                    if (rs.next()) {
                        try {
                            userById.setId(id);
                            userById.setName(rs.getString("name"));
                            userById.setEmail(rs.getString("email"));
                            if (rs.getString("phone") != null) {
                                userById.setPhone(rs.getString("phone"));
                            }
                            if (rs.getString("gender") != null) {
                                userById.setGender(rs.getString("gender"));
                            }
                            if (rs.getString("profile_url") != null) {
                                userById.setPictureUrl(rs.getString("profile_url"));
                            }
                            userById.setType(rs.getInt("isAdmin"));
                        } catch (UserException e) {
                            throw new SQLException(e.getMessage());
                        }
                    }
                    return userById;
                }
            });
        } catch (Exception e) {
            throw new UserException(e.getMessage(), e);
        }
        return userById;
    }

    @Override
    public User findUserByEmail(String email) throws UserException {
        checkDoesGivenUserExists(email);
        String getUserByEmail = ConstantsSQL.FIND_USER_BY_EMAIL;

        HashMap<String, Object> userParams = new HashMap<>();
        userParams.put("email", email);
        User user = null;
        try {
            user = jdbcTemplate.query(getUserByEmail, userParams, new ResultSetExtractor<User>() {

                @Override
                public User extractData(ResultSet rs) throws SQLException {
                    User user = new User();
                    if (rs.next()) {
                        try {
                            user.setId(rs.getLong("id"));
                            user.setName(rs.getString("name"));
                            user.setEmail(rs.getString("email"));
                            user.setType(rs.getInt("isAdmin"));
                        } catch (UserException e) {
                            throw new SQLException(e.getMessage());
                        }
                    }
                    return user;
                }
            });
        } catch (Exception e) {
            throw new UserException(e.getMessage(), e);
        }
        return user;
    }

    @Override
    public User registerUser(User user) throws UserException {
        String registerUser = ConstantsSQL.ADD_USER;
        HashMap<String, Object> userParams = new HashMap<>();
        userParams.put("name", user.getName());
        userParams.put("email", user.getEmail());
        userParams.put("password", user.getPassword());
        boolean checker = false;
        try {
            checkDoesGivenUserExists(user.getEmail());
            checker = true;
        } catch (UserException e) {
            try {
                jdbcTemplate.update(registerUser, userParams);
            } catch (DataAccessException ex) {
                throw new UserException(ex.getMessage(), e);
            }
        }
        if (checker) throw new UserException(ConstantsErrorMessages.USER_ALREADY_EXISTS);
        return user;
    }
    

    public void checkDoesGivenUserExists(String email) throws UserException {
        String checkForUserRequest = ConstantsSQL.FIND_USER_BY_EMAIL;

        HashMap<String, Object> userParams = new HashMap<>();
        userParams.put("email", email);
        Boolean checkForUser = checkForUser(checkForUserRequest, userParams);
        if (!checkForUser) throw new UserException(ConstantsErrorMessages.NO_SUCH_USER);
    }


    public void checkDoesGivenUserExists(String email, String password) throws UserException {
        String checkForUserRequest = ConstantsSQL.SELECT_USER_BY_EMAIL_AND_PASS;

        HashMap<String, Object> userParams = new HashMap<>();
        userParams.put("email", email);
        userParams.put("password", password);
        Boolean checkForUser = checkForUser(checkForUserRequest, userParams);
        if (!checkForUser) throw new UserException(ConstantsErrorMessages.WRONG_USERNAME_OR_PASSWORD);
    }

    private Boolean checkForUser(String checkForUserRequest, HashMap<String, Object> userParams) throws UserException {
        Boolean checkForUser;
        try{
            checkForUser = jdbcTemplate.query(checkForUserRequest, userParams, new ResultSetExtractor<Boolean>() {

            @Override
            public Boolean extractData(ResultSet rs) throws SQLException {
                if (rs.next()) {
                    return true;
                }
                return false;
            }
        });}
        catch(Exception e){
            throw new UserException(e.getMessage(),e);
        }
        return checkForUser;
    }

}
