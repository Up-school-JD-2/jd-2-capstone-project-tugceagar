package io.upschool.capstoneProject.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Flight {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Builder.Default
    @Column(name = "capacity")
    private Integer capacity = 3; //kapasite dolu

    @Column(name = "duration")
    private Integer duration;

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "time")
    private LocalTime time;

    @ManyToOne(fetch =FetchType.LAZY )
    @JoinColumn(name = "route_id",nullable = false)
    private Route route;

    @ManyToOne(fetch =FetchType.LAZY )
    @JoinColumn(name = "airline_id", nullable = false)
    private Airline airline;


}
