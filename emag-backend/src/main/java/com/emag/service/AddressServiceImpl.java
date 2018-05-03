package com.emag.service;

import com.emag.dao.AddressDao;
import com.emag.exception.AddressException;
import com.emag.model.Address;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedHashSet;

@Service
public class AddressServiceImpl implements AddressService {

    @Autowired
    AddressDao addressDao;


    @Override
    public void addAddress(Long userId, Address address) throws AddressException {
        addressDao.addAddress(userId, address);
    }

    @Override
    public LinkedHashSet<Address> getAllAddresses(Long userId) throws AddressException {
        return addressDao.getAllAddresses(userId);
    }

    @Override
    public void updateAddress(Address address) throws AddressException {
        addressDao.updateAddress(address);
    }

    @Override
    public Address getAddress(Long addressId) throws AddressException {
        return addressDao.getAddress(addressId);
    }

    @Override
    public void deleteAddress(Long addressId) throws AddressException {
        addressDao.deleteAddress(addressId);
    }
}
