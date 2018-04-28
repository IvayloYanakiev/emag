package com.emag.exceptions;

public class CityException extends Exception{
    public CityException() {
    }

    public CityException(String message) {
        super(message);
    }

    public CityException(String message, Throwable cause) {
        super(message, cause);
    }

    public CityException(Throwable cause) {
        super(cause);
    }

    public CityException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
