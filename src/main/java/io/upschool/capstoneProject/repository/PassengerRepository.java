package io.upschool.capstoneProject.repository;

import io.upschool.capstoneProject.entity.Passenger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PassengerRepository extends JpaRepository<Passenger, Long> {


    Passenger findPassengerByTcNumber(String tcNumber);
}
