package com.example.course_project.web;

import com.example.course_project.entity.Airline;
import com.example.course_project.entity.Card;
import com.example.course_project.entity.Passenger;
import com.example.course_project.service.CardService;
import com.example.course_project.service.PassengerService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.security.Principal;

@Controller
@RequestMapping(value = "/cards")
public class CardController {
    private CardService cardService;
    private PassengerService passengerService;

    public CardController(CardService cardService, PassengerService passengerService) {
        this.cardService = cardService;
        this.passengerService = passengerService;
    }

    @PostMapping(value = "/save")
    @PreAuthorize("hasAuthority('Passenger')")
    public String save(@Valid Card card, BindingResult bindingResult, Principal principal) {
        Card cardDB = cardService.findCardByCardNumber(card.getNumber());
        Passenger passenger = passengerService.findPassengerByEmail(principal.getName());
        if (cardDB != null) bindingResult.rejectValue("number", null, "Карта с таким номером уже существует");
        if (bindingResult.hasErrors()) return "redirect:/mainForUser/cards";
        card.setPassenger(passenger);
        cardService.createOrUpdateCard(card);
        return "redirect:/mainForUser/cards";
    }
}
