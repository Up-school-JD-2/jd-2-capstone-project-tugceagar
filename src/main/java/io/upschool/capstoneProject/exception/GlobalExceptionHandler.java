package io.upschool.capstoneProject.exception;

import io.upschool.capstoneProject.dto.BaseResponse;
import io.upschool.capstoneProject.dto.CreditCard.CreditCardResponse;
import io.upschool.capstoneProject.dto.airline.AirlineSaveResponse;
import io.upschool.capstoneProject.dto.flight.FlightSaveResponse;
import io.upschool.capstoneProject.dto.passenger.PassengerResponse;
import io.upschool.capstoneProject.dto.route.RouteSaveResponse;
import io.upschool.capstoneProject.exception.creditcard.InvalidCardNumberException;
import io.upschool.capstoneProject.exception.creditcard.InvalidCcvException;
import io.upschool.capstoneProject.exception.creditcard.InvalidExpirationMonthException;
import io.upschool.capstoneProject.exception.creditcard.InvalidExpirationYearException;
import io.upschool.capstoneProject.exception.flight.FlightCapacityIsFullException;
import io.upschool.capstoneProject.exception.passenger.InvalidTcNumberException;
import io.upschool.capstoneProject.exception.route.NotNullException;
import io.upschool.capstoneProject.exception.route.RouteAlreadySaveException;
import io.upschool.capstoneProject.exception.route.RouteConflictException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.text.MessageFormat;
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex,
                                                                   HttpHeaders headers,
                                                                   HttpStatusCode status,
                                                                   WebRequest request) {

        final var errorMessage =
                MessageFormat.format("No handler found for {0} {1}", ex.getHttpMethod(), ex.getRequestURL());
        var response = BaseResponse.<AirlineSaveResponse>builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .isSuccess(true)
                .build();
        return ResponseEntity.badRequest().body(response);
    }


//    @ExceptionHandler(AirlineAlreadySaveException.class)
//    public ResponseEntity<Object> handleAll(final AirlineAlreadySaveException exception) {
//        System.out.println("Error Occurred Exception:" + exception.getMessage());
//
//        // return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//        var response = BaseResponse.<AirlineSaveResponse>builder()
//                .status(HttpStatus.BAD_REQUEST.value())
//                .message(exception.getMessage())
//                .isSuccess(false)
//                .build();
//        return ResponseEntity.badRequest().body(response);
//    }
//
//    @ExceptionHandler(AirlineNotFoundException.class)
//    public ResponseEntity<Object> handleAll(final AirlineNotFoundException exception) {
//        System.out.println("Error Occurred Exception:" + exception.getMessage());
//
//        // return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//        var response = BaseResponse.<AirlineSaveResponse>builder()
//                .status(HttpStatus.BAD_REQUEST.value())
//                .message(exception.getMessage())
//                .isSuccess(false)
//                .build();
//        return ResponseEntity.badRequest().body(response);
//    }
//
//    @ExceptionHandler(AirportNotFoundException.class)
//    public ResponseEntity<Object> handleAll(final AirportNotFoundException exception) {
//        System.out.println("Error Occurred Exception:" + exception.getMessage());
//
//        // return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//        var response = BaseResponse.<AirlineSaveResponse>builder()
//                .status(HttpStatus.BAD_REQUEST.value())
//                .message(exception.getMessage())
//                .isSuccess(false)
//                .build();
//        return ResponseEntity.badRequest().body(response);
//    }

    @ExceptionHandler(InvalidCardNumberException.class)
    public ResponseEntity<Object> handleAll(final InvalidCardNumberException exception) {
        System.out.println("Error Occurred Exception:" + exception.getMessage());

        var response = BaseResponse.<CreditCardResponse>builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .message(exception.getMessage())
                .isSuccess(false)
                .build();
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(FlightCapacityIsFullException.class)
    public ResponseEntity<Object> handleAll(final FlightCapacityIsFullException exception) {
        System.out.println("Error Occurred Exception:" + exception.getMessage());

        var response = BaseResponse.<FlightSaveResponse>builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .message(exception.getMessage())
                .isSuccess(false)
                .build();
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(InvalidCcvException.class)
    public ResponseEntity<Object> handleAll(final InvalidCcvException exception) {
        System.out.println("Error Occurred Exception:" + exception.getMessage());

        var response = BaseResponse.<CreditCardResponse>builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .message(exception.getMessage())
                .isSuccess(false)
                .build();
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(InvalidExpirationMonthException.class)
    public ResponseEntity<Object> handleAll(final InvalidExpirationMonthException exception) {
        System.out.println("Error Occurred Exception:" + exception.getMessage());

        var response = BaseResponse.<CreditCardResponse>builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .message(exception.getMessage())
                .isSuccess(false)
                .build();
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(InvalidExpirationYearException.class)
    public ResponseEntity<Object> handleAll(final InvalidExpirationYearException exception) {
        System.out.println("Error Occurred Exception:" + exception.getMessage());

        var response = BaseResponse.<CreditCardResponse>builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .message(exception.getMessage())
                .isSuccess(false)
                .build();
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(InvalidTcNumberException.class)
    public ResponseEntity<Object> handleAll(final InvalidTcNumberException exception) {
        System.out.println("Error Occurred Exception:" + exception.getMessage());

        var response = BaseResponse.<PassengerResponse>builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .message(exception.getMessage())
                .isSuccess(false)
                .build();
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(RouteConflictException.class)
    public ResponseEntity<Object> handleAll(final RouteConflictException exception) {
        System.out.println("Error Occurred Exception:" + exception.getMessage());

        // return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        var response = BaseResponse.<RouteSaveResponse>builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .message(exception.getMessage())
                .isSuccess(false)
                .build();
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(RouteAlreadySaveException.class)
    public ResponseEntity<Object> handleAll(final RouteAlreadySaveException exception) {
        System.out.println("Error Occurred Exception:" + exception.getMessage());

        var response = BaseResponse.<RouteSaveResponse>builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .message(exception.getMessage())
                .isSuccess(false)
                .build();
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(AlreadySavedException.class)
    public ResponseEntity<Object> handleAll(final AlreadySavedException exception) {
        System.out.println("Error Occurred Exception:" + exception.getMessage());

        var response = BaseResponse.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .message(exception.getMessage())
                .isSuccess(false)
                .build();
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Object> handleAll(final NotFoundException exception) {
        System.out.println("Error Occurred Exception:" + exception.getMessage());

        var response = BaseResponse.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .message(exception.getMessage())
                .isSuccess(false)
                .build();
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(NotNullException.class)
    public ResponseEntity<Object> handleNotNullException(final NotNullException exception) {
        System.out.println("Error Occurred Exception:" + exception.getMessage());

        var response = BaseResponse.<RouteSaveResponse>builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .message(exception.getMessage())
                .isSuccess(false)
                .build();
        return ResponseEntity.badRequest().body(response);
    }




}
