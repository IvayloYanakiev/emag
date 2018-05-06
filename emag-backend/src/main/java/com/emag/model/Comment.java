package com.emag.model;

import com.emag.exception.CommentException;

public class Comment {
    public static final int MAX_COMMENT_VALUE_LENGTH = 100;
    public static final String INVALID_VALUE_OF_COMMENT = "Invalid value of comment";
    private Long id;
    private Long productId;
    private Long userId;
    private String userNames;
    private String value;
    private Integer stars;

    public Integer getStars() {
        return stars;
    }

    public void setStars(Integer stars) throws CommentException {
        if (stars!=null && stars > -1 && stars<6)
            this.stars = stars;
        else throw new CommentException("Invalid comment stars");
    }

    public String getUserNames() {
        return userNames;
    }

    public void setUserNames(String userNames) throws CommentException {
        if (userNames != null && userNames.trim().length() > 0) {
            this.userNames = userNames;
        } else throw new CommentException("Invalid names of user comment");
    }

    public Comment(Long productId, Long userId, String value, Integer stars) throws CommentException {
        setProductId(productId);
        setUserId(userId);
        setValue(value);
        setStars(stars);
    }

    public Comment(Long id, Long productId, Long userId, String userNames, String value, Integer stars) throws CommentException {
        setId(id);
        setProductId(productId);
        setUserId(userId);
        setValue(value);
        setUserNames(userNames);
        setStars(stars);
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) throws CommentException {
        if (id > -1)
            this.id = id;
        else throw new CommentException("Invalid comment id");
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) throws CommentException {
        if (productId > -1)
            this.productId = productId;
        else throw new CommentException("Invalid product id");
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) throws CommentException {
        if (userId > -1)
            this.userId = userId;
        else throw new CommentException("Invalid user id");
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) throws CommentException {
        if (value != null && value.trim().length() > 0 && value.trim().length() <= MAX_COMMENT_VALUE_LENGTH) {
            this.value = value;
        } else throw new CommentException(INVALID_VALUE_OF_COMMENT);
    }
}
