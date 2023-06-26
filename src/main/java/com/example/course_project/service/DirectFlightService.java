package com.example.course_project.service;

import com.example.course_project.entity.Card;
import com.example.course_project.entity.DirectFlight;
import com.example.course_project.entity.Passenger;
import com.example.course_project.entity.TransitFlight;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;

public interface DirectFlightService {
    DirectFlight createDirectFlight(String flightNumber, String arrivalAirport, String arrivalCity, LocalDate arrivalDate, LocalTime arrivalTime, String departureAirport, String departureCity, LocalDate departureDate, LocalTime departureTime, double priceOfTickets, Long airlineId);
    DirectFlight findDirectFlightById(Long id);
    DirectFlight findDirectFlightByFlightNumber(String flightNumber);
    List<DirectFlight> findDirectFlightsByDirection(String arrivalCity, String departureCity);
    List<DirectFlight> findDirectFlightByDirectionAndDate(String arrivalCity, String departureCity, LocalDate arrivalDate);
    List<DirectFlight> fetchAll();
    DirectFlight createOrUpdateDirectFlight(DirectFlight directFlight);
    void removeDirectFlight(Long directFlightId);
    String countDuration(LocalDate departureDate, LocalDate arrivalDate, LocalTime departureTime, LocalTime arrivalTime);
}
