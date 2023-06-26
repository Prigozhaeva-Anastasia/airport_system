package com.example.course_project.service.impl;

import com.example.course_project.entity.Airline;
import com.example.course_project.entity.DirectFlight;
import com.example.course_project.entity.TransitFlight;
import com.example.course_project.repository.AirlineRepository;
import com.example.course_project.repository.DirectFlightRepository;
import com.example.course_project.service.DirectFlightService;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class DirectFlightServiceImpl implements DirectFlightService {
    private DirectFlightRepository directFlightRepository;
    private AirlineRepository airlineRepository;

    public DirectFlightServiceImpl(DirectFlightRepository directFlightRepository, AirlineRepository airlineRepository) {
        this.directFlightRepository = directFlightRepository;
        this.airlineRepository = airlineRepository;
    }

    @Override
    public DirectFlight createDirectFlight(String flightNumber, String arrivalAirport, String arrivalCity, LocalDate arrivalDate, LocalTime arrivalTime, String departureAirport, String departureCity, LocalDate departureDate, LocalTime departureTime, double priceOfTickets, Long airlineId) {
        Airline airline = airlineRepository.findById(airlineId).orElseThrow(()->new EntityNotFoundException("Airline with id " + airlineId + " Not Found"));
        return directFlightRepository.save(new DirectFlight(flightNumber, arrivalAirport, arrivalCity, arrivalDate, arrivalTime, departureAirport, departureCity, departureDate, departureTime, priceOfTickets, airline));
    }

    @Override
    public DirectFlight findDirectFlightById(Long id) {
        return directFlightRepository.findById(id).orElseThrow(()->new EntityNotFoundException("DirectFlight with id " + id + " Not Found"));
    }

    @Override
    public DirectFlight findDirectFlightByFlightNumber(String flightNumber) {
        return directFlightRepository.getDirectFlightByFlightNumber(flightNumber);
    }

    @Override
    public List<DirectFlight> findDirectFlightsByDirection(String arrivalCity, String departureCity) {
        return directFlightRepository.getDirectFlightsByDirection(arrivalCity, departureCity);
    }

    @Override
    public List<DirectFlight> findDirectFlightByDirectionAndDate(String arrivalCity, String departureCity, LocalDate arrivalDate) {
        List<DirectFlight> resultList = new ArrayList<>();
        if (arrivalDate.isAfter(LocalDate.now()) || arrivalDate.equals(LocalDate.now())) {
            List<DirectFlight> directFlights = directFlightRepository.getDirectFlightsByDirection(arrivalCity, departureCity);
            for (DirectFlight directFlight : directFlights) {
                if (directFlight.getArrivalDate().equals(arrivalDate)) resultList.add(directFlight);
            }
        }
        return resultList;
    }

    @Override
    public List<DirectFlight> fetchAll() {
        return directFlightRepository.findAll();
    }

    @Override
    public DirectFlight createOrUpdateDirectFlight(DirectFlight directFlight) {
        return directFlightRepository.save(directFlight);
    }

    @Override
    public void removeDirectFlight(Long directFlightId) {
        directFlightRepository.deleteById(directFlightId);
    }

    @Override
    public String countDuration(LocalDate departureDate, LocalDate arrivalDate, LocalTime departureTime, LocalTime arrivalTime) {
        String dateStart = departureDate.toString() + " " + departureTime.toString();
        String dateStop = arrivalDate.toString() + " " + arrivalTime.toString();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date d1 = null;
        Date d2 = null;
        try {
            d1 = format.parse(dateStart);
            d2 = format.parse(dateStop);
            long diff = d2.getTime() - d1.getTime();
            long diffMinutes = diff / (60 * 1000) % 60;
            long diffHours = diff / (60 * 60 * 1000) % 24;
            long diffDays = diff / (24 * 60 * 60 * 1000);
            if (diffDays == 0) return Math.abs(diffHours) + " ч " + Math.abs(diffMinutes) + " мин";
            else return Math.abs(diffDays) + " дн " + Math.abs(diffHours) + " ч " + Math.abs(diffMinutes) + " мин";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
}
