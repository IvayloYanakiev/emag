package com.emag.service;

import com.emag.exception.EmailException;
import org.springframework.mail.MailException;
import org.springframework.stereotype.Service;

@Service
public interface SendEmailService {
    void sendEmail(String email) throws EmailException;
}
