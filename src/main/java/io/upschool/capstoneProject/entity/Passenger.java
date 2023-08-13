package io.upschool.capstoneProject.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Passenger {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "tc_number") //exception
    private String tcNumber;

    @Column(name = "name")
    private String name;

    @Column(name = "surname")
    private String surname;

//    @OneToOne(mappedBy = "passenger")
//    @JoinColumn(name = "passenger_ticket")
//    private Ticket ticket;

}
