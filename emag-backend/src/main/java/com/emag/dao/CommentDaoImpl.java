package com.emag.dao;

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

    public static final String ADDING_COMMENT_FAILED = "adding comment failed";
    public static final String PROBLEM_GETTING_COMMENTS = "problem getting comments";
    public static final String GET_ALL_COMMENTS = "select * from comments c " +
            " join users u" +
            " on c.user_id = u.id" +
            " where product_id=:product_id" +
            " order by c.id";
    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public void addProductComment(Comment comment) throws CommentException {
        String insertComment = "insert into comments(user_id,product_id,value,stars) values(:user_id,:product_id,:commentValue,:stars)";
        HashMap<String,Object> params = new HashMap<>();
        params.put("user_id",comment.getUserId());
        params.put("product_id",comment.getProductId());
        params.put("commentValue",comment.getValue());
        params.put("stars",comment.getStars());
        try{
            jdbcTemplate.update(insertComment,params);
        }catch (DataAccessException e){
            throw new CommentException(ADDING_COMMENT_FAILED,e);
        }
    }

    @Override
    public Collection<Comment> getAllComments(Long productId) throws CommentException {
        String getAllComments = GET_ALL_COMMENTS;
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
                        try {
                            Comment comment = new Comment(id,productId,userId,namesOfUser,commentValue,stars);
                            myComments.add(comment);
                        } catch (CommentException e) {
                           throw new SQLException(e.getMessage());
                        }

                    }
                    return myComments;

                }
            });
        } catch (Exception e) {
            throw new CommentException(PROBLEM_GETTING_COMMENTS, e);
        }
        return comments;
    }

}
