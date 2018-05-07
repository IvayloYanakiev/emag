package com.emag.service;

import com.emag.exception.EmailException;
import com.emag.exception.UserException;
import org.springframework.mail.MailException;
import org.springframework.stereotype.Service;

@Service
public interface SendEmailService {
    void sendEmail(String email) throws EmailException;
    void informUserForOrder(String email, Long addressId, String payingMethod) throws EmailException;
}
