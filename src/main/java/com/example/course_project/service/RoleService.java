package com.example.course_project.service;

import com.example.course_project.entity.Airline;
import com.example.course_project.entity.Passenger;
import com.example.course_project.entity.Review;
import com.example.course_project.entity.Role;

import java.util.List;

public interface RoleService {
    Role createRole(String roleName);
    Role findRoleByName(String roleName);
}
