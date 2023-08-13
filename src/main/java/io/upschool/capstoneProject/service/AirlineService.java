package io.upschool.capstoneProject.service;

import io.upschool.capstoneProject.dto.airline.AirlineSaveRequest;
import io.upschool.capstoneProject.dto.airline.AirlineSaveResponse;
import io.upschool.capstoneProject.entity.Airline;
import io.upschool.capstoneProject.exception.AlreadySavedException;
import io.upschool.capstoneProject.exception.NotFoundException;

import io.upschool.capstoneProject.repository.AirlineRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor

public class AirlineService {
    private final AirlineRepository airlineRepository;


    @Transactional
    public AirlineSaveResponse save(AirlineSaveRequest request) {
        checkIsAirlineAlreadySaved(request);
        Airline airline = requestToEntity(request);
        Airline savedAirline = airlineRepository.save(airline);
        return entityToResponse(savedAirline);
    }


    public List<AirlineSaveResponse> getAllAirlines() {
        return airlineRepository.findAll()
                .stream()
                .map(this::entityToResponse)
                .toList();
    }


    public List<AirlineSaveResponse> searchAirlinesByName(String name) {
        List<Airline> airlines = airlineRepository.findByName(name);
        if(airlines.isEmpty()){
            throw new NotFoundException("Airline " + NotFoundException.DATA_NOT_FOUND_EXCEPTION);
        }
        return airlines.stream().map(this::entityToResponse)
                .toList();
    }

    public Airline getAirline(Long airlineId) {
        return airlineRepository.findById(airlineId)
                .orElseThrow(()-> new NotFoundException("Airline " + NotFoundException.DATA_NOT_FOUND_EXCEPTION));
    }
    @Transactional
    private Airline requestToEntity(AirlineSaveRequest request){
        return Airline
                .builder()
                .name(request.getName())
                .build();
    }

    @Transactional
    private AirlineSaveResponse entityToResponse(Airline airline){
        return AirlineSaveResponse
                .builder()
                .id(airline.getId())
                .name(airline.getName())
                .build();
    }
    private void checkIsAirlineAlreadySaved(AirlineSaveRequest request) {
        int byNameIs = airlineRepository.findAirlineCountByName(request.getName());
        if (byNameIs > 0) {
            throw new AlreadySavedException("Airline " + AlreadySavedException.ALREADY_SAVED_EXCEPTION);
        }
    }
}
