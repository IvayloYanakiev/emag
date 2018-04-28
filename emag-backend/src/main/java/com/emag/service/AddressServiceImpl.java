package com.emag.service;

import com.emag.dao.AddressDao;
import com.emag.exceptions.AddressException;
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
    public LinkedHashSet<Address> getAllAddresses(Long userId) {
       return addressDao.getAllAddresses(userId);
    }
}
