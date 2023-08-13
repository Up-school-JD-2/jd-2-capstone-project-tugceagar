package io.upschool.capstoneProject.exception.creditcard;

public class InvalidExpirationMonthException extends RuntimeException{

    public static final String INVALID_EXPIRATION_MONTH_EXCEPTION = "Invalid expiration month.";

    public InvalidExpirationMonthException(String message){
        super(message);
    }
}
