package com.example.course_project.repository;

import com.example.course_project.entity.Airline;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AirlineRepository extends JpaRepository<Airline, Long> {
    List<Airline> findAirlinesByAirlineNameContains(String airlineName);
    Airline findAirlineByAirlineName(String airlineName);
}
