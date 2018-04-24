package com.emag.dao;

import com.emag.exceptions.AddressException;

public interface AddressDao {
    void changeReceiverPhoneNumber(Long id, String newPhoneNumber) throws AddressException;
    void changeCity(Long id, int newCityId) throws AddressException;
    void changeStreet(Long id,String newStreetInfo) throws AddressException;
    void changeFloor(Long id, int newFloor) throws AddressException;
}
