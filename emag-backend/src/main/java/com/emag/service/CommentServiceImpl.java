package com.emag.service;

import com.emag.dao.CommentDao;
import com.emag.exception.CommentException;
import com.emag.model.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;


@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentDao commentDao;

    @Override
    public Collection<Comment> addProductComment(Comment comment) throws CommentException {
        commentDao.addProductComment(comment);
        return commentDao.getAllComments(comment.getProductId());
    }
}
