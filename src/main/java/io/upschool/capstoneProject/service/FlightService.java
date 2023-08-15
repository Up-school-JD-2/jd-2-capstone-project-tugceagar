package io.upschool.capstoneProject.service;


import io.upschool.capstoneProject.dto.flight.FlightRequest;
import io.upschool.capstoneProject.dto.flight.FlightResponse;
import io.upschool.capstoneProject.entity.Airline;
import io.upschool.capstoneProject.entity.Flight;
import io.upschool.capstoneProject.entity.Route;
import io.upschool.capstoneProject.exception.NotFoundException;
import io.upschool.capstoneProject.exception.flight.FlightCapacityIsFullException;
import io.upschool.capstoneProject.repository.FlightRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class FlightService {
    private final FlightRepository flightRepository;
    private final AirlineService airlineService;
    private final RouteService routeService;


    @Transactional(readOnly = true)
    public List<FlightResponse> getAllFlights() {
        return flightRepository.findAll()
                .stream()
                .map(flight -> FlightResponse.builder()
                        .routeId(flight.getRoute().getId())
                        .airlineId(flight.getAirline().getId())
                        .departureAirport(flight.getRoute().getDepartureAirport().getLocation())
                        .arrivalAirport(flight.getRoute().getArrivalAirport().getLocation())
                        .time(flight.getTime())
                        .date(flight.getDate())
                        .capacity(flight.getCapacity())
                        .duration(flight.getDuration())
                        .build()).toList();
    }


    @Transactional
    public FlightResponse createFlightsByAirlineId(Long airlineId, FlightRequest flightRequest) {
        Airline airline = airlineService.getAirline(airlineId);
        Route route = routeService.getRoute(flightRequest.getRouteId());

        if (route == null) {
            throw new NotFoundException("Route " + NotFoundException.DATA_NOT_FOUND_EXCEPTION);
        }

        Flight newFlight = Flight.builder()
                .airline(airline)
                .date(flightRequest.getDate())
                .time(flightRequest.getTime())
                .route(route)
                .duration(flightRequest.getDuration())
                .build();

        Flight savedFlight = flightRepository.save(newFlight);

        return FlightResponse.builder()
                .airlineId(savedFlight.getAirline().getId())
                .time(savedFlight.getTime())
                .date(savedFlight.getDate())
                .routeId(savedFlight.getRoute().getId())
                .departureAirport(savedFlight.getRoute().getDepartureAirport().getLocation())
                .arrivalAirport(savedFlight.getRoute().getArrivalAirport().getLocation())
                .capacity(savedFlight.getCapacity())
                .duration(savedFlight.getDuration())
                .build();
    }


    public List<FlightResponse> getFlightsByAirlineId(Long airlineId) {
        Airline airline = airlineService.getAirline(airlineId);

        List<Flight> flights = flightRepository.findAllByAirline_Id(airline.getId());
        return flights.stream()
                .map(flight -> FlightResponse.builder()
                        .airlineId(flight.getAirline().getId())
                        .time(flight.getTime())
                        .date(flight.getDate())
                        .routeId(flight.getRoute().getId())
                        .departureAirport(flight.getRoute().getDepartureAirport().getLocation())
                        .arrivalAirport(flight.getRoute().getArrivalAirport().getLocation())
                        .capacity(flight.getCapacity())
                        .duration(flight.getDuration())
                        .build()).toList();
    }

    public List<FlightResponse> searchDepartureAirportByAirlineId(Long airlineId, String arrival, String departure) {
        Airline airline = airlineService.getAirline(airlineId);

        List<Flight> flights = flightRepository
                .findAllByRouteDepartureAirportLocationAndRouteArrivalAirportLocation(arrival, departure)
                .stream()
                .filter(flight -> flight.getAirline().getId().equals(airline.getId()))
                .toList();
        flightNotFound(flights);

        return flights.stream()
                .map(flight -> FlightResponse.builder()
                        .time(flight.getTime())
                        .duration(flight.getDuration())
                        .capacity(flight.getCapacity())
                        .date(flight.getDate())
                        .routeId(flight.getRoute().getId())
                        .departureAirport(flight.getRoute().getDepartureAirport().getLocation())
                        .arrivalAirport(flight.getRoute().getArrivalAirport().getLocation())
                        .airlineId(flight.getAirline().getId())
                        .build()).toList();

    }

    public List<FlightResponse> searchFlightByRoute(String arrival, String departure) {
        List<Flight> flights = flightRepository
                .findAllByRouteDepartureAirportLocationAndRouteArrivalAirportLocation(arrival, departure)
                .stream()
                .toList();
        flightNotFound(flights);
        return flights.stream()
                .map(flight -> FlightResponse.builder()
                        .time(flight.getTime())
                        .duration(flight.getDuration())
                        .capacity(flight.getCapacity())
                        .date(flight.getDate())
                        .routeId(flight.getRoute().getId())
                        .departureAirport(flight.getRoute().getDepartureAirport().getLocation())
                        .arrivalAirport(flight.getRoute().getArrivalAirport().getLocation())
                        .airlineId(flight.getAirline().getId())
                        .build()).toList();
    }

    @Transactional
    public FlightResponse save(FlightRequest request) {
        Airline airline = airlineService.getAirline(request.getAirlineId());
        Route route = routeService.getRoute(request.getRouteId());
        Flight newFlight = Flight.builder()
                .airline(airline)
                .date(request.getDate())
                .time(request.getTime())
                .route(route)
                .duration(request.getDuration())
                .build();
        Flight savedFlight = flightRepository.save(newFlight);
        return FlightResponse.builder()
                .airlineId(savedFlight.getAirline().getId())
                .time(savedFlight.getTime())
                .date(savedFlight.getDate())
                .routeId(savedFlight.getRoute().getId())
                .departureAirport(savedFlight.getRoute().getDepartureAirport().getLocation())
                .arrivalAirport(savedFlight.getRoute().getArrivalAirport().getLocation())
                .capacity(savedFlight.getCapacity())
                .duration(savedFlight.getDuration())
                .build();
    }

    @Transactional
    public Flight save(Flight flight) {
        return flightRepository.save(flight);
    }

    public Flight getFlight(Long flightId) {
        return flightRepository.findById(flightId).orElse(null);
    }

    @Transactional
    public void decreaseFlightCapacity(Long flightId) {
        Flight flight = flightRepository.findById(flightId).orElseThrow(() -> new NotFoundException("Flight " + NotFoundException.DATA_NOT_FOUND_EXCEPTION));

        if (flight.getCapacity() > 0) {
            flight.setCapacity(flight.getCapacity() - 1);

        } else {
            throw new FlightCapacityIsFullException(FlightCapacityIsFullException.CAPACITY_IS_FULL_EXCEPTION);
        }
    }

    private void flightNotFound(List<Flight> flights) {
        if (flights.isEmpty()) {
            throw new NotFoundException("Flights " + NotFoundException.DATA_NOT_FOUND_EXCEPTION);
        }
    }

}
