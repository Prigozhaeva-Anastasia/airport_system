package com.example.course_project.web;

import com.example.course_project.entity.DirectFlight;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import static com.example.course_project.constants.Constants.FLIGHT;

@Controller
public class MainController {
    @GetMapping(value = "/")
    @PreAuthorize("hasAnyAuthority('Admin', 'Passenger')")
    public String main(Model model) {
        model.addAttribute(FLIGHT, new DirectFlight());
        return "main-view/main";
    }
}
