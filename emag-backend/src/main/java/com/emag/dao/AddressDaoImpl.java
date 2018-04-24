package com.emag.dao;

import com.emag.config.Constants;
import com.emag.exceptions.AddressException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import java.util.HashMap;

@Repository
public class AddressDaoImpl implements AddressDao {

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public void changeReceiverPhoneNumber(Long id, String phone) throws AddressException {
        String updateReceiverPhone = Constants.UPDATE_RECEIVER_PHONE_BY_ADDRESS_ID;
        HashMap<String, Object> addressParam = new HashMap<>();
        addressParam.put("id", id);
        addressParam.put("phone", phone);

        int rowsUpdated = jdbcTemplate.update(updateReceiverPhone, addressParam);
        if(rowsUpdated == 0){
            throw new AddressException("Address not found");
        }
    }

    @Override
    public void changeCity(Long id, int city) throws AddressException {
        String updateCity = Constants.UPDATE_CITY_BY_ADDRESS_ID;
        HashMap<String, Object> addressParam = new HashMap<>();
        addressParam.put("id", id);
        addressParam.put("city", city);

        int rowsUpdated = jdbcTemplate.update(updateCity, addressParam);
        if(rowsUpdated == 0){
            throw new AddressException("Address not found");
        }
    }

    @Override
    public void changeStreet(Long id, String street) throws AddressException {
        String updateStreetInfo = Constants.UPDATE_STREET_INFO_BY_ADDRESS_ID;
        HashMap<String, Object> addressParam = new HashMap<>();
        addressParam.put("id", id);
        addressParam.put("street", street);

        int rowsUpdated = jdbcTemplate.update(updateStreetInfo, addressParam);
        if(rowsUpdated == 0){
            throw new AddressException("Address not found");
        }
    }

    @Override
    public void changeFloor(Long id, int floor) throws AddressException {
        String updateFloor = Constants.UPDATE_FLOOR_BY_ADDRESS_ID;
        HashMap<String, Object> addressParam = new HashMap<>();
        addressParam.put("id", id);
        addressParam.put("floor", floor);

        int rowsUpdated = jdbcTemplate.update(updateFloor, addressParam);
        if(rowsUpdated == 0){
            throw new AddressException("Address not found");
        }
    }
}
