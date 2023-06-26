package com.example.course_project.service.impl;

import com.example.course_project.entity.Airline;
import com.example.course_project.entity.TransitFlight;
import com.example.course_project.repository.AirlineRepository;
import com.example.course_project.repository.TransitFlightRepository;
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
class TransitFlightServiceImplTest {
    @Mock
    private TransitFlightRepository transitFlightRepository;
    @Mock
    private AirlineRepository airlineRepository;
    @InjectMocks
    private TransitFlightServiceImpl transitFlightService;

    @Test
    void testCreateTransitFlight() {
        Airline airline = new Airline();
        airline.setId(1L);
        when(airlineRepository.findById(any())).thenReturn(Optional.of(airline));
        transitFlightService.createTransitFlight("QR1236","Minsk", "Minsk", LocalDate.now(), LocalTime.now(), "Sheremetevo", "Moscow", LocalDate.now(), LocalTime.now(), 1L, 1, 620);
        verify(transitFlightRepository).save(any());
    }

    @Test
    void testFindTransitFlightById() {
        TransitFlight transitFlight = new TransitFlight();
        transitFlight.setId(1L);
        when(transitFlightRepository.findById(any())).thenReturn(Optional.of(transitFlight));
        TransitFlight actualTransitFlight = transitFlightService.findTransitFlightById(1L);
        assertEquals(transitFlight, actualTransitFlight);
    }

    @Test
    void testFindTransitFlightByFlightNumber() {
        String flightNumber = "QW8547";
        transitFlightService.findTransitFlightByFlightNumber(flightNumber);
        verify(transitFlightRepository).getTransitFlightByFlightNumber(flightNumber);
    }

    @Test
    void testFindTransitFlightsByDirectionAndArrivalDate() {
        String arrivalCity = "Minsk";
        String departureCity = "Moscow";
        transitFlightService.findTransitFlightsByDirection(arrivalCity, departureCity);
        verify(transitFlightRepository).getTransitFlightsByDirection(arrivalCity, departureCity);
    }

    @Test
    void testFindTransitFlightsByDirection() {
        String arrivalCity = "Minsk";
        String departureCity = "Moscow";
        transitFlightService.findTransitFlightsByDirection(arrivalCity, departureCity);
        verify(transitFlightRepository).getTransitFlightsByDirection(arrivalCity, departureCity);
    }

    @Test
    void testFetchAll() {
        transitFlightService.fetchAll();
        verify(transitFlightRepository).findAll();
    }

    @Test
    void testCreateOrUpdateTransitFlight() {
        TransitFlight transitFlight = new TransitFlight();
        transitFlight.setId(1L);
        transitFlightService.createOrUpdateTransitFlight(transitFlight);
        ArgumentCaptor<TransitFlight> argumentCaptor = ArgumentCaptor.forClass(TransitFlight.class);
        verify(transitFlightRepository).save(argumentCaptor.capture());
        TransitFlight capturedValue = argumentCaptor.getValue();
        assertEquals(transitFlight, capturedValue);
    }

    @Test
    void testRemoveTransitFlight() {
        transitFlightService.removeTransitFlight(1L);
        verify(transitFlightRepository).deleteById(1L);
    }
}