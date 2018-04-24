package com.emag.controller;

import com.emag.config.Constants;
import com.emag.exceptions.UserException;
import com.emag.model.ResponseEntity;
import com.emag.model.User;
import com.emag.service.UserService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;


@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;


    @GetMapping("/getUserPersonalData")
    @ResponseBody
    public ResponseEntity getUserInfo(@RequestParam("id") Long id) {

        Gson gson = new Gson();
        String json = null;

        try {
            User user = userService.findUserById(id);
            json = gson.toJson(user);
        } catch (UserException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (SQLException e) {
            return new ResponseEntity(Constants.ACC_PROBLEM, HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity(json, HttpStatus.OK);
    }

}
