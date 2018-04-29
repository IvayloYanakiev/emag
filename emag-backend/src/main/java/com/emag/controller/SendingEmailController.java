package com.emag.controller;

import com.emag.exceptions.UserException;
import com.emag.model.ResponseEntity;
import com.emag.model.User;
import com.emag.service.SendingEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/subscribe")
public class SendingEmailController {

    @Autowired
    private SendingEmailService sendingEmailService;

    @PostMapping("/subscribeUser")
    public ResponseEntity subscribe(@RequestParam("email") String email) throws UserException {
        User user = new User("simona", "simona", "simona.georgieva096@gmail.com");

        try {
            sendingEmailService.sendEmail(user);
        }catch (MailException e){
            System.out.println(e.getMessage());
        }

        return new ResponseEntity();
    }
}
