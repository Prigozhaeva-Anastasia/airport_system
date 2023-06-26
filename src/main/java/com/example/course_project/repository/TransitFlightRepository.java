package com.example.course_project.repository;

import com.example.course_project.entity.Transfer;
import com.example.course_project.entity.TransitFlight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TransitFlightRepository extends JpaRepository<TransitFlight, Long> {
    TransitFlight getTransitFlightByFlightNumber(String flightNumber);
    @Query(value = "select t from TransitFlight as t where t.arrivalCity=:arrivalCity and  t.departureCity=:departureCity")
    List<TransitFlight> getTransitFlightsByDirection(@Param("arrivalCity") String arrivalCity, @Param("departureCity") String departureCity);
}
