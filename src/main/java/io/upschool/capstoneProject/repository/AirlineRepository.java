package io.upschool.capstoneProject.repository;

import io.upschool.capstoneProject.entity.Airline;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AirlineRepository extends JpaRepository<Airline, Long> {

    @Query("SELECT a FROM Airline a WHERE lower(a.name) LIKE lower(concat('%', :name, '%'))")
    List<Airline> findByNameWithQuery(@Param("name") String name); //elastic search

    @Query(value = "select count(a) from Airline a " +
            "where a.name = :name")
    int findAirlineCountByName(@Param("name") String name);

}
