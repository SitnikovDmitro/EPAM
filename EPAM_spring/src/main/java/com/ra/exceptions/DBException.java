package com.ra.exceptions;

public class DBException extends Exception{
    public DBException() {}

    public DBException(String message) {
        super(message);
    }

    public DBException(Throwable cause) {
        super(cause);
    }
}
