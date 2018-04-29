package com.emag.dao;

<<<<<<< HEAD
import com.emag.exceptions.AddressException;
=======

import com.emag.config.ErrorMessages;
import com.emag.config.SqlConstants;
import com.emag.exceptions.AddressException;
import com.emag.exceptions.CityException;
>>>>>>> 9b4f12be27a379934557043ebe5bc20918f62ddd
import com.emag.model.Address;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedHashSet;

@Repository
public class AddressDaoImpl implements AddressDao {

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    DataSourceTransactionManager transactionManager;

    @Override
    public void addAddress(Long userId, Address address) throws AddressException {

        TransactionTemplate tx = new TransactionTemplate(transactionManager);
<<<<<<< HEAD
        String insertIntoAddresses =
                " insert into addresses(receiver_name,receiver_phone,city,street,floor) values (:receiverName,:receiverPhone,:city,:street,:floor); ";
        String insertIntoUserAddresses = " insert into users_addresses(address_id,user_id) values ((SELECT LAST_INSERT_ID() from addresses group by last_insert_id()),:userId); ";
=======
        String insertIntoAddresses = SqlConstants.INSERT_INTO_ADDRESSES;
        String insertIntoUserAddresses = SqlConstants.INSERT_INTO_USER_ADDRESSES;
>>>>>>> 9b4f12be27a379934557043ebe5bc20918f62ddd

        HashMap<String, Object> addressParam = new HashMap<>();
        addressParam.put("userId", userId);
        addressParam.put("receiverName", address.getReceiverName());
        addressParam.put("receiverPhone", address.getReceiverPhone());
        addressParam.put("city", address.getCity());
        addressParam.put("street", address.getStreet());
        addressParam.put("floor", address.getFloor());

        tx.execute(new TransactionCallbackWithoutResult() {
            protected void doInTransactionWithoutResult(TransactionStatus status) {
                try {
                    jdbcTemplate.update(insertIntoAddresses, addressParam);
                    jdbcTemplate.update(insertIntoUserAddresses, addressParam);
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                    status.setRollbackOnly();
                }
            }
        });

    }

    @Override
    public LinkedHashSet<Address> getAllAddresses(Long userId) {
        String getAllAddresses = SqlConstants.GET_ALL_ADDRESSES;
        HashMap<String, Object> params = new HashMap<>();
        params.put("userId", userId);
        LinkedHashSet<Address> addresses = jdbcTemplate.query(getAllAddresses, params, new ResultSetExtractor<LinkedHashSet<Address>>(){

            @Override
            public LinkedHashSet<Address> extractData(ResultSet rs) throws SQLException {
                LinkedHashSet<Address> myAddresses = new LinkedHashSet<Address>();

                while (rs.next()) {
                        Long addressId = rs.getLong("address_id");
                        String receiverName = rs.getString("receiver_name");
                        String receiverPhone = rs.getString("receiver_phone");
                        Long cityId = rs.getLong("city_id");
                        String cityName = rs.getString("city");
                        String street = rs.getString("street");
                        Integer floor = rs.getInt("floor");

                        try {
                            City city = new City(cityId, cityName);
                            Address address = new Address(addressId, receiverName, receiverPhone, city, street, floor);
                            myAddresses.add(address);
                        }catch (AddressException | CityException e){
                            throw new SQLException(e.getMessage(), e);
                        }
                }
                return myAddresses;

            }
        });
        return addresses;
    }

    @Override
    public void updateAddress(Address address) throws AddressException {
        String updateAddress = SqlConstants.UPDATE_ADDRESS;
        HashMap<String, Object> params = new HashMap<>();
        params.put("id", address.getId());
        params.put("receiver_name", address.getReceiverName());
        params.put("receiver_phone", address.getReceiverPhone());
        params.put("city_id", address.getCity().getId());
        params.put("street", address.getStreet());
        params.put("floor", address.getFloor());
        try {
            jdbcTemplate.update(updateAddress, params);
        } catch (Exception e) {
            throw new AddressException(ErrorMessages.ERROR_UPDATING_ADDRESS, e);
        }
    }




    @Override
    public Address getAddress(Long addressId) {
        String getAddress = SqlConstants.GET_ADDRESS_BY_ID;
        HashMap<String, Object> params = new HashMap<>();
        params.put("addressId", addressId);

        Address addressById = jdbcTemplate.query(getAddress, params, new ResultSetExtractor<Address>() {

            @Override
            public Address extractData(ResultSet rs) throws SQLException {
               Address address = new Address();
                if (rs.next()) {
                    try {
                        address.setId(rs.getLong("address_id"));
                        address.setReceiverName(rs.getString("receiver_name"));
                        address.setReceiverPhone(rs.getString("receiver_phone"));
                        address.setStreet(rs.getString("street"));
                        address.setFloor(rs.getInt("floor"));
                        Long cityId = rs.getLong("city_id");
                        String cityName = rs.getString("city_name");
                        City city = new City(cityId,cityName);
                        address.setCity(city);
                    } catch (AddressException|CityException e) {
                        e.printStackTrace();
                    }
                }
                return address;
            }
        });


        return addressById;
    }

    @Override
    public void deleteAddress(Long addressId) throws AddressException {
        String deleteAddress = SqlConstants.DELETE_ADDRESS;
        HashMap<String, Object> params = new HashMap<>();
        params.put("addressId", addressId);
        try {
            jdbcTemplate.update(deleteAddress, params);
        } catch (Exception e) {
            throw new AddressException(ErrorMessages.ERROR_DELETING_ADDRESS, e);
        }
    }


}
