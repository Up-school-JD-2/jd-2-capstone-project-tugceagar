package io.upschool.capstoneProject.service;

import io.upschool.capstoneProject.dto.CreditCard.CreditCardRequest;
import io.upschool.capstoneProject.entity.*;
import io.upschool.capstoneProject.exception.creditcard.InvalidCardNumberException;
import io.upschool.capstoneProject.exception.creditcard.InvalidCcvException;
import io.upschool.capstoneProject.exception.creditcard.InvalidExpirationMonthException;
import io.upschool.capstoneProject.exception.creditcard.InvalidExpirationYearException;
import org.springframework.stereotype.Service;

import java.util.Calendar;

@Service

public class CreditCardService {


    public CreditCard save(CreditCardRequest card) {
        validateCardNumber(card);
        validateCcvNumber(card);
        validateExpirationYear(card);
        validateExpirationMonth(card);

        return requestToEntity(card);
    }


    public String maskCardNumber(String cardNumber) {
        String cleanedNumber = cardNumber.replaceAll("[^0-9]", "");
        StringBuilder maskedNumber = new StringBuilder(cleanedNumber.substring(0, 6));
        int middleDigitsCount = cleanedNumber.length() - 10;

        if (middleDigitsCount > 0) {
            maskedNumber.append("*".repeat(middleDigitsCount));
        }

        maskedNumber.append(cleanedNumber.substring(cleanedNumber.length() - 4));


        return maskedNumber.toString();
    }


    private CreditCard requestToEntity(CreditCardRequest request) {
        return CreditCard.builder()
                .cardNumber(request.getCardNumber())
                .ccv(request.getCcv())
                .expirationMonth(request.getExpirationMonth())
                .expirationYear(request.getExpirationYear())
                .build();
    }

    private void validateCardNumber(CreditCardRequest request) {
        String cardNumber = request.getCardNumber();
        if (!cardNumber.matches("^[0-9]{6}\\*{6}[0-9]{4}$")) {
            throw new InvalidCardNumberException(InvalidCardNumberException.INVALID_CARD_NUMBER_EXCEPTION);
        }
    }

    private void validateCcvNumber(CreditCardRequest request) {
        String ccvNumber = String.valueOf(request.getCcv());

        if (ccvNumber.matches(".*[a-zA-Z].*") || !ccvNumber.matches("\\d{3}")) {
            throw new InvalidCcvException(InvalidCcvException.INVALID_CCV_NUMBER_EXCEPTION);
        }
    }

    private void validateExpirationYear(CreditCardRequest request) {
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);

        String expirationYearStr = request.getExpirationYear();

        if (!expirationYearStr.matches("\\d{4}") || expirationYearStr.matches(".*[a-zA-Z].*")) {
            throw new InvalidExpirationYearException(InvalidExpirationYearException.INVALID_EXPIRATION_YEAR_EXCEPTION);
        }

        int expirationYear = Integer.parseInt(expirationYearStr);

        if (expirationYear < currentYear) {
            throw new InvalidExpirationYearException(InvalidExpirationYearException.INVALID_EXPIRATION_YEAR_EXCEPTION);
        }
    }

    private void validateExpirationMonth(CreditCardRequest request) {
        String expirationYearStr = request.getExpirationYear();
        String expirationMonthStr = request.getExpirationMonth();

        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        int currentMonth = Calendar.getInstance().get(Calendar.MONTH) + 1;

        if (!expirationMonthStr.matches("\\d{1,2}") ||
                Integer.parseInt(expirationYearStr) < currentYear ||
                (Integer.parseInt(expirationYearStr) == currentYear && Integer.parseInt(expirationMonthStr) < currentMonth)) {
            throw new InvalidExpirationMonthException(InvalidExpirationMonthException.INVALID_EXPIRATION_MONTH_EXCEPTION);
        }
    }


}

