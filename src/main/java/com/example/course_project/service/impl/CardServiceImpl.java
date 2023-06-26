package com.example.course_project.service.impl;

import com.example.course_project.entity.Card;
import com.example.course_project.entity.Passenger;
import com.example.course_project.repository.CardRepository;
import com.example.course_project.repository.PassengerRepository;
import com.example.course_project.service.CardService;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.text.DecimalFormat;
import java.util.List;

@Service
@Transactional
public class CardServiceImpl implements CardService {
    private CardRepository cardRepository;
    private PassengerRepository passengerRepository;

    public CardServiceImpl(CardRepository cardRepository, PassengerRepository passengerRepository) {
        this.cardRepository = cardRepository;
        this.passengerRepository = passengerRepository;
    }

    @Override
    public Card createCard(long number, int month, int year, double balance, Long passengerId) {
        Passenger passenger = passengerRepository.findById(passengerId).orElseThrow(()->new EntityNotFoundException("Passenger with id " + passengerId + " Not Found"));
        return cardRepository.save(new Card(number, month, year, balance, passenger));
    }

    @Override
    public Card findCardById(Long id) {
        return cardRepository.findById(id).orElseThrow(()->new EntityNotFoundException("Card with id " + id + " Not Found"));
    }

    @Override
    public Card findCardByCardNumber(long number) {
        return cardRepository.getCardByNumber(number);
    }

    @Override
    public List<Card> fetchAll() {
        return cardRepository.findAll();
    }

    @Override
    public List<Card> fetchCardsForPassenger(Long passengerId) {
        return cardRepository.getCardsByPassengerId(passengerId);
    }

    @Override
    public Card createOrUpdateCard(Card card) {
        if (card.getId() == null) {
            String formattedDouble = new DecimalFormat("#0.00").format(300 + Math.random() * 1500).replace(',', '.');
            double balance = Double.parseDouble(formattedDouble);
            card.setBalance(balance);
        }
        return cardRepository.save(card);
    }

    @Override
    public void removeCard(Long cardId) {
        cardRepository.deleteById(cardId);
    }
}