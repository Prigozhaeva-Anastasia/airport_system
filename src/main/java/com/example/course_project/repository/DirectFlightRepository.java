package com.example.course_project.repository;
import com.example.course_project.entity.DirectFlight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DirectFlightRepository extends JpaRepository<DirectFlight, Long> {
    DirectFlight getDirectFlightByFlightNumber(String flightNumber);
    @Query(value = "select d from DirectFlight as d where d.arrivalCity=:arrivalCity and  d.departureCity=:departureCity")
    List<DirectFlight> getDirectFlightsByDirection(@Param("arrivalCity") String arrivalCity, @Param("departureCity") String departureCity);
}
