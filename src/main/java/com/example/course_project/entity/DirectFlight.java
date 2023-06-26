package com.example.course_project.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "direct_flights")
@Data
@NoArgsConstructor
@EqualsAndHashCode(exclude={"airTickets"})
public class DirectFlight extends Flight{
    @ToString.Exclude
    @OneToMany(mappedBy = "directFlight")
    private List<AirTicket> airTickets = new ArrayList<>();

    public DirectFlight(String flightNumber, String arrivalAirport, String arrivalCity, LocalDate arrivalDate, LocalTime arrivalTime, String departureAirport, String departureCity, LocalDate departureDate, LocalTime departureTime, double priceOfTickets, Airline airline) {
        super(flightNumber, arrivalAirport, arrivalCity, arrivalDate, arrivalTime, departureAirport, departureCity, departureDate, departureTime, priceOfTickets, airline);
    }
}
