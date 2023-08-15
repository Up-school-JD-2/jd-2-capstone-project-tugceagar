package io.upschool.capstoneProject.controller;

import io.upschool.capstoneProject.dto.BaseResponse;
import io.upschool.capstoneProject.dto.route.RouteRequest;
import io.upschool.capstoneProject.dto.route.RouteResponse;
import io.upschool.capstoneProject.service.RouteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/routes")
@RequiredArgsConstructor
public class RouteController {

    private final RouteService routeService;

    @GetMapping
    public ResponseEntity<BaseResponse<List<RouteResponse>>> getRoutes() {
        List<RouteResponse> routes = routeService.getAllRoutes();
        BaseResponse<List<RouteResponse>> response = BaseResponse.<List<RouteResponse>>builder()
                .status(HttpStatus.OK.value())
                .isSuccess(true)
                .data(routes)
                .build();
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<Object> createAirports(@RequestBody RouteRequest request) {
        var routeResponse = routeService.save(request);
        var response = BaseResponse.<RouteResponse>builder()
                .status(HttpStatus.CREATED.value())
                .isSuccess(true)
                .data(routeResponse)
                .build();

        return ResponseEntity.ok(response);
    }


    @GetMapping("/searchRoutes")
    public ResponseEntity<BaseResponse<List<RouteResponse>>> searchRoutesByNames(
            @RequestParam("from") String departureName,
            @RequestParam("to") String arrivalName) {
        List<RouteResponse> routes = routeService.searchRoutesByDepartureAndArrival(departureName, arrivalName);
        BaseResponse<List<RouteResponse>> response = BaseResponse.<List<RouteResponse>>builder()
                .status(HttpStatus.OK.value())
                .isSuccess(true)
                .data(routes)
                .build();
        return ResponseEntity.ok(response);
    }
}
