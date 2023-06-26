package com.example.course_project.service;

import com.example.course_project.entity.*;

import java.util.List;

public interface CardService {
    Card createCard(long number, int month, int year, double balance, Long passengerId);
    Card findCardById(Long id);
    Card findCardByCardNumber(long number);
    List<Card> fetchAll();
    List<Card> fetchCardsForPassenger(Long passengerId);
    Card createOrUpdateCard(Card card);
    void removeCard(Long cardId);
}
