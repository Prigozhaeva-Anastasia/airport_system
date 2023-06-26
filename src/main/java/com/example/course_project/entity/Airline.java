package com.example.course_project.entity;


import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "airlines")
@Data
@Builder
@AllArgsConstructor
@EqualsAndHashCode(exclude={"flights", "reviews"})
public class Airline {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;
    @NotBlank(message = "Поле не должно быть пустым")
    @Size(max = 40, message = "Поле 'Нзвание авиакомпании' может включать не более 40 символов")
    @Column(name="airline_name")
    private String airlineName;
    @Column(name="rating")
    private double rating;
    @Column(name = "path_to_img")
    private String pathToImg;
    @ToString.Exclude
    @OneToMany(mappedBy = "airline", cascade = CascadeType.ALL)
    private List<Flight> flights = new ArrayList<>();
    @ToString.Exclude
    @OneToMany(mappedBy = "airline", cascade = CascadeType.ALL)
    private List<Review> reviews = new ArrayList<>();

    public Airline() {
    }
}
