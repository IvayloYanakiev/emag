package com.emag.controller;

import com.emag.model.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/category")

public class CategoryController {

   @GetMapping("/getAllCategories")
    public ResponseEntity getAllCategories(){


       return new ResponseEntity(null,null);
   }

}
