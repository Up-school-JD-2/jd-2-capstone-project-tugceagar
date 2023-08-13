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
public class Airport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name ="airport_name",unique = true)
    private String name; //name yok exception

    @Column(name = "location")
    private String location;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "airport_airlines",nullable = false)
//    private Airline airline;

//    @ManyToMany(mappedBy = "airports")
//    private Set<Airline> airlines = new HashSet<>();

}
