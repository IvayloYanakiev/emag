package com.emag.dao;


import com.emag.exceptions.UserException;
import com.emag.model.User;

public interface LoggedUserDao {
    void updateUserPersonalInfo(User user) throws UserException;

    void updateUserProfilePicture(Long userId, String pictureUrl) throws UserException;
}
