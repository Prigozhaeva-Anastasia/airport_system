package com.example.course_project.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "transit_flights")
@Data
@NoArgsConstructor
@EqualsAndHashCode(exclude={"transferList", "airTickets"})
public class TransitFlight extends Flight{
    @Column(name = "count_of_transfers")
    private int countOfTransfers;
    @ToString.Exclude
    @OneToMany(mappedBy = "transitFlight", cascade = CascadeType.ALL)
    private List<Transfer> transferList = new ArrayList<>();
    @ToString.Exclude
    @OneToMany(mappedBy = "transitFlight")
    private List<AirTicket> airTickets = new ArrayList<>();
    public TransitFlight(String flightNumber, String arrivalAirport, String arrivalCity, LocalDate arrivalDate, LocalTime arrivalTime, String departureAirport, String departureCity, LocalDate departureDate, LocalTime departureTime, double priceOfTickets, int countOfTransfers, Airline airline) {
        super(flightNumber, arrivalAirport, arrivalCity, arrivalDate, arrivalTime, departureAirport, departureCity, departureDate, departureTime, priceOfTickets, airline);
        this.countOfTransfers = countOfTransfers;
    }
}
