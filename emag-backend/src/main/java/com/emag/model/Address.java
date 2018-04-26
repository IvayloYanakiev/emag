package com.emag.model;

import com.emag.config.Constants;
import com.emag.exceptions.AddressException;
import org.apache.tomcat.util.bcel.classfile.Constant;

public class Address {
    private Long id;
    private String receiverName;
    private String receiverPhone;
    private Long cityId;
    private String street;
    private int floor;

    public Address() {
    }

    public Address(String receiverName, String receiverPhone, Long cityId, String street, int floor) throws AddressException {
        this.setReceiverName(receiverName);
        this.setReceiverPhone(receiverPhone);
        this.setCityId(cityId);
        this.setStreet(street);
        this.setFloor(floor);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) throws AddressException {
        if (id > -1) {
            this.id = id;
        } else {
            throw new AddressException("Invalid id value");
        }
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) throws AddressException {
        if (receiverName != null && receiverName.trim().length() > 0) {
            this.receiverName = receiverName;
        } else {
            throw new AddressException("Invalid receiver name");
        }
    }

    public String getReceiverPhone() {
        return receiverPhone;
    }

    public void setReceiverPhone(String receiverPhone) throws AddressException {
        if (receiverPhone != null && receiverPhone.trim().length() == Constants.PHONE_NUMBER_LENGTH && receiverPhone.startsWith(Constants.PHONE_NUMBER_PREFIX)) {
            this.receiverPhone = receiverPhone;
        } else {
            throw new AddressException("Invalid phone number value");
        }
    }

    public Long getCityId() {
        return cityId;
    }

    public void setCityId(Long cityId) throws AddressException {
        if (cityId > -1) {
            this.cityId = cityId;
        } else {
            throw new AddressException("Invalid city ID value");
        }
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) throws AddressException {
        if (street != null && street.trim().length() > 0) {
            this.street = street;
        } else {
            throw new AddressException("Invalid street value");
        }
    }

    public int getFloor() {
        return floor;
    }

    public void setFloor(int floor) throws AddressException {
        if (floor >= 0) {
            this.floor = floor;
        } else {
            throw new AddressException("Invalid floor value");
        }
    }
}
