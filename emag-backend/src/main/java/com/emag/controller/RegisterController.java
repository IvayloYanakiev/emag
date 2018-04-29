package com.emag.controller;


import com.emag.config.Constants;
import com.emag.config.ConstantsErrorMessages;
import com.emag.exceptions.UserException;
import com.emag.model.User;
import com.emag.model.ResponseEntity;
import com.emag.service.UserService;
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
    UserService userService;

    @PostMapping("/createUser")
    public ResponseEntity register(@RequestParam("name") String name, @RequestParam("email") String email, @RequestParam("password") String password, @RequestParam("confirmPassword") String confirmPassword) {

        JSONObject obj = new JSONObject();

        try {
            User user = new User(name, password, email);
            if (validateEmail(user.getEmail())) {
                if (confirmPassword != null && confirmPassword.trim().length() > 0) {
                    if (user.getPassword().equals(confirmPassword)) {
                        userService.registerUser(user);
                    } else return new ResponseEntity(ConstantsErrorMessages.PASSWORDS_NOT_THE_SAME, HttpStatus.BAD_REQUEST);
                } else return new ResponseEntity(ConstantsErrorMessages.CHECK_YOUR_PASSWORD, HttpStatus.BAD_REQUEST);
            } else return new ResponseEntity(ConstantsErrorMessages.INVALID_EMAIL, HttpStatus.BAD_REQUEST);
        } catch (UserException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (SQLException e) {
            return new ResponseEntity(ConstantsErrorMessages.ERROR, HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity(Constants.SUCCESS, HttpStatus.OK);
    }

    private boolean validateEmail(String emailStr) {
        Matcher matcher = Constants.VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
        return matcher.find();
    }

}

