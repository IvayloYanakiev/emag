package com.emag.model;

import com.emag.config.Constants;
import com.emag.exceptions.UserException;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

@Component
public class User {


    private Long id;
    private String name;
    private String password;
    private String email;
    private String pictureUrl;
    private String gender;
    private String phone;
    private String type;

    public User() {
    }

    public User(String name, String password, String email) throws UserException {
        setName(name);
        setPassword(password);
        setEmail(email);
    }

    public User(Long id, String name, String email, String gender, String phone) throws UserException {
        setId(id);
        setName(name);
        setEmail(email);
        setGender(gender);
        setPhone(phone);
    }

    public User(Long id, String name, String email, String pictureUrl, String gender, String phone) throws UserException {
        setId(id);
        setName(name);
        setEmail(email);
        setPictureUrl(pictureUrl);
        setGender(gender);
        setPhone(phone);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) throws UserException {
        if (id!=null && id>-1)
            this.id = id;
        else throw new UserException(Constants.INVALID_ID);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) throws UserException {
        if (name != null && name.trim().length() > 0) {
            this.name = name;
        } else throw new UserException(Constants.INVALID_NAME);
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
        } else throw new UserException(Constants.INVALID_EMAIL);
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setPictureUrl(String pictureUrl) throws UserException {
        if (pictureUrl != null && pictureUrl.trim().length() > 0)
            this.pictureUrl = pictureUrl;
        else throw new UserException(Constants.INVALID_PICTURE_URL);
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) throws UserException {
        if (gender != null && gender.trim().length() > 0)
            this.gender = gender;
        else throw new UserException(Constants.INVALID_GENDER);
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) throws UserException {
        if (phone != null && phone.trim().length() == Constants.PHONE_NUMBER_LENGTH && phone.startsWith(Constants.PHONE_NUMBER_PREFIX))
            this.phone = phone;
        else throw new UserException(Constants.INVALID_PHONE);
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", pictureUrl='" + pictureUrl + '\'' +
                ", gender='" + gender + '\'' +
                ", phone='" + phone + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
