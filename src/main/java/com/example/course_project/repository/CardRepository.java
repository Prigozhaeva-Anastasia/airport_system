package com.example.course_project.repository;

import com.example.course_project.entity.Card;
import com.example.course_project.entity.Passenger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CardRepository extends JpaRepository<Card, Long> {
    Card getCardByNumber(long number);
    @Query(value = "select c from Card as c where c.passenger.id=:passengerId")
    List<Card> getCardsByPassengerId(@Param("passengerId")Long passengerId);
}
