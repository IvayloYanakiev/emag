package com.emag.dao;

import com.emag.model.Address;

public interface LoggedUserDao {
    void addAddress(Address address);
//    void addFirm(Firm firm);
    void changeUsername(String name);
    void changeAlias(String alias);
    void changePhoneNumber(String phone);
    void changeEmail(String email);
    void changePassword(String password);
    void showOrders();
//    void addToFavourites(Product product);
//    void addToCart(Product product);
}
