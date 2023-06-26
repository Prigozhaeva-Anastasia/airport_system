package com.example.course_project.service.impl;

import com.example.course_project.entity.Card;
import com.example.course_project.entity.Passenger;
import com.example.course_project.repository.CardRepository;
import com.example.course_project.repository.PassengerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CardServiceImplTest {
    @Mock
    private CardRepository cardRepository;
    @Mock
    private PassengerRepository passengerRepository;
    @InjectMocks
    private CardServiceImpl cardService;

    @Test
    void testCreateCard() {
        Passenger passenger = new Passenger();
        passenger.setId(1L);
        when(passengerRepository.findById(any())).thenReturn(Optional.of(passenger));
        cardService.createCard(4598652312457458l, 12, 2005, 600, 1L);
        verify(cardRepository).save(any());
    }

    @Test
    void testFindCardById() {
        Card card = new Card();
        card.setId(1L);
        when(cardRepository.findById(any())).thenReturn(Optional.of(card));
        Card actualCard = cardService.findCardById(1L);
        assertEquals(card, actualCard);
    }

    @Test
    void testFindCardByCardNumber() {
        Long cardNumber = 1111111111111111l;
        cardService.findCardByCardNumber(cardNumber);
        verify(cardRepository).getCardByNumber(cardNumber);
    }

    @Test
    void testFetchAll() {
        cardService.fetchAll();
        verify(cardRepository).findAll();
    }

    @Test
    void fetchCardsForPassenger() {
        cardService.fetchCardsForPassenger(1L);
        verify(cardRepository).getCardsByPassengerId(1L);
    }

    @Test
    void testCreateOrUpdateCard() {
        Card card = new Card();
        card.setId(1L);
        cardService.createOrUpdateCard(card);
        ArgumentCaptor<Card> argumentCaptor = ArgumentCaptor.forClass(Card.class);
        verify(cardRepository).save(argumentCaptor.capture());
        Card capturedValue = argumentCaptor.getValue();
        assertEquals(card, capturedValue);
    }

    @Test
    void testRemoveCard() {
        cardService.removeCard(1L);
        verify(cardRepository).deleteById(1L);
    }
}