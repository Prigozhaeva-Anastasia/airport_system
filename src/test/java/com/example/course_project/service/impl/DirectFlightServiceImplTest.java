package com.example.course_project.service.impl;

import com.example.course_project.entity.Airline;
import com.example.course_project.entity.DirectFlight;
import com.example.course_project.repository.AirlineRepository;
import com.example.course_project.repository.DirectFlightRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DirectFlightServiceImplTest {
    @Mock
    private DirectFlightRepository directFlightRepository;
    @Mock
    private AirlineRepository airlineRepository;
    @InjectMocks
    private DirectFlightServiceImpl directFlightService;

    @Test
    void testCreateDirectFlight() {
        Airline airline = new Airline();
        airline.setId(1L);
        when(airlineRepository.findById(any())).thenReturn(Optional.of(airline));
        directFlightService.createDirectFlight("QR1236","Minsk", "Minsk", LocalDate.now(), LocalTime.now(), "Sheremetevo", "Moscow", LocalDate.now(), LocalTime.now(), 620, 1L);
        verify(directFlightRepository).save(any());
    }
    @Test
    void testFindDirectFlightById() {
        DirectFlight directFlight = new DirectFlight();
        directFlight.setId(1L);
        when(directFlightRepository.findById(any())).thenReturn(Optional.of(directFlight));
        DirectFlight actualDirectFlight = directFlightService.findDirectFlightById(1L);
        assertEquals(directFlight, actualDirectFlight);
    }

    @Test
    void testFindDirectFlightByFlightNumber() {
        String directFlightNumber = "QW1234";
        directFlightService.findDirectFlightByFlightNumber(directFlightNumber);
        verify(directFlightRepository).getDirectFlightByFlightNumber(directFlightNumber);
    }

    @Test
    void testFindDirectFlightsByDirection() {
        String arrivalCity = "Minsk";
        String departureCity = "Moscow";
        directFlightService.findDirectFlightsByDirection(arrivalCity, departureCity);
        verify(directFlightRepository).getDirectFlightsByDirection(arrivalCity, departureCity);
    }

    @Test
    void testFetchAll() {
        directFlightService.fetchAll();
        verify(directFlightRepository).findAll();
    }

    @Test
    void testCreateOrUpdateDirectFlight() {
        DirectFlight directFlight = new DirectFlight();
        directFlight.setId(1L);
        directFlightService.createOrUpdateDirectFlight(directFlight);
        ArgumentCaptor<DirectFlight> argumentCaptor = ArgumentCaptor.forClass(DirectFlight.class);
        verify(directFlightRepository).save(argumentCaptor.capture());
        DirectFlight capturedValue = argumentCaptor.getValue();
        assertEquals(directFlight, capturedValue);
    }

    @Test
    void testRemoveDirectFlight() {
        directFlightService.removeDirectFlight(1L);
        verify(directFlightRepository).deleteById(1L);
    }
}