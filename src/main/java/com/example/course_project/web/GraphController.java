package com.example.course_project.web;

import com.example.course_project.entity.AirTicket;
import com.example.course_project.entity.Airline;
import com.example.course_project.service.AirTicketService;
import com.example.course_project.service.AirlineService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.*;

@Controller
@RequestMapping(value = "/charts")
public class GraphController {
    private AirlineService airlineService;
    private AirTicketService airTicketService;

    public GraphController(AirlineService airlineService, AirTicketService airTicketService) {
        this.airlineService = airlineService;
        this.airTicketService = airTicketService;
    }

    @GetMapping(value = "/airlineRatingChart")
    @PreAuthorize("hasAuthority('Admin')")
    public String airlineRatingChart(Model model) {
        List<Airline> airlines = airlineService.fetchAll();
        Map<String, Double> map = new HashMap<>();
        for (Airline airline : airlines) {
            map.put(airline.getAirlineName(), airline.getRating());
        }
        model.addAttribute("airlines", map);
        return "graph-views/airlineRatingChart";
    }

    @GetMapping(value = "/passengerTrafficDynamics")
    @PreAuthorize("hasAuthority('Admin')")
    public String passengerTrafficDynamics(Model model) {
        int[] numberOfPassengersPerMonth = new int[12];
        for (int i = 0; i < numberOfPassengersPerMonth.length; i++) {
            for (AirTicket airTicket : airTicketService.fetchAll()) {
                if (airTicket.getDirectFlight() != null) {
                    if (airTicket.getDirectFlight().getArrivalDate().getMonthValue() == i + 1) {
                        numberOfPassengersPerMonth[i]++;
                    }
                } else if (airTicket.getTransitFlight() != null) {
                    if (airTicket.getTransitFlight().getArrivalDate().getMonthValue() == i + 1) {
                        numberOfPassengersPerMonth[i]++;
                    }
                }
            }
        }
        Map<Integer, Integer> passengerTrafficDynamics = new HashMap<>();
        for (int i = 0; i < numberOfPassengersPerMonth.length; i++) {
            passengerTrafficDynamics.put(i+1, numberOfPassengersPerMonth[i]);
        }
        model.addAttribute("passengerTrafficDynamics", passengerTrafficDynamics);
        OptionalInt max = Arrays.stream(numberOfPassengersPerMonth).max();
        model.addAttribute("countOfPassengers", max.getAsInt() + 1);
        return "graph-views/passengerTrafficDynamics";
    }
}
