package com.example.course_project.repository;

import com.example.course_project.entity.Transfer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TransferRepository extends JpaRepository<Transfer, Long> {
    @Query(value = "select t from Transfer as t where t.transitFlight.flightNumber=:flightNumber")
    List<Transfer> getTransfersByTransitFlightNumber(@Param("flightNumber") String flightNumber);
}
