package com.example.course_project.repository;

import com.example.course_project.entity.Passenger;
import com.example.course_project.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Sql(scripts = {"file:src/test/resources/db/clear_all.sql","file:src/test/resources/db/insert_script.sql"})
class PassengerRepositoryTest {
    @Autowired
    private PassengerRepository passengerRepository;
    @Test
    void testFindPassengersByName() {
        List<Passenger> passengerList = passengerRepository.findPassengersByName("Butskevich", "Victoria");
        int expectedValue = 1;
        assertEquals(expectedValue, passengerList.size());
    }

    @Test
    void testFindPassengersByLastName() {
        List<Passenger> passengerList = passengerRepository.findPassengersByLastName("Butskevich");
        int expectedValue = 1;
        assertEquals(expectedValue, passengerList.size());
    }

    @Test
    void testFindPassengersByFirstName() {
        List<Passenger> passengerList = passengerRepository.findPassengersByFirstName("Victoria");
        int expectedValue = 1;
        assertEquals(expectedValue, passengerList.size());
    }

    @Test
    void findPassengerByEmail() {
        String email = "prigozhaeva@gmail.com";
        Passenger passenger = passengerRepository.findPassengerByEmail(email);
        assertEquals(email, passenger.getUser().getEmail());
    }

    @Test
    void findPassengerByPassportNumber() {
        String passportNumber = "HB3052370";
        Passenger passenger = passengerRepository.findPassengerByPassportNumber(passportNumber);
        assertEquals(passportNumber, passenger.getPassportNumber());
    }

    @Test
    void findPassengerByPhoneNumber() {
        String phoneNumber = "+375291023712";
        Passenger passenger = passengerRepository.findPassengerByPhoneNumber(phoneNumber);
        assertEquals(phoneNumber, passenger.getPhoneNumber());
    }
}