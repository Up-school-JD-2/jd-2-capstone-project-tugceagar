package io.upschool.capstoneProject.dto.ticket;

import io.upschool.capstoneProject.dto.CreditCard.CreditCardRequest;
import io.upschool.capstoneProject.dto.passenger.PassengerRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TicketSaveRequest {

    private Double price;
    private Long flightId;
    private CreditCardRequest cardRequest;
    private PassengerRequest passengerRequest;

}
