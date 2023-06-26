package com.example.course_project.web;
import com.example.course_project.entity.DirectFlight;
import com.example.course_project.entity.Passenger;
import com.example.course_project.entity.User;
import com.example.course_project.service.PassengerService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import static com.example.course_project.constants.Constants.*;

@Controller
@RequestMapping(value = "/passengers")
public class PassengerController {
    private PassengerService passengerService;

    public PassengerController(PassengerService passengerService) {
        this.passengerService = passengerService;
    }

    @GetMapping(value = "/index")
    @PreAuthorize("hasAuthority('Admin')")
    public String passengers(Model model, @RequestParam(name = KEYWORD, defaultValue = "") String keyword) {
        List<Passenger> passengers;
        if (keyword.isEmpty()) passengers = new CopyOnWriteArrayList<>(passengerService.fetchAll());
        else passengers = new CopyOnWriteArrayList<>(passengerService.findPassengersByName(keyword));
        model.addAttribute(LIST_PASSENGERS, passengers);
        model.addAttribute(KEYWORD, keyword);
        return "passenger-views/passengers";
    }

    @GetMapping(value = "/formForMoreDetails")
    @PreAuthorize("hasAuthority('Admin')")
    public String formMoreDetails(Model model, Long passengerId) {
        Passenger passenger = passengerService.findPassengerById(passengerId);
        model.addAttribute(PASSENGER, passenger);
        return "passenger-views/formForMoreDetails";
    }

    @GetMapping(value = "/sortByLastName")
    @PreAuthorize("hasAuthority('Admin')")
    public String sortByLastName(Model model) {
        List<Passenger> passengers = passengerService.fetchAll();
        Collections.sort(passengers);
        model.addAttribute(LIST_PASSENGERS, passengers);
        return "passenger-views/passengers";
    }
}
