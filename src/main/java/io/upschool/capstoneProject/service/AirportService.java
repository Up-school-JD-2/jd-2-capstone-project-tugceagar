package io.upschool.capstoneProject.service;

import io.upschool.capstoneProject.dto.airport.AirportRequest;
import io.upschool.capstoneProject.dto.airport.AirportResponse;
import io.upschool.capstoneProject.entity.Airport;
import io.upschool.capstoneProject.exception.AlreadySavedException;
import io.upschool.capstoneProject.exception.NotFoundException;
import io.upschool.capstoneProject.repository.AirportRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AirportService {
    private final AirportRepository airportRepository;

    public List<AirportResponse> searchAirportByName(String name) {
        List<Airport> airports = airportRepository.findByName(name);
        if (airports.isEmpty()) {
            throw new NotFoundException("Airport " + NotFoundException.DATA_NOT_FOUND_EXCEPTION);
        }
        return airports.stream()
                .map(this::entityToResponse)
                .toList();
    }

    @Transactional
    public AirportResponse save(AirportRequest request) {
        checkIsAirportAlreadySaved(request);
        Airport airport = requestToEntity(request);
        Airport savedAirport = airportRepository.save(airport);
        return entityToResponse(savedAirport);
    }

    public List<AirportResponse> getAllAirports() {
        return airportRepository.findAll()
                .stream()
                .map(this::entityToResponse)
                .toList();
    }

    public Optional<Airport> findById(Long id) {
        return airportRepository.findById(id);
    }

    public Airport getAirport(Long airportId) {
        return airportRepository.findById(airportId).orElseThrow(() -> new NotFoundException("Airport " + NotFoundException.DATA_NOT_FOUND_EXCEPTION));
    }

    private AirportResponse entityToResponse(Airport airport) {
        return AirportResponse
                .builder()
                .id(airport.getId())
                .name(airport.getName())
                .location(airport.getLocation())
                .build();

    }


    private Airport requestToEntity(AirportRequest request) {
        return Airport
                .builder()
                .name(request.getName())
                .location(request.getLocation())
                .build();
    }

    private void checkIsAirportAlreadySaved(AirportRequest request) {
        int byNameIs = airportRepository.findAirportCountByName(request.getName());
        if (byNameIs > 0) {
            throw new AlreadySavedException("Airport " + AlreadySavedException.ALREADY_SAVED_EXCEPTION);
        }
    }

}
