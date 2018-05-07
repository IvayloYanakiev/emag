package com.emag.controller;


import com.emag.config.Constants;
import com.emag.config.ConstantsErrorMessages;
import com.emag.config.EmagCloud;
import com.emag.exception.UserException;
import com.emag.model.User;
import com.emag.service.UserService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.regex.Matcher;

@RequestMapping("/register")
@RestController
public class RegisterController {

    public static final String NO_PICTURE = "http://res.cloudinary.com/dxnmejm7r/image/upload/v1525705078/zx95yaxdahmoocm9fbt4.jpg";
    @Autowired
    UserService userService;

    @Autowired
    EmagCloud myCloud;

    @PostMapping("/createUser")
    public ResponseEntity register(@RequestParam("name") String name,
                                   @RequestParam("email") String email,
                                   @RequestParam("password") String password,
                                   @RequestParam("confirmPassword") String confirmPassword) {
        Gson gson = new Gson();

        try {
            User user = new User(name, password, email, NO_PICTURE);
            if (confirmPassword != null && confirmPassword.trim().length() > 0) {
                if (user.getPassword().equals(confirmPassword)) {
                    userService.registerUser(user);
                } else
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(gson.toJson(ConstantsErrorMessages.PASSWORDS_NOT_THE_SAME));
            } else
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(gson.toJson(ConstantsErrorMessages.CHECK_YOUR_PASSWORD));

        } catch (UserException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(gson.toJson(e.getMessage()));
        }
        return ResponseEntity.status(HttpStatus.OK).body(gson.toJson(Constants.SUCCESS));
    }


}

