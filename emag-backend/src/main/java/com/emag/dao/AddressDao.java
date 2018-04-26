package com.emag.dao;

import com.emag.exceptions.AddressException;

public interface AddressDao {
    void changeReceiverName(Long id, String name) throws AddressException;
    void changeReceiverPhoneNumber(Long id, String newPhoneNumber) throws AddressException;
    void changeCity(Long id, Long newCityId) throws AddressException;
    void changeStreet(Long id,String newStreetInfo) throws AddressException;
    void changeFloor(Long id, int newFloor) throws AddressException;
}
