package com.emag.dao;

import com.emag.config.ConstantsErrorMessages;
import com.emag.config.ConstantsSQL;
import com.emag.exception.EmailException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;

@Repository
public class SendEmailDaoImpl implements SendEmailDao {

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;


    @Override
    public void sendEmail(String email) throws EmailException {
        String updateSubscribeStatus = ConstantsSQL.UPDATE_USER_SUBSCRIBE_STATUS;
        HashMap<String, Object> params = new HashMap<>();
        params.put("email", email);
        try {
            jdbcTemplate.update(updateSubscribeStatus, params);
        } catch (DataAccessException e) {
            throw new EmailException(ConstantsErrorMessages.NO_SUCH_EMAIL);
        }
    }

}
