package io.upschool.capstoneProject.controller;

import io.upschool.capstoneProject.dto.BaseResponse;
import io.upschool.capstoneProject.dto.airport.AirportRequest;
import io.upschool.capstoneProject.dto.airport.AirportResponse;
import io.upschool.capstoneProject.service.AirportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/airports")
@RequiredArgsConstructor
public class AirportController {
    private final AirportService airportService;

    @GetMapping
    public ResponseEntity<BaseResponse<List<AirportResponse>>> getAllAirports() {
        List<AirportResponse> airports = airportService.getAllAirports();
        BaseResponse<List<AirportResponse>> response = BaseResponse.<List<AirportResponse>>builder()
                .status(HttpStatus.OK.value())
                .isSuccess(true)
                .data(airports)
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/search")
    public ResponseEntity<BaseResponse<List<AirportResponse>>> searchAirlines(@RequestBody Map<String, String> request) {
        String name = request.get("name");
        List<AirportResponse> airports = airportService.searchAirportByName(name);
        BaseResponse<List<AirportResponse>> response = BaseResponse.<List<AirportResponse>>builder()
                .status(HttpStatus.OK.value())
                .isSuccess(true)
                .data(airports)
                .build();
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<Object> createAirports(@RequestBody AirportRequest request) {
        var airportResponse = airportService.save(request);
        var response = BaseResponse.<AirportResponse>builder()
                .status(HttpStatus.CREATED.value())
                .isSuccess(true)
                .data(airportResponse)
                .build();
        return ResponseEntity.ok(response);
    }

}
