package io.upschool.capstoneProject.dto.CreditCard;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreditCardRequest {
    private String cardNumber;

    private String ccv;

    private String  expirationMonth;

    private String expirationYear;



}
