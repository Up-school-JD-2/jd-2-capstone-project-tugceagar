package io.upschool.capstoneProject.service;

import io.upschool.capstoneProject.dto.airline.AirlineAirportRequest;
import io.upschool.capstoneProject.dto.airline.AirlineAirportResponse;
import io.upschool.capstoneProject.dto.airline.AirlineRequest;
import io.upschool.capstoneProject.dto.airline.AirlineResponse;
import io.upschool.capstoneProject.entity.Airline;
import io.upschool.capstoneProject.entity.Airport;
import io.upschool.capstoneProject.exception.AlreadySavedException;
import io.upschool.capstoneProject.exception.NotFoundException;
import io.upschool.capstoneProject.repository.AirlineRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor

public class AirlineService {
    private final AirlineRepository airlineRepository;
    private final AirportService airportService;

    @Transactional
    public AirlineAirportResponse addAirlineToAirport(AirlineAirportRequest request) {

        Long airlineId = request.getAirlineId();
        Long airportId = request.getAirportId();

        boolean isAirlineToAirportSaved = checkIsAirlineToAirportAlreadySaved(airlineId, airportId);
        if (isAirlineToAirportSaved) {
            throw new AlreadySavedException(AlreadySavedException.ALREADY_SAVED_EXCEPTION);
        }
        Optional<Airline> airlineOptional = airlineRepository.findById(airlineId);
        Optional<Airport> airportOptional = airportService.findById(airportId);

        if (airlineOptional.isPresent() && airportOptional.isPresent()) {
            Airline airline = airlineOptional.get();
            Airport airport = airportOptional.get();


            airline.getAirports().add(airport);
            airlineRepository.save(airline);

            return AirlineAirportResponse.builder()
                    .airlineName(airline.getName())
                    .airportName(airport.getName())
                    .build();
        } else {
            throw new NotFoundException(NotFoundException.DATA_NOT_FOUND_EXCEPTION);
        }
    }

    @Transactional
    public AirlineResponse save(AirlineRequest request) {
        checkIsAirlineAlreadySaved(request);
        Airline airline = requestToEntity(request);
        Airline savedAirline = airlineRepository.save(airline);
        return entityToResponse(savedAirline);
    }


    public List<AirlineResponse> getAllAirlines() {
        return airlineRepository.findAll()
                .stream()
                .map(this::entityToResponse)
                .toList();
    }


    public List<AirlineResponse> searchAirlinesByName(String name) {
        List<Airline> airlines = airlineRepository.findByNameWithQuery(name);
        if (airlines.isEmpty()) {
            throw new NotFoundException("Airline " + NotFoundException.DATA_NOT_FOUND_EXCEPTION);
        }
        return airlines.stream().map(this::entityToResponse)
                .toList();
    }

    public Airline getAirline(Long airlineId) {
        return airlineRepository.findById(airlineId)
                .orElseThrow(() -> new NotFoundException("Airline " + NotFoundException.DATA_NOT_FOUND_EXCEPTION));
    }

    @Transactional
    private Airline requestToEntity(AirlineRequest request) {
        return Airline
                .builder()
                .name(request.getName())
                .build();
    }

    @Transactional
    private AirlineResponse entityToResponse(Airline airline) {
        return AirlineResponse
                .builder()
                .id(airline.getId())
                .name(airline.getName())
                .build();
    }

    private void checkIsAirlineAlreadySaved(AirlineRequest request) {
        int byNameIs = airlineRepository.findAirlineCountByName(request.getName());
        if (byNameIs > 0) {
            throw new AlreadySavedException("Airline " + AlreadySavedException.ALREADY_SAVED_EXCEPTION);
        }
    }


    private boolean checkIsAirlineToAirportAlreadySaved(Long airlineId, Long airportId) {
        Optional<Airline> airlineOptional = airlineRepository.findById(airlineId);
        Optional<Airport> airportOptional = airportService.findById(airportId);

        if (airlineOptional.isPresent() && airportOptional.isPresent()) {
            Airline airline = airlineOptional.get();
            Airport airport = airportOptional.get();

            return airline.getAirports().contains(airport);
        }

        return false;
    }


}
