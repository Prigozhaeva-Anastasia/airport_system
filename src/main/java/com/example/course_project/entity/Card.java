package com.example.course_project.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;

@Entity
@Table(name = "cards")
@Data
@NoArgsConstructor
@EqualsAndHashCode(exclude={"passenger"})
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;
    @Column(name = "number")
    private long number;
    @Column(name = "month")
    private int month;
    @Column(name = "year")
    private int year;
    @Column(name = "balance")
    private double balance;
    @ToString.Exclude
    @ManyToOne()
    @JoinColumn(name = "passenger_id", referencedColumnName = "id")
    @JsonIgnore
    private Passenger passenger;

    public Card(long number, int month, int year, double balance, Passenger passenger) {
        this.number = number;
        this.month = month;
        this.year = year;
        this.balance = balance;
        this.passenger = passenger;
    }
}
