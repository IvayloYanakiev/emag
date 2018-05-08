package com.emag.service;

import com.emag.dao.UserDao;
import com.emag.exception.UserException;
import com.emag.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserDao userDao;

    @Override
    public User findUserById(Long id) throws UserException {
        return userDao.findUserById(id);
    }

    @Override
    public User registerUser(User data) throws UserException {
        return userDao.registerUser(data);
    }

    @Override
    public User findUserByEmail(String email) throws UserException {
        return userDao.findUserByEmail(email);
    }

    @Override
    public void checkDoesGivenUserExists(String email, String password) throws UserException {
        userDao.checkDoesGivenUserExists(email, password);
    }

    @Override
    public void checkDoesGivenUserExists(String email) throws  UserException {
        userDao.checkDoesGivenUserExists(email);
    }

}
