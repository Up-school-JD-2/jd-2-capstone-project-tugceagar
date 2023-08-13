package io.upschool.capstoneProject.service;

import io.upschool.capstoneProject.dto.passenger.PassengerRequest;
import io.upschool.capstoneProject.dto.passenger.PassengerResponse;
import io.upschool.capstoneProject.entity.Passenger;
import io.upschool.capstoneProject.exception.passenger.InvalidTcNumberException;
import io.upschool.capstoneProject.repository.PassengerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PassengerService {

    private final PassengerRepository passengerRepository;
//createPassenger
//public responseToEntity

@Transactional
    public Passenger save(PassengerRequest request) {
        validateTcNumber(request);
        return requestToEntity(request);

    }

    public Passenger findPassengerByTcNumber(String tcNumber) {
        return passengerRepository.findPassengerByTcNumber(tcNumber);
    }


    @Transactional
    public PassengerResponse entityToResponse(Passenger passenger) {
        return PassengerResponse.builder()
                //.id(passenger.getId())
                .name(passenger.getName())
                .surname(passenger.getSurname())
                .build();
    }
@Transactional
    private Passenger requestToEntity(PassengerRequest request) {
        return passengerRepository.save(Passenger.builder()
                .tcNumber(request.getTcNumber())
                .name(request.getName())
                .surname(request.getSurname()).build());
    }

    private void validateTcNumber(PassengerRequest request) {
        String tcNumber = request.getTcNumber();
        if (tcNumber == null || !tcNumber.matches("\\d{11}")) {
            throw new InvalidTcNumberException(InvalidTcNumberException.INVALID_TC_NUMBER);
        }
    }
}
