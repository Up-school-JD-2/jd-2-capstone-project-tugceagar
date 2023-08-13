package io.upschool.capstoneProject.exception.flight;

public class FlightCapacityIsFullException extends RuntimeException{
    public static final String CAPACITY_IS_FULL_EXCEPTION = "This flight capacity is full.";

    public FlightCapacityIsFullException(String message){
        super(message);
    }
}
