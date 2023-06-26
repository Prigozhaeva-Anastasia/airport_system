package com.example.course_project.service.impl;

import com.example.course_project.entity.AirTicket;
import com.example.course_project.entity.Airline;
import com.example.course_project.entity.Passenger;
import com.example.course_project.repository.*;
import org.checkerframework.checker.units.qual.A;
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
class AirTicketServiceImplTest {
    @Mock
    private AirTicketRepository airTicketRepository;
    @Mock
    private PassengerRepository passengerRepository;
    @InjectMocks
    private AirTicketServiceImpl airTicketService;

    @Test
    void testCreateAirTicket() {
        Passenger passenger = new Passenger();
        passenger.setId(1L);
        when(passengerRepository.findById(any())).thenReturn(Optional.of(passenger));
        airTicketService.createAirTicket(1500, 30, 1L,null, null);
        verify(airTicketRepository).save(any());
    }

    @Test
    void testFindAirTicketById() {
        AirTicket airTicket = new AirTicket();
        airTicket.setId(1L);
        when(airTicketRepository.findById(any())).thenReturn(Optional.of(airTicket));
        AirTicket actualAirTicket = airTicketService.findAirTicketById(1L);
        assertEquals(airTicket, actualAirTicket);
    }

    @Test
    void testFetchAll() {
        airTicketService.fetchAll();
        verify(airTicketRepository).findAll();
    }

    @Test
    void testFetchAirTicketsForPassenger() {
        airTicketService.fetchAirTicketsForPassenger(1L);
        verify(airTicketRepository).getAirTicketsByPassengerId(1L);
    }

    @Test
    void testCreateOrUpdateAirTicket() {
        AirTicket airTicket = new AirTicket();
        airTicket.setId(1L);
        airTicketService.createOrUpdateAirTicket(airTicket);
        ArgumentCaptor<AirTicket> argumentCaptor = ArgumentCaptor.forClass(AirTicket.class);
        verify(airTicketRepository).save(argumentCaptor.capture());
        AirTicket capturedValue = argumentCaptor.getValue();
        assertEquals(airTicket, capturedValue);
    }

    @Test
    void testRemoveAirTicket() {
        airTicketService.removeAirTicket(1L);
        verify(airTicketRepository).deleteById(1L);
    }
}