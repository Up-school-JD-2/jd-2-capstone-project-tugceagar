package io.upschool.capstoneProject.exception.creditcard;

public class InvalidExpirationYearException extends RuntimeException{

    public static final String INVALID_EXPIRATION_YEAR_EXCEPTION = "Expiration year is invalid.";

    public InvalidExpirationYearException(String message){
        super(message);
    }
}
