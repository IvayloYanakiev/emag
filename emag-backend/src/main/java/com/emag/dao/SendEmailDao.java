package com.emag.dao;

import com.emag.exception.EmailException;

public interface SendEmailDao {
    void sendEmail(String email) throws EmailException;
}
