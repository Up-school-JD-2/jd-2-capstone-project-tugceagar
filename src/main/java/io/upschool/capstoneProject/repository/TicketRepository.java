package io.upschool.capstoneProject.repository;

import io.upschool.capstoneProject.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {

    Ticket findByTicketNumber(String ticketNumber);


}
