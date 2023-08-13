package io.upschool.capstoneProject.controller;

import io.upschool.capstoneProject.dto.BaseResponse;
import io.upschool.capstoneProject.dto.airport.AirportSaveRequest;
import io.upschool.capstoneProject.dto.airport.AirportSaveResponse;
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
    public ResponseEntity<BaseResponse<List<AirportSaveResponse>>> getAllAirports(){
        List<AirportSaveResponse> airports = airportService.getAllAirports();
        BaseResponse<List<AirportSaveResponse>> response = BaseResponse.<List<AirportSaveResponse>>builder()
                .status(HttpStatus.OK.value())
                .isSuccess(true)
                .data(airports)
                .build();
        return ResponseEntity.ok(response);
    }
    @GetMapping ("/search")
    public ResponseEntity<BaseResponse<List<AirportSaveResponse>>> searchAirlines(@RequestBody Map<String, String> request)  {
        String name = request.get("name");
        List<AirportSaveResponse> airports = airportService.searchAirportByName(name);
        BaseResponse<List<AirportSaveResponse>> response = BaseResponse.<List<AirportSaveResponse>>builder()
                .status(HttpStatus.OK.value())
                .isSuccess(true)
                .data(airports)
                .build();
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<Object> createAirports(@RequestBody AirportSaveRequest request){
        var airportResponse = airportService.save(request);
        var response = BaseResponse.<AirportSaveResponse>builder()
                .status(HttpStatus.CREATED.value())
                .isSuccess(true)
                .data(airportResponse)
                .build();
        return ResponseEntity.ok(response);
    }


}
