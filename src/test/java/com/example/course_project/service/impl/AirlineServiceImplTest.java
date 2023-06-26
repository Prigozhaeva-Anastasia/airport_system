package com.example.course_project.service.impl;

import com.example.course_project.entity.Airline;
import com.example.course_project.repository.AirlineRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AirlineServiceImplTest {
    @Mock
    private AirlineRepository airlineRepository;
    @InjectMocks
    private AirlineServiceImpl airlineService;

    @Test
    void testCreateAirline() {
        airlineService.createAirline("Wizz", 4, "file:/D:/c_project_5_sem/client/src/main/resources/ru/airport/logos/wizzair.png");
        verify(airlineRepository).save(any());
    }

    @Test
    void testFindAirlineById() {
        Airline airline = new Airline();
        airline.setId(1L);
        when(airlineRepository.findById(any())).thenReturn(Optional.of(airline));
        Airline actualAirline = airlineService.findAirlineById(1L);
        assertEquals(airline, actualAirline);
    }
    @Test
    void testFindAirlineByAirlineName() {
        String airlineName = "Aeroflot";
        airlineService.findAirlineByAirlineName(airlineName);
        verify(airlineRepository).findAirlineByAirlineName(airlineName);
    }
    @Test
    void testFindAirlinesByAirlineName() {
        String airlineName = "Aeroflot";
        airlineService.findAirlinesByAirlineName(airlineName);
        verify(airlineRepository).findAirlinesByAirlineNameContains(airlineName);
    }

    @Test
    void testFetchAll() {
        airlineService.fetchAll();
        verify(airlineRepository).findAll();
    }

    @Test
    void testCreateOrUpdateAirline() {
        Airline airline = new Airline();
        airline.setId(1L);
        airlineService.createOrUpdateAirline(airline);
        ArgumentCaptor<Airline> argumentCaptor = ArgumentCaptor.forClass(Airline.class);
        verify(airlineRepository).save(argumentCaptor.capture());
        Airline capturedValue = argumentCaptor.getValue();
        assertEquals(airline, capturedValue);
    }

    @Test
    void testRemoveAirline() {
        Airline airline = new Airline();
        airline.setId(1L);
        when(airlineRepository.findById(any())).thenReturn(Optional.of(airline));
        airlineService.removeAirline(1L);
        verify(airlineRepository).deleteById(1L);
    }
}