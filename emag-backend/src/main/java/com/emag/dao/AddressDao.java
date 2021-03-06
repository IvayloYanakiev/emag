package com.emag.dao;

import com.emag.exception.AddressException;
import com.emag.model.Address;

import java.util.Collection;

public interface AddressDao {
    Long addAddress(Long userId, Address address) throws AddressException;

    Collection getAllAddresses(Long userId) throws AddressException;

    void updateAddress(Address address) throws AddressException;

    Address getAddress(Long addressId) throws AddressException;

    void deleteAddress(Long addressId) throws AddressException;

}
