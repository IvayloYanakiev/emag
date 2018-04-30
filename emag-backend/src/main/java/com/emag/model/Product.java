package com.emag.model;

import com.emag.config.ConstantsErrorMessages;
import com.emag.exception.ProductException;

public class Product {
    private Long id;
    private String name;
    private String pictureURL;
    private double price;
    private Long typeID;
    private int quantity;

    public Product() {

    }

    public Product(String name, String pictureURL, double price, Long typeID, int quantity) throws ProductException {
        setName(name);
        setPictureURL(pictureURL);
        setPrice(price);
        setTypeID(typeID);
        setQuantity(quantity);
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

    public void setPrice(double price) throws ProductException {
        if (price > 0) {
            this.price = price;
        } else {
            throw new ProductException(ConstantsErrorMessages.INVALID_PRODUCT_PRICE);
        }
    }

    public Long getTypeID() {
        return typeID;
    }

    public void setTypeID(Long typeID) throws ProductException {
        if (typeID != null && typeID > -1) {
            this.typeID = typeID;
        } else {
            throw new ProductException(ConstantsErrorMessages.INVALID_PRODUCT_TYPE_ID);
        }
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) throws ProductException {
        if (quantity >= 0) {
            this.quantity = quantity;
        } else {
            throw new ProductException(ConstantsErrorMessages.INVALID_PRODUCT_QUANTITY);
        }
    }
}
