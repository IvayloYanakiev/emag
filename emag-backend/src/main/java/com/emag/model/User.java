package com.emag.model;

import com.emag.exceptions.UserException;
import org.springframework.stereotype.Component;

@Component
public class User {

    private Long id;
    private String name;
    private String password;
    private String email;
    private String type;

    public User() {
    }

    public User(String name, String password, String email) throws UserException {
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

    public void setName(String name) throws UserException {
        if (name != null && name.trim().length() > 0) {
            this.name = name;
        } else throw new UserException("Invalid name");
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

    public void setEmail(String email) throws UserException {
        if (email != null && email.trim().length() > 0) {
            this.email = email;
        } else throw new UserException("Invalid email");
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
