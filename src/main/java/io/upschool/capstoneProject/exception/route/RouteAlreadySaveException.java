package io.upschool.capstoneProject.exception.route;

public class RouteAlreadySaveException extends RuntimeException{

    public static final String ALREADY_SAVED_EXCEPTION = "Route already saved.";
    public RouteAlreadySaveException(String message){
        super(message);
    }
}
