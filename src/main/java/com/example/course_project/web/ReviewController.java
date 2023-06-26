package com.example.course_project.web;

import com.example.course_project.entity.Airline;
import com.example.course_project.entity.Passenger;
import com.example.course_project.entity.Review;
import com.example.course_project.service.AirlineService;
import com.example.course_project.service.PassengerService;
import com.example.course_project.service.ReviewService;
import org.springframework.context.annotation.Primary;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

import java.security.Principal;

import static com.example.course_project.constants.Constants.AIRLINE;
import static com.example.course_project.constants.Constants.REVIEW;

@Controller
@RequestMapping(value = "/reviews")
public class ReviewController {
    private ReviewService reviewService;
    private AirlineService airlineService;
    private PassengerService passengerService;

    public ReviewController(ReviewService reviewService, AirlineService airlineService, PassengerService passengerService) {
        this.reviewService = reviewService;
        this.airlineService = airlineService;
        this.passengerService = passengerService;
    }

    @PostMapping(value = "/addReview")
    @PreAuthorize("hasAuthority('Passenger')")
    public String addReview(Model model, Long airlineId) {
        Airline airline = airlineService.findAirlineById(airlineId);
        model.addAttribute(AIRLINE, airline);
        model.addAttribute(REVIEW, new Review());
        return "review-views/formCreate";
    }

    @PostMapping(value = "/save")
    @PreAuthorize("hasAuthority('Passenger')")
    public String save(Review review, Long airlineId, Principal principal) {
        Passenger passenger = passengerService.findPassengerByEmail(principal.getName());
        reviewService.createOrUpdateReview(review, airlineId, passenger.getId());
        return "redirect:/mainForUser/myOrders";
    }
}
