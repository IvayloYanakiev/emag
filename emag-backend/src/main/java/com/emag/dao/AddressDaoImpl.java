package com.emag.dao;

import com.emag.exceptions.AddressException;
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
        String insertIntoAddresses =
                " insert into addresses(receiver_name,receiver_phone,city,street,floor) values (:receiverName,:receiverPhone,:city,:street,:floor); ";
        String insertIntoUserAddresses = " insert into users_addresses(address_id,user_id) values ((SELECT LAST_INSERT_ID() from addresses group by last_insert_id()),:userId); ";

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
        String getAllCategories = "select a.id as address_id,a.receiver_name,a.receiver_phone,c.id as city_id,c.name as city,a.street,a.floor from users u " +
                " join users_addresses ua " +
                " on u.id=ua.user_id " +
                " join addresses a " +
                " on a.id=ua.address_id " +
                " join cities c on a.city_id=c.id " +
                " where u.id=:userId;";
        HashMap<String, Object> params = new HashMap<>();
        params.put("userId", userId);
        LinkedHashSet<Address> addresses = jdbcTemplate.query(getAllCategories, params, new ResultSetExtractor<LinkedHashSet<Address>>() {

            @Override
            public LinkedHashSet<Address> extractData(ResultSet rs) throws SQLException {
                LinkedHashSet<Address> myAddresses = new LinkedHashSet<Address>();

                while (rs.next()) {
                    try {
                        Long addressId = rs.getLong("address_id");
                        String receiverName = rs.getString("receiver_name");
                        String receiverPhone = rs.getString("receiver_phone");
                        Long cityId = rs.getLong("city_id");
                        String cityName = rs.getString("city");
                        String street = rs.getString("street");
                        Integer floor = rs.getInt("floor");
                        City city = new City(cityId, cityName);
                        Address address = new Address(addressId, receiverName, receiverPhone, city, street, floor);
                        myAddresses.add(address);
                    } catch (AddressException | CityException e) {
                        System.out.println(e.getMessage());
                    }
                }
                return myAddresses;

            }
        });
        return addresses;
    }

    @Override
    public void updateAddress(Address address) throws AddressException {
        String updateAddress = "update addresses set receiver_name=:receiver_name,receiver_phone=:receiver_phone,city_id=:city_id,street=:street,floor=:floor where id=:id";
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
            throw new AddressException("Error updating address", e);
        }
    }




    @Override
    public Address getAddress(Long addressId) {
        String getAddress = "select a.id as address_id,a.receiver_name,a.receiver_phone,c.id as city_id,a.street,a.floor,c.name as city_name " +
                " from addresses a join cities c on a.city_id=c.id where a.id=:addressId";
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
        String updateAddress = "delete from addresses where id=:addressId";
        HashMap<String, Object> params = new HashMap<>();
        params.put("addressId", addressId);
        try {
            jdbcTemplate.update(updateAddress, params);
        } catch (Exception e) {
            throw new AddressException("Error deleting address", e);
        }
    }


}
