package com.example.course_project.web;

import com.example.course_project.entity.*;
import com.example.course_project.service.AirlineService;
import com.example.course_project.service.DirectFlightService;
import com.example.course_project.service.TransitFlightService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

import static com.example.course_project.constants.Constants.*;

@Controller
@RequestMapping(value = "/transitFlights")
public class TransitFlightController {
    private TransitFlightService transitFlightService;
    private DirectFlightService directFlightService;
    private AirlineService airlineService;

    public TransitFlightController(TransitFlightService transitFlightService, AirlineService airlineService, DirectFlightService directFlightService) {
        this.transitFlightService = transitFlightService;
        this.airlineService = airlineService;
        this.directFlightService = directFlightService;
    }
    @GetMapping(value = "/index")
    @PreAuthorize("hasAuthority('Admin')")
    public String transitFlights(Model model, @RequestParam(name = KEYWORD, defaultValue = "") String keyword) {
        List<TransitFlight> transitFlights;
        if (!keyword.isEmpty() && keyword.contains("-")) {
            String[] direction = keyword.split("-");
            transitFlights = new CopyOnWriteArrayList<>(transitFlightService.findTransitFlightsByDirection(direction[0], direction[1]));
        } else if (!keyword.contains("-") && !keyword.isEmpty()) transitFlights = new CopyOnWriteArrayList<>();
        else transitFlights = new CopyOnWriteArrayList<>(transitFlightService.fetchAll());
        model.addAttribute(LIST_TRANSIT_FLIGHTS, transitFlights);
        model.addAttribute(KEYWORD, keyword);
        return "transitFlight-views/transitFlights";
    }

    @GetMapping(value = "/formMoreDetails")
    @PreAuthorize("hasAuthority('Admin')")
    public String fetchMoreDetails(Model model, Long transitFlightId) {
        TransitFlight transitFlight = transitFlightService.findTransitFlightById(transitFlightId);
        model.addAttribute(TRANSIT_FLIGHT, transitFlight);
        model.addAttribute(LIST_TRANSFERS, transitFlight.getTransferList());
        return "transitFlight-views/formMoreDetails";
    }

    @GetMapping(value = "/delete")
    @PreAuthorize("hasAuthority('Admin')")
    public String deleteTransitFlight(Long transitFlightId, String keyword) {
        List<AirTicket> airTickets = transitFlightService.findTransitFlightById(transitFlightId).getAirTickets();
        for (AirTicket airTicket : airTickets) {
            airTicket.setTransitFlight(null);
        }
        transitFlightService.removeTransitFlight(transitFlightId);
        return "redirect:/transitFlights/index?keyword=" + keyword;
    }
    @GetMapping(value = "/formUpdate")
    @PreAuthorize("hasAuthority('Admin')")
    public String updateTransitFlight(Model model, Long transitFlightId) {
        List<Airline> airlines = airlineService.fetchAll();
        TransitFlight transitFlight = transitFlightService.findTransitFlightById(transitFlightId);
        model.addAttribute(TRANSIT_FLIGHT, transitFlight);
        model.addAttribute(LIST_AIRLINES, airlines);
        return "transitFlight-views/formUpdate";
    }
    @PostMapping(value = "/update")
    @PreAuthorize("hasAuthority('Admin')")
    public String update(@Valid TransitFlight transitFlight, BindingResult bindingResult) {
        if (!transitFlight.getFlightNumber().equals(transitFlightService.findTransitFlightById(transitFlight.getId()).getFlightNumber())) {
            TransitFlight transitFlightDB = transitFlightService.findTransitFlightByFlightNumber(transitFlight.getFlightNumber());
            DirectFlight directFlightDB = directFlightService.findDirectFlightByFlightNumber(transitFlight.getFlightNumber());
            if (transitFlightDB != null || directFlightDB != null) bindingResult.rejectValue("flightNumber", null, "Рейс с таким номером уже существует");
        }
        if (bindingResult.hasErrors()) return "transitFlight-views/formUpdate";
        transitFlightService.createOrUpdateTransitFlight(transitFlight);
        return "redirect:/transitFlights/index";
    }
    @PostMapping(value = "/save")
    @PreAuthorize("hasAuthority('Admin')")
    public String save(@Valid TransitFlight transitFlight, BindingResult bindingResult) {
        TransitFlight transitFlightDB = transitFlightService.findTransitFlightByFlightNumber(transitFlight.getFlightNumber());
        DirectFlight directFlightDB = directFlightService.findDirectFlightByFlightNumber(transitFlight.getFlightNumber());
        if (transitFlightDB != null || directFlightDB != null) bindingResult.rejectValue("flightNumber", null, "Рейс с таким номером уже существует");
        if (bindingResult.hasErrors()) return "transitFlight-views/formCreate";
        transitFlightService.createOrUpdateTransitFlight(transitFlight);
        return "redirect:/transfers/formCreate?transitFlightId=" + transitFlightService.findTransitFlightByFlightNumber(transitFlight.getFlightNumber()).getId();
    }
    @GetMapping(value = "/formCreate")
    @PreAuthorize("hasAuthority('Admin')")
    public String formTransitFlights(Model model) {
        List<Airline> airlines = airlineService.fetchAll();
        model.addAttribute(TRANSIT_FLIGHT, new TransitFlight());
        model.addAttribute(LIST_AIRLINES, airlines);
        return "transitFlight-views/formCreate";
    }

    @GetMapping(value = "/sortByData")
    @PreAuthorize("hasAuthority('Admin')")
    public String sortByData(Model model) {
        List<TransitFlight> flights = transitFlightService.fetchAll();
        Collections.sort(flights, Comparator.comparing(TransitFlight::getArrivalDate));
        model.addAttribute(LIST_TRANSIT_FLIGHTS, flights);
        return "transitFlight-views/transitFlights";
    }

    @PostMapping(value = "/filterByDate")
    @PreAuthorize("hasAuthority('Admin')")
    public String filterByDate(Model model, String from, String to) {
        List<TransitFlight> transitFlights = new ArrayList<>();
        if (!from.isEmpty() && !to.isEmpty())
            transitFlights = transitFlightService.fetchAll().stream().filter(element->(element.getArrivalDate().isAfter(LocalDate.parse(from)) || element.getArrivalDate().equals(LocalDate.parse(from))) && (element.getArrivalDate().isBefore(LocalDate.parse(to)) || element.getArrivalDate().equals(LocalDate.parse(to)))).collect((Collectors.toList()));
        model.addAttribute(LIST_TRANSIT_FLIGHTS, transitFlights);
        return "transitFlight-views/transitFlights";
    }

    @PostMapping(value = "/filterByTime")
    @PreAuthorize("hasAuthority('Admin')")
    public String filterByTime(Model model, String from, String to) {
        List<TransitFlight> transitFlights = new ArrayList<>();
        if (!from.isEmpty() && !to.isEmpty())
            transitFlights = transitFlightService.fetchAll().stream().filter(element->(element.getArrivalTime().isAfter(LocalTime.parse(from)) || element.getArrivalTime().equals(LocalTime.parse(from))) && (element.getArrivalTime().isBefore(LocalTime.parse(to)) || element.getArrivalTime().equals(LocalTime.parse(to)))).collect((Collectors.toList()));
        model.addAttribute(LIST_TRANSIT_FLIGHTS, transitFlights);
        return "transitFlight-views/transitFlights";
    }
}
