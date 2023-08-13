package io.upschool.capstoneProject.dto.airport;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AirportSaveResponse {
    private Long id;
    private String name;
    private String location;

}
