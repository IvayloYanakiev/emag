package com.emag.controller;

import com.emag.exceptions.CategoryException;
import com.emag.model.ResponseEntity;
import com.google.gson.Gson;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/city")
public class CityController {

    @GetMapping("/getAllCities")
    public ResponseEntity getAllCities(){

        Gson gson = new Gson();
        String json = null;

       
        return new ResponseEntity(json,HttpStatus.OK);
    }
}
