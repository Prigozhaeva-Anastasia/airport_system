package com.example.course_project.web;

import com.example.course_project.entity.User;
import com.example.course_project.service.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

import java.security.Principal;

import static com.example.course_project.constants.Constants.*;

@Controller
@RequestMapping(value = "/admin")
public class AdminController {
    private UserService userService;
    private PasswordEncoder passwordEncoder;

    public AdminController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }
    @GetMapping(value = "/personalData")
    @PreAuthorize("hasAuthority('Admin')")
    public String personalData(Model model, Principal principal) {
        if (userService.doesCurrentUserHasRole(ADMIN_ROLE)) {
            User admin = userService.findUserByEmail(principal.getName());
            model.addAttribute(ADMIN, admin);
        }
        return "admin-views/personalData";
    }

    @GetMapping(value = "/formUpdate")
    @PreAuthorize("hasAuthority('Admin')")
    public String formUpdate(Model model, Principal principal) {
        if (userService.doesCurrentUserHasRole(ADMIN_ROLE)) {
            User admin = userService.findUserByEmail(principal.getName());
            model.addAttribute(ADMIN, admin);
        }
        return "admin-views/formUpdate";
    }

    @PostMapping(value = "/update")
    @PreAuthorize("hasAuthority('Admin')")
    public String update(@Valid User user, BindingResult bindingResult) {
        User userDB = userService.findUserById(user.getId());
        user.setEmail(userDB.getEmail());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(userDB.getRoles());
        userService.createOrUpdateUser(user);
        return "redirect:/admin/personalData";
    }
}
