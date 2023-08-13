package io.upschool.capstoneProject.dto.flight;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FlightSaveRequest {

    private Integer duration;
    private LocalDate date;
    private LocalTime time;
    private Long routeId;
    private Long airlineId;
}
