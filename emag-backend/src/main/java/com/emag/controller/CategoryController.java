package com.emag.controller;

import com.emag.exceptions.CategoryException;
import com.emag.model.Category;
import com.emag.service.CategoryService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
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
           return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(gson.toJson(e.getMessage()));
       }
       return new ResponseEntity<>(json, HttpStatus.OK);
   }

}
