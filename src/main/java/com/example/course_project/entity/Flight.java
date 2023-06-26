package com.example.course_project.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Data
@NoArgsConstructor
@EqualsAndHashCode(exclude={"airline"})
public abstract class Flight {
    @Id
    @GenericGenerator(name = "idFlight", strategy = "increment")
    @GeneratedValue(generator = "idFlight")
    @Column(name="id")
    protected Long id;
    @NotBlank(message = "Поле не должно быть пустым")
    @Pattern(regexp = "^$|^[A-Z]{2}[0-9]{2,4}$", message = "Поле 'Номер рейса' должно содержать 2 заглавные буквы латинского алфавита и цифры от 2-значных до 5-ти")
    @Column(name="flight_number")
    protected String flightNumber;
    @NotBlank(message = "Поле не должно быть пустым")
    @Pattern(regexp = "^$|^[A-Za-zА-Яа-я -]+$", message = "Поле 'Аэропорт вылета' должно содержать только буквы русского и латинского алфавита и '-'")
    @Column(name="arrival_airport")
    protected String arrivalAirport;
    @NotBlank(message = "Поле не должно быть пустым")
    @Pattern(regexp = "^$|^[A-Za-zА-Яа-я -]+$", message = "Поле 'Город вылета' должно содержать только буквы русского и латинского алфавита и '-'")
    @Column(name="arrival_city")
    protected String arrivalCity;
    @NotNull(message = "Поле не должно быть пустым")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name="arrival_date")
    protected LocalDate arrivalDate;
    @NotNull(message = "Поле не должно быть пустым")
    @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
    @Column(name="arrival_time")
    protected LocalTime arrivalTime;
    @NotBlank(message = "Поле не должно быть пустым")
    @Pattern(regexp = "^$|^[A-Za-zА-Яа-я -]+$", message = "Поле 'Аэропорт посадки' должно содержать только буквы русского и латинского алфавита и '-'")
    @Column(name="departure_airport")
    protected String departureAirport;
    @NotBlank(message = "Поле не должно быть пустым")
    @Pattern(regexp = "^$|^[A-Za-zА-Яа-я -]+$", message = "Поле 'Город посадки' должно содержать только буквы русского и латинского алфавита и '-'")
    @Column(name="departure_city")
    protected String departureCity;
    @NotNull(message = "Поле не должно быть пустым")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name="departure_date")
    protected LocalDate departureDate;
    @NotNull(message = "Поле не должно быть пустым")
    @Column(name="departure_time")
    @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
    protected LocalTime departureTime;
    @Column(name="price_of_ticket")
    protected double priceOfTickets;
    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "airline_id")
    protected Airline airline;

    public Flight(String flightNumber, String arrivalAirport, String arrivalCity, LocalDate arrivalDate, LocalTime arrivalTime, String departureAirport, String departureCity, LocalDate departureDate, LocalTime departureTime, double priceOfTickets, Airline airline) {
        this.flightNumber = flightNumber;
        this.arrivalAirport = arrivalAirport;
        this.arrivalCity = arrivalCity;
        this.arrivalDate = arrivalDate;
        this.arrivalTime = arrivalTime;
        this.departureAirport = departureAirport;
        this.departureCity = departureCity;
        this.departureDate = departureDate;
        this.departureTime = departureTime;
        this.priceOfTickets = priceOfTickets;
        this.airline = airline;
    }
}
