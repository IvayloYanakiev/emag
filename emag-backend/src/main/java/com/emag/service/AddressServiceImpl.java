package com.emag.service;

import com.emag.dao.AddressDao;
import com.emag.exceptions.AddressException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AddressServiceImpl implements AddressService {

    @Autowired
    AddressDao addressDao;

    @Override
    public void changeReceiverPhoneNumber(Long id, String newPhoneNumber) throws AddressException {
        addressDao.changeReceiverPhoneNumber(id, newPhoneNumber);
    }

    @Override
    public void changeReceiverName(Long id, String name) throws AddressException {
        addressDao.changeReceiverName(id, name);
    }

    @Override
    public void changeCity(Long id, Long newCityId) throws AddressException {
        addressDao.changeCity(id, newCityId);
    }

    @Override
    public void changeStreet(Long id, String newStreetInfo) throws AddressException {
        addressDao.changeStreet(id, newStreetInfo);
    }

    @Override
    public void changeFloor(Long id, int newFloor) throws AddressException {
        addressDao.changeFloor(id, newFloor);
    }
}
