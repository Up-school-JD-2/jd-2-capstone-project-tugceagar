package io.upschool.capstoneProject.controller;

import io.upschool.capstoneProject.dto.BaseResponse;
import io.upschool.capstoneProject.dto.airline.AirlineAirportRequest;
import io.upschool.capstoneProject.dto.airline.AirlineAirportResponse;
import io.upschool.capstoneProject.dto.airline.AirlineRequest;
import io.upschool.capstoneProject.dto.airline.AirlineResponse;
import io.upschool.capstoneProject.service.AirlineService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/airlines")
@RequiredArgsConstructor
public class AirlineController {
    private final AirlineService airlineService;

    @GetMapping
    public ResponseEntity<BaseResponse<List<AirlineResponse>>> getAirlines() {
        List<AirlineResponse> airlines = airlineService.getAllAirlines();
        BaseResponse<List<AirlineResponse>> response = BaseResponse.<List<AirlineResponse>>builder()
                .status(HttpStatus.OK.value())
                .isSuccess(true)
                .data(airlines)
                .build();
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<Object> createAirline(@RequestBody AirlineRequest request) {
        var airlineResponse = airlineService.save(request);
        var response = BaseResponse.<AirlineResponse>builder()
                .status(HttpStatus.CREATED.value())
                .isSuccess(true)
                .data(airlineResponse)
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/search")
    public ResponseEntity<BaseResponse<List<AirlineResponse>>> searchAirlines(@RequestBody Map<String, String> request) {
        String name = request.get("name");
        List<AirlineResponse> airlines = airlineService.searchAirlinesByName(name);
        BaseResponse<List<AirlineResponse>> response = BaseResponse.<List<AirlineResponse>>builder()
                .status(HttpStatus.OK.value())
                .isSuccess(true)
                .data(airlines)
                .build();
        return ResponseEntity.ok(response);
    }

    @PostMapping("/add-airline-to-airport")
    public ResponseEntity<BaseResponse<AirlineAirportResponse>> addAirlineToAirport(@Valid @RequestBody AirlineAirportRequest request) {

        AirlineAirportResponse airlineAirportResponse = airlineService.addAirlineToAirport(request);

        BaseResponse<AirlineAirportResponse> response = BaseResponse.<AirlineAirportResponse>builder()
                .status(HttpStatus.OK.value())
                .isSuccess(true)
                .data(airlineAirportResponse)
                .build();

        return ResponseEntity.ok(response);
    }
}

