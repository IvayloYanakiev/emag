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
    public void changeReceiverName(Long id, String name) throws AddressException {
        String updateReceiverName = Constants.UPDATE_RECEIVER_NAME_BY_ADDRESS_ID;
        HashMap<String, Object> addressParam = new HashMap<>();
        addressParam.put("id", id);
        addressParam.put("name", name);

        int rowsUpdated = jdbcTemplate.update(updateReceiverName, addressParam);
        if (rowsUpdated == 0) {
            throw new AddressException(Constants.ADDRESS_NOT_FOUND);
        }
    }

    @Override
    public void changeReceiverPhoneNumber(Long id, String phone) throws AddressException {
        String updateReceiverPhone = Constants.UPDATE_RECEIVER_PHONE_BY_ADDRESS_ID;
        HashMap<String, Object> addressParam = new HashMap<>();
        addressParam.put("id", id);
        addressParam.put("phone", phone);

        int rowsUpdated = jdbcTemplate.update(updateReceiverPhone, addressParam);
        if (rowsUpdated == 0) {
            throw new AddressException(Constants.ADDRESS_NOT_FOUND);
        }
    }

    @Override
    public void changeCity(Long id, Long cityId) throws AddressException {
        String updateCityId = Constants.UPDATE_CITY_BY_ADDRESS_ID;
        HashMap<String, Object> addressParam = new HashMap<>();
        addressParam.put("id", id);
        addressParam.put("cityId", cityId);

        int rowsUpdated = jdbcTemplate.update(updateCityId, addressParam);
        if (rowsUpdated == 0) {
            throw new AddressException(Constants.ADDRESS_NOT_FOUND);
        }
    }

    @Override
    public void changeStreet(Long id, String street) throws AddressException {
        String updateStreetInfo = Constants.UPDATE_STREET_INFO_BY_ADDRESS_ID;
        HashMap<String, Object> addressParam = new HashMap<>();
        addressParam.put("id", id);
        addressParam.put("street", street);

        int rowsUpdated = jdbcTemplate.update(updateStreetInfo, addressParam);
        if (rowsUpdated == 0) {
            throw new AddressException(Constants.ADDRESS_NOT_FOUND);
        }
    }

    @Override
    public void changeFloor(Long id, int floor) throws AddressException {
        String updateFloor = Constants.UPDATE_FLOOR_BY_ADDRESS_ID;
        HashMap<String, Object> addressParam = new HashMap<>();
        addressParam.put("id", id);
        addressParam.put("floor", floor);

        int rowsUpdated = jdbcTemplate.update(updateFloor, addressParam);
        if (rowsUpdated == 0) {
            throw new AddressException(Constants.ADDRESS_NOT_FOUND);
        }
    }
}
