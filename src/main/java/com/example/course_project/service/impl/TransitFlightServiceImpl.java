package com.example.course_project.service.impl;

import com.example.course_project.entity.Airline;
import com.example.course_project.entity.TransitFlight;
import com.example.course_project.repository.AirlineRepository;
import com.example.course_project.repository.TransitFlightRepository;
import com.example.course_project.service.TransitFlightService;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimerTask;

@Service
@Transactional
public class TransitFlightServiceImpl implements TransitFlightService {
    private TransitFlightRepository transitFlightRepository;
    private AirlineRepository airlineRepository;

    public TransitFlightServiceImpl(TransitFlightRepository transitFlightRepository, AirlineRepository airlineRepository) {
        this.transitFlightRepository = transitFlightRepository;
        this.airlineRepository = airlineRepository;
    }

    @Override
    public TransitFlight createTransitFlight(String flightNumber, String arrivalAirport, String arrivalCity, LocalDate arrivalDate, LocalTime arrivalTime, String departureAirport, String departureCity, LocalDate departureDate, LocalTime departureTime, Long airlineId, double priceOfTickets, int countOfTransfers) {
        Airline airline = airlineRepository.findById(airlineId).orElseThrow(()->new EntityNotFoundException("Airline with id " + airlineId + " Not Found"));
        return transitFlightRepository.save(new TransitFlight(flightNumber, arrivalAirport, arrivalCity, arrivalDate, arrivalTime, departureAirport, departureCity, departureDate, departureTime, priceOfTickets, countOfTransfers, airline));
    }

    @Override
    public TransitFlight findTransitFlightById(Long id) {
        return transitFlightRepository.findById(id).orElseThrow(()->new EntityNotFoundException("TransitFlight with id " + id + " Not Found"));
    }

    @Override
    public TransitFlight findTransitFlightByFlightNumber(String flightNumber) {
        return transitFlightRepository.getTransitFlightByFlightNumber(flightNumber);
    }

    @Override
    public List<TransitFlight> findTransitFlightsByDirection(String arrivalCity, String departureCity) {
        return transitFlightRepository.getTransitFlightsByDirection(arrivalCity, departureCity);
    }

    @Override
    public List<TransitFlight> findTransitFlightByDirectionAndDate(String arrivalCity, String departureCity, LocalDate arrivalDate) {
        List<TransitFlight> resultList = new ArrayList<>();
        if (arrivalDate.isAfter(LocalDate.now()) || arrivalDate.equals(LocalDate.now())) {
            List<TransitFlight> transitFlights = transitFlightRepository.getTransitFlightsByDirection(arrivalCity, departureCity);
            for (TransitFlight transitFlight : transitFlights) {
                if (transitFlight.getArrivalDate().equals(arrivalDate)) {
                    resultList.add(transitFlight);
                }
            }
        }
        return resultList;
    }

    @Override
    public List<TransitFlight> fetchAll() {
        return transitFlightRepository.findAll();
    }

    @Override
    public TransitFlight createOrUpdateTransitFlight(TransitFlight transitFlight) {
        return transitFlightRepository.save(transitFlight);
    }

    @Override
    public void removeTransitFlight(Long transitFlightId) {
        transitFlightRepository.deleteById(transitFlightId);
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
