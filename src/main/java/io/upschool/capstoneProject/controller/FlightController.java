package io.upschool.capstoneProject.controller;

import io.upschool.capstoneProject.dto.BaseResponse;
import io.upschool.capstoneProject.dto.flight.FlightSaveRequest;
import io.upschool.capstoneProject.dto.flight.FlightSaveResponse;
import io.upschool.capstoneProject.service.AirlineService;
import io.upschool.capstoneProject.service.FlightService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api/flights")
@RequiredArgsConstructor
public class FlightController {
    private final FlightService flightService;



    @GetMapping
    public ResponseEntity<BaseResponse<List<FlightSaveResponse>>> getFlights(){
            List<FlightSaveResponse> flights = flightService.getAllFlights();
        BaseResponse<List<FlightSaveResponse>> response = BaseResponse.<List<FlightSaveResponse>>builder()
                .status(HttpStatus.OK.value())
                .isSuccess(true)
                .data(flights)
                .build();
            return ResponseEntity.ok(response);
    }
    @GetMapping("/{airlineId}/flights")
    public ResponseEntity<BaseResponse<List<FlightSaveResponse>>> getFlightsByAirlineId(@PathVariable Long airlineId)  {
        List<FlightSaveResponse> flights= flightService.getFlightsByAirlineId(airlineId);
        BaseResponse<List<FlightSaveResponse>> response = BaseResponse.<List<FlightSaveResponse>>builder()
                .status(HttpStatus.OK.value())
                .isSuccess(true)
                .data(flights)
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/searchFlights")
    public ResponseEntity<BaseResponse<List<FlightSaveResponse>>> getFlightsByAirlineId(@RequestParam("airlineId") Long airlineId
            ,@RequestParam("from") String departureAirport
            ,@RequestParam("to") String arrivalAirport)  {
        List<FlightSaveResponse> flights = flightService.searchDepartureAirportByAirlineId(airlineId,departureAirport,arrivalAirport);
        BaseResponse<List<FlightSaveResponse>> response = BaseResponse.<List<FlightSaveResponse>>builder()
                .status(HttpStatus.OK.value())
                .isSuccess(true)
                .data(flights)
                .build();
        return ResponseEntity.ok(response);
    }
    @GetMapping("/searchFlightsByRoute")
    public ResponseEntity<BaseResponse<List<FlightSaveResponse>>> getFlightsByRoute(@RequestParam("from") String departureAirport
            ,@RequestParam("to") String arrivalAirport) {
        List<FlightSaveResponse> flights = flightService.searchFlightByRoute(departureAirport,arrivalAirport);
        BaseResponse<List<FlightSaveResponse>> response = BaseResponse.<List<FlightSaveResponse>>builder()
                .status(HttpStatus.OK.value())
                .isSuccess(true)
                .data(flights)
                .build();
        return ResponseEntity.ok(response);
    }


    @PostMapping("/{airlineId}/createFlight")
    public ResponseEntity<Object> createFlight(
            @PathVariable Long airlineId,
            @RequestBody FlightSaveRequest request
    )
    {
        FlightSaveResponse flightResponse = flightService.createFlightsByAirlineId(airlineId, request);
        var response = BaseResponse.<FlightSaveResponse>builder()
                .status(HttpStatus.CREATED.value())
                .isSuccess(true)
                .data(flightResponse)
                .build();
        return ResponseEntity.ok(response);
    }


    @PostMapping
    public ResponseEntity<Object> createFlights(@RequestBody FlightSaveRequest request) {
        var flightResponse = flightService.save(request);
        var response = BaseResponse.<FlightSaveResponse>builder()
                .status(HttpStatus.CREATED.value())
                .isSuccess(true)
                .data(flightResponse)
                .build();
        return ResponseEntity.ok(response);

    }




}
