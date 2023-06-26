package com.example.course_project.entity;

import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.*;
import java.util.concurrent.CopyOnWriteArraySet;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@EqualsAndHashCode(exclude={"roles", "passenger"})
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @NotBlank(message = "Поле не должно быть пустым")
    @Pattern(regexp = "$|^(?=.*[A-Z].*[A-Z])(?=.*[0-9])(?=.*[a-z])[0-9a-zA-Z!@#$%^&*/.]{8,}$", message = "Поле должно содержать 2 заглавные латинские буквы латинского, строчные и цифры (>8 симв)")
    @Column(name = "password")
    private String password;
    @NotBlank(message = "Поле не должно быть пустым")
    @Pattern(regexp = "^$|^\\w+([\\.-]?\\w+)*@\\w+([\\.-]?\\w+)*(\\.\\w{2,8})+$", message = "Поле должно содержать локальное имя, символ '@' и имя домена(2-8)")
    @Column(name = "email")
    private String email;
    @NotBlank(message = "Поле не должно быть пустым")
    @Pattern(regexp = "^$|^[A-Za-zА-Яа-я]+$", message = "Поле должно содержать только буквы")
    @Column(name = "last_name")
    private String lastName;
    @NotBlank(message = "Поле не должно быть пустым")
    @Pattern(regexp = "^$|^[A-Za-zА-Яа-я]+$", message = "Поле должно содержать только буквы")
    @Column(name = "first_name")
    private String firstName;
    @ToString.Exclude
    @ManyToMany(cascade = CascadeType.ALL, fetch=FetchType.EAGER)
    @JoinTable(name = "user_role",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id")})
    private Set<Role> roles = new HashSet<>();
    @ToString.Exclude
    @OneToOne(mappedBy = "user")
    private Passenger passenger;

    public User(String password, String email, String lastName, String firstName) {
        this.password = password;
        this.email = email;
        this.lastName = lastName;
        this.firstName = firstName;
    }

    public void assignRoleToUser(Role role) {
        this.roles.add(role);
        role.getUsers().add(this);
    }
    public void removeRoleFromUser(Role role) {
        this.roles.remove(role);
        role.getUsers().remove(this);
    }
}