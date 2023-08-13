package io.upschool.capstoneProject.service;

import io.upschool.capstoneProject.dto.route.RouteSaveRequest;
import io.upschool.capstoneProject.dto.route.RouteSaveResponse;
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

//    public List<Route> getAllRoutes(){
//        return routeRepository.findAll();
//    }

    @Transactional(readOnly = true)
    public List<RouteSaveResponse> getAllRoutes() {

        return routeRepository.findAll()
                .stream()
                .map(route -> RouteSaveResponse.builder()
                        .id(route.getId())
                        .arrivalAirport(route.getArrivalAirport().getLocation())
                        .departureAirport(route.getDepartureAirport().getLocation())
                        .build()).toList();
    }


    public Route getRoute(Long routeId) {
        return routeRepository.findById(routeId).orElse(null);
    }


    public RouteSaveResponse save(RouteSaveRequest request) {
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
        return RouteSaveResponse.builder()
                .id(savedRoute.getId())
                .departureAirport(savedRoute.getDepartureAirport().getLocation())
                .arrivalAirport(savedRoute.getArrivalAirport().getLocation())
                .build();
    }


    public List<RouteSaveResponse> searchRoutesByDepartureAndArrival(String departure, String arrival) {
        List<Route> routes = routeRepository.findAllByDepartureAirportLocationAndArrivalAirportLocation(departure, arrival);
        routeNotFound(routes);
        return routes.stream()
                .map(route -> RouteSaveResponse.builder()
                        .id(route.getId())
                        .departureAirport(route.getDepartureAirport().getLocation())
                        .arrivalAirport(route.getArrivalAirport().getLocation())
                        .build()).toList();
    }

    private void validateRouteConflict(RouteSaveRequest request) {
        Long arrivalAirport = request.getArrivalAirport();
        Long departureAirport = request.getDepartureAirport();
        if (arrivalAirport == departureAirport) {
            throw new RouteConflictException(RouteConflictException.ROUTE_CONFLICT_EXCEPTION);
        }

    }

    private void checkIsRouteAlreadySaved(RouteSaveRequest request) {
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

    private void departureAirportNotNull(RouteSaveRequest request) {
        if (request.getDepartureAirport() == null) {
            throw new NotNullException("Departure airport " + NotNullException.NOT_NULL_EXCEPTION);
        }
    }

    private void arrivalAirportNotNull(RouteSaveRequest request) {
        if (request.getArrivalAirport() == null) {
            throw new NotNullException("Arrival airport " + NotNullException.NOT_NULL_EXCEPTION);
        }
    }
}