package io.upschool.capstoneProject.dto.ticket;

import io.upschool.capstoneProject.dto.CreditCard.CreditCardResponse;
import io.upschool.capstoneProject.dto.passenger.PassengerResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TicketSaveResponse {

    private String ticketNumber;
    private Double price;
    private CreditCardResponse cardResponse;
    private PassengerResponse passengerResponse;
    private String arrivalLocation;
    private String departureLocation;

}
