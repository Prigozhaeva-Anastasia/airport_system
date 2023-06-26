package com.example.course_project.repository;

import com.example.course_project.entity.AirTicket;
import com.example.course_project.entity.Passenger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AirTicketRepository extends JpaRepository<AirTicket, Long> {
    @Query(value = "select a from AirTicket as a where a.passenger.id=:passengerId")
    List<AirTicket> getAirTicketsByPassengerId(@Param("passengerId")Long passengerId);
}
