package com.emag.controller;

import com.emag.config.Constants;
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

    @Autowired
    ProductService productService;

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
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(gson.toJson("Invalid file type"));
        try {
            File newFile = convertProductPicture(picture);
            Product product = new Product(name, category, price, quantity, description, newFile.getPath(), discount);
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

    private File convertProductPicture(MultipartFile file) throws IOException {
        File convFile = new File("D:\\emagPictures\\w\\" + file.getOriginalFilename());
        convFile.createNewFile();
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(file.getBytes());
        fos.close();
        return convFile;
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

    @GetMapping("/getProductsFromShoppingCart")
    public ResponseEntity getProductsFromShoppingCart(@RequestParam("products") String ids) {
        Gson gson = new Gson();
        LinkedHashSet<Product> products = new LinkedHashSet<>();
        if (ids == null || ids.trim().length() == 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(gson.toJson(products));
        }
        try {
            products = productService.getProductsFromShoppingCart(ids);
        } catch (ProductException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(gson.toJson(products));
        }
        return ResponseEntity.status(HttpStatus.OK).body(gson.toJson(products));
    }




    @GetMapping("/orderProductsBy")
    public ResponseEntity orderProductsBy(@RequestParam("by") String orderBy) {
        Gson gson = new Gson();
        LinkedHashSet<Product> products = null;
        try {
            products = productService.getAllProductsOrderedByPrice(orderBy);
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
}
