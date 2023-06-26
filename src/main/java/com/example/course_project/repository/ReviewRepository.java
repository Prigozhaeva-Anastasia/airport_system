package com.example.course_project.repository;

import com.example.course_project.entity.Passenger;
import com.example.course_project.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    @Query(value = "select r from Review as r where r.airline.id=:airlineId")
    List<Review> getReviewsByAirlineId(@Param("airlineId") Long airlineId);
}
