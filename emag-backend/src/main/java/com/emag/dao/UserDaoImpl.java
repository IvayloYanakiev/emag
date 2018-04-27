package com.emag.dao;

import com.emag.config.Constants;
import com.emag.exceptions.UserException;
import com.emag.model.User;
import org.springframework.beans.factory.annotation.Autowired;
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
    public User findUserById(Long id) throws SQLException, UserException {

        String getUserById = Constants.FIND_USER_BY_ID;

        HashMap<String, Object> userParams = new HashMap<>();
        userParams.put("id", id);

        User userById = jdbcTemplate.query(getUserById, userParams, new ResultSetExtractor<User>() {

            @Override
            public User extractData(ResultSet rs) throws SQLException {
                User userById = new User();
                if (rs.next()) {
                    try {
                        userById.setId(id);
                        userById.setName(rs.getString("name"));
                        userById.setEmail(rs.getString("email"));
                        if(rs.getString("phone")!=null) {
                            userById.setPhone(rs.getString("phone"));
                        }
                        if(rs.getString("gender")!=null) {
                            userById.setGender(rs.getString("gender"));
                        }
                        if(rs.getString("profile_url")!=null) {
                            userById.setPictureUrl(rs.getString("profile_url"));
                        }

                    } catch (UserException e) {
                        System.out.println(e.getMessage());
                    }
                }
                return userById;
            }
        });
        return userById;
    }

    @Override
    public User findUserByEmail(String email) throws SQLException, UserException {
        checkDoesGivenUserExists(email);
        String getUserByEmail = Constants.FIND_USER_BY_EMAIL;

        HashMap<String, Object> userParams = new HashMap<>();
        userParams.put("email", email);

        User user = jdbcTemplate.query(getUserByEmail, userParams, new ResultSetExtractor<User>() {

            @Override
            public User extractData(ResultSet rs) throws SQLException {
                User user = new User();
                if (rs.next()) {
                    try {
                        user.setId(rs.getLong("id"));
                        user.setName(rs.getString("name"));
                        user.setEmail(rs.getString("email"));
                    } catch (UserException e) {
                        System.out.println(e.getMessage());
                    }
                }
                return user;
            }
        });
        return user;
    }

    @Override
    public User registerUser(User user) throws SQLException, UserException {
        String registerUser = Constants.ADD_USER;
        HashMap<String, Object> userParams = new HashMap<>();
        userParams.put("name", user.getName());
        userParams.put("email", user.getEmail());
        userParams.put("password", user.getPassword());
        boolean checker = false;
        try {
            checkDoesGivenUserExists(user.getEmail());
            checker = true;
        } catch (UserException e) {
            jdbcTemplate.update(registerUser, userParams);
        }
        if (checker) throw new UserException(Constants.USER_ALREADY_EXISTS);
        return user;
    }


    public void checkDoesGivenUserExists(String email) throws SQLException, UserException {
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
        if (!checkForUser) throw new UserException(Constants.NO_SUCH_USER);
    }


    public void checkDoesGivenUserExists(String email, String password) throws SQLException, UserException {
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
        if (!checkForUser) throw new UserException(Constants.WRONG_USERNAME_OR_PASSWORD);
    }

}
