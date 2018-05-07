package com.emag.controller;

import com.emag.exception.ProductException;
import com.emag.model.Product;
import com.emag.service.ShoppingCartService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashSet;

@RestController
@RequestMapping("/shoppingCart")
public class ShoppingCartController {

    @Autowired
    ShoppingCartService shoppingCartService;

    @GetMapping("/getProductsFromShoppingCart")
    public ResponseEntity getProductsFromShoppingCart(@RequestParam("products") String ids) {
        Gson gson = new Gson();
        LinkedHashSet<Product> products = new LinkedHashSet<>();
        if (ids == null || ids.trim().length() == 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(gson.toJson(products));
        }
        try {
            products = shoppingCartService.getProductsFromShoppingCart(ids);
        } catch (ProductException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(gson.toJson(products));
        }
        return ResponseEntity.status(HttpStatus.OK).body(gson.toJson(products));
    }
}
