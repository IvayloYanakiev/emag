package com.emag.service;

import com.emag.exceptions.AddressException;
import com.emag.model.Address;

import java.util.LinkedHashSet;

public interface AddressService  {

    void addAddress(Long userId, Address address) throws AddressException;

    LinkedHashSet<Address> getAllAddresses(Long id);

    void updateAddress(Address address) throws AddressException;
}
