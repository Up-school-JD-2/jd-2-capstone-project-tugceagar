package io.upschool.capstoneProject.dto.route;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RouteResponse {
    private Long id;
    private String departureAirport;
    private String arrivalAirport;
}
