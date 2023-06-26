package com.example.course_project.web;

import com.example.course_project.entity.Airline;
import com.example.course_project.entity.Passenger;
import com.example.course_project.service.PassengerService;
import com.example.course_project.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

import static com.example.course_project.constants.Constants.PASSENGER;
import static com.example.course_project.constants.Constants.PASSENGER_ROLE;

@Controller
public class RegistrationController {
    private PassengerService passengerService;
    private UserService userService;

    public RegistrationController(PassengerService passengerService, UserService userService) {
        this.passengerService = passengerService;
        this.userService = userService;
    }

    @PostMapping(value = "/registration")
    public String registered(@Valid Passenger passenger, BindingResult bindingResult) {
        Passenger passengerDB = passengerService.findPassengerByEmail(passenger.getUser().getEmail());
        if (passengerDB != null) bindingResult.rejectValue("user.email", null, "Пассажир с таким Email уже был зарегистрирован");
        passengerDB = passengerService.findPassengerByPassportNumber(passenger.getPassportNumber());
        if (passengerDB != null) bindingResult.rejectValue("passportNumber", null, "Пассажир с таким номером паспорта уже был зарегистрирован");
        passengerDB = passengerService.findPassengerByPhoneNumber(passenger.getPhoneNumber());
        if (passengerDB != null) bindingResult.rejectValue("phoneNumber", null, "Пассажир с таким номером телефона уже был зарегистрирован");
        if (bindingResult.hasErrors()) return "registration";
        passengerService.createOrUpdatePassenger(passenger);
        userService.assignRoleToUser(passenger.getUser().getEmail(), PASSENGER_ROLE);
        return "redirect:/login";
    }
    @GetMapping(value = "/registration")
    public String registration(Model model) {
        model.addAttribute(PASSENGER, new Passenger());
        return "registration";
    }
}
