package com.emag.controller;

import com.google.gson.Gson;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.activation.MimetypesFileTypeMap;
import java.io.File;
import java.io.IOException;

@RestController
@RequestMapping("/product")
public class ProductController {

    @RequestMapping(value = {"/addProduct"}, method = RequestMethod.POST, consumes = "multipart/form-data")
    public ResponseEntity addProduct(
            @RequestParam("name") String name,
            @RequestParam("price") Long price,
            @RequestParam("quantity") Integer quantity,
            @RequestParam("description") String description,
            @RequestParam("picture") MultipartFile picture) {
        Gson gson = new Gson();

        String mimetype = picture.getOriginalFilename().split("\\.")[1];
        String type = mimetype.split("/")[0];
        if (!type.equals("image"))
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(gson.toJson("Invalid file type"));
        System.out.println("OK");
        return new ResponseEntity<>(HttpStatus.OK);

    }

}
