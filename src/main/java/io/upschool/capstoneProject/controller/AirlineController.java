package io.upschool.capstoneProject.controller;

import io.upschool.capstoneProject.dto.BaseResponse;
import io.upschool.capstoneProject.dto.airline.AirlineSaveRequest;
import io.upschool.capstoneProject.dto.airline.AirlineSaveResponse;
import io.upschool.capstoneProject.service.AirlineService;
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
    public ResponseEntity<BaseResponse<List<AirlineSaveResponse>>> getAirlines() {
        List<AirlineSaveResponse> airlines = airlineService.getAllAirlines();
        BaseResponse<List<AirlineSaveResponse>> response = BaseResponse.<List<AirlineSaveResponse>>builder()
                .status(HttpStatus.OK.value())
                .isSuccess(true)
                .data(airlines)
                .build();
        return ResponseEntity.ok(response);
    }


    @PostMapping
    public ResponseEntity<Object> createAirline(@RequestBody AirlineSaveRequest request) {
        var airlineResponse = airlineService.save(request);
        var response = BaseResponse.<AirlineSaveResponse>builder()
                .status(HttpStatus.CREATED.value())
                .isSuccess(true)
                .data(airlineResponse)
                .build();
        return ResponseEntity.ok(response);
    }


    @GetMapping("/search")
    public ResponseEntity<BaseResponse<List<AirlineSaveResponse>>> searchAirlines(@RequestBody Map<String, String> request)  {
        String name = request.get("name");
        List<AirlineSaveResponse> airlines = airlineService.searchAirlinesByName(name);
        BaseResponse<List<AirlineSaveResponse>> response = BaseResponse.<List<AirlineSaveResponse>>builder()
                .status(HttpStatus.OK.value())
                .isSuccess(true)
                .data(airlines)
                .build();
        return ResponseEntity.ok(response);
    }
}

