package io.upschool.capstoneProject.service;

import io.upschool.capstoneProject.dto.airport.AirportSaveRequest;
import io.upschool.capstoneProject.dto.airport.AirportSaveResponse;
import io.upschool.capstoneProject.entity.Airport;
import io.upschool.capstoneProject.exception.AlreadySavedException;
import io.upschool.capstoneProject.exception.NotFoundException;
import io.upschool.capstoneProject.repository.AirportRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AirportService {
    private final AirportRepository airportRepository;

    public List<AirportSaveResponse> searchAirportByName(String name) {
        List<Airport> airports = airportRepository.findByName(name);
        if (airports.isEmpty()) {
            throw new NotFoundException("Airport " + NotFoundException.DATA_NOT_FOUND_EXCEPTION);
        }
        return airports.stream()
                .map(this::entityToResponse)
                .toList();
    }

    @Transactional
    public AirportSaveResponse save(AirportSaveRequest request) {
        checkIsAirportAlreadySaved(request);
        Airport airport = requestToEntity(request);
        Airport savedAirport = airportRepository.save(airport);
        return entityToResponse(savedAirport);
    }

    public List<AirportSaveResponse> getAllAirports() {
        return airportRepository.findAll()
                .stream()
                .map(this::entityToResponse)
                .toList();
    }

    public Airport getAirport(Long airportId) {
        return airportRepository.findById(airportId).orElseThrow(() -> new NotFoundException("Airport " + NotFoundException.DATA_NOT_FOUND_EXCEPTION));
    }

    private AirportSaveResponse entityToResponse(Airport airport) {
        return AirportSaveResponse
                .builder()
                .id(airport.getId())
                .name(airport.getName())
                .location(airport.getLocation())
                .build();

    }


    private Airport requestToEntity(AirportSaveRequest request) {
        return Airport
                .builder()
                .name(request.getName())
                .location(request.getLocation())
                .build();
    }
    private void checkIsAirportAlreadySaved(AirportSaveRequest request) {
        int byNameIs = airportRepository.findAirportCountByName(request.getName());
        if (byNameIs > 0) {
            throw new AlreadySavedException("Airport " + AlreadySavedException.ALREADY_SAVED_EXCEPTION);
        }
    }

}
