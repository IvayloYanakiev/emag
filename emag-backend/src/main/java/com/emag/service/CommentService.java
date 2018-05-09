package com.emag.service;

import com.emag.exception.CommentException;
import com.emag.model.Comment;

import java.util.Collection;


public interface CommentService {
     Comment addProductComment(Comment comment) throws CommentException;
}
