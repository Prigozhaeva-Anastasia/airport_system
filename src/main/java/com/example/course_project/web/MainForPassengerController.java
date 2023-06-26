package com.example.course_project.web;

import com.example.course_project.entity.*;
import com.example.course_project.service.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.security.Principal;
import java.text.DecimalFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

import static com.example.course_project.constants.Constants.*;

@Controller
@RequestMapping(value = "/mainForUser")
public class MainForPassengerController {
    private DirectFlightService directFlightService;
    private TransitFlightService transitFlightService;
    private AirlineService airlineService;
    private AirTicketService airTicketService;
    private PassengerService passengerService;
    private CardService cardService;
    private UserService userService;

    public MainForPassengerController(DirectFlightService directFlightService, TransitFlightService transitFlightService, AirlineService airlineService, AirTicketService airTicketService, PassengerService passengerService, CardService cardService, UserService userService) {
        this.directFlightService = directFlightService;
        this.transitFlightService = transitFlightService;
        this.airlineService = airlineService;
        this.airTicketService = airTicketService;
        this.passengerService = passengerService;
        this.cardService = cardService;
        this.userService = userService;
    }

    @PostMapping(value = "/foundDirectFlights")
    @PreAuthorize("hasAuthority('Passenger')")
    public String foundDirectFlights(Model model, String arrivalCity, String departureCity, String arrivalDate) {
        List<DirectFlight> directFlights = directFlightService.findDirectFlightByDirectionAndDate(arrivalCity, departureCity, LocalDate.parse(arrivalDate));
        if (directFlights.size() != 0) {
            String[] timeOnWay = new String[directFlights.size()];
            for (int i = 0; i < directFlights.size(); i++) {
                timeOnWay[i] = directFlightService.countDuration(directFlights.get(i).getDepartureDate(), directFlights.get(i).getArrivalDate(), directFlights.get(i).getDepartureTime(), directFlights.get(i).getArrivalTime());
            }
            model.addAttribute("listDurations", timeOnWay);
        }
        model.addAttribute("condition", Boolean.FALSE);
        model.addAttribute(LIST_FLIGHTS, directFlights);
        return "user-views/foundFlights";
    }

    @PostMapping(value = "/foundTransitFlights")
    @PreAuthorize("hasAuthority('Passenger')")
    public String foundTransitFlights(Model model, String arrivalCity, String departureCity, String arrivalDate) {
        List<TransitFlight> transitFlights = transitFlightService.findTransitFlightByDirectionAndDate(arrivalCity, departureCity, LocalDate.parse(arrivalDate));
        if (transitFlights.size() != 0) {
            String[] timeOnWay = new String[transitFlights.size()];
            for (int i = 0; i < transitFlights.size(); i++) {
                timeOnWay[i] = transitFlightService.countDuration(transitFlights.get(i).getDepartureDate(), transitFlights.get(i).getArrivalDate(), transitFlights.get(i).getDepartureTime(), transitFlights.get(i).getArrivalTime());
            }
            model.addAttribute("listDurations", timeOnWay);
        }
        model.addAttribute("condition", Boolean.TRUE);
        model.addAttribute(LIST_FLIGHTS, transitFlights);
        return "user-views/foundFlights";
    }

    @GetMapping(value = "/reviews")
    @PreAuthorize("hasAuthority('Passenger')")
    public String reviews(Model model, Long airlineId) {
        Airline airline = airlineService.findAirlineById(airlineId);
        model.addAttribute(LIST_REVIEWS, airline.getReviews());
        model.addAttribute(AIRLINE, airline);
        return "user-views/reviewsOfAirline";
    }

    @PostMapping(value = "/planeScheme")
    @PreAuthorize("hasAuthority('Passenger')")
    public String planeScheme(Model model, String flightNumber) {
        List<AirTicket> airTickets = airTicketService.fetchAll();
        List<AirTicket> airTicketsForFlight = new CopyOnWriteArrayList<>();
        for (AirTicket airTicket : airTickets) {
            if ((airTicket.getDirectFlight() != null && flightNumber.equals(airTicket.getDirectFlight().getFlightNumber())) ||
                    airTicket.getTransitFlight() != null && flightNumber.equals(airTicket.getTransitFlight().getFlightNumber())) {
                airTicketsForFlight.add(airTicket);
            }
        }
        int[] upperLeftWingOfAirplane = new int[] { 1, 2, 3, 4, 9, 10, 11, 12, 17, 18, 19, 20, 25, 26, 27, 28, 33, 34, 35, 36};
        int[] upperRightWingOfAirplane = new int[] { 5, 6, 7, 8, 13, 14, 15, 16, 21, 22, 23, 34, 29, 30, 31, 32, 37, 38, 39, 40};
        int[] bottomLeftWingOfAirplane = new int[] { 41, 42, 45, 46};
        int[] bottomRightWingOfAirplane = new int[] { 43, 44, 47, 48};
        if (airTicketsForFlight.size() != 0) {
            if (airTicketsForFlight.get(0).getDirectFlight() != null)
                model.addAttribute(LIST_AIR_TICKETS_FOR_DIRECT_FLIGHT, airTicketsForFlight);
            else model.addAttribute(LIST_AIR_TICKETS_FOR_TRANSIT_FLIGHT, airTicketsForFlight);
        }
        model.addAttribute("upperLeftWingOfAirplane", upperLeftWingOfAirplane);
        model.addAttribute("upperRightWingOfAirplane", upperRightWingOfAirplane);
        model.addAttribute("bottomLeftWingOfAirplane", bottomLeftWingOfAirplane);
        model.addAttribute("bottomRightWingOfAirplane", bottomRightWingOfAirplane);
        model.addAttribute("flightNumber", flightNumber);
        return "user-views/planeScheme";
    }

    @PostMapping(value = "/confirmationOfOrder")
    @PreAuthorize("hasAuthority('Passenger')")
    public String confirmationOfOrder(Model model, int seatNumber, String flightNumber, Principal principal) {
        String typeOfTicket = null;
        if (seatNumber >= 1 && seatNumber <= 40) typeOfTicket = "эконом-класс";
        else typeOfTicket = "бизнес-класс";
        Passenger passenger = passengerService.findPassengerByEmail(principal.getName());
        DirectFlight directFlight = directFlightService.findDirectFlightByFlightNumber(flightNumber);
        TransitFlight transitFlight = transitFlightService.findTransitFlightByFlightNumber(flightNumber);
        AirTicket airTicket = null;
        String timeOnWay = null, transfers = null;
        if (directFlight != null) {
            if (typeOfTicket.equals("бизнес-класс")) directFlight.setPriceOfTickets(5 * directFlight.getPriceOfTickets());
            airTicket = AirTicket.builder().price(directFlight.getPriceOfTickets()).directFlight(directFlight).seatNumber(seatNumber).typeOfTicket(typeOfTicket).passenger(passenger).build();
            timeOnWay = directFlightService.countDuration(directFlight.getDepartureDate(), directFlight.getArrivalDate(), directFlight.getDepartureTime(), directFlight.getArrivalTime());
            model.addAttribute("transfers", "Без пересадок");
            model.addAttribute("condition", Boolean.TRUE);
        }
        else {
            if (typeOfTicket.equals("бизнес-класс")) transitFlight.setPriceOfTickets(5 * transitFlight.getPriceOfTickets());
            airTicket = AirTicket.builder().price(transitFlight.getPriceOfTickets()).transitFlight(transitFlight).seatNumber(seatNumber).typeOfTicket(typeOfTicket).passenger(passenger).build();
            timeOnWay = transitFlightService.countDuration(transitFlight.getDepartureDate(), transitFlight.getArrivalDate(), transitFlight.getDepartureTime(), transitFlight.getArrivalTime());
            for (Transfer transfer : airTicket.getTransitFlight().getTransferList())
                if (transfers != null ) transfers = transfers + " - " + transfer.getNameOfCity();
            else  transfers = transfer.getNameOfCity();
            model.addAttribute("transfers", transfers);
            model.addAttribute("condition", Boolean.FALSE);
        }
        model.addAttribute("timeOnWay", timeOnWay);
        model.addAttribute(AIR_TICKET, airTicket);
        return "user-views/confirmationOfOrder";
    }

    @GetMapping(value = "/personalData")
    @PreAuthorize("hasAuthority('Passenger')")
    public String personalData(Model model, Principal principal) {
        Passenger passenger = passengerService.findPassengerByEmail(principal.getName());
        model.addAttribute(PASSENGER, passenger);
        return "user-views/personalData";
    }

    @GetMapping(value = "/cards")
    @PreAuthorize("hasAuthority('Passenger')")
    public String cards(Model model, Principal principal) {
        Passenger passenger = passengerService.findPassengerByEmail(principal.getName());
        model.addAttribute(LIST_CARDS, passenger.getCards());
        model.addAttribute(CARD, new Card());
        return "user-views/cards";
    }

    @GetMapping(value = "/cards/delete")
    @PreAuthorize("hasAuthority('Passenger')")
    public String deleteCard(Long cardId) {
        cardService.removeCard(cardId);
        return "redirect:/mainForUser/cards";
    }

    @PostMapping(value = "/chooseCard")
    @PreAuthorize("hasAuthority('Passenger')")
    public String chooseCard(Model model, String flightNumber, String typeOfTicket, int seatNumber, double price, Principal principal) {
        Passenger passenger = passengerService.findPassengerByEmail(principal.getName());
        DirectFlight directFlight = directFlightService.findDirectFlightByFlightNumber(flightNumber);
        TransitFlight transitFlight = transitFlightService.findTransitFlightByFlightNumber(flightNumber);
        AirTicket airTicket = AirTicket.builder().typeOfTicket(typeOfTicket).seatNumber(seatNumber).price(price).passenger(passenger).directFlight(directFlight). transitFlight(transitFlight).build();
        model.addAttribute(LIST_CARDS, passenger.getCards());
        if (directFlight != null) model.addAttribute("flightNumber", directFlight.getFlightNumber());
        else  model.addAttribute("flightNumber", transitFlight.getFlightNumber());
        model.addAttribute(AIR_TICKET, airTicket);
        return "user-views/chooseCard";
    }

    @PostMapping(value = "/payment")
    @PreAuthorize("hasAuthority('Passenger')")
    public String payment(String flightNumber, String typeOfTicket, int seatNumber, double price, Long cardId, Principal principal) {
        Passenger passenger = passengerService.findPassengerByEmail(principal.getName());
        DirectFlight directFlight = directFlightService.findDirectFlightByFlightNumber(flightNumber);
        TransitFlight transitFlight = transitFlightService.findTransitFlightByFlightNumber(flightNumber);
        Card card = cardService.findCardById(cardId);
        double remainder = Double.parseDouble(new DecimalFormat("#0.00").format(card.getBalance() - price));
        card.setBalance(remainder);
        cardService.createOrUpdateCard(card);
        AirTicket airTicket = AirTicket.builder().typeOfTicket(typeOfTicket).seatNumber(seatNumber).price(price).passenger(passenger).directFlight(directFlight). transitFlight(transitFlight).build();
        airTicketService.createOrUpdateAirTicket(airTicket);
        return "redirect:/";
    }

    @GetMapping(value = "/formUpdate")
    @PreAuthorize("hasAuthority('Passenger')")
    public String updatePersonalData(Model model, Principal principal) {
        Passenger passenger = passengerService.findPassengerByEmail(principal.getName());
        model.addAttribute("confirmation");
        model.addAttribute("date", passenger.getDateOfBirth());
        model.addAttribute(PASSENGER, passenger);
        return "user-views/formUpdate";
    }

    @PostMapping(value = "/update")
    @PreAuthorize("hasAuthority('Passenger')")
    public String update(Passenger passenger, Long userId, String date, String lastName, String firstName, String password) {
        passengerService.updatePassengerWithSplit(passenger, userId, date, lastName, firstName, password);
        return "redirect:/mainForUser/personalData";
    }

    @GetMapping(value = "/myOrders")
    @PreAuthorize("hasAuthority('Passenger')")
    public String myOrders(Model model, Principal principal) {
        model.addAttribute("condition", Boolean.TRUE);
        model.addAttribute("isActive", Boolean.TRUE);
        model.addAttribute(LIST_AIR_TICKETS_FOR_DIRECT_FLIGHT, airTicketService.getActiveAirTicketsForDirectFlight(principal.getName()));
        return "user-views/myOrders";
    }

    @GetMapping(value = "/myOrders/activeDirectAirTickets")
    @PreAuthorize("hasAuthority('Passenger')")
    public String activeDirectAirTickets(Model model, Principal principal) {
        model.addAttribute("condition", Boolean.TRUE);
        model.addAttribute("isActive", Boolean.TRUE);
        model.addAttribute(LIST_AIR_TICKETS_FOR_DIRECT_FLIGHT, airTicketService.getActiveAirTicketsForDirectFlight(principal.getName()));
        return "user-views/myOrders";
    }

    @GetMapping(value = "/myOrders/activeTransitAirTickets")
    @PreAuthorize("hasAuthority('Passenger')")
    public String activeTransitAirTickets(Model model, Principal principal) {
        model.addAttribute("condition", Boolean.FALSE);
        model.addAttribute("isActive", Boolean.TRUE);
        model.addAttribute(LIST_AIR_TICKETS_FOR_TRANSIT_FLIGHT, airTicketService.getActiveAirTicketsForTransitFlight(principal.getName()));
        return "user-views/myOrders";
    }

    @GetMapping(value = "/myOrders/archiveDirectAirTickets")
    @PreAuthorize("hasAuthority('Passenger')")
    public String archiveDirectAirTickets(Model model, Principal principal) {
        model.addAttribute("condition", Boolean.TRUE);
        model.addAttribute("isActive", Boolean.FALSE);
        model.addAttribute(LIST_AIR_TICKETS_FOR_DIRECT_FLIGHT, airTicketService.getArchiveAirTicketsForDirectFlight(principal.getName()));
        return "user-views/myOrders";
    }

    @GetMapping(value = "/myOrders/archiveTransitAirTickets")
    @PreAuthorize("hasAuthority('Passenger')")
    public String archiveTransitAirTickets(Model model, Principal principal) {
        model.addAttribute("condition", Boolean.FALSE);
        model.addAttribute("isActive", Boolean.FALSE);
        model.addAttribute(LIST_AIR_TICKETS_FOR_TRANSIT_FLIGHT, airTicketService.getArchiveAirTicketsForTransitFlight(principal.getName()));
        return "user-views/myOrders";
    }

    @GetMapping(value = "/myOrders/remoteTickets")
    @PreAuthorize("hasAuthority('Passenger')")
    public String archiveRemoteAirTickets(Model model, Principal principal) {
        Passenger passenger = passengerService.findPassengerByEmail(principal.getName());
        List<AirTicket> remoteAirTickets = passenger.getTickets().stream().filter(airTicket -> airTicket.getDirectFlight() == null && airTicket.getTransitFlight() == null).collect(Collectors.toList());
        model.addAttribute("isRemote", Boolean.TRUE);
        model.addAttribute(LIST_AIR_TICKETS, remoteAirTickets);
        return "user-views/myOrders";
    }

    @PostMapping(value = "/refund")
    @PreAuthorize("hasAuthority('Passenger')")
    public String refund(Long airTicketId, Long cardId) {
        AirTicket airTicket = airTicketService.findAirTicketById(airTicketId);
        Card card = cardService.findCardById(cardId);
        card.setBalance(card.getBalance() + airTicket.getPrice());
        cardService.createOrUpdateCard(card);
        if (airTicket.getDirectFlight() != null) return "redirect:/airTickets/forDirectFlight/delete?airTicketId=" + airTicketId;
        return "redirect:/airTickets/forTransitFlight/delete?airTicketId=" + airTicketId;
    }
    @GetMapping(value = "/chooseCardToReturn")
    @PreAuthorize("hasAuthority('Passenger')")
    public String chooseCard(Model model, Long airTicketId) {
        AirTicket airTicket = airTicketService.findAirTicketById(airTicketId);
        model.addAttribute(LIST_CARDS, airTicket.getPassenger().getCards());
        model.addAttribute(AIR_TICKET, airTicket);
        return "user-views/chooseCardToReturn";
    }

    @GetMapping(value = "/scoreboard")
    @PreAuthorize("hasAuthority('Passenger')")
    public String scoreboard(Model model) {
        List<Flight> flights = directFlightService.fetchAll().stream().filter(directFlight -> directFlight.getDepartureDate().equals(LocalDate.now()) && directFlight.getDepartureCity().equals("Минск")).collect(Collectors.toList());
        flights.addAll(transitFlightService.fetchAll().stream().filter(transitFlight -> transitFlight.getDepartureDate().equals(LocalDate.now()) && transitFlight.getDepartureCity().equals("Минск")).collect(Collectors.toList()));
        List<TransitFlight> transitFlights = transitFlightService.fetchAll().stream().filter(transitFlight -> transitFlight.getTransferList().stream().filter(transfer -> transfer.getNameOfCity().equals("Минск") && transitFlight.getDepartureDate().equals(LocalDate.now())).collect(Collectors.toList()).size() != 0).collect(Collectors.toList());
        transitFlights.stream().forEach(flight -> flight.setDepartureTime(flight.getTransferList().stream().filter(transfer -> transfer.getNameOfCity().equals("Минск")).findAny().get().getArrivalTime()));
        flights.addAll(transitFlights);
        Collections.sort(flights, Comparator.comparing(Flight::getDepartureTime));
        model.addAttribute("isArrivalsBoard", Boolean.TRUE);
        model.addAttribute(LIST_FLIGHTS, flights);
        return "user-views/scoreboard";
    }

    @GetMapping(value = "/arrivalFlights")
    @PreAuthorize("hasAuthority('Passenger')")
    public String arrivalFlightsOnBoard(Model model) {
        List<Flight> flights = directFlightService.fetchAll().stream().filter(directFlight -> directFlight.getArrivalDate().equals(LocalDate.now()) && directFlight.getArrivalCity().equals("Минск")).collect(Collectors.toList());
        flights.addAll(transitFlightService.fetchAll().stream().filter(transitFlight -> transitFlight.getArrivalDate().equals(LocalDate.now()) && transitFlight.getArrivalCity().equals("Минск")).collect(Collectors.toList()));
        List<TransitFlight> transitFlights = transitFlightService.fetchAll().stream().filter(transitFlight -> transitFlight.getTransferList().stream().filter(transfer -> transfer.getNameOfCity().equals("Минск") && transitFlight.getArrivalDate().equals(LocalDate.now())).collect(Collectors.toList()).size() != 0).collect(Collectors.toList());
        transitFlights.stream().forEach(flight -> flight.setArrivalTime(flight.getTransferList().stream().filter(transfer -> transfer.getNameOfCity().equals("Минск")).findAny().get().getArrivalTime()));
        flights.addAll(transitFlights);
        Collections.sort(flights, Comparator.comparing(Flight::getArrivalTime));
        model.addAttribute("isArrivalsBoard", Boolean.FALSE);
        model.addAttribute(LIST_FLIGHTS, flights);
        return "user-views/scoreboard";
    }

    @GetMapping(value = "/rulesForPassengers")
    @PreAuthorize("hasAuthority('Passenger')")
    public String rulesForPassengers() {
        return "user-views/rulesForPassengers";
    }
}
