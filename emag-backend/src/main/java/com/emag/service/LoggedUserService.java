package com.emag.service;

import com.emag.exception.UserException;
import com.emag.model.User;

public interface LoggedUserService {
    void updateUserPersonalInfo(User user) throws UserException;
    void updateUserProfilePicture(Long userId,String pictureUrl) throws UserException;
}
