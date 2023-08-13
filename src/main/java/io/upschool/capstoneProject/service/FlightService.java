package io.upschool.capstoneProject.service;


import io.upschool.capstoneProject.dto.flight.FlightSaveRequest;
import io.upschool.capstoneProject.dto.flight.FlightSaveResponse;
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
    public List<FlightSaveResponse> getAllFlights() {
        return flightRepository.findAll()
                .stream()
                .map(flight -> FlightSaveResponse.builder()
                        //   .id(flight.getId())
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
    public FlightSaveResponse createFlightsByAirlineId(Long airlineId, FlightSaveRequest flightRequest) {
        Airline airline = airlineService.getAirline(airlineId);
        Route route = routeService.getRoute(flightRequest.getRouteId());

        Flight newFlight = Flight.builder()
                .airline(airline)
                .date(flightRequest.getDate())
                .time(flightRequest.getTime())
                .route(route)
                .duration(flightRequest.getDuration())
                .build();

        Flight savedFlight = flightRepository.save(newFlight);

        return FlightSaveResponse.builder()
                .airlineId(savedFlight.getAirline().getId())
                // .id(savedFlight.getId())
                .time(savedFlight.getTime())
                .date(savedFlight.getDate())
                .routeId(savedFlight.getRoute().getId())
                .departureAirport(savedFlight.getRoute().getDepartureAirport().getLocation())
                .arrivalAirport(savedFlight.getRoute().getArrivalAirport().getLocation())
                .capacity(savedFlight.getCapacity())
                .duration(savedFlight.getDuration())
                .build();
    }


    public List<FlightSaveResponse> getFlightsByAirlineId(Long airlineId)  {
        Airline airline = airlineService.getAirline(airlineId);

        List<Flight> flights = flightRepository.findAllByAirline_Id(airline.getId());
        return flights.stream()
                .map(flight -> FlightSaveResponse.builder()
                        .airlineId(flight.getAirline().getId())
                        //  .id(flight.getId())
                        .time(flight.getTime())
                        .date(flight.getDate())
                        .routeId(flight.getRoute().getId())
                        .departureAirport(flight.getRoute().getDepartureAirport().getLocation())
                        .arrivalAirport(flight.getRoute().getArrivalAirport().getLocation())
                        .capacity(flight.getCapacity())
                        .duration(flight.getDuration())
                        .build()).toList();
    }

    public List<FlightSaveResponse> searchDepartureAirportByAirlineId(Long airlineId, String arrival, String departure) {
        Airline airline = airlineService.getAirline(airlineId);

        List<Flight> flights = flightRepository
                .findAllByRouteDepartureAirportLocationAndRouteArrivalAirportLocation(arrival, departure)
                .stream()
                .filter(flight -> flight.getAirline().getId().equals(airline.getId()))
                .toList();
            flightNotFound(flights);

        return flights.stream()
                .map(flight -> FlightSaveResponse.builder()
                        //.id(flight.getId())
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

    public List<FlightSaveResponse> searchFlightByRoute(String arrival, String departure) {
        List<Flight> flights = flightRepository
                .findAllByRouteDepartureAirportLocationAndRouteArrivalAirportLocation(arrival, departure)
                .stream()
                .toList();
        flightNotFound(flights);
        return flights.stream()
                .map(flight -> FlightSaveResponse.builder()
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

    public FlightSaveResponse save(FlightSaveRequest request){
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
        return FlightSaveResponse.builder()
                .airlineId(savedFlight.getAirline().getId())
                // .id(savedFlight.getId())
                .time(savedFlight.getTime())
                .date(savedFlight.getDate())
                .routeId(savedFlight.getRoute().getId())
                .departureAirport(savedFlight.getRoute().getDepartureAirport().getLocation())
                .arrivalAirport(savedFlight.getRoute().getArrivalAirport().getLocation())
                .capacity(savedFlight.getCapacity())
                .duration(savedFlight.getDuration())
                .build();
    }

    public Flight save(Flight flight) {
        return flightRepository.save(flight);
    }

    public Flight getFlight(Long flightId) {
        return flightRepository.findById(flightId).orElse(null);
    }

    @Transactional
    public void decreaseFlightCapacity(Long flightId)  {
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

//    private Flight requestToEntity(FlightSaveRequest request){
//
//    }
}
