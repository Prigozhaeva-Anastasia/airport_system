package com.example.course_project.web.rest;

import com.example.course_project.entity.Card;
import com.example.course_project.service.CardService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cards")
public class CardRestController {
    private CardService cardService;

    public CardRestController(CardService cardService) {
        this.cardService = cardService;
    }

    @GetMapping("/{cardId}")
    @PreAuthorize("hasAuthority('Passenger')")
    public Card findById(@PathVariable Long cardId) {
        return cardService.findCardById(cardId);
    }

    @GetMapping("/byNumber/{number}")
    @PreAuthorize("hasAuthority('Passenger')")
    public Card findByCardNumber(@PathVariable long number) { return cardService.findCardByCardNumber(number);}

    @PostMapping
    @PreAuthorize("hasAuthority('Passenger')")
    public Card update(@RequestBody Card card) {
        Card cardDB = cardService.findCardById(card.getId());
        cardDB.setBalance(cardDB.getBalance() + card.getBalance());
        return cardService.createOrUpdateCard(cardDB);
    }
}
