package com.emag.model;

import com.emag.exceptions.AddressException;

public class Address {
    private int id;
    private String receiverName;
    private String receiverPhone;
    private int cityId;
    private String street;
    private int floor;

    public Address(){
    }

    public Address(String receiverName, String receiverPhone, int cityId, String street, int floor) throws AddressException {
        this.setReceiverName(receiverName);
        this.setReceiverPhone(receiverPhone);
        this.setCityId(cityId);
        this.setStreet(street);
        this.setFloor(floor);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) throws AddressException {
        if(receiverName != null && receiverName.trim().length() > 0){
            this.receiverName = receiverName;
        } else {
            throw new AddressException("Invalid receiver name");
        }
    }

    public String getReceiverPhone() {
        return receiverPhone;
    }

    public void setReceiverPhone(String receiverPhone) throws AddressException {
        if(receiverPhone != null && receiverPhone.trim().length() > 0 ) {
            this.receiverPhone = receiverPhone;
        } else {
            throw new AddressException("Invalid phone number");
        }
    }

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) throws AddressException {
        if(street != null && street.trim().length() > 0){
            this.street = street;
        } else {
            throw new AddressException("Invalid street");
        }
    }

    public int getFloor() {
        return floor;
    }

    public void setFloor(int floor) throws AddressException {
        if(floor >= 0) {
            this.floor = floor;
        } else {
            throw new AddressException("Invalid floor value");
        }
    }
}
