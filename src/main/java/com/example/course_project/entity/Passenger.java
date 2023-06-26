package com.example.course_project.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "passengers")
@Data
@NoArgsConstructor
@EqualsAndHashCode(exclude={"user", "cards", "tickets", "reviews"})
public class Passenger implements Comparable<Passenger>{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "gender")
    private String gender;
    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;
    @NotBlank(message = "Поле не должно быть пустым")
    @Pattern(regexp = "^$|^[A-Za-zА-Яа-я -]+$", message = "Поле должно содержать только буквы")
    @Column(name = "citizenship")
    private String citizenship;
    @NotBlank(message = "Поле не должно быть пустым")
    @Pattern(regexp = "^$|^[A-Z]{2}\\d{7}$", message = "Поле должно содержать 2 заглавные лат. буквы и 7 цифр")
    @Column(name = "passport_number")
    private String passportNumber;
    @NotBlank(message = "Поле не должно быть пустым")
    @Pattern(regexp = "^$|^(\\+375|80)(29|25|44|33)(\\d{3})(\\d{2})(\\d{2})$", message = "Поле должно быть введено в формате +375(29)(44)(33)(25)_______")
    @Column(name = "phone_number")
    private String phoneNumber;
    @ToString.Exclude
    @OneToOne(cascade = CascadeType.ALL)
    @Valid
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;
    @ToString.Exclude
    @OneToMany(mappedBy = "passenger")
    private List<Card> cards = new ArrayList<>();
    @ToString.Exclude
    @OneToMany(mappedBy = "passenger")
    private List<AirTicket> tickets = new ArrayList<>();
    @ToString.Exclude
    @OneToMany(mappedBy = "passenger")
    private List<Review> reviews = new ArrayList<>();

    public Passenger(String gender, LocalDate dateOfBirth, String citizenship, String passportNumber, String phoneNumber, User user) {
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.citizenship = citizenship;
        this.passportNumber = passportNumber;
        this.phoneNumber = phoneNumber;
        this.user = user;
    }

    @Override
    public int compareTo(Passenger obj) {
        return this.user.getLastName().compareTo(obj.user.getLastName());
    }
}