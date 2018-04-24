package com.emag.dao;

public interface AddressDao {
    void changeReceiverName(String newReceiverName);
    void changeReceiverPhoneNumber(String newPhoneNumber);
    void changeCity(int newCityId);
    void changeStreet(String newStreetInfo);
    void changeFloor(int newFloor);
}
