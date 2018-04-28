package com.emag.dao;

import com.emag.exceptions.AddressException;
import com.emag.model.Address;

import java.util.LinkedHashSet;

public interface AddressDao {
    void addAddress(Long userId, Address address) throws AddressException;

    LinkedHashSet<Address> getAllAddresses(Long userId);
}
