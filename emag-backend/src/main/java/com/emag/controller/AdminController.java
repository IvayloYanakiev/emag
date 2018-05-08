package com.emag.controller;

import com.emag.config.Constants;
import com.emag.config.ConstantsErrorMessages;
import com.emag.config.EmagCloud;
import com.emag.exception.ProductException;
import com.emag.model.Product;
import com.emag.service.AdminService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RequestMapping("/admin")
@RestController
public class AdminController {

    @Autowired
    private EmagCloud myCloud;

    @Autowired
    private AdminService adminService;
    //comments deleting

    @RequestMapping(value = {"/addProduct"}, method = RequestMethod.POST, consumes = "multipart/form-data")
    public ResponseEntity addProduct(
            @RequestParam("name") String name,
            @RequestParam("categoryId") Long category,
            @RequestParam("price") Double price,
            @RequestParam("quantity") Integer quantity,
            @RequestParam("description") String description,
            @RequestParam("picture") MultipartFile picture,
            @RequestParam("discount") Integer discount) {
        Gson gson = new Gson();

        String mimetype = picture.getOriginalFilename().split("\\.")[1];
        String type = mimetype.split("/")[0];
        if (!type.equals("jpg") && !type.equals("png") && !type.equals("jpeg"))
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(gson.toJson(ConstantsErrorMessages.INVALID_FILE_TYPE));
        try {
            File newFile = UserController.convertFromMultypartToFile(picture);
            Map uploadResult = myCloud.emagCloud().uploader().upload(newFile, new HashMap<String, Object>());
            String url = (String) uploadResult.get("url");
            Product product = new Product(name, category, price, quantity, description, url, discount);
            adminService.addProduct(product);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(gson.toJson(Constants.ERROR));
        } catch (ProductException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(gson.toJson(e.getMessage()));
        }
        return ResponseEntity.status(HttpStatus.OK).body(gson.toJson(Constants.SUCCESS));
    }

    @DeleteMapping("/removeProductById")
    public ResponseEntity removeProductById(@RequestParam("id") Long id) {
        Gson gson = new Gson();
        try {
            adminService.deleteProductById(id);
        } catch (ProductException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(gson.toJson(e.getMessage()));
        }
        return ResponseEntity.status(HttpStatus.OK).body(gson.toJson(Constants.SUCCESS));
    }

    @PutMapping("/updateProduct")
    public ResponseEntity updateProduct(@RequestParam("id") Long id,
                                        @RequestParam("name") String name,
                                        @RequestParam("categoryId") Long category,
                                        @RequestParam("price") Double price,
                                        @RequestParam("quantity") Integer quantity,
                                        @RequestParam("description") String description,
                                        @RequestParam("discount") Integer discount) {
        Gson gson = new Gson();
        try {
            Product product = new Product(id, name, category, price, quantity, description, discount);
            adminService.updateProductById(product);
        } catch (ProductException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(gson.toJson(e.getMessage()));
        }
        return ResponseEntity.status(HttpStatus.OK).body(gson.toJson(Constants.SUCCESS));
    }
}
