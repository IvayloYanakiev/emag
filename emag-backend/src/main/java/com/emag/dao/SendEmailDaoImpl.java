package com.emag.dao;

import com.emag.config.ConstantsErrorMessages;
import com.emag.config.ConstantsSQL;
import com.emag.exceptions.EmailException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.mail.MailException;
import org.springframework.stereotype.Repository;

import java.util.HashMap;

@Repository
public class SendEmailDaoImpl implements SendEmailDao {

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;


    @Override
    public void sendEmail(String email) throws MailException, EmailException {
        String updateSubscribeStatus = ConstantsSQL.UPDATE_USER_SUBSCRIBE_STATUS;
        HashMap<String, Object> params = new HashMap<>();
        params.put("email", email);
        int rowUpdated = jdbcTemplate.update(updateSubscribeStatus, params);

        if (rowUpdated == 0) {
            throw new EmailException(ConstantsErrorMessages.NO_SUCH_EMAIL);
        }
    }
}
