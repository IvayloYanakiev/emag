package com.emag.controller;

import com.emag.exceptions.CategoryException;
import com.emag.model.Category;
import com.emag.model.ResponseEntity;
import com.emag.service.CategoryService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@RequestMapping("/category")

public class CategoryController {

    @Autowired
    CategoryService categoryService;

   @GetMapping("/getAllCategories")
    public ResponseEntity getAllCategories(){

       Gson gson = new Gson();
       String json = null;

       try {
           HashMap<Long, Category> categories =  categoryService.getAllCategories();
           json = gson.toJson(categories);
       } catch (CategoryException e) {
           System.out.println(e.getMessage());
           return new ResponseEntity(e.getMessage(),HttpStatus.BAD_REQUEST);
       }
       return new ResponseEntity(json,HttpStatus.OK);
   }

}
