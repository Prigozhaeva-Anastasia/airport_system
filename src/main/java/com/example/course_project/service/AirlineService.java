package com.example.course_project.service;

import com.example.course_project.entity.Airline;

import java.util.List;

public interface AirlineService {
    Airline createAirline(String airlineName, double rating, String pathToImg);
    Airline findAirlineById(Long id);
    Airline findAirlineByAirlineName(String airlineName);
    List<Airline> findAirlinesByAirlineName(String airlineName);
    List<Airline> fetchAll();
    Airline createOrUpdateAirline(Airline airline);
    void removeAirline(Long airlineId);
}
