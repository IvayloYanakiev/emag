package com.emag.service;

import com.emag.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class SendingEmailService {
    private JavaMailSender javaMailSender;

    @Autowired
    public SendingEmailService(JavaMailSender javaMailSender){
        this.javaMailSender = javaMailSender;
    }

    public void sendEmail(User user) throws MailException {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(user.getEmail());
        mailMessage.setFrom("emag.7377@gmail.com");
        mailMessage.setSubject("Email sent via spring boot");
        mailMessage.setText("Some text");

        javaMailSender.send(mailMessage);
    }
}
