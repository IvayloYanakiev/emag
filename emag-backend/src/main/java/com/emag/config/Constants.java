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
    public static final String ADDRESS_NOT_FOUND = "Address not found";
    public static final int PHONE_NUMBER_LENGTH = 10;
    public static final String PHONE_NUMBER_PREFIX = "08";
    public static final String INVALID_PHONE = "Invalid phone";
    public static final String INVALID_GENDER = "Invalid gender";
    public static final String INVALID_PICTURE_URL = "Invalid picture url";
    public static final String INVALID_NAME = "Invalid name";
    public static final String INVALID_ID = "Invalid id";
    public static final String INVALID_STREET_VALUE = "Invalid street value";
    public static final String INVALID_FLOOR_VALUE = "Invalid floor value";
    public static final String INVALID_CITY_ID_VALUE = "Invalid city ID value";
    public static final String INVALID_PHONE_NUMBER_VALUE = "Invalid phone number value";
    public static final String INVALID_RECEIVER_NAME = "Invalid receiver name";
    public static final String INVALID_ID_VALUE = "Invalid adress id";
}
