package io.upschool.capstoneProject.service;

import io.upschool.capstoneProject.dto.CreditCard.CreditCardRequest;
import io.upschool.capstoneProject.dto.CreditCard.CreditCardResponse;
import io.upschool.capstoneProject.dto.passenger.PassengerRequest;
import io.upschool.capstoneProject.dto.passenger.PassengerResponse;
import io.upschool.capstoneProject.dto.ticket.TicketSaveRequest;
import io.upschool.capstoneProject.dto.ticket.TicketSaveResponse;
import io.upschool.capstoneProject.entity.CreditCard;
import io.upschool.capstoneProject.entity.Flight;
import io.upschool.capstoneProject.entity.Passenger;
import io.upschool.capstoneProject.entity.Ticket;
import io.upschool.capstoneProject.exception.NotFoundException;
import io.upschool.capstoneProject.repository.TicketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TicketService {
    private final TicketRepository ticketRepository;
    private final PassengerService passengerService;
    private final CreditCardService cardService;
    private final FlightService flightService;


    public List<TicketSaveResponse> getAllTickets() {
        return ticketRepository.findAll()
                .stream()
                .map(this::entityToResponse).toList();
    }

    @Transactional
    public TicketSaveResponse save(TicketSaveRequest request) {

        Ticket ticket = requestToEntity(request);
        flightService.decreaseFlightCapacity(request.getFlightId());

        return entityToResponse(ticket);

    }


    public void ticketCancel(String ticketNumber) {
        checkTicketExists(ticketNumber);
        Ticket ticket = ticketRepository.findByTicketNumber(ticketNumber);
        ticket.setIsActive(false);
        ticketRepository.save(ticket);
    }


    public TicketSaveResponse findTicketByNumber(String ticketNumber) {
        checkTicketExists(ticketNumber);
        Ticket ticket = ticketRepository.findByTicketNumber(ticketNumber);

        return entityToResponse(ticket);
    }

    @Transactional
    private TicketSaveResponse entityToResponse(Ticket ticket) {
        CreditCard card = ticket.getCard();
        Passenger passenger = ticket.getPassenger();
        Flight flight = ticket.getFlight();


        CreditCardResponse creditCardResponse = CreditCardResponse.builder()
                .cardNumber(card.getCardNumber()).build();

        PassengerResponse passengerSaveResponse = passengerService.entityToResponse(passenger);


        return TicketSaveResponse.builder()
                .ticketNumber(ticket.getTicketNumber())
                .price(ticket.getPrice())
                .cardResponse(creditCardResponse)
                .passengerResponse(passengerSaveResponse)
                .arrivalLocation(flight.getRoute().getArrivalAirport().getLocation())
                .departureLocation(flight.getRoute().getDepartureAirport().getLocation())
                .build();

    }


    private Ticket requestToEntity(TicketSaveRequest request) {
        PassengerRequest passengerRequest = request.getPassengerRequest();
        CreditCardRequest cardRequest = request.getCardRequest();
        Flight flight = flightService.getFlight(request.getFlightId());

        Passenger passenger = passengerService.findPassengerByTcNumber(passengerRequest.getTcNumber());

        if (passenger == null) {
            passenger = passengerService.save(passengerRequest);
        }

        String maskedCardNumber = cardService.maskCardNumber(cardRequest.getCardNumber());
        cardRequest.setCardNumber(maskedCardNumber);

        CreditCard savedCard = cardService.save(cardRequest);

        return ticketRepository.save(Ticket.builder()
                .price(request.getPrice())
                .card(savedCard)
                .passenger(passenger)
                .flight(flight)
                .build());
    }

    private void checkTicketExists(String ticketNumber) {
        Ticket ticket = ticketRepository.findByTicketNumber(ticketNumber);
        if (ticket == null) {
            throw new NotFoundException("Ticket " + NotFoundException.DATA_NOT_FOUND_EXCEPTION);
        }
    }
}

