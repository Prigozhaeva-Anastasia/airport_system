package com.example.course_project.repository;

import com.example.course_project.entity.Passenger;
import com.example.course_project.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PassengerRepository extends JpaRepository<Passenger, Long> {
    @Query(value = "select p from Passenger as p where p.user.lastName=:lastName and  p.user.firstName=:firstName")
    List<Passenger> findPassengersByName(@Param("lastName") String lastName, @Param("firstName") String firstName);
    @Query(value = "select p from Passenger as p where p.user.lastName=:lastName")
    List<Passenger> findPassengersByLastName(@Param("lastName") String lastName);
    @Query(value = "select p from Passenger as p where p.user.firstName=:firstName")
    List<Passenger> findPassengersByFirstName(@Param("firstName") String firstName);
    @Query(value = "select p from Passenger as p where p.user.email=:email")
    Passenger findPassengerByEmail(@Param("email") String email);
    Passenger findPassengerByPassportNumber(String passportNumber);
    Passenger findPassengerByPhoneNumber(String phoneNumber);
}
