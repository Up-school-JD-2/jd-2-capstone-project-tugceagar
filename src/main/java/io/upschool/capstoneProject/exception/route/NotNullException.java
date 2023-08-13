package io.upschool.capstoneProject.exception.route;

public class NotNullException extends RuntimeException {

    public static final String NOT_NULL_EXCEPTION = "should be not null.";
    public NotNullException(String message){
        super(message);
    }
}

