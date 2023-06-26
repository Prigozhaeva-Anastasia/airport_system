package com.example.course_project.web;

import com.example.course_project.entity.*;
import com.example.course_project.service.AirTicketService;
import com.example.course_project.service.DirectFlightService;
import com.example.course_project.service.PassengerService;
import com.example.course_project.service.TransitFlightService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import static com.example.course_project.constants.Constants.*;

@Controller
@RequestMapping(value = "/airTickets")
public class AirTicketController {
    private AirTicketService airTicketService;
    private DirectFlightService directFlightService;
    private TransitFlightService transitFlightService;
    private PassengerService passengerService;

    public AirTicketController(AirTicketService airTicketService, PassengerService passengerService, DirectFlightService directFlightService, TransitFlightService transitFlightService) {
        this.airTicketService = airTicketService;
        this.passengerService = passengerService;
        this.directFlightService = directFlightService;
        this.transitFlightService = transitFlightService;
    }

    @GetMapping(value = "/index")
    @PreAuthorize("hasAuthority('Admin')")
    public String airTickets(Model model, Long passengerId) {
        Passenger passenger = passengerService.findPassengerById(passengerId);
        List<AirTicket> airTicketsForDirectFlight = new CopyOnWriteArrayList<>();
        List<AirTicket> airTicketsForTransitFlight = new CopyOnWriteArrayList<>();
        for (AirTicket airTicket : passenger.getTickets()) {
            if (airTicket.getDirectFlight() != null) airTicketsForDirectFlight.add(airTicket);
            else airTicketsForTransitFlight.add(airTicket);
        }
        model.addAttribute(LIST_AIR_TICKETS_FOR_DIRECT_FLIGHT, airTicketsForDirectFlight);
        model.addAttribute(LIST_AIR_TICKETS_FOR_TRANSIT_FLIGHT, airTicketsForTransitFlight);
        return "airTicket-views/airTickets";
    }

    @GetMapping(value = "/forDirectFlight/delete")
    @PreAuthorize("hasAuthority('Admin')")
    public String deleteAirTicketForDirectFlight(Long airTicketId) {
        airTicketService.removeAirTicket(airTicketId);
        return "redirect:/mainForUser/myOrders/activeDirectAirTickets";
    }

    @GetMapping(value = "/forTransitFlight/delete")
    @PreAuthorize("hasAuthority('Admin')")
    public String deleteAirTicketForTransitFlight(Long airTicketId) {
        airTicketService.removeAirTicket(airTicketId);
        return "redirect:/mainForUser/myOrders/activeTransitAirTickets";
    }

    @GetMapping(value = "/formForMoreDetails")
    @PreAuthorize("hasAnyAuthority('Admin', 'Passenger')")
    public String fetchMoreDetails(Model model, Long airTicketId) {
        AirTicket airTicket = airTicketService.findAirTicketById(airTicketId);
        String timeOnWay = null, transfers = null;
        if (airTicket.getDirectFlight() != null) {
            timeOnWay = directFlightService.countDuration(airTicket.getDirectFlight().getDepartureDate(), airTicket.getDirectFlight().getArrivalDate(), airTicket.getDirectFlight().getDepartureTime(), airTicket.getDirectFlight().getArrivalTime());
            model.addAttribute("transfers", "Без пересадок");
            model.addAttribute("condition", Boolean.TRUE);
            if (airTicket.getDirectFlight().getArrivalDate().isBefore(LocalDate.now())) model.addAttribute("hasReview", Boolean.TRUE);
            else model.addAttribute("hasReview", Boolean.FALSE);
        }
        else {
            timeOnWay = transitFlightService.countDuration(airTicket.getTransitFlight().getDepartureDate(), airTicket.getTransitFlight().getArrivalDate(), airTicket.getTransitFlight().getDepartureTime(), airTicket.getTransitFlight().getArrivalTime());
            for (Transfer transfer : airTicket.getTransitFlight().getTransferList())
                if (transfers != null ) transfers = transfers + " - " + transfer.getNameOfCity();
                else  transfers = transfer.getNameOfCity();
            model.addAttribute("transfers", transfers);
            model.addAttribute("condition", Boolean.FALSE);
            if (airTicket.getTransitFlight().getArrivalDate().isBefore(LocalDate.now())) model.addAttribute("hasReview", Boolean.TRUE);
            else model.addAttribute("hasReview", Boolean.FALSE);
        }
        model.addAttribute("timeOnWay", timeOnWay);
        model.addAttribute(AIR_TICKET, airTicket);
        return "airTicket-views/formForMoreDetails";
    }
    @GetMapping(value = "/formForRemoteAirTicket")
    @PreAuthorize("hasAuthority('Passenger')")
    public String formForRemoteAirTicket(Model model) {
        return "airTicket-views/formForRemoteAirTicket";
    }
}
