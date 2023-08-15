package io.upschool.capstoneProject.exception.passenger;

public class InvalidTcNumberException extends RuntimeException {

    public static final String INVALID_TC_NUMBER = "Tc Number is invalid.";

    public InvalidTcNumberException(String message) {
        super(message);
    }
}
