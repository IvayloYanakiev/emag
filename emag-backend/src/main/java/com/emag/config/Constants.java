package com.emag.config;

import java.util.regex.Pattern;

public class Constants {
    public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
    public static final String ERROR = "Error";
    public static final String ACC_PROBLEM = "Theres a problem with your account";
    public static final String INVALID_EMAIL = "Invalid email";
    public static final String CHECK_YOUR_PASSWORD = "Please check your confirm password";
    public static final String PASSWORDS_NOT_THE_SAME = "Passwords are not the same";
    public static final String USER_ALREADY_EXISTS = "User already exists";
    public static final String NO_SUCH_USER = "No such user";
    public static final String WRONG_USERNAME_OR_PASSWORD = "Wrong username or password";
    public static final String SUCCESS = "Success";
    public static final String FIND_USER_BY_ID = "select * from users where id=:id";
    public static final String FIND_USER_BY_EMAIL = "select * from users where email=:email";
    public static final String ADD_USER = "insert into users(name,email,password) values(:name,:email,sha1(:password))";
    public static final String SELECT_USER_BY_EMAIL_AND_PASS = "select * from users where email=:email and password = sha1(:password);";
    public static final String GET_ALL_CATEGORIES = "select main.id as id,main.name as main_name,middle.id as middle_id,middle.name as middle_name from main_type as main join middle_type as middle on main.id = middle.main_type_id";
    public static final String UPDATE_RECEIVER_PHONE_BY_ADDRESS_ID = "update adresses set reciever_phone=:phone where id=:id";
    public static final String UPDATE_STREET_INFO_BY_ADDRESS_ID = "update adresses set street=:street where id=:id";
    public static final String UPDATE_FLOOR_BY_ADDRESS_ID = "update adresses set floor=:floor where id=:id";
    public static final String UPDATE_CITY_BY_ADDRESS_ID ="update adresses set city_id=:city where id=:id";







}
