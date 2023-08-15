package io.upschool.capstoneProject.service;

import io.upschool.capstoneProject.dto.route.RouteRequest;
import io.upschool.capstoneProject.dto.route.RouteResponse;
import io.upschool.capstoneProject.entity.Airport;
import io.upschool.capstoneProject.entity.Route;
import io.upschool.capstoneProject.exception.AlreadySavedException;
import io.upschool.capstoneProject.exception.NotFoundException;
import io.upschool.capstoneProject.exception.route.NotNullException;
import io.upschool.capstoneProject.exception.route.RouteConflictException;
import io.upschool.capstoneProject.repository.RouteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class RouteService {

    private final RouteRepository routeRepository;

    private final AirportService airportService;


    @Transactional(readOnly = true)
    public List<RouteResponse> getAllRoutes() {

        return routeRepository.findAll()
                .stream()
                .map(route -> RouteResponse.builder()
                        .id(route.getId())
                        .arrivalAirport(route.getArrivalAirport().getLocation())
                        .departureAirport(route.getDepartureAirport().getLocation())
                        .build()).toList();
    }


    public Route getRoute(Long routeId) {
        return routeRepository.findById(routeId).orElse(null);
    }


    @Transactional
    public RouteResponse save(RouteRequest request) {
        departureAirportNotNull(request);
        arrivalAirportNotNull(request);

        Airport departureAirport = airportService.getAirport(request.getDepartureAirport());
        Airport arrivalAirport = airportService.getAirport(request.getArrivalAirport());
        validateRouteConflict(request);
        checkIsRouteAlreadySaved(request);

        Route newRoute = Route.builder()
                .arrivalAirport(arrivalAirport)
                .departureAirport(departureAirport)
                .build();

        Route savedRoute = routeRepository.save(newRoute);
        return RouteResponse.builder()
                .id(savedRoute.getId())
                .departureAirport(savedRoute.getDepartureAirport().getLocation())
                .arrivalAirport(savedRoute.getArrivalAirport().getLocation())
                .build();
    }


    public List<RouteResponse> searchRoutesByDepartureAndArrival(String departure, String arrival) {
        List<Route> routes = routeRepository.findAllByDepartureAirportLocationAndArrivalAirportLocation(departure, arrival);
        routeNotFound(routes);
        return routes.stream()
                .map(route -> RouteResponse.builder()
                        .id(route.getId())
                        .departureAirport(route.getDepartureAirport().getLocation())
                        .arrivalAirport(route.getArrivalAirport().getLocation())
                        .build()).toList();
    }

    private void validateRouteConflict(RouteRequest request) {
        Long arrivalAirport = request.getArrivalAirport();
        Long departureAirport = request.getDepartureAirport();
        if (arrivalAirport == departureAirport) {
            throw new RouteConflictException(RouteConflictException.ROUTE_CONFLICT_EXCEPTION);
        }

    }

    private void checkIsRouteAlreadySaved(RouteRequest request) {
        Long arrivalAirport = request.getArrivalAirport();
        Long departureAirport = request.getDepartureAirport();

        boolean isrouteExists = routeRepository.existsByArrivalAirportIdAndDepartureAirportId(arrivalAirport, departureAirport);

        if (isrouteExists) {
            throw new AlreadySavedException("Route " + AlreadySavedException.ALREADY_SAVED_EXCEPTION);
        }

    }

    private void routeNotFound(List<Route> routes) {
        if (routes.isEmpty()) {
            throw new NotFoundException("Route " + NotFoundException.DATA_NOT_FOUND_EXCEPTION);
        }
    }

    private void departureAirportNotNull(RouteRequest request) {
        if (request.getDepartureAirport() == null) {
            throw new NotNullException("Departure airport " + NotNullException.NOT_NULL_EXCEPTION);
        }
    }

    private void arrivalAirportNotNull(RouteRequest request) {
        if (request.getArrivalAirport() == null) {
            throw new NotNullException("Arrival airport " + NotNullException.NOT_NULL_EXCEPTION);
        }
    }
}