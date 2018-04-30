package com.emag.controller;

import com.emag.config.Constants;
import com.emag.config.ConstantsErrorMessages;
import com.emag.exception.UserException;
import com.emag.model.User;
import com.emag.service.UserService;
import com.google.gson.Gson;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.sql.SQLException;


@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    UserService userService;

    @PostMapping("/user")
    public ResponseEntity login(@RequestParam("email") String email, @RequestParam("password") String password) {
        Gson gson = new Gson();
        String json = null;
        try {
            userService.checkDoesGivenUserExists(email, password);
            User user = userService.findUserByEmail(email);
            json = gson.toJson(user);
        } catch (UserException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(gson.toJson(e.getMessage()));
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(gson.toJson(ConstantsErrorMessages.ACCOUNT_PROBLEM));
        }

        return new ResponseEntity<>(json,HttpStatus.OK);
    }

    @GetMapping("/getUserPageByEmail")
    public ResponseEntity getUserPageByEmail(@RequestParam("email") String email) {

        Gson gson = new Gson();
        String json = null;

        try {
            User user = userService.findUserByEmail(email);
            json = gson.toJson(user);
        } catch (UserException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(gson.toJson(e.getMessage()));
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(gson.toJson(ConstantsErrorMessages.ACCOUNT_PROBLEM));
        }

        return new ResponseEntity<>(json,HttpStatus.OK);
    }


    @GetMapping("/getUserPageById")
    public ResponseEntity getUserPageById(@RequestParam("id") Long id) {

        JSONObject obj = new JSONObject();
        Gson gson = new Gson();
        try {
            User user = userService.findUserById(id);
            obj.put("user", user);
        } catch (UserException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(gson.toJson(e.getMessage()));
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(gson.toJson(ConstantsErrorMessages.ACCOUNT_PROBLEM));
        }
        return ResponseEntity.status(HttpStatus.OK).body(gson.toJson(Constants.SUCCESS));
    }
}
