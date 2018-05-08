package com.emag.controller;

import com.emag.exception.CommentException;
import com.emag.model.Comment;
import com.emag.service.CommentService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @PostMapping("/addComment")
    public ResponseEntity orderProductsBy(@RequestParam("productId") Long productId, @RequestParam("userId") Long userId, String value,@RequestParam("stars") Integer stars) {
        Gson gson = new Gson();
        Collection<Comment> comments = null;
        try {
            Comment comment = new Comment(productId, userId, value,stars);
            comments = commentService.addProductComment(comment);
        } catch (CommentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(gson.toJson(e.getMessage()));
        }
        String json = gson.toJson(comments);
        return ResponseEntity.status(HttpStatus.OK).body(json);
    }


}
