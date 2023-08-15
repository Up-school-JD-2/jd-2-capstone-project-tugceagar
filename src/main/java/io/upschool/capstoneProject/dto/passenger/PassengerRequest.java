package io.upschool.capstoneProject.dto.passenger;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PassengerRequest {

    private String name;

    private String surname;

    private String tcNumber;

}
