package com.emag.controller;

import com.emag.config.Constants;
import com.emag.exception.ProductException;
import com.emag.model.Product;
import com.emag.service.ProductService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@RestController
@RequestMapping("/product")
public class ProductController {


    @Autowired
    ProductService productService;

    @RequestMapping(value = {"/addProduct"}, method = RequestMethod.POST, consumes = "multipart/form-data")
    public ResponseEntity addProduct(
            @RequestParam("name") String name,
            @RequestParam("categoryId") Long category,
            @RequestParam("price") Double price,
            @RequestParam("quantity") Integer quantity,
            @RequestParam("description") String description,
            @RequestParam("picture") MultipartFile picture) {
        Gson gson = new Gson();

        String mimetype = picture.getOriginalFilename().split("\\.")[1];
        String type = mimetype.split("/")[0];
        if (!type.equals("jpg") && !type.equals("png"))  return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(gson.toJson("Invalid file type"));
        try {
            File newFile = convertProductPicture(picture);
            Product product = new Product(name,category,price,quantity,description,newFile.getPath());
            productService.addProduct(product);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(gson.toJson(Constants.ERROR));
        } catch (ProductException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(gson.toJson(e.getMessage()));
        }
        return ResponseEntity.status(HttpStatus.OK).body(gson.toJson(Constants.SUCCESS));
    }

    private File convertProductPicture(MultipartFile file) throws IOException {
        File convFile = new File("D:\\emagPictures\\productPictures\\" + file.getOriginalFilename());
        convFile.createNewFile();
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(file.getBytes());
        fos.close();
        return convFile;
    }
}