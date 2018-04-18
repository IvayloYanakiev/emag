package com.emag.controller;


import com.emag.config.Constants;
import com.emag.exceptions.AccountException;
import com.emag.model.Account;
import com.emag.model.ResponseEntity;
import com.emag.service.AccountService;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.regex.Matcher;

@RequestMapping("/register")
@RestController
public class RegisterController {

    @Autowired
    AccountService accountService;

    @PostMapping("/createAccount")
    @ResponseBody
    public ResponseEntity register(@RequestParam("name") String name, @RequestParam("email") String email, @RequestParam("password") String password, @RequestParam("confirmPassword") String confirmPassword) {

        JSONObject obj = new JSONObject();

        try {
            Account user = new Account(name, password, email);
            if (validateEmail(user.getEmail())) {
                if (confirmPassword != null && confirmPassword.trim().length() > 0) {
                    if (user.getPassword().equals(confirmPassword)) {
                        accountService.createAccount(user);
                    } else return new ResponseEntity("Passwords are not the same", HttpStatus.BAD_REQUEST);
                } else return new ResponseEntity("Please check your confirm password", HttpStatus.BAD_REQUEST);
            } else return new ResponseEntity("Invalid email", HttpStatus.BAD_REQUEST);
        } catch (AccountException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (SQLException e) {
            return new ResponseEntity("Error", HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity("Success", HttpStatus.OK);
    }

    private boolean validateEmail(String emailStr) {
        Matcher matcher = Constants.VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
        return matcher.find();
    }

}

