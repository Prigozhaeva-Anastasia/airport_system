package com.example.course_project.entity;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalTime;

@Entity
@Table(name = "transfers")
@Data
@NoArgsConstructor
@EqualsAndHashCode(exclude={"transitFlight"})
public class Transfer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @NotBlank(message = "Поле не должно быть пустым")
    @Pattern(regexp = "^$|^[A-Za-zА-Яа-я -]+$", message = "Поле 'Город пересадки' должно содержать только буквы русского и латинского алфавита")
    @Column(name = "name_of_city")
    private String nameOfCity;
    @NotNull(message = "Поле не должно быть пустым")
    @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
    @Column(name = "arrival_time")
    private LocalTime arrivalTime;
    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "transit_flight_id")
    private TransitFlight transitFlight;

    public Transfer(String nameOfCity, LocalTime arrivalTime, TransitFlight transitFlight) {
        this.nameOfCity = nameOfCity;
        this.arrivalTime = arrivalTime;
        this.transitFlight = transitFlight;
    }
}