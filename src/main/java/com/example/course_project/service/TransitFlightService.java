package com.example.course_project.service;
import com.example.course_project.entity.Transfer;
import com.example.course_project.entity.TransitFlight;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface TransitFlightService {
    TransitFlight createTransitFlight(String flightNumber, String arrivalAirport, String arrivalCity, LocalDate arrivalDate, LocalTime arrivalTime, String departureAirport, String departureCity, LocalDate departureDate, LocalTime departureTime, Long airlineId, double priceOfTickets, int countOfTransfers);
    TransitFlight findTransitFlightById(Long id);
    TransitFlight findTransitFlightByFlightNumber(String flightNumber);
    List<TransitFlight> findTransitFlightsByDirection(String arrivalCity, String departureCity);
    List<TransitFlight> findTransitFlightByDirectionAndDate(String arrivalCity, String departureCity, LocalDate arrivalDate);
    List<TransitFlight> fetchAll();
    TransitFlight createOrUpdateTransitFlight(TransitFlight transitFlight);
    void removeTransitFlight(Long transitFlightId);
    String countDuration(LocalDate departureDate, LocalDate arrivalDate, LocalTime departureTime, LocalTime arrivalTime);
}
