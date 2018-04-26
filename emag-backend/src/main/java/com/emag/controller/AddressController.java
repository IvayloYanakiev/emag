package com.emag.controller;

import com.emag.config.Constants;
import com.emag.exceptions.AddressException;
import com.emag.model.ResponseEntity;
import com.emag.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/address")
@RestController
public class AddressController {

    @Autowired
    AddressService addressService;

    @RequestMapping("/changeReceiverPhoneNumber")
    public ResponseEntity changeReceiverPhoneNumber(@RequestParam("id") Long id, @RequestParam("receiverPhone") String receiverPhone) {
        try {
            addressService.changeReceiverPhoneNumber(id, receiverPhone);
        } catch (AddressException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(Constants.SUCCESS, HttpStatus.OK);
    }

    @RequestMapping("/changeReceiverName")
    public ResponseEntity changeReceiverName(@RequestParam("id") Long id, @RequestParam("receiverName") String receiverName) {
        try {
            addressService.changeReceiverName(id, receiverName);
        } catch (AddressException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(Constants.SUCCESS, HttpStatus.OK);
    }

    @RequestMapping("/changeCity")
    public ResponseEntity changeCity(@RequestParam("id") Long id, @RequestParam("city") Long cityId) {
        try {
            addressService.changeCity(id, cityId);
        } catch (AddressException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(Constants.SUCCESS, HttpStatus.OK);
    }

    @RequestMapping("/changeStreet")
    public ResponseEntity changeStreet(@RequestParam("id") Long id, @RequestParam("streetInfo") String newStreetInfo) {
        try {
            addressService.changeStreet(id, newStreetInfo);
        } catch (AddressException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(Constants.SUCCESS, HttpStatus.OK);
    }

    @RequestMapping("/changeFloor")
    public ResponseEntity changeFloor(@RequestParam("id") Long id, @RequestParam("floor") int newFloor) {
        try {
            addressService.changeFloor(id, newFloor);
        } catch (AddressException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(Constants.SUCCESS, HttpStatus.OK);
    }
}
