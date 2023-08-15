package io.upschool.capstoneProject.repository;

import io.upschool.capstoneProject.entity.Airport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AirportRepository extends JpaRepository<Airport, Long> {
    @Query("SELECT a FROM Airport a WHERE lower(a.name) LIKE lower(concat('%', :name, '%'))")
    List<Airport> findByName(@Param("name") String name);

    Optional<Airport> findById(Long id);

    @Query(value = "select count(a) from Airport a " +
            "where a.name = :name")
    int findAirportCountByName(@Param("name") String name);
}
