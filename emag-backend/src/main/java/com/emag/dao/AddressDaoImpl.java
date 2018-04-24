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
    public void changeReceiverPhoneNumber(Long id, String newPhoneNumber) throws AddressException {
        String updateReceiverPhone = Constants.UPDATE_RECEIVER_PHONE_BY_ADDRESS_ID;
        HashMap<String, Object> addressParam = new HashMap<>();
        addressParam.put("id", id);
        addressParam.put("newPhoneNumber", newPhoneNumber);

        int rowsUpdated = jdbcTemplate.update(updateReceiverPhone, addressParam);

        if(rowsUpdated == 0){
            throw new AddressException("Address not found");
        }
    }

//    @Override
//    public void changeCity(Long id, int newCityId) {
//
//    }

//    @Override
//    public void changeStreet(Long id, String newStreetInfo) {
//
//    }
//
//    @Override
//    public void changeFloor(Long id, int newFloor) {
//
//    }
}
