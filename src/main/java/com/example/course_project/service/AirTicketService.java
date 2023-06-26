package com.example.course_project.service;

import com.example.course_project.entity.*;

import java.util.List;

public interface AirTicketService {
    AirTicket createAirTicket(double price, int seatNumber, Long passengerId, Long directFlightId, Long transitFlightId);
    AirTicket findAirTicketById(Long id);
    List<AirTicket> fetchAll();
    List<AirTicket> fetchAirTicketsForPassenger(Long passengerId);
    AirTicket createOrUpdateAirTicket(AirTicket airTicket);
    void removeAirTicket(Long airTicketId);
    List<AirTicket> getActiveAirTicketsForDirectFlight(String email);
    List<AirTicket> getActiveAirTicketsForTransitFlight(String email);
    List<AirTicket> getArchiveAirTicketsForDirectFlight(String email);
    List<AirTicket> getArchiveAirTicketsForTransitFlight(String email);
}
