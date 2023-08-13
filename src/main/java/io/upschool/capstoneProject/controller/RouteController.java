package io.upschool.capstoneProject.controller;

import io.upschool.capstoneProject.dto.BaseResponse;
import io.upschool.capstoneProject.dto.route.RouteSaveRequest;
import io.upschool.capstoneProject.dto.route.RouteSaveResponse;
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
    public ResponseEntity<BaseResponse<List<RouteSaveResponse>>> getRoutes(){
        List<RouteSaveResponse> routes = routeService.getAllRoutes();
        BaseResponse<List<RouteSaveResponse>> response = BaseResponse.<List<RouteSaveResponse>>builder()
                .status(HttpStatus.OK.value())
                .isSuccess(true)
                .data(routes)
                .build();
        return ResponseEntity.ok(response);
    }
    @PostMapping
    public ResponseEntity<Object> createAirports(@RequestBody RouteSaveRequest request) {
        var routeResponse = routeService.save(request);
        var response = BaseResponse.<RouteSaveResponse>builder()
                .status(HttpStatus.CREATED.value())
                .isSuccess(true)
                .data(routeResponse)
                .build();

        return ResponseEntity.ok(response);
    }


    @GetMapping("/searchRoutes")
    public ResponseEntity<BaseResponse<List<RouteSaveResponse>>> searchRoutesByNames(
            @RequestParam("from") String departureName,
            @RequestParam("to") String arrivalName) {
        List<RouteSaveResponse> routes = routeService.searchRoutesByDepartureAndArrival(departureName, arrivalName);
        BaseResponse<List<RouteSaveResponse>> response = BaseResponse.<List<RouteSaveResponse>>builder()
                .status(HttpStatus.OK.value())
                .isSuccess(true)
                .data(routes)
                .build();
        return ResponseEntity.ok(response);
    }
}
