package com.emag.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class AddressDaoImpl implements AddressDao {

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public void changeReceiverName(String newReceiverName) {

    }

    @Override
    public void changeReceiverPhoneNumber(String newPhoneNumber) {

    }

    @Override
    public void changeCity(int newCityId) {

    }

    @Override
    public void changeStreet(String newStreetInfo) {

    }

    @Override
    public void changeFloor(int newFloor) {

    }
}
