package com.emag.model;

import com.emag.config.ConstantsErrorMessages;
import com.emag.exception.ProductException;

public class Product {

    private Long id;
    private String name;
    private String pictureURL;
    private Double price;
    private Long innerCategoryId;
    private Integer quantity;
    private String description;

    public Product(String name, Long innerCategoryId, Double price, Integer quantity,String description, String pictureURL) throws ProductException {
        setName(name);
        setInnerCategoryId(innerCategoryId);
        setPrice(price);
        setQuantity(quantity);
        setDescription(description);
        setPictureURL(pictureURL);
    }


    public Product(Long id, String name, String pictureURL, Double price, Long innerCategoryId, Integer quantity, String description) throws ProductException {
        setId(id);
        setName(name);
        setInnerCategoryId(innerCategoryId);
        setPrice(price);
        setQuantity(quantity);
        setDescription(description);
        setPictureURL(pictureURL);
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) throws ProductException {
        if (description != null && description.trim().length() > 0) {
            this.description = description;
        } else {
            throw new ProductException(ConstantsErrorMessages.INVALID_PRODUCT_DESCRIPTION);
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) throws ProductException {
        if (id != null && id > -1) {
            this.id = id;
        } else {
            throw new ProductException(ConstantsErrorMessages.INVALID_PRODUCT_ID);
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) throws ProductException {
        if (name != null && name.trim().length() > 0) {
            this.name = name;
        } else {
            throw new ProductException(ConstantsErrorMessages.INVALID_PRODUCT_NAME);
        }
    }

    public String getPictureURL() {
        return pictureURL;
    }

    public void setPictureURL(String pictureURL) throws ProductException {
        if (pictureURL != null && pictureURL.trim().length() > 0) {
            this.pictureURL = pictureURL;
        } else {
            throw new ProductException(ConstantsErrorMessages.INVALID_PRODUCT_PICTURE_URL);
        }
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(Double price) throws ProductException {
        if (price!=null && price > 0) {
            this.price = price;
        } else {
            throw new ProductException(ConstantsErrorMessages.INVALID_PRODUCT_PRICE);
        }
    }


    public Long getInnerCategoryId() {
        return innerCategoryId;
    }

    public void setInnerCategoryId(Long innerCategoryId) throws ProductException {
        if (innerCategoryId != null && innerCategoryId > -1) {
            this.innerCategoryId = innerCategoryId;
        } else {
            throw new ProductException(ConstantsErrorMessages.INVALID_PRODUCT_TYPE_ID);
        }
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) throws ProductException {
        if (quantity!=null && quantity >= 0) {
            this.quantity = quantity;
        } else {
            throw new ProductException(ConstantsErrorMessages.INVALID_PRODUCT_QUANTITY);
        }
    }

}
