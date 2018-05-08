package com.emag.dao;

import com.emag.config.ConstantsErrorMessages;
import com.emag.config.ConstantsSQL;
import com.emag.exception.CommentException;
import com.emag.model.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashSet;

@Repository

public class CommentDaoImpl implements CommentDao{

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public void addProductComment(Comment comment) throws CommentException {
        String insertComment = ConstantsSQL.INSERT_INTO_COMMENTS;
        HashMap<String,Object> params = new HashMap<>();
        params.put("user_id",comment.getUserId());
        params.put("product_id",comment.getProductId());
        params.put("commentValue",comment.getValue());
        params.put("stars",comment.getStars());
        params.put("creationDate",comment.getCreatingDate());
        try{
            jdbcTemplate.update(insertComment,params);
        }catch (Exception e){
            throw new CommentException(ConstantsErrorMessages.ADDING_COMMENT_FAILED,e);
        }
    }

    @Override
    public Collection<Comment> getAllComments(Long productId) throws CommentException {
        String getAllComments = ConstantsSQL.GET_ALL_COMMENTS;
        HashMap<String, Object> params = new HashMap<>();
        params.put("product_id", productId);
       Collection<Comment> comments = null;
        try {
            comments = jdbcTemplate.query(getAllComments, params, new ResultSetExtractor<Collection<Comment>>() {

                @Override
                public Collection<Comment> extractData(ResultSet rs) throws SQLException {
                    Collection<Comment> myComments = new LinkedHashSet<>();

                    while (rs.next()) {
                        Long id = rs.getLong("id");
                        Long userId = rs.getLong("user_id");
                        Long productId = rs.getLong("product_id");
                        String commentValue = rs.getString("value");
                        Integer stars = rs.getInt("stars");
                        String namesOfUser = rs.getString("name");
                        String profileUrl = rs.getString("profile_url");
                        String creationDate = rs.getString("creation_date");
                        try {
                            Comment comment = new Comment(id,productId,userId,namesOfUser,commentValue,stars,profileUrl,creationDate);
                            myComments.add(comment);
                        } catch (CommentException e) {
                           throw new SQLException(e.getMessage());
                        }
                    }
                    return myComments;
                }
            });
        } catch (Exception e) {
            throw new CommentException(ConstantsErrorMessages.PROBLEM_GETTING_COMMENTS, e);
        }
        return comments;
    }

}
