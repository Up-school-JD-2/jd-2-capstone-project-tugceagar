package io.upschool.capstoneProject.repository;

import io.upschool.capstoneProject.entity.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.List;


@Repository
public interface FlightRepository extends JpaRepository<Flight,Long> {

    List<Flight> findAllByRouteDepartureAirportLocationAndRouteArrivalAirportLocation(String departureAirport,String arrivalAirport);

    List<Flight> findAllByAirline_Id(Long id);




}
