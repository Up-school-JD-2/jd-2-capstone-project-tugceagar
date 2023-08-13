package io.upschool.capstoneProject.exception;

public class AlreadySavedException extends RuntimeException {

    public static final String ALREADY_SAVED_EXCEPTION = "already saved.";
    public AlreadySavedException(String message) {
        super(message);
    }
}
