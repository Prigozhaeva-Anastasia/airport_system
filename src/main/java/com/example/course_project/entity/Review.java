package com.example.course_project.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "reviews")
@Data
@Builder
@AllArgsConstructor
@EqualsAndHashCode(exclude={"airline", "passenger"})
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "airline_id", referencedColumnName = "id")
    private Airline airline;
    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "passenger_id", referencedColumnName = "id")
    private Passenger passenger;
    @Column(name = "check_in_speed")
    private double checkInSpeed;
    @Column(name = "state_of_salon")
    private double state_of_salon;
    @Column(name = "crew_work")
    private double crewWork;
    @Column(name = "quality_of_flight_meals")
    private double qualityOfFlightMeals;
    @Column(name = "overall_score")
    private double overallScore;
    @Column(name = "remark")
    private String remark;

    public Review() {
    }
}
