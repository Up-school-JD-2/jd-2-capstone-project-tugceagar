package io.upschool.capstoneProject.dto.airline;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AirlineAirportRequest {

    @NotNull(message = "Airline id can not be null.")
    private Long airlineId;

    @NotNull(message = "Airport id can not be null.")
    private Long airportId;
}
