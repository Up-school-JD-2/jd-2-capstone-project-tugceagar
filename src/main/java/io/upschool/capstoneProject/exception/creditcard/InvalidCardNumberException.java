package io.upschool.capstoneProject.exception.creditcard;

public class InvalidCardNumberException extends RuntimeException{

    public static final String INVALID_CARD_NUMBER_EXCEPTION = "Card number is invalid.";

    public InvalidCardNumberException(String message){
        super(message);
    }

}
