package com.example.course_project.service.impl;

import com.example.course_project.entity.*;
import com.example.course_project.repository.*;
import com.example.course_project.service.AirTicketService;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class AirTicketServiceImpl implements AirTicketService {
    private AirTicketRepository airTicketRepository;
    private PassengerRepository passengerRepository;
    private DirectFlightRepository directFlightRepository;
    private TransitFlightRepository transitFlightRepository;

    public AirTicketServiceImpl(AirTicketRepository airTicketRepository, PassengerRepository passengerRepository, DirectFlightRepository directFlightRepository, TransitFlightRepository transitFlightRepository) {
        this.airTicketRepository = airTicketRepository;
        this.passengerRepository = passengerRepository;
        this.directFlightRepository = directFlightRepository;
        this.transitFlightRepository = transitFlightRepository;
    }

    @Override
    public AirTicket createAirTicket(double price, int seatNumber, Long passengerId, Long directFlightId, Long transitFlightId) {
        String typeOfTicket = null;
        Passenger passenger = passengerRepository.findById(passengerId).orElseThrow(()->new EntityNotFoundException("Passenger with id " + passengerId + " Not Found"));
        DirectFlight directFlight = null;
        TransitFlight transitFlight = null;
        if (directFlightId != null)  directFlight = directFlightRepository.findById(directFlightId).orElseThrow(() -> new EntityNotFoundException("DirectFlight with id " + directFlightId + " Not Found"));
        if (transitFlightId != null) transitFlight = transitFlightRepository.findById(transitFlightId).orElseThrow(() -> new EntityNotFoundException("TransitFlight with id " + transitFlightId + " Not Found"));
        if (seatNumber >= 1 && seatNumber <= 40) typeOfTicket = "эконом-класс";
        else {
            typeOfTicket = "бизнес-класс";
            price = 5 * price;
        }
        return airTicketRepository.save(AirTicket.builder().price(price).seatNumber(seatNumber).typeOfTicket(typeOfTicket).passenger(passenger).directFlight(directFlight).transitFlight(transitFlight).build());
    }

    @Override
    public AirTicket findAirTicketById(Long id) {
        return airTicketRepository.findById(id).orElseThrow(()->new EntityNotFoundException("AirTicket with id " + id + " Not Found"));
    }

    @Override
    public List<AirTicket> fetchAll() {
        return airTicketRepository.findAll();
    }

    @Override
    public List<AirTicket> fetchAirTicketsForPassenger(Long passengerId) {
        return airTicketRepository.getAirTicketsByPassengerId(passengerId);
    }

    @Override
    public AirTicket createOrUpdateAirTicket(AirTicket airTicket) {
        return airTicketRepository.save(airTicket);
    }

    @Override
    public void removeAirTicket(Long airTicketId) {
        airTicketRepository.deleteById(airTicketId);
    }

    @Override
    public List<AirTicket> getActiveAirTicketsForDirectFlight(String email) {
        Passenger passenger = passengerRepository.findPassengerByEmail(email);
        List<AirTicket> airTicketsForDirectFlight = passenger.getTickets().stream().filter(airTicket -> airTicket.getDirectFlight() != null).collect(Collectors.toList());
        List<AirTicket> activeAirTicketsForDirectFlight =  airTicketsForDirectFlight.stream().filter(airTicket -> airTicket.getDirectFlight().getArrivalDate().isAfter(LocalDate.now()) || airTicket.getDirectFlight().getArrivalDate().equals(LocalDate.now())).collect(Collectors.toList());
        return activeAirTicketsForDirectFlight;
    }

    @Override
    public List<AirTicket> getActiveAirTicketsForTransitFlight(String email) {
        Passenger passenger = passengerRepository.findPassengerByEmail(email);
        List<AirTicket> airTicketsForTransitFlight = passenger.getTickets().stream().filter(airTicket -> airTicket.getTransitFlight() != null).collect(Collectors.toList());
        List<AirTicket> activeAirTicketsForTransitFlight =  airTicketsForTransitFlight.stream().filter(airTicket -> airTicket.getTransitFlight().getArrivalDate().isAfter(LocalDate.now()) || airTicket.getTransitFlight().getArrivalDate().equals(LocalDate.now())).collect(Collectors.toList());
        return activeAirTicketsForTransitFlight;
    }

    @Override
    public List<AirTicket> getArchiveAirTicketsForDirectFlight(String email) {
        Passenger passenger = passengerRepository.findPassengerByEmail(email);
        List<AirTicket> airTicketsForDirectFlight = passenger.getTickets().stream().filter(airTicket -> airTicket.getDirectFlight() != null).collect(Collectors.toList());
        List<AirTicket> archiveAirTicketsForDirectFlight =  airTicketsForDirectFlight.stream().filter(airTicket -> airTicket.getDirectFlight().getArrivalDate().isBefore(LocalDate.now())).collect(Collectors.toList());
        return archiveAirTicketsForDirectFlight;
    }

    @Override
    public List<AirTicket> getArchiveAirTicketsForTransitFlight(String email) {
        Passenger passenger = passengerRepository.findPassengerByEmail(email);
        List<AirTicket> airTicketsForTransitFlight = passenger.getTickets().stream().filter(airTicket -> airTicket.getTransitFlight() != null).collect(Collectors.toList());
        List<AirTicket> archiveAirTicketsForTransitFlight =  airTicketsForTransitFlight.stream().filter(airTicket -> airTicket.getTransitFlight().getArrivalDate().isBefore(LocalDate.now())).collect(Collectors.toList());
        return archiveAirTicketsForTransitFlight;
    }
}
