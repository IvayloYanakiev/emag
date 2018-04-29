package com.emag.controller;

import com.emag.config.Constants;
import com.emag.exceptions.AddressException;
import com.emag.model.Address;
import com.emag.model.ResponseEntity;
import com.emag.service.AddressService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashSet;

@RequestMapping("/address")
@RestController
public class AddressController {

    @Autowired
    private AddressService addressService;

    @PostMapping("/addAddress")
    public ResponseEntity addAddress(
            @RequestParam("userId") Long id,
            @RequestParam("receiverName") String receiverName,
            @RequestParam("receiverPhone") String receiverPhone,
            @RequestParam("city") String city,
            @RequestParam("street") String street,
            @RequestParam("floor") int floor) {
        try {
            Address address = new Address(receiverName, receiverPhone, city, street, floor);
            addressService.addAddress(id, address);
        } catch (AddressException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(Constants.SUCCESS, HttpStatus.OK);
    }

    @GetMapping("/getAllAddresses")
    public ResponseEntity getAllAddresses(@RequestParam("userId") Long id) {

        LinkedHashSet<Address> addresses = addressService.getAllAddresses(id);
        Gson gson = new Gson();
        String json = gson.toJson(addresses);

        return new ResponseEntity(json, HttpStatus.OK);
    }

    @PutMapping("/updateAddress")
    public ResponseEntity updateAddress( @RequestParam("addressId") Long addressId,
                                         @RequestParam("receiverName") String receiverName,
                                         @RequestParam("receiverPhone") String receiverPhone,
                                         @RequestParam("cityId") Long cityId,
                                         @RequestParam("street") String street,
                                         @RequestParam("floor") int floor) {
        try {
            City city = new City();
            city.setId(cityId);
            Address address = new Address(addressId,receiverName,receiverPhone,city,street,floor);
            addressService.updateAddress(address);
        } catch (CityException|AddressException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

        return null;
    }

    @GetMapping("/getAddress")
    public ResponseEntity getAddress( @RequestParam("addressId") Long addressId) {
        Address address = addressService.getAddress(addressId);
        Gson gson = new Gson();
        String json = gson.toJson(address);
        return new ResponseEntity(json, HttpStatus.OK);
    }

    @DeleteMapping("/deleteAddress")
    public ResponseEntity deleteAddress( @RequestParam("addressId") Long addressId) {

        try {
            addressService.deleteAddress(addressId);
        } catch (AddressException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity("OK", HttpStatus.OK);
    }


}
