package com.kodilla.hibernate.manytomany.facade;

public class ValidationException extends Exception {
    public static String VALIDATE_ERROR ="At least three letters are required.";

    public ValidationException(String message) {
        super(message);
    }
}
