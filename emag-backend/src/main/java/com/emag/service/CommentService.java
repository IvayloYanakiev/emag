package com.emag.service;

import com.emag.exception.CommentException;
import com.emag.model.Comment;

import java.util.Collection;


public interface CommentService {
     Collection<Comment> addProductComment(Comment comment) throws CommentException;
}
