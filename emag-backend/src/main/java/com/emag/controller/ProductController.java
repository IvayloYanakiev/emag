package com.emag.controller;

import com.emag.config.Constants;
import com.emag.config.EmagCloud;
import com.emag.exception.ProductException;
import com.emag.model.Product;
import com.emag.service.ProductService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;


@RestController
@RequestMapping("/product")
public class ProductController {

    public static final String INVALID_FILE_TYPE = "Invalid file type";
    @Autowired
    private ProductService productService;

    @Autowired
    private EmagCloud myCloud;

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
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(gson.toJson(INVALID_FILE_TYPE));
        try {
            File newFile = UserController.convertFromMultypartToFile(picture);
            Map uploadResult = myCloud.emagCloud().uploader().upload(newFile, new HashMap<String, Object>());
            String url = (String) uploadResult.get("url");
            Product product = new Product(name, category, price, quantity, description, url, discount);
            productService.addProduct(product);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(gson.toJson(Constants.ERROR));
        } catch (ProductException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(gson.toJson(e.getMessage()));
        }
        return ResponseEntity.status(HttpStatus.OK).body(gson.toJson(Constants.SUCCESS));
    }

    @GetMapping("/getAllProducts")
    public ResponseEntity getAllProducts() {
        Gson gson = new Gson();
        LinkedHashSet<Product> products = null;
        try {
            products = productService.getAllProducts();
        } catch (ProductException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(gson.toJson(e.getMessage()));
        }
        return ResponseEntity.status(HttpStatus.OK).body(gson.toJson(products));
    }

    @GetMapping("/getInnerCategoryProducts")
    public ResponseEntity getProductsByInnerCategoryId(@RequestParam("id") Long id) {
        LinkedHashSet<Product> products = null;
        Gson gson = new Gson();
        try {
            products = productService.getProductsByInnerCategoryId(id);
        } catch (ProductException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(gson.toJson(e.getMessage()));
        }
        String json = gson.toJson(products);
        return ResponseEntity.ok(json);
    }

    @GetMapping("/orderProductsByPrice")
    public ResponseEntity orderProductsByPrice(@RequestParam("orderIn") String orderIn) {
        Gson gson = new Gson();
        LinkedHashSet<Product> products = null;
        try {
            products = productService.getAllProductsOrderedByPrice(orderIn);
        } catch (ProductException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(gson.toJson(e.getMessage()));
        }
        return ResponseEntity.status(HttpStatus.OK).body(gson.toJson(products));
    }

    @GetMapping("/orderProductsByDiscount")
    public ResponseEntity orderProductsByDiscount(@RequestParam("orderIn") String orderIn) {
        Gson gson = new Gson();
        LinkedHashSet<Product> products = null;
        try {
            products = productService.getAllProductsOrderedByDiscount(orderIn);
        } catch (ProductException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(gson.toJson(e.getMessage()));
        }
        return ResponseEntity.status(HttpStatus.OK).body(gson.toJson(products));
    }

    @GetMapping("/orderProductsByName")
    public ResponseEntity orderProductsByName(@RequestParam("orderIn") String orderIn) {
        Gson gson = new Gson();
        LinkedHashSet<Product> products = null;
        try {
            products = productService.getAllProductsOrderedByName(orderIn);
        } catch (ProductException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(gson.toJson(e.getMessage()));
        }
        return ResponseEntity.status(HttpStatus.OK).body(gson.toJson(products));
    }


    @GetMapping("/getProductById")
    public ResponseEntity getProductById(@RequestParam("id") Long id) {
        Gson gson = new Gson();
        Product selectedProduct = null;
        try {
            selectedProduct = productService.getProductById(id);
        } catch (ProductException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(gson.toJson(e.getMessage()));
        }
        String json = gson.toJson(selectedProduct);
        return ResponseEntity.status(HttpStatus.OK).body(json);
    }

    @DeleteMapping("/removeProductById")
    public ResponseEntity removeProductById(@RequestParam("id") Long id) {
        Gson gson = new Gson();
        try {
            productService.deleteProductById(id);
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
            productService.updateProductById(product);
        } catch (ProductException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(gson.toJson(e.getMessage()));
        }
        return ResponseEntity.status(HttpStatus.OK).body(gson.toJson(Constants.SUCCESS));
    }


    @RequestMapping("/getProductsFilteredByName")
    public ResponseEntity getProductsFilteredByName(@RequestParam("searchInput") String searchInput) {
        Gson gson = new Gson();
        String json = null;

        try {
            LinkedHashSet<Product> products = productService.getProductsFilteredByName(searchInput);
            json = gson.toJson(products);
        } catch (ProductException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(gson.toJson(e.getMessage()));
        }
        return ResponseEntity.status(HttpStatus.OK).body(json);
    }

    @GetMapping("/getProductsFilteredByPrice")
    public ResponseEntity getProductsFilteredByPrice(@RequestParam("maxPrice") Integer maxPrice) {
        Gson gson = new Gson();
        LinkedHashSet<Product> products = null;
        try {
            products = productService.getProductsFilteredByPrice(maxPrice);
        } catch (ProductException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(gson.toJson(e.getMessage()));
        }
        return ResponseEntity.status(HttpStatus.OK).body(gson.toJson(products));
    }

}
