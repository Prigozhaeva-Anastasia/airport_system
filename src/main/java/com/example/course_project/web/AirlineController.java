package com.example.course_project.web;

import com.example.course_project.entity.Airline;
import com.example.course_project.entity.Flight;
import com.example.course_project.service.AirlineService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

import static com.example.course_project.constants.Constants.*;

@Controller
@RequestMapping(value = "/airlines")
public class AirlineController {
    private AirlineService airlineService;

    public AirlineController(AirlineService airlineService) {
        this.airlineService = airlineService;
    }

    @GetMapping(value = "/index")
    @PreAuthorize("hasAuthority('Admin')")
    public String airlines(Model model, @RequestParam(name = KEYWORD, defaultValue = "") String keyword) {
        List<Airline> airlines = new CopyOnWriteArrayList<>(airlineService.findAirlinesByAirlineName(keyword));
        model.addAttribute(LIST_AIRLINES, airlines);
        model.addAttribute(KEYWORD, keyword);
        return "airline-views/airlines";
    }
    @GetMapping(value = "/delete")
    @PreAuthorize("hasAuthority('Admin')")
    public String deleteAirline(Long airlineId, String keyword) {
        airlineService.removeAirline(airlineId);
        return "redirect:/airlines/index?keyword=" + keyword;
    }
    @GetMapping(value = "/formUpdate")
    @PreAuthorize("hasAuthority('Admin')")
    public String updateAirline(Model model, Long airlineId) {
        Airline airline = airlineService.findAirlineById(airlineId);
        model.addAttribute(AIRLINE, airline);
        return "airline-views/formUpdate";
    }
    @PostMapping(value = "/update")
    @PreAuthorize("hasAuthority('Admin')")
    public String update(@Valid Airline airline, BindingResult bindingResult) {
        if (!airline.getAirlineName().equals(airlineService.findAirlineById(airline.getId()).getAirlineName())) {
            Airline airlineDB = airlineService.findAirlineByAirlineName(airline.getAirlineName());
            if (airlineDB != null) bindingResult.rejectValue("airlineName", null, "Авиакомпания с таким названием уже существует");
        }
        if (airline.getPathToImg() == "") airline.setPathToImg(airlineService.findAirlineById(airline.getId()).getPathToImg());
        else {
            if (!airline.getPathToImg().contains("/images/")) airline.setPathToImg("/images/" + airline.getPathToImg());
            if (airline.getPathToImg().contains(",")) airline.setPathToImg((airline.getPathToImg().replace(",", "")));
        }
        if (bindingResult.hasErrors()) return "airline-views/formUpdate";
        airlineService.createOrUpdateAirline(airline);
        return "redirect:/airlines/index";
    }
    @PostMapping(value = "/save")
    @PreAuthorize("hasAuthority('Admin')")
    public String save(@Valid Airline airline, BindingResult bindingResult) {
        Airline airlineDB = airlineService.findAirlineByAirlineName(airline.getAirlineName());
        if (airlineDB != null) bindingResult.rejectValue("airlineName", null, "Авиакомпания с таким названием уже существует");
        if (!airline.getPathToImg().contains("/images/")) airline.setPathToImg("/images/" + airline.getPathToImg());
        if (airline.getPathToImg().contains(",")) airline.setPathToImg((airline.getPathToImg().replace(",", "")));
        if (bindingResult.hasErrors()) return "airline-views/formCreate";
        airlineService.createOrUpdateAirline(airline);
        return "redirect:/airlines/index";
    }
    @GetMapping(value = "/formCreate")
    @PreAuthorize("hasAuthority('Admin')")
    public String formAirlines(Model model) {
        model.addAttribute(AIRLINE, new Airline());
        return "airline-views/formCreate";
    }

    @GetMapping(value = "/sortByAirlineName")
    @PreAuthorize("hasAuthority('Admin')")
    public String sortByAirlineName(Model model) {
        List<Airline> airlines = airlineService.fetchAll();
        Collections.sort(airlines, Comparator.comparing(Airline::getAirlineName));
        model.addAttribute(LIST_AIRLINES, airlines);
        return "airline-views/airlines";
    }

    @GetMapping(value = "/sortByRating")
    @PreAuthorize("hasAuthority('Admin')")
    public String sortByRating(Model model) {
        List<Airline> airlines = airlineService.fetchAll();
        Collections.sort(airlines, Comparator.comparing(Airline::getRating));
        Collections.reverse(airlines);
        model.addAttribute(LIST_AIRLINES, airlines);
        return "airline-views/airlines";
    }

    @PostMapping(value = "/filterByRating")
    @PreAuthorize("hasAuthority('Admin')")
    public String filterByRating(Model model, String from, String to) {
        List<Airline> airlines = new ArrayList<>();
        if (!from.isEmpty() && !to.isEmpty())
            airlines = airlineService.fetchAll().stream().filter(element->element.getRating() >= Double.parseDouble(from) && element.getRating() <= Double.parseDouble(to)).collect((Collectors.toList()));
        model.addAttribute(LIST_AIRLINES, airlines);
        return "airline-views/airlines";
    }
}
