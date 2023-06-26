package com.example.course_project.service.impl;

import com.example.course_project.entity.Passenger;
import com.example.course_project.entity.User;
import com.example.course_project.repository.PassengerRepository;
import com.example.course_project.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PassengerServiceImplTest {
    @Mock
    private PassengerRepository passengerRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @InjectMocks
    private PassengerServiceImpl passengerService;

    @Test
    void testCreatePassenger() {
        User user = new User();
        user.setId(1L);
        when(userRepository.findById(any())).thenReturn(Optional.of(user));
        passengerService.createPassenger(1L, "femake", LocalDate.now(), "RB", "KB7689754", "+375448965471");
        verify(passengerRepository).save(any());
    }

    @Test
    void testFindPassengerById() {
        Passenger passenger = new Passenger();
        passenger.setId(1L);
        when(passengerRepository.findById(any())).thenReturn(Optional.of(passenger));
        Passenger actualPassenger = passengerService.findPassengerById(1L);
        assertEquals(passenger, actualPassenger);
    }

    @Test
    void testFindPassengersByName() {
        String name = "Prigozhaeva Nastya";
        String[] firstNameAndLastName = name.split(" ");
        passengerService.findPassengersByName(name);
        verify(passengerRepository).findPassengersByName(firstNameAndLastName[0], firstNameAndLastName[1]);
    }

    @Test
    void testFetchAll() {
        passengerService.fetchAll();
        verify(passengerRepository).findAll();
    }

    @Test
    void testCreateOrUpdatePassenger() {
        User user = new User();
        user.setId(1L);
        Passenger passenger = new Passenger();
        passenger.setId(1L);
        passenger.setUser(user);
        passengerService.createOrUpdatePassenger(passenger);
        ArgumentCaptor<Passenger> argumentCaptor = ArgumentCaptor.forClass(Passenger.class);
        verify(passengerRepository).save(argumentCaptor.capture());
        Passenger capturedValue = argumentCaptor.getValue();
        assertEquals(passenger, capturedValue);
    }

    @Test
    void testRemovePassenger() {
        passengerService.removePassenger(1L);
        verify(passengerRepository).deleteById(1L);
    }
}