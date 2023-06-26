package com.example.course_project.entity;


import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "airTickets")
@Data
@Builder
@AllArgsConstructor
@EqualsAndHashCode(exclude={"passenger", "directFlight", "transitFlight"})
public class AirTicket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;
    @Column(name = "price")
    private double price;
    @Column(name = "seat_number")
    private int seatNumber;
    @Column(name = "type_of_ticket")
    private String typeOfTicket;
    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "passenger_id", referencedColumnName = "id")
    private Passenger passenger;
    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "direct_flight_id", referencedColumnName = "id")
    private DirectFlight directFlight;
    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "transit_flight_id", referencedColumnName = "id")
    private TransitFlight transitFlight;
    public AirTicket() {
    }
}
