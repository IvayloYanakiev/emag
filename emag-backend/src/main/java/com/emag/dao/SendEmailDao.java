package com.emag.dao;

import com.emag.exception.EmailException;
import org.springframework.mail.MailException;

public interface SendEmailDao {
    void sendEmail(String email) throws MailException, EmailException;
}
