package com.example.course_project.service;

import com.example.course_project.entity.Airline;
import com.example.course_project.entity.Card;
import com.example.course_project.entity.Passenger;
import com.example.course_project.entity.Review;

import java.time.LocalDate;
import java.util.List;

public interface ReviewService {
    Review createReview(Long airlineId, Long passengerId, double checkInSpeed, double state_of_salon, double crew_work, double quality_of_flight_meals, double overall_score, String remark);
    Review findReviewById(Long id);
    List<Review> fetchAll();
    List<Review> fetchReviewsForAirline(Long airlineId);
    Review createOrUpdateReview(Review review, Long airlineId, Long passengerId);
    void removeReview(Long reviewId);
}
