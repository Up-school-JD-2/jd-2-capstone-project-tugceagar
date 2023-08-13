package io.upschool.capstoneProject.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Airline {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", unique = true)
    private String name;

//    @OneToMany(mappedBy = "airline")
//    private List<Flight> flights = new ArrayList<>();

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "airline_airports",nullable = false)
//    private Airport airport;

//    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST,CascadeType.MERGE})
//    @JoinTable(
//            name = "airline_airport",
//            joinColumns = @JoinColumn(name = "airline_id"),
//            inverseJoinColumns = @JoinColumn(name = "airport_id")
//    )
//    private Set<Airport> airports = new HashSet<>();
}
