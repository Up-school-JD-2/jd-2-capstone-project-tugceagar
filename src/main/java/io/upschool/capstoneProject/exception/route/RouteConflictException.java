package io.upschool.capstoneProject.exception.route;

public class RouteConflictException extends RuntimeException {

    public static final String ROUTE_CONFLICT_EXCEPTION = "Departure airport and arrival airport can not be the same.";

    public RouteConflictException(String message) {
        super(message);
    }
}
