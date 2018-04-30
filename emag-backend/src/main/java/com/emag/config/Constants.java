package com.emag.config;

import java.util.regex.Pattern;

public class Constants {
    public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
    public static final String SUCCESS = "Success";
    public static final int PHONE_NUMBER_LENGTH = 10;
    public static final String PHONE_NUMBER_PREFIX = "08";
    public static final int MIN_CITY_NAME_LENGTH = 3;
    public static final int MIN_PASSWORD_LENGTH = 5;
    public static final int MIN_EMAIL_LENGTH = 5;
    public static final String SENDER_EMAIL = "emag.7377@gmail.com";
    public static final String EMAIL_SUBJECT = "Subscription for eMAG.bg";
    public static final String EMAIL_TEXT = "Congratulations!\nYou have successfully subscribed to eMAG.bg";
}
