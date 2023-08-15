package io.upschool.capstoneProject.controller;

import io.upschool.capstoneProject.dto.BaseResponse;
import io.upschool.capstoneProject.dto.flight.FlightRequest;
import io.upschool.capstoneProject.dto.flight.FlightResponse;
import io.upschool.capstoneProject.service.FlightService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/flights")
@RequiredArgsConstructor
public class FlightController {
    private final FlightService flightService;


    @GetMapping
    public ResponseEntity<BaseResponse<List<FlightResponse>>> getFlights() {
        List<FlightResponse> flights = flightService.getAllFlights();
        BaseResponse<List<FlightResponse>> response = BaseResponse.<List<FlightResponse>>builder()
                .status(HttpStatus.OK.value())
                .isSuccess(true)
                .data(flights)
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{airlineId}/flights")
    public ResponseEntity<BaseResponse<List<FlightResponse>>> getFlightsByAirlineId(@PathVariable Long airlineId) {
        List<FlightResponse> flights = flightService.getFlightsByAirlineId(airlineId);
        BaseResponse<List<FlightResponse>> response = BaseResponse.<List<FlightResponse>>builder()
                .status(HttpStatus.OK.value())
                .isSuccess(true)
                .data(flights)
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/searchFlights")
    public ResponseEntity<BaseResponse<List<FlightResponse>>> getFlightsByAirlineId(@RequestParam("airlineId") Long airlineId
            , @RequestParam("from") String departureAirport
            , @RequestParam("to") String arrivalAirport) {
        List<FlightResponse> flights = flightService.searchDepartureAirportByAirlineId(airlineId, departureAirport, arrivalAirport);
        BaseResponse<List<FlightResponse>> response = BaseResponse.<List<FlightResponse>>builder()
                .status(HttpStatus.OK.value())
                .isSuccess(true)
                .data(flights)
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/searchFlightsByRoute")
    public ResponseEntity<BaseResponse<List<FlightResponse>>> getFlightsByRoute(@RequestParam("from") String departureAirport
            , @RequestParam("to") String arrivalAirport) {
        List<FlightResponse> flights = flightService.searchFlightByRoute(departureAirport, arrivalAirport);
        BaseResponse<List<FlightResponse>> response = BaseResponse.<List<FlightResponse>>builder()
                .status(HttpStatus.OK.value())
                .isSuccess(true)
                .data(flights)
                .build();
        return ResponseEntity.ok(response);
    }


    @PostMapping("/{airlineId}/createFlight")
    public ResponseEntity<Object> createFlight(
            @PathVariable Long airlineId,
            @RequestBody FlightRequest request
    ) {
        FlightResponse flightResponse = flightService.createFlightsByAirlineId(airlineId, request);
        var response = BaseResponse.<FlightResponse>builder()
                .status(HttpStatus.CREATED.value())
                .isSuccess(true)
                .data(flightResponse)
                .build();
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<Object> createFlights(@RequestBody FlightRequest request) {
        var flightResponse = flightService.save(request);
        var response = BaseResponse.<FlightResponse>builder()
                .status(HttpStatus.CREATED.value())
                .isSuccess(true)
                .data(flightResponse)
                .build();
        return ResponseEntity.ok(response);

    }
}
