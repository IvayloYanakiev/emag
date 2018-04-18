package com.emag.model;

import com.emag.exceptions.AccountException;
import org.springframework.stereotype.Component;

@Component
public class Account {

    private Long id;
    private String name;
    private String password;
    private String email;
    private String type;

    public Account() {
    }

    public Account(String name, String password, String email) throws AccountException {
        setName(name);
        setPassword(password);
        setEmail(email);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) throws AccountException {
        if (name != null && name.trim().length() > 0) {
            this.name = name;
        } else throw new AccountException("Invalid name");
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) throws AccountException {
        if (email != null && email.trim().length() > 0) {
            this.email = email;
        } else throw new AccountException("Invalid email");
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
