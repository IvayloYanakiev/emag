package com.emag.service;

import com.emag.exception.EmailException;
import org.springframework.stereotype.Service;

@Service
public interface SendEmailService {
    void sendEmail(String email) throws EmailException;
    void informUserForOrder(String email, Long addressId, String payingMethod, Double totalSum, String shoppingCart) throws EmailException;
}
