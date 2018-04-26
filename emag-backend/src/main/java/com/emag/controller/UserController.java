package com.emag.controller;

import com.emag.config.Constants;
import com.emag.exceptions.UserException;
import com.emag.model.ResponseEntity;
import com.emag.model.User;
import com.emag.service.LoggedUserService;
import com.emag.service.UserService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;


@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    LoggedUserService loggedUserService;

    @GetMapping("/getUserPersonalData")
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

    @RequestMapping(value = {"/updateUserPersonalData"}, method = RequestMethod.POST, consumes = "multipart/form-data")
    public ResponseEntity updateUserPersonalData
            (@RequestParam("id") Long id,@RequestParam("name") String name,@RequestParam("email") String email,
             @RequestParam("gender") String gender,@RequestParam("mobilePhone") String phone,@RequestParam("picture") MultipartFile picture){

        try {
            File newFile = convert(picture);
            User user = new User(id,name,email,newFile.getPath(),gender,phone);
            loggedUserService.updateUserPersonalInfo(user);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (UserException e) {
            e.printStackTrace();
        }


        return null;
    }

    public File convert(MultipartFile file) throws IOException {
        File convFile = new File("D:\\userPictures\\" + file.getOriginalFilename());
        convFile.createNewFile();
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(file.getBytes());
        fos.close();
        return convFile;
    }


}
