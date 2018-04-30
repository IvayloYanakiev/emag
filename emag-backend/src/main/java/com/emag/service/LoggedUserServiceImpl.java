package com.emag.service;

import com.emag.dao.LoggedUserDao;
import com.emag.exception.UserException;
import com.emag.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoggedUserServiceImpl implements LoggedUserService {

    @Autowired
    LoggedUserDao loggedUserDao;

    @Override
    public void updateUserPersonalInfo(User user) throws UserException {

        loggedUserDao.updateUserPersonalInfo(user);
    }

    @Override
    public void updateUserProfilePicture(Long userId, String pictureUrl) throws UserException {
        loggedUserDao.updateUserProfilePicture(userId,pictureUrl);
    }
}
