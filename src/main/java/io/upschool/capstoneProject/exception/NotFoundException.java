package io.upschool.capstoneProject.exception;

public class NotFoundException extends RuntimeException {

    public static final String DATA_NOT_FOUND_EXCEPTION = "not found";

    public NotFoundException(String message) {
        super(message);
    }
}
