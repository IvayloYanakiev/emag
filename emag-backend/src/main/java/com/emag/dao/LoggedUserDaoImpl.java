package com.emag.dao;

import com.emag.config.ErrorMessages;
import com.emag.config.SqlConstants;
import com.emag.exceptions.UserException;
import com.emag.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import java.util.HashMap;

@Repository
public class LoggedUserDaoImpl implements LoggedUserDao{

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public void updateUserPersonalInfo(User user) throws UserException {
        String updateUserPersonalInfo = SqlConstants.UPDATE_USER_PERSONAL_INFO;
        HashMap<String,Object> params = new HashMap<>();
        params.put("id",user.getId());
        params.put("name",user.getName());
        params.put("email",user.getEmail());
        params.put("phone",user.getPhone());
        params.put("gender",user.getGender());
        int rowUpdated  = jdbcTemplate.update(updateUserPersonalInfo,params);
        if(rowUpdated==0){
            throw new UserException(ErrorMessages.ERROR_UPDATING_USER);
        }
    }

    @Override
    public void updateUserProfilePicture(Long userId, String pictureUrl) throws UserException {
        String updateUserProfilePicture = SqlConstants.UPDATE_USER_PERSONAL_PICTURE;
        HashMap<String,Object> params = new HashMap<>();
        params.put("userId",userId);
        params.put("profile_url",pictureUrl);
        int rowUpdated  = jdbcTemplate.update(updateUserProfilePicture,params);
        if(rowUpdated==0){
            throw new UserException(ErrorMessages.ERROR_UPDATING_USER);
        }
    }
}
