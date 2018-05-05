package com.emag.service;

import com.emag.config.Constants;
import com.emag.dao.SendEmailDao;
import com.emag.dao.UserDao;
import com.emag.exception.EmailException;
import com.emag.exception.UserException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class SendEmailServiceImpl implements SendEmailService {

    @Autowired
    SendEmailDao sendEmailDao;

    @Autowired
    private JavaMailSender javaMailSender;

    @Override
    public void sendEmail(String email) throws EmailException {
        sendEmailDao.sendEmail(email);

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(email);
        mailMessage.setFrom(Constants.SENDER_EMAIL);
        mailMessage.setSubject(Constants.EMAIL_SUBJECT_SUBSCRIPTION);
        mailMessage.setText(Constants.EMAIL_TEXT_SUCCESSFUL_SUBSCRIPTION);
        try {
            javaMailSender.send(mailMessage);
        } catch (MailException e) {
            throw new EmailException(e.getMessage(), e);
        }
    }

}
