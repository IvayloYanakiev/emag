package com.emag.service;

import com.emag.dao.LoggedUserDao;
import com.emag.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoggedUserServiceImpl implements LoggedUserService {

    @Autowired
    LoggedUserDao loggedUserDao;

    @Override
    public void updateUserPersonalInfo(User user) {
        loggedUserDao.updateUserPersonalInfo(user);
    }
}
