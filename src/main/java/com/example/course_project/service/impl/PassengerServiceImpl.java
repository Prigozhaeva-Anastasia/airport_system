package com.example.course_project.service.impl;

import com.example.course_project.entity.Passenger;
import com.example.course_project.entity.User;
import com.example.course_project.repository.PassengerRepository;
import com.example.course_project.repository.UserRepository;
import com.example.course_project.service.PassengerService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
public class PassengerServiceImpl implements PassengerService {
    private PassengerRepository passengerRepository;
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    public PassengerServiceImpl(PassengerRepository passengerRepository, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.passengerRepository = passengerRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Passenger createPassenger(Long userId, String gender, LocalDate dateOfBirth, String citizenship, String passportNumber, String phoneNumber) {
        User user = userRepository.findById(userId).orElseThrow(()->new EntityNotFoundException("User with id " + userId + " Not Found"));
        return passengerRepository.save(new Passenger(gender, dateOfBirth, citizenship, passportNumber, phoneNumber, user));
    }

    @Override
    public Passenger findPassengerById(Long id) {
        return passengerRepository.findById(id).orElseThrow(()->new EntityNotFoundException("Passenger with id " + id + " Not Found"));
    }

    @Override
    public Passenger findPassengerByEmail(String email) {
        return passengerRepository.findPassengerByEmail(email);
    }

    @Override
    public Passenger findPassengerByPassportNumber(String passportNumber) {
        return passengerRepository.findPassengerByPassportNumber(passportNumber);
    }

    @Override
    public Passenger findPassengerByPhoneNumber(String phoneNumber) {
        return passengerRepository.findPassengerByPhoneNumber(phoneNumber);
    }

    @Override
    public List<Passenger> findPassengersByName(String name) {
        if (name.contains(" ")) {
            String[] firstNameAndLastName = name.split(" ");
            return passengerRepository.findPassengersByName(firstNameAndLastName[0], firstNameAndLastName[1]);
        } else {
            List<Passenger> passengerList = passengerRepository.findPassengersByLastName(name);
            if (passengerList.size() != 0) return  passengerList;
            else return passengerRepository.findPassengersByFirstName(name);
        }
    }

    @Override
    public List<Passenger> fetchAll() {
        return passengerRepository.findAll();
    }

    @Override
    public Passenger createOrUpdatePassenger(Passenger passenger) {
        passenger.getUser().setPassword(passwordEncoder.encode(passenger.getUser().getPassword()));
        return passengerRepository.save(passenger);
    }

    @Override
    public void removePassenger(Long passengerId) {
        passengerRepository.deleteById(passengerId);
    }

    @Override
    public Passenger updatePassengerWithSplit(Passenger passenger, Long userId,  String dateOfBirth, String lastName, String firstName, String password) {
        String[] genderArr = passenger.getGender().split(",");
        passenger.setGender(genderArr[genderArr.length - 1]);
        String[] dataArr = dateOfBirth.split(",");
        dateOfBirth = dataArr[dataArr.length - 1];
        String[] lastNameArr = lastName.split(",");
        lastName = lastNameArr[lastNameArr.length - 1];
        String[] firstNameArr = firstName.split(",");
        firstName = firstNameArr[firstNameArr.length - 1];
        String[] passwordArr = password.split(",");
        password = passwordArr[passwordArr.length - 1];
        User user = userRepository.findById(userId).orElseThrow(()->new EntityNotFoundException("User with id " + userId + " Not Found"));
        user.setLastName(lastName);
        user.setFirstName(firstName);
        user.setPassword(passwordEncoder.encode(password));
        passenger.setDateOfBirth(LocalDate.parse(dateOfBirth));
        passenger.setUser(user);
        passengerRepository.save(passenger);
        return null;
    }

}
