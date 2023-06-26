package com.example.course_project.service.impl;

import com.example.course_project.entity.*;
import com.example.course_project.repository.AirTicketRepository;
import com.example.course_project.repository.AirlineRepository;
import com.example.course_project.repository.DirectFlightRepository;
import com.example.course_project.service.AirlineService;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class AirlineServiceImpl implements AirlineService {
    private AirlineRepository airlineRepository;
    private AirTicketRepository airTicketRepository;
    public AirlineServiceImpl(AirlineRepository airlineRepository, AirTicketRepository airTicketRepository) {
        this.airlineRepository = airlineRepository;
        this.airTicketRepository = airTicketRepository;
    }

    @Override
    public Airline createAirline(String airlineName, double rating, String pathToImg) {
        return airlineRepository.save(Airline.builder().airlineName(airlineName).rating(rating).pathToImg(pathToImg).build());
    }

    @Override
    public Airline findAirlineById(Long id) {
        return airlineRepository.findById(id).orElseThrow(()->new EntityNotFoundException("Airline with id " + id + " Not Found"));
    }

    @Override
    public Airline findAirlineByAirlineName(String airlineName) {
        return airlineRepository.findAirlineByAirlineName(airlineName);
    }

    @Override
    public List<Airline> findAirlinesByAirlineName(String airlineName) {
        return airlineRepository.findAirlinesByAirlineNameContains(airlineName);
    }

    @Override
    public List<Airline> fetchAll() {
        return airlineRepository.findAll();
    }

    @Override
    public Airline createOrUpdateAirline(Airline airline) {
        return airlineRepository.save(airline);
    }

    @Override
    public void removeAirline(Long airlineId) {
        Airline airline = airlineRepository.findById(airlineId).orElseThrow(()->new EntityNotFoundException("Airline with id " + airlineId + " Not Found"));
        List<DirectFlight> directFlights = new ArrayList<>();
        List<TransitFlight> transitFlights = new ArrayList<>();
        for (int i = 0; i < airline.getFlights().size(); i++) {
            if (airline.getFlights().get(i) instanceof DirectFlight) directFlights.add((DirectFlight) airline.getFlights().get(i));
            else transitFlights.add((TransitFlight) airline.getFlights().get(i));
        }
        List<AirTicket> airTickets = new ArrayList<>();
        for (DirectFlight directFlight : directFlights) {
            airTickets.addAll(directFlight.getAirTickets());
        }
        for (TransitFlight transitFlight : transitFlights) {
            airTickets.addAll(transitFlight.getAirTickets());
        }
        for (AirTicket airTicket : airTickets) {
            airTicket.setDirectFlight(null);
            airTicket.setTransitFlight(null);
        }
        airlineRepository.deleteById(airlineId);
    }
}
