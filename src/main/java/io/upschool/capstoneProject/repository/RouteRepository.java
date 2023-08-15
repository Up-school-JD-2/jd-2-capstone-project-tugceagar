package io.upschool.capstoneProject.repository;

import io.upschool.capstoneProject.entity.Route;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RouteRepository extends JpaRepository<Route, Long> {
    List<Route> findAllByDepartureAirportLocationAndArrivalAirportLocation(String departureAirport, String arrivalAirport);

    boolean existsByArrivalAirportIdAndDepartureAirportId(Long arrivalAirport, Long departureAirport);
}
