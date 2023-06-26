package com.example.course_project.web;

import com.example.course_project.entity.AirTicket;
import com.example.course_project.entity.Airline;
import com.example.course_project.entity.DirectFlight;
import com.example.course_project.entity.TransitFlight;
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
@RequestMapping(value = "/directFlights")
public class DirectFlightController {
    private DirectFlightService directFlightService;
    private TransitFlightService transitFlightService;
    private AirlineService airlineService;

    public DirectFlightController(DirectFlightService directFlightService, AirlineService airlineService, TransitFlightService transitFlightService) {
        this.directFlightService = directFlightService;
        this.airlineService = airlineService;
        this.transitFlightService = transitFlightService;
    }
    @GetMapping(value = "/index")
    @PreAuthorize("hasAuthority('Admin')")
    public String directFlights(Model model, @RequestParam(name = KEYWORD, defaultValue = "") String keyword) {
        List<DirectFlight> directFlights;
        if (!keyword.isEmpty() && keyword.contains("-")) {
            String[] direction = keyword.split("-");
            directFlights = new CopyOnWriteArrayList<>(directFlightService.findDirectFlightsByDirection(direction[0], direction[1]));
        } else if (!keyword.contains("-") && !keyword.isEmpty()) directFlights = new CopyOnWriteArrayList<>();
        else directFlights = new CopyOnWriteArrayList<>(directFlightService.fetchAll());
        model.addAttribute(LIST_DIRECT_FLIGHTS, directFlights);
        model.addAttribute(KEYWORD, keyword);
        return "directFlight-views/directFlights";
    }

    @GetMapping(value = "/formMoreDetails")
    @PreAuthorize("hasAuthority('Admin')")
    public String fetchMoreDetails(Model model, Long directFlightId) {
        DirectFlight directFlight = directFlightService.findDirectFlightById(directFlightId);
        model.addAttribute(DIRECT_FLIGHT, directFlight);
        return "directFlight-views/formMoreDetails";
    }

    @GetMapping(value = "/delete")
    @PreAuthorize("hasAuthority('Admin')")
    public String deleteDirectFlight(Long directFlightId, String keyword) {
        List<AirTicket> airTickets = directFlightService.findDirectFlightById(directFlightId).getAirTickets();
        for (AirTicket airTicket : airTickets) {
            airTicket.setDirectFlight(null);
        }
        directFlightService.removeDirectFlight(directFlightId);
        return "redirect:/directFlights/index?keyword=" + keyword;
    }
    @GetMapping(value = "/formUpdate")
    @PreAuthorize("hasAuthority('Admin')")
    public String updateDirectFlight(Model model, Long directFlightId) {
        List<Airline> airlines = airlineService.fetchAll();
        DirectFlight directFlight = directFlightService.findDirectFlightById(directFlightId);
        model.addAttribute(DIRECT_FLIGHT, directFlight);
        model.addAttribute(LIST_AIRLINES, airlines);
        return "directFlight-views/formUpdate";
    }
    @PostMapping(value = "/update")
    @PreAuthorize("hasAuthority('Admin')")
    public String update(@Valid DirectFlight directFlight, BindingResult bindingResult) {
        if (!directFlight.getFlightNumber().equals(directFlightService.findDirectFlightById(directFlight.getId()).getFlightNumber())) {
            DirectFlight directFlightDB = directFlightService.findDirectFlightByFlightNumber(directFlight.getFlightNumber());
            TransitFlight transitFlightDB = transitFlightService.findTransitFlightByFlightNumber(directFlight.getFlightNumber());
            if (directFlightDB != null || transitFlightDB != null) bindingResult.rejectValue("flightNumber", null, "Рейс с таким номером уже существует");
        }
        if (bindingResult.hasErrors()) return "directFlight-views/formUpdate";
        directFlightService.createOrUpdateDirectFlight(directFlight);
        return "redirect:/directFlights/index";
    }
    @PostMapping(value = "/save")
    @PreAuthorize("hasAuthority('Admin')")
    public String save(@Valid DirectFlight directFlight, BindingResult bindingResult) {
        DirectFlight directFlightDB = directFlightService.findDirectFlightByFlightNumber(directFlight.getFlightNumber());
        TransitFlight transitFlightDB = transitFlightService.findTransitFlightByFlightNumber(directFlight.getFlightNumber());
        if (directFlightDB != null || transitFlightDB != null) {
            bindingResult.rejectValue("flightNumber", null, "Рейс с таким номером уже существует");
        }
        if (bindingResult.hasErrors()) return "directFlight-views/formCreate";
        directFlightService.createOrUpdateDirectFlight(directFlight);
        return "redirect:/directFlights/index";
    }
    @GetMapping(value = "/formCreate")
    @PreAuthorize("hasAuthority('Admin')")
    public String formDirectFlights(Model model) {
        List<Airline> airlines = airlineService.fetchAll();
        model.addAttribute(DIRECT_FLIGHT, new DirectFlight());
        model.addAttribute(LIST_AIRLINES, airlines);
        return "directFlight-views/formCreate";
    }

    @GetMapping(value = "/sortByData")
    @PreAuthorize("hasAuthority('Admin')")
    public String sortByData(Model model) {
        List<DirectFlight> flights = directFlightService.fetchAll();
        Collections.sort(flights, Comparator.comparing(DirectFlight::getArrivalDate));
        model.addAttribute(LIST_DIRECT_FLIGHTS, flights);
        return "directFlight-views/directFlights";
    }

    @PostMapping(value = "/filterByDate")
    @PreAuthorize("hasAuthority('Admin')")
    public String filterByDate(Model model, String from, String to) {
        List<DirectFlight> directFlights = new ArrayList<>();
        if (!from.isEmpty() && !to.isEmpty())
            directFlights = directFlightService.fetchAll().stream().filter(element->(element.getArrivalDate().isAfter(LocalDate.parse(from)) || element.getArrivalDate().equals(LocalDate.parse(from))) && (element.getArrivalDate().isBefore(LocalDate.parse(to)) || element.getArrivalDate().equals(LocalDate.parse(to)))).collect((Collectors.toList()));
        model.addAttribute(LIST_DIRECT_FLIGHTS, directFlights);
        return "directFlight-views/directFlights";
    }

    @PostMapping(value = "/filterByTime")
    @PreAuthorize("hasAuthority('Admin')")
    public String filterByTime(Model model, String from, String to) {
        List<DirectFlight> directFlights = new ArrayList<>();
        if (!from.isEmpty() && !to.isEmpty())
            directFlights = directFlightService.fetchAll().stream().filter(element->(element.getArrivalTime().isAfter(LocalTime.parse(from)) || element.getArrivalTime().equals(LocalTime.parse(from))) && (element.getArrivalTime().isBefore(LocalTime.parse(to)) || element.getArrivalTime().equals(LocalTime.parse(to)))).collect((Collectors.toList()));
        model.addAttribute(LIST_DIRECT_FLIGHTS, directFlights);
        return "directFlight-views/directFlights";
    }
}
