package io.upschool.capstoneProject.exception.creditcard;

public class InvalidCcvException extends RuntimeException {

    public static final String INVALID_CCV_NUMBER_EXCEPTION = "Ccv number is invalid.";

    public InvalidCcvException(String message){
        super(message);
    }
}
