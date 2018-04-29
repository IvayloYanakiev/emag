package com.emag.controller;


import com.emag.config.Constants;
import com.emag.config.ConstantsErrorMessages;
import com.emag.exceptions.UserException;
import com.emag.model.User;
import com.emag.service.UserService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.regex.Matcher;

@RequestMapping("/register")
@RestController
public class RegisterController {

    @Autowired
    UserService userService;

    @PostMapping("/createUser")
    public ResponseEntity register(@RequestParam("name") String name,
                                   @RequestParam("email") String email,
                                   @RequestParam("password") String password,
                                   @RequestParam("confirmPassword") String confirmPassword) {
        Gson gson = new Gson();

        try {
            User user = new User(name, password, email);
            if (validateEmail(user.getEmail())) {
                if (confirmPassword != null && confirmPassword.trim().length() > 0) {
                    if (user.getPassword().equals(confirmPassword)) {
                        userService.registerUser(user);
                    } else
                        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(gson.toJson(ConstantsErrorMessages.PASSWORDS_NOT_THE_SAME));
                } else
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(gson.toJson(ConstantsErrorMessages.CHECK_YOUR_PASSWORD));
            } else return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(gson.toJson(ConstantsErrorMessages.INVALID_EMAIL));
        } catch (UserException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(gson.toJson(e.getMessage()));
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(gson.toJson(ConstantsErrorMessages.ACCOUNT_PROBLEM));
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    private boolean validateEmail(String emailStr) {
        Matcher matcher = Constants.VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
        return matcher.find();
    }

}

