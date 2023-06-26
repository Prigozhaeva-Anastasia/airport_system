package com.example.course_project.service;

import com.example.course_project.entity.Passenger;
import com.example.course_project.entity.TransitFlight;
import com.example.course_project.entity.User;

import java.time.LocalDate;
import java.util.List;

public interface UserService {
    User createUser(String password, String email, String lastName, String firstName);
    User findUserById(Long userId);
    User findUserByEmail(String email);
    void assignRoleToUser(String email, String roleName);
    User createOrUpdateUser(User user);
    void removeRoleFromUser(String email, String roleName);
    boolean doesCurrentUserHasRole(String roleName);
}
