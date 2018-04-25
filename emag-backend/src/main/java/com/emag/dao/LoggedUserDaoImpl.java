package com.emag.dao;

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
    public void updateUserPersonalInfo(User user) {
        String updateUserPersonalInfo = "update users set name=:name,email=:email,phone=:phone,profile_url=:url,gender=:gender where id=:id";
        HashMap<String,Object> params = new HashMap<>();
        params.put("name",user.getName());
        params.put("email",user.getEmail());
        params.put("phone",user.getPhone());
        params.put("url",user.getPictureUrl());
        params.put("gender",user.getGender());
        params.put("id",user.getId());
        int rowUpdated  = jdbcTemplate.update(updateUserPersonalInfo,params);
        System.out.println(rowUpdated);
    }
}
