package com.emag.dao;

import com.emag.exception.CommentException;
import com.emag.model.Comment;

import java.util.Collection;


public interface CommentDao {
    Long addProductComment(Comment comment) throws CommentException;
    Collection<Comment> getAllComments(Long productId) throws CommentException;
    Comment getCommentById(Long commentId) throws CommentException;
}
