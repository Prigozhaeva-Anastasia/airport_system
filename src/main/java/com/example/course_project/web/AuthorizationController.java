package com.example.course_project.web;

import com.example.course_project.entity.User;
import com.example.course_project.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import static com.example.course_project.constants.Constants.USER;

@Controller
public class AuthorizationController {
    @PostMapping(value = "/login")
    public String authorized() {
        return "main-view/main";
    }
    @GetMapping(value = "/login")
    public String login() {
        return "authorization";
    }
}
