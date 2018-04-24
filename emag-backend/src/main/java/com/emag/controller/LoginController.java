package com.emag.controller;

import com.emag.config.Constants;
import com.emag.exceptions.UserException;
import com.emag.model.User;
import com.emag.model.ResponseEntity;
import com.emag.service.UserService;
import com.google.gson.Gson;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.sql.SQLException;

@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    UserService userService;

    @PostMapping("/user")
    @ResponseBody
    public ResponseEntity login(@RequestParam("email") String email, @RequestParam("password") String password) {

        Gson gson = new Gson();
        String json = null;

        try {
            userService.checkDoesGivenUserExists(email, password);
            User user = userService.findUserByEmail(email);
            json = gson.toJson(user);
        } catch (UserException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (SQLException e) {
            return new ResponseEntity(Constants.ACC_PROBLEM, HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity(json, HttpStatus.OK);
    }

    @GetMapping("/getUserPageByEmail")
    @ResponseBody
    public ResponseEntity getUserPageByEmail(@RequestParam("email") String email) {

        JSONObject obj = new JSONObject();
        try {
            User user = userService.findUserByEmail(email);
            obj.put("user", user);
        } catch (UserException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (SQLException e) {
            return new ResponseEntity(Constants.ERROR, HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity(obj, HttpStatus.OK);
    }


    @GetMapping("/getUserPageById")
    @ResponseBody
    public ResponseEntity getUserPageById(@RequestParam("id") Long id) {

        JSONObject obj = new JSONObject();
        try {
            User user = userService.findUserById(id);
            obj.put("user", user);
        } catch (UserException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (SQLException e) {

            return new ResponseEntity(Constants.ERROR, HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity(obj, HttpStatus.OK);
    }
}
