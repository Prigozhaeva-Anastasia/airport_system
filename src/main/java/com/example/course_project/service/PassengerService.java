package com.example.course_project.service;
import com.example.course_project.entity.Passenger;
import com.example.course_project.entity.User;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface PassengerService {
    Passenger createPassenger(Long userId, String gender, LocalDate dateOfBirth, String citizenship, String passportNumber, String phoneNumber);
    Passenger findPassengerById(Long id);
    Passenger findPassengerByEmail(String email);
    Passenger findPassengerByPassportNumber(String passportNumber);
    Passenger findPassengerByPhoneNumber(String phoneNumber);
    List<Passenger> findPassengersByName(String name);
    List<Passenger> fetchAll();
    Passenger createOrUpdatePassenger(Passenger passenger);
    void removePassenger(Long passengerId);
    Passenger updatePassengerWithSplit(Passenger passenger, Long userId,  String dateOfBirth, String lastName, String firstName, String password);
}
